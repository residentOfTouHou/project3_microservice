package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 杨盛
 * @date 2019/11/28 22:00
 */

@Data
public class CinemaInfoVO implements Serializable {

    private static final long serialVersionUID = -4156914417945889598L;

    Integer cinemaId;

    String cinemaName;

    String cinemaPhone;

    String cinemaAdress;

    String imgUrl;
}
