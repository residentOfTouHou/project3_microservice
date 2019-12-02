package com.stylefeng.guns.rest.common.persistence.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImgVO implements Serializable {
    private static final long serialVersionUID = 5199201121366557901L;
    String img01;
    String img02;
    String img03;
    String img04;
    String mainImg;
}
