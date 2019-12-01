package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class YearInfoVO implements Serializable {

    private static final long serialVersionUID = -809076577245178799L;
    private String yearId;

    private String yearName;

    private Boolean active;
}
