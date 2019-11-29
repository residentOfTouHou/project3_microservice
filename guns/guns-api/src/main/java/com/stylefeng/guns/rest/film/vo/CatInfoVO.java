package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class CatInfoVO implements Serializable {
    private static final long serialVersionUID = -1468915636508569043L;

    private String catId;

    private String catName;

    private Boolean isActive;
}
