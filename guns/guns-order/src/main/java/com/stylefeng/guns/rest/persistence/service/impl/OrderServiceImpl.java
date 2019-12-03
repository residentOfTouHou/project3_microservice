package com.stylefeng.guns.rest.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.order.vo.OrderVo;
import com.stylefeng.guns.rest.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.rest.persistence.dao.MtimeUserTMapper;
import com.stylefeng.guns.rest.persistence.model.MoocOrderT;
import com.stylefeng.guns.rest.persistence.model.MtimeUserT;
import com.stylefeng.guns.rest.persistence.utils.OrderStatusUtil;
import com.stylefeng.guns.rest.vo.GunsVo;
import com.stylefeng.guns.rest.vo.OrderVo3;
import com.stylefeng.guns.rest.vo.OrderVo2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stylefeng.guns.rest.order.vo.SeatBean;
import com.stylefeng.guns.rest.persistence.dao.*;
import com.stylefeng.guns.rest.persistence.model.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    MoocOrderTMapper moocOrderTMapper;
    @Autowired
    MtimeUserTMapper mtimeUserTMapper;

    @Autowired
    MtimeFieldTMapper mtimeFieldTMapper;

    @Autowired
    MtimeHallDictTMapper mtimeHallDictTMapper;

    @Autowired
    MtimeFilmTMapper mtimeFilmTMapper;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 验证售出的票是否为真
     * @return
     */
    @Override
    public Integer isTrueSeats(String fieldId, String soldSeats)  {
        MtimeFieldT mtimeFieldT = mtimeFieldTMapper.selectById(fieldId);
        Integer hallId = mtimeFieldT.getHallId();
        MtimeHallDictT mtimeHallDictT = mtimeHallDictTMapper.selectById(hallId);
        String seatAddress = mtimeHallDictT.getSeatAddress();

        //检查redis中有无json可以使用
        SeatBean seatBean = checkRedisSeatJson(seatAddress);

        String[] split = soldSeats.split(",");
        if(split.length > seatBean.getLimit()){
            return -9;
        }
        Integer contains = 0;
        for (String s : split) {
            if(!seatBean.getIds().contains(s)){
                contains = -1;
                break;
            }
        }
        return contains;
    }

    /**
     * 验证是否卖出
     */
    @Override
    public boolean isNotSoldSeats(String fieldId, String seats) {
        EntityWrapper<MoocOrderT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("field_id",fieldId);
        List<MoocOrderT> moocOrderTS = moocOrderTMapper.selectList(entityWrapper);
        StringBuffer buffer = new StringBuffer();
        for (MoocOrderT moocOrderT : moocOrderTS) {
            buffer.append(moocOrderT.getSeatsIds());
        }
        String seatBuyed = buffer.toString();
        String[] split = seats.split(",");
        boolean result = false;
        for (String s : split) {
            if(seatBuyed.contains(s)){
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 创建订单
     */
    @Override
    public OrderVo saveOrderInfo(String filedId, String soldSeats, String seatsName, String userName) {
        MoocOrderT moocOrderT = new MoocOrderT();
        MtimeFieldT mtimeFieldT = mtimeFieldTMapper.selectById(filedId);
        moocOrderT.setCinemaId(mtimeFieldT.getCinemaId());
        moocOrderT.setFieldId(Integer.valueOf(filedId));
        moocOrderT.setFilmId(mtimeFieldT.getFilmId());
        moocOrderT.setSeatsIds(soldSeats);
        String[] split = soldSeats.split(",");
//        //根据ids判断
//        StringBuffer buffer = new StringBuffer();
//        Integer hallId = mtimeFieldT.getHallId();
//        MtimeHallDictT mtimeHallDictT = mtimeHallDictTMapper.selectById(hallId);
//        String seatAddress = mtimeHallDictT.getSeatAddress();
//        SeatBean seatBean = checkRedisSeatJson(seatAddress);
//        seatBean.getSingle();
//        for (String s : split) {
//
//        }
        moocOrderT.setSeatsName(seatsName);
        moocOrderT.setFilmPrice(Double.valueOf(mtimeFieldT.getPrice()));
        double orderPrice = Double.valueOf(mtimeFieldT.getPrice()) * split.length;
        moocOrderT.setOrderPrice(orderPrice);
        Date orderTime = new Date();
        moocOrderT.setOrderTime(orderTime);


        List<MtimeUserT> user_name = mtimeUserTMapper.selectList(new EntityWrapper<MtimeUserT>().eq("user_name", userName));
        Integer userId = user_name.get(0).getUuid();
        moocOrderT.setOrderUser(userId);
        moocOrderT.setOrderStatus(0);
        //可以考虑使用insert内嵌selectKey
        moocOrderTMapper.insert(moocOrderT);
        //缺少唯一标识
        List<MoocOrderT> moocOrderTS = moocOrderTMapper.selectList(new EntityWrapper<MoocOrderT>().eq("seats_ids", soldSeats)
                .eq("order_user", userId).eq("order_status",0));
        String orderID = moocOrderTS.get(0).getUuid();
        Integer orderId = Integer.valueOf(orderID);

        OrderVo orderVo = new OrderVo();
        orderVo.setOrderId(orderId);
        MtimeFilmT mtimeFilmT = mtimeFilmTMapper.selectById(mtimeFieldT.getFilmId());
        orderVo.setFilmName(mtimeFilmT.getFilmName());
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        orderVo.setFieldTime(format + mtimeFieldT.getBeginTime());
        orderVo.setCinemaName(String.valueOf(mtimeFieldT.getCinemaId()));
        orderVo.setSeatsName(seatsName);
        orderVo.setOrderPrice(String.valueOf(orderPrice));
        orderVo.setOrderTimestamp(String.valueOf(orderTime.getTime()/1000)); //秒级时间戳
        return orderVo;
    }

    private SeatBean checkRedisSeatJson(String seatAddress) {
        ObjectMapper objectMapper = new ObjectMapper();
        SeatBean seatBean = new SeatBean();
        String redisSeatJson = (String) redisTemplate.opsForValue().get(seatAddress);
        try {
            if(redisSeatJson==null){
                File file = null;
                file = new File(ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX)+"\\"+seatAddress);
                seatBean = objectMapper.readValue(file,SeatBean.class);
                String json = objectMapper.readValue(file, String.class);
                redisTemplate.opsForValue().set(seatAddress,json);
                redisTemplate.expire(seatAddress,60, TimeUnit.SECONDS);
            }else {
                seatBean = objectMapper.readValue(redisSeatJson,SeatBean.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return seatBean;
    }

    /**
     * 获取当前用户的订单信息
     * @param username
     * @param nowPage
     * @param pageSize
     * @return
     */
    @Override
    public GunsVo getOrderInfoByUserName(String username, Integer nowPage, Integer pageSize) {
        //分页
        PageHelper.startPage(nowPage,pageSize);
        //拿到userId
        try{
            List<MtimeUserT> userTList = mtimeUserTMapper.selectList(new EntityWrapper<MtimeUserT>().eq("user_name", username));
            Integer userId = 1;
            if (!CollectionUtils.isEmpty(userTList)){
                userId = userTList.get(0).getUuid();
            }
            //查询到需要的数据
            List<OrderVo3> orderVoList = moocOrderTMapper.selectOrderInfoByUserId(userId);
            List<OrderVo2> orderVoList2 = moocOrderTMapper.selectOrderInfoByUserId2(userId);
            for(int x=0;x<orderVoList.size();x++){
                String s2 = String.valueOf(orderVoList.get(x).getOrderId());
                orderVoList2.get(x).setOrderId(s2);
                //转换时间戳毫秒格式
                long time = (orderVoList.get(x).getOrderTimestamp().getTime()) / 1000;
                String s = String.valueOf(time);
                orderVoList2.get(x).setOrderTimestamp(s);
                //转换订单状态格式
                String s1 = OrderStatusUtil.int2String(orderVoList.get(x).getOrderStatus());
                orderVoList2.get(x).setOrderStatus(s1);
                //电影开场时间数据库没有年限 自己拼一个
                String fieldTime = orderVoList.get(x).getFieldTime();
                String str = "19年12月2日 " + fieldTime;
                orderVoList2.get(x).setFieldTime(str);
                //返回响应报文
                GunsVo gunsVo = new GunsVo();
                PageInfo<OrderVo2> orderVo2PageInfo = new PageInfo<>(orderVoList2);
                if(nowPage >0 && (orderVo2PageInfo.getTotal() / pageSize < nowPage - 1) ){
                    return new GunsVo(1,"订单列表为空哦！~");
                }else {
                    gunsVo.setStatus(0);
                    gunsVo.setData(orderVo2PageInfo.getList());
                    return gunsVo;
                }
            }
        }catch (Exception e){
            return new GunsVo(999,"系统出现异常，请联系管理员");
        }
        return null;
    }

    /**
     * 获取已经售出的座位号
     * @param fileId
     * @return 返回已经售出的座位号（字符串格式）
     */
    @Override
    public String getSoldSeatsByFileId(Integer fileId) {
        Wrapper<MoocOrderT> field_id1 = new EntityWrapper<MoocOrderT>().eq("field_id", fileId);
        List<MoocOrderT> field_id = moocOrderTMapper.selectList(field_id1);
        StringBuilder str = new StringBuilder();
        for (MoocOrderT moocOrderT : field_id) {
            str.append(moocOrderT.getSeatsIds());
        }
        String s = str.toString();
        return s;
    }
}
