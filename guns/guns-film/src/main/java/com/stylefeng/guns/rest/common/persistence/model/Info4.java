package com.stylefeng.guns.rest.common.persistence.model;

import lombok.Data;

import java.io.Serializable;


@Data
public class Info4 implements Serializable {
    private static final long serialVersionUID = -524825176943056530L;
    private ActorsAndDirector actors;
    String biopgraphy;
    Integer filmId;
    ImgVO imgVO;
}
