package com.stylefeng.guns.rest.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderVo3 implements Serializable {
    private static final long serialVersionUID = 5049619968964199180L;
    /**
     * orderId : 479
     * seatsName : 16
     * cinemaName : 大地影院(顺义店)
     * filmName : 跳舞吧大象
     * orderStatus : 未支付
     * orderPrice : 60.0
     * orderTimestamp : 1575204851
     * fieldTime : 19年12月01日 11:50
     */
    private Integer orderId;
    private String seatsName;
    private String cinemaName;
    private String filmName;
    private Integer orderStatus;
    private String orderPrice;
    private Date orderTimestamp;
    private String fieldTime;

}
