package com.stylefeng.guns.rest.utils;

import lombok.Data;

/**
 * @author 杨盛
 * @date 2019/12/2 15:04
 */

@Data
public class FailedBaseReqVO {

    int status;

    String msg;

    public static FailedBaseReqVO fail(int code, String message) {
        FailedBaseReqVO failedBaseReqVO = new FailedBaseReqVO();
        failedBaseReqVO.setStatus(code);
        failedBaseReqVO.setMsg(message);
        return failedBaseReqVO;
    }
}

