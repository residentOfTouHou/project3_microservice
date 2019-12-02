package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.film.FilmCatServiceAPI;
import com.stylefeng.guns.rest.film.FilmService;
import com.stylefeng.guns.rest.film.FilmServiceAPI;
import com.stylefeng.guns.rest.film.vo.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/film")
public class FilmController {

    @Reference(interfaceClass = FilmServiceAPI.class, check = false)
    private FilmServiceAPI filmServiceAPI;

    @Reference(interfaceClass = FilmCatServiceAPI.class, check = false)
    private FilmCatServiceAPI filmCatServiceAPI;

    @Reference(interfaceClass = FilmService.class,check = false)
    private FilmService filmService;

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
    @Transactional
    public Map<String,Object> getConditionList(String catId, String sourceId, String yearId) {
        FilmConditionVO filmConditionVO = new FilmConditionVO();
        Map<String,Object> map = new HashMap<>();
            List<CatInfoVO> catsInfo = filmCatServiceAPI.getCatsInfo();
            List<SourceInfoVO> sourceInfo = filmCatServiceAPI.getSourceInfo();
            List<YearInfoVO> yearInfo = filmCatServiceAPI.getYearInfo();

            filmConditionVO.setCatInfo(catsInfo);
            filmConditionVO.setSourceInfo(sourceInfo);
            filmConditionVO.setYearInfo(yearInfo);
            map.put("data",filmConditionVO);
            map.put("status",0);
        return map;

    }

    @RequestMapping(value = "/getFilms", method = RequestMethod.GET)
    public Map<String,Object> getFilms(FilmRequestVO filmRequestVO){
        List<FilmsVo> filmsVos = filmService.getFilms(filmRequestVO);
        Map<String,Object> map = new HashMap<>();
        map.put("data",filmsVos);
        map.put("imgPre","http://img.meetingshop.cn/");
        map.put("status",0);
        map.put("nowPage",filmRequestVO.getOffset()+1);
        map.put("totalPage",(filmRequestVO.getOffset()+1)*filmsVos.size());
        return map;
    }

    @RequestMapping(value = "/films/{filmId}", method = RequestMethod.GET)
    public Map<String,Object> getspecficFilm(@PathVariable("filmId") Integer filmId,Integer searchType){
        return filmService.getSpecificFilmInfo(filmId);

}



}
