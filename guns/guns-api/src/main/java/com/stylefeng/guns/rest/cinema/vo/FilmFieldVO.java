package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 杨盛
 * @date 2019/11/28 22:22
 */

@Data
public class FilmFieldVO implements Serializable {

    private static final long serialVersionUID = -8486999419228170411L;

    Integer fieldId;

    String beginTime;

    String endTime;

    String hallName;

    String language;

    Integer price;
}

