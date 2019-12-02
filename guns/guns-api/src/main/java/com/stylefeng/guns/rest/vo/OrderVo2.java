package com.stylefeng.guns.rest.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderVo2 implements Serializable {
    private static final long serialVersionUID = -3122390091767348188L;
    private String orderId;
    private String seatsName;
    private String cinemaName;
    private String filmName;
    private String orderStatus;
    private String orderPrice;
    private String orderTimestamp;
    private String fieldTime;
}
