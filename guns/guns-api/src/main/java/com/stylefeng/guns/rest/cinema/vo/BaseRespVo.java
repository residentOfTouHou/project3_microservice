package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/11/27
 * @time 22:01
 */
@Data
public class BaseRespVo implements Serializable {
    private static final long serialVersionUID = -3692431370139291593L;

    private Object data;

    private Integer status;
}
