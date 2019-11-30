package com.stylefeng.guns.rest.film.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author yuanshushu
 * @date 2018/8/29
 * @description 返回结果VO对象
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseVO<T> implements Serializable {

    private static final long serialVersionUID = -3088712101495884310L;
    @ApiModelProperty("状态码 0成功 1失败 999系统异常")
    private Integer status;

    @ApiModelProperty("返回信息")
    private String imgPre;

    @ApiModelProperty("返回数据")
    private T data;

    public ResponseVO() {
    }

    public ResponseVO(Integer code, String msg) {
        this.status = code;
        this.imgPre = msg;
    }

    public ResponseVO(Integer code, String msg, T data) {
        this.status = code;
        this.imgPre = msg;
        this.data = data;
    }

    public ResponseVO(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    /**
     * 请求成功  状态码 0
     *
     * @param msg 返回信息
     * @param <T> 类型
     * @return ResponseVO
     */
    public static <T> ResponseVO getSuccess(String msg) {
        return new ResponseVO(0, msg);
    }

    public static <T> ResponseVO getSuccess(T data) {
        return new ResponseVO(0, data);
    }

    /**
     * 请求成功  状态码 0
     *
     * @param msg  返回信息
     * @param data 返回对象
     * @param <T>  类型
     * @return ResponseVO
     */
    public static <T> ResponseVO getSuccess(String msg, T data) {
        return new ResponseVO(0, msg, data);
    }

    /**
     * 请求失败   状态码 1
     *
     * @param msg 返回信息
     * @param <T> 类型
     * @return ResponseVO
     */
    public static <T> ResponseVO getFailed(String msg) {
        return new ResponseVO(1, msg);
    }

    /**
     * 请求失败  状态 1
     *
     * @param msg  返回信息
     * @param data 返回数据
     * @param <T>  类型
     * @return ResponseVO
     */
    public static <T> ResponseVO getFailed(String msg, T data) {
        return new ResponseVO(1, msg, data);
    }


    /**
     * 系统异常
     *
     * @param <T> 类型
     * @return ResponseVO
     */
    public static <T> ResponseVO getException(String msg) {
        return new ResponseVO(999, msg);
    }





    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImgPre() {
        return imgPre;
    }

    public void setImgPre(String imgPre) {
        this.imgPre = imgPre;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}