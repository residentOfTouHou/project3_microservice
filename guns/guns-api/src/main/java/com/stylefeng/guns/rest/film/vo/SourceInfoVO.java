package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SourceInfoVO implements Serializable {


    private static final long serialVersionUID = -3716873131869819906L;
    private String sourceId;

    private String sourceName;

    private Boolean active;
}
