package com.stylefeng.guns.rest.common;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/12/1
 * @time 23:21
 */

/**
 * 返回体状态的枚举
 */
public enum RespEnum {
    /**
     * 正常返回
     */
    NORMAL_RESP(0, ""),

    /**
     * 订单校验座位已经被购买
     */
    SEAT_BUYED(1, "该座位已被购买"),

    SEAT_ERROR(99, "抱歉出了一点小问题，座位码有误"),

    SEAT_LIMIT(100, "单个用户一次只能买5张"),

    TOKEN_OUTTIME(1001, "抱歉 您没有登陆，点击确定跳转到登录页面"),

    PROMO_NULL(1099, "暂时没有秒杀活动，敬请期待");

    private Integer status;

    private String msg;

    RespEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
