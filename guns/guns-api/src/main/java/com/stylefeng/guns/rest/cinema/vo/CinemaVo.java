package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/11/28
 * @time 20:53
 */

/**
 * getCinemas Resp的内嵌bean
 */
@Data
public class CinemaVo implements Serializable {
    private static final long serialVersionUID = -5895750649240237059L;

    private Integer uuid;

    private String cinemaName;

    private String cinemaAddress;

    private Integer minimumPrice;

}
