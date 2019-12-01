package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FilmInfoVO implements Serializable {
    private static final long serialVersionUID = 2701353979775435812L;

    private String filmId;

    private Integer filmType;

    private String imgAddress;

    private String filmName;

    private String filmScore;

    private Integer expectNum;

    private String showTime;

    private String score;

    private Integer boxNum;


}
