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

    private int status;

    private String totalPage;

    public static OrderBaseReqVO success(String imgPre) {
        OrderBaseReqVO failedBaseReqVO = new OrderBaseReqVO();
        failedBaseReqVO.setImgPre(imgPre);
        failedBaseReqVO.setMsg("");
        failedBaseReqVO.setNowPage("");
        failedBaseReqVO.setStatus(0);
        failedBaseReqVO.setTotalPage("");
        return failedBaseReqVO;
    }

    public static OrderBaseReqVO success(Object data, String imgPre) {
        OrderBaseReqVO failedBaseReqVO = new OrderBaseReqVO();
        failedBaseReqVO.setImgPre(imgPre);
        failedBaseReqVO.setMsg("");
        failedBaseReqVO.setNowPage("");
        failedBaseReqVO.setStatus(0);
        failedBaseReqVO.setTotalPage("");
        failedBaseReqVO.setData(data);
        return failedBaseReqVO;
    }

    public static OrderBaseReqVO fail(int status, String message) {
        OrderBaseReqVO failedBaseReqVO = new OrderBaseReqVO();
        failedBaseReqVO.setStatus(status);
        failedBaseReqVO.setMsg(message);
        return failedBaseReqVO;
    }
}

