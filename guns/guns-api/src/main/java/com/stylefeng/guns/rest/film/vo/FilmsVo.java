package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class FilmsVo implements Serializable {
    private static final long serialVersionUID = 7133040108022693747L;
    Integer filmId;
    String filmName;
    String filmScore;
    Integer filmType;
    String imgAddress;

}
