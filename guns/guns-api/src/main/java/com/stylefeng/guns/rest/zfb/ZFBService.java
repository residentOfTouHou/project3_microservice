package com.stylefeng.guns.rest.zfb;

import com.stylefeng.guns.rest.order.vo.OrderVo;

public interface ZFBService {
    //BaseVo getPayResult(String orderId, Integer tryNums);

    String generateQRCode(String orderId, String amount);

    OrderVo isPay(int orderId);

    int updateOrderStatusById(int orderId);
}