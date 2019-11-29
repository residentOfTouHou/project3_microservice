package com.stylefeng.guns.rest.film;

import com.stylefeng.guns.rest.film.vo.BannerVO;
import com.stylefeng.guns.rest.film.vo.FilmInfoVO;
import com.stylefeng.guns.rest.film.vo.FilmVO;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/11/27
 * @time 17:35
 */
public interface FilmServiceAPI {

    List<BannerVO> getBanners();

    FilmVO getHotFilm(Integer showNum, Boolean showAll);

    FilmVO getSoonFilm(Integer showNum, Boolean showAll);

    List<FilmInfoVO> getBoxRanking(Integer showNum, Boolean showAll);

    List<FilmInfoVO> getExpectRanking(Integer showNum, Boolean showAll);

    List<FilmInfoVO> getTop100(Integer showNum, Boolean showAll);
}
