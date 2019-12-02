package com.stylefeng.guns.rest.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.rest.persistence.dao.MtimeUserTMapper;
import com.stylefeng.guns.rest.persistence.model.MoocOrderT;
import com.stylefeng.guns.rest.persistence.model.MtimeUserT;
import com.stylefeng.guns.rest.persistence.utils.OrderStatusUtil;
import com.stylefeng.guns.rest.vo.GunsVo;
import com.stylefeng.guns.rest.vo.OrderVo;
import com.stylefeng.guns.rest.vo.OrderVo2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    MoocOrderTMapper moocOrderTMapper;
    @Autowired
    MtimeUserTMapper mtimeUserTMapper;

    @Override
    public boolean isTrueSeats(String fieldId, String seats) {
        return false;
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
            List<OrderVo> orderVoList = moocOrderTMapper.selectOrderInfoByUserId(userId);
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
