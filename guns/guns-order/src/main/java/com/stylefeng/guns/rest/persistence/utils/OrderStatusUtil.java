package com.stylefeng.guns.rest.persistence.utils;

/**
 * 订单状态转换工具类
 */
public class OrderStatusUtil {
    /**
     * 从数字转换为汉字
     */
    public static String int2String(int x){
        if(x == 0){
            return "未支付";
        }else if(x == 1){
            return "已支付";
        }else if(x == 2){
            return "已关闭";
        }else {
            return null;
        }
    }


}
