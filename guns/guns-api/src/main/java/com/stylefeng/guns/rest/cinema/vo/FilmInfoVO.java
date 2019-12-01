package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 杨盛
 * @date 2019/11/28 22:15
 */

@Data
public class FilmInfoVO implements Serializable {

    private static final long serialVersionUID = -4737088342724437275L;

    Integer filmId;

    String actors;

    String filmCats;

    String filmLength;

    String filmName;

    String filmType;

    String imgAddress;

    List<FilmFieldVO> filmFields;
}