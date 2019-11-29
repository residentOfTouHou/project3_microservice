package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/11/28
 * @time 22:19
 */
@Data
public class AreaVo implements Serializable {
    private static final long serialVersionUID = -1541575715001392539L;

    private Integer areaId;

    private String areaName;

    private boolean active;
}
