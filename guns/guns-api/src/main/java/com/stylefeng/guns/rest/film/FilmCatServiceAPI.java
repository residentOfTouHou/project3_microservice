package com.stylefeng.guns.rest.film;

import com.stylefeng.guns.rest.film.vo.CatInfoVO;
import com.stylefeng.guns.rest.film.vo.SourceInfoVO;
import com.stylefeng.guns.rest.film.vo.YearInfoVO;

import java.util.List;

public interface FilmCatServiceAPI {
    List<CatInfoVO> getCatsInfo();

    List<SourceInfoVO> getSourceInfo();

    List<YearInfoVO> getYearInfo();
}
