package com.stylefeng.guns.rest.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.order.vo.OrderVo;
import com.stylefeng.guns.rest.order.vo.SeatBean;
import com.stylefeng.guns.rest.persistence.dao.*;
import com.stylefeng.guns.rest.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    MtimeFieldTMapper mtimeFieldTMapper;

    @Autowired
    MtimeHallDictTMapper mtimeHallDictTMapper;

    @Autowired
    MoocOrderTMapper moocOrderTMapper;

    @Autowired
    MtimeUserTMapper mtimeUserTMapper;

    @Autowired
    MtimeFilmTMapper mtimeFilmTMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    JwtProperties jwtProperties;

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
    public OrderVo saveOrderInfo(String filedId, String soldSeats, String seatsName, HttpServletRequest request) {
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

        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
        }
        String userName = (String) redisTemplate.opsForValue().get(authToken);
        List<MtimeUserT> user_name = mtimeUserTMapper.selectList(new EntityWrapper<MtimeUserT>().eq("user_name", userName));
        Integer uuid = user_name.get(0).getUuid();
        moocOrderT.setOrderUser(uuid);
        Integer insert = moocOrderTMapper.insert(moocOrderT);
        Integer orderId = moocOrderTMapper.getLastInsertId();

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
}
