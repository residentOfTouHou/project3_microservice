package com.stylefeng.guns.rest.film.vo;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class FilmVO implements Serializable {

    private static final long serialVersionUID = 3488947045265036650L;

    private Integer filmNum;

    private List<FilmInfoVO> filmInfo;
}
