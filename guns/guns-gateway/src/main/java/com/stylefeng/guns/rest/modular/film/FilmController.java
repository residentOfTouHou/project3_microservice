package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.film.FilmCatServiceAPI;
import com.stylefeng.guns.rest.film.FilmServiceAPI;
import com.stylefeng.guns.rest.film.vo.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/film")
public class FilmController {

    @Reference(interfaceClass = FilmServiceAPI.class, check = false)
    private FilmServiceAPI filmServiceAPI;

    @Reference(interfaceClass = FilmCatServiceAPI.class, check = false)
    private FilmCatServiceAPI filmCatServiceAPI;

    @RequestMapping(value = "/getIndex", method = RequestMethod.GET)
    public ResponseVO getIndex() {
        IndexVO indexVO = new IndexVO();

        try {
            List<BannerVO> banners = filmServiceAPI.getBanners();
            FilmVO hotFilm = filmServiceAPI.getHotFilm(8, false);
            FilmVO soonFilm = filmServiceAPI.getSoonFilm(8, false);
            List<FilmInfoVO> boxRanking = filmServiceAPI.getBoxRanking(10, false);
            List<FilmInfoVO> expectRanking = filmServiceAPI.getExpectRanking(10, false);
            List<FilmInfoVO> top100 = filmServiceAPI.getTop100(10, false);

            indexVO.setBanners(banners);
            indexVO.setHotFilms(hotFilm);
            indexVO.setSoonFilms(soonFilm);
            indexVO.setBoxRanking(boxRanking);
            indexVO.setExpectRanking(expectRanking);
            indexVO.setTop100(top100);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.getFailed("查询失败");
        }

        return ResponseVO.getSuccess("http://img.meetingshop.cn", indexVO);
    }

    @RequestMapping(value = "/getConditionList", method = RequestMethod.GET)
    public ResponseVO getConditionList(String catId, String sourceId, String yearId) {
        FilmConditionVO filmConditionVO = new FilmConditionVO();

        try {
            List<CatInfoVO> catsInfo = filmCatServiceAPI.getCatsInfo();
            List<SourceInfoVO> sourceInfo = filmCatServiceAPI.getSourceInfo();
            List<YearInfoVO> yearInfo = filmCatServiceAPI.getYearInfo();

            filmConditionVO.setCatInfo(catsInfo);
            filmConditionVO.setSourceInfo(sourceInfo);
            filmConditionVO.setYearInfo(yearInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.getFailed("查询失败");
        }
        return ResponseVO.getSuccess(filmConditionVO);

    }
}
