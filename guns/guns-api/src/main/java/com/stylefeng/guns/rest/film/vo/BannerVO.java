package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BannerVO implements Serializable {

    private static final long serialVersionUID = -4483617030492366654L;

    private String bannerId;

    private String bannerAddress;

    private String bannerUrl;
}
