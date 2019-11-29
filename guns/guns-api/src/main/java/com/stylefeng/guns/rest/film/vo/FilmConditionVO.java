package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class FilmConditionVO implements Serializable {

    private static final long serialVersionUID = -5647795825183243537L;

    private List<CatInfoVO> catInfo;

    private List<SourceInfoVO> sourceInfo;

    private List<YearInfoVO> yearInfo;
}
