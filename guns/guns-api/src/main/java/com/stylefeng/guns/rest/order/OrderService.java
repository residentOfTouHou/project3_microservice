package com.stylefeng.guns.rest.order;

import com.alibaba.dubbo.container.page.Page;
import com.stylefeng.guns.rest.vo.GunsVo;
import com.stylefeng.guns.rest.vo.OrderVo;

public interface OrderService {

    //验证售出的票是否为真
    boolean isTrueSeats(String fieldId,String seats);

    //已经销售的座位里检查有没有这些座位（防止卖重）
    //boolean isNotSoldSeats(String fieldId,String seats);

    //创建订单信息
    //OrderVo saveOrderInfo(Integer filedId,String soldSeats,String seatsName,Integer userId);

    //获取当前用户的订单信息
    GunsVo getOrderInfoByUserName(String username, Integer nowPage, Integer pageSize);

    //根据FieldId获取所有已经销售的座位编号(给影院服务提供的接口)
    String getSoldSeatsByFileId(Integer fileId);
}
