package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmRequestVO implements Serializable{

    private static final long serialVersionUID = 35635438346185853L;
    private Integer showType=1;
    private Integer sortId=1;
    private Integer catId=99;
    private Integer sourceId=99;
    private Integer yearId=99;
    private Integer offset=1;
    private Integer pageSize=18;
}
