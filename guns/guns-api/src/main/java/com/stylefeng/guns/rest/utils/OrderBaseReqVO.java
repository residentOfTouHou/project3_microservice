package com.stylefeng.guns.rest.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 杨盛
 * @date 2019/12/2 15:04
 */

@Data
public class OrderBaseReqVO<T> implements Serializable {

    private T data;

    private String imgPre;

    private String msg;

    private String nowPage;

    private int Status;

    private int orderStatus;

    private String totalPage;

    public static OrderBaseReqVO success(String imgPre) {
        OrderBaseReqVO orderBaseReqVO = new OrderBaseReqVO();
        orderBaseReqVO.setImgPre(imgPre);
        orderBaseReqVO.setMsg("");
        orderBaseReqVO.setStatus(0);
        orderBaseReqVO.setNowPage("");
        orderBaseReqVO.setTotalPage("");
        return orderBaseReqVO;
    }

    public static OrderBaseReqVO success(Object data, String imgPre) {
        OrderBaseReqVO orderBaseReqVO = new OrderBaseReqVO();
        orderBaseReqVO.setImgPre(imgPre);
        orderBaseReqVO.setMsg("");
        orderBaseReqVO.setNowPage("");
        orderBaseReqVO.setStatus(0);
        orderBaseReqVO.setOrderStatus(1);
        orderBaseReqVO.setTotalPage("");
        orderBaseReqVO.setData(data);
        return orderBaseReqVO;
    }

    public static OrderBaseReqVO fail(int orderStatus, String message) {
        OrderBaseReqVO orderBaseReqVO = new OrderBaseReqVO();
        orderBaseReqVO.setStatus(1);
        orderBaseReqVO.setOrderStatus(orderStatus);
        orderBaseReqVO.setMsg(message);
        return orderBaseReqVO;
    }

    public static OrderBaseReqVO fail(Object data, String message) {
        OrderBaseReqVO orderBaseReqVO = new OrderBaseReqVO();
        orderBaseReqVO.setStatus(1);
        orderBaseReqVO.setData(data);
        orderBaseReqVO.setMsg(message);
        return orderBaseReqVO;
    }
}

