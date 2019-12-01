package com.stylefeng.guns.rest.film;


import com.stylefeng.guns.rest.film.vo.FilmRequestVO;
import com.stylefeng.guns.rest.film.vo.FilmsVo;
import com.stylefeng.guns.rest.film.vo.SpecificFilmVo;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/11/27
 * @time 17:35
 */
public interface FilmService {

    List<FilmsVo> getHotFilms(FilmRequestVO filmRequestVO);

    List<FilmsVo> getSoonFilms(FilmRequestVO filmRequestVO);

    List<FilmsVo> getClassicalFilms(FilmRequestVO filmRequestVO);

    Map<String,Object> getSpecificFilmInfo(Integer filmId);
}
