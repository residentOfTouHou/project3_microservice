package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.rest.common.persistence.dao.ActorMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeFilmInfoTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeFilmTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeHallFilmInfoTMapper;
import com.stylefeng.guns.rest.common.persistence.model.*;
import com.stylefeng.guns.rest.film.FilmService;
import com.stylefeng.guns.rest.film.vo.FilmRequestVO;
import com.stylefeng.guns.rest.film.vo.FilmsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
@Component
@Service(interfaceClass = FilmService.class)
public class FilmServiceImpl  implements FilmService {

    @Autowired
    MtimeFilmTMapper mtimeFilmTMapper;

    @Autowired
    MtimeFilmInfoTMapper mtimeFilmInfoTMapper;

    @Autowired
    ActorMapper actorMapper;

    @Autowired
    MtimeHallFilmInfoTMapper mtimeHallFilmInfoTMapper;

    @Override
    public List<FilmsVo> getFilms(FilmRequestVO filmRequestVO) {
        EntityWrapper<MtimeFilmT> filmsVoEntityWrapper = new EntityWrapper<>();
        filmsVoEntityWrapper.eq("film_status",filmRequestVO.getShowType());
        if(filmRequestVO.getSourceId()!=99) {
            filmsVoEntityWrapper.eq("film_source", filmRequestVO.getSourceId());
        }
        if(filmRequestVO.getCatId()!=99) {
            String filmCats = String.valueOf(filmRequestVO.getCatId());
            filmsVoEntityWrapper.like("film_cats",filmCats);
        }
        if(filmRequestVO.getYearId()!=99) {
            filmsVoEntityWrapper.eq("film_date", filmRequestVO.getYearId());
        }
        Page<MtimeFilmT> mtimeFilmTPage = new Page<>(filmRequestVO.getOffset(),filmRequestVO.getPageSize());
        List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectPage(mtimeFilmTPage,filmsVoEntityWrapper);
        List<FilmsVo> filmsVos = new ArrayList<>();
        for (MtimeFilmT mtimeFilmT : mtimeFilmTS) {
            FilmsVo filmsVo = new FilmsVo();
            BeanUtils.copyProperties(mtimeFilmT,filmsVo);
            filmsVo.setFilmId(mtimeFilmT.getUuid());
            filmsVos.add(filmsVo);
        }
        return filmsVos;
    }


    @Override
    @Transactional
    public Map<String, Object> getSpecificFilmInfo(Integer filmId) {
        //影片信息
        MtimeFilmT mtimeFilmT = mtimeFilmTMapper.selectById(filmId);
        MtimeFilmInfoT mtimeFilmInfoTQuery = new MtimeFilmInfoT();
        mtimeFilmInfoTQuery.setFilmId(String.valueOf(filmId));
        MtimeFilmInfoT mtimeFilmInfoT = mtimeFilmInfoTMapper.selectOne(mtimeFilmInfoTQuery);
        Map<String,Object> map = new HashMap<>();
        map.put("filmEnName",mtimeFilmInfoT.getFilmEnName());
        map.put("filmId",filmId);
        map.put("filmName",mtimeFilmT.getFilmName());
        map.put("imgAddress",mtimeFilmT.getImgAddress());
        MtimeHallFilmInfoT mtimeHallFilmInfoTQuery = new MtimeHallFilmInfoT();
        mtimeHallFilmInfoTQuery.setFilmId(filmId);
        MtimeHallFilmInfoT mtimeHallFilmInfoT = mtimeHallFilmInfoTMapper.selectOne(mtimeHallFilmInfoTQuery);
        map.put("info01",mtimeHallFilmInfoT.getFilmCats());
        map.put("info02",mtimeHallFilmInfoT.getFilmLanguage());
        map.put("info03",mtimeFilmT.getFilmTime()+mtimeHallFilmInfoT.getFilmLanguage());

        //演员信息
        List<Actors> actors = actorMapper.selectActors(filmId);
        ActorsAndDirector actorsAndDirector = new ActorsAndDirector();
        actorsAndDirector.setActors(actors);
        Actors actor = actorMapper.selectDirector(filmId);
        actorsAndDirector.setDirector(actor);

        Info4 info4 = new Info4();
        info4.setActors(actorsAndDirector);
        info4.setFilmId(filmId);
        info4.setBiopgraphy(mtimeFilmInfoT.getBiography());
        String imgVO = mtimeFilmInfoT.getFilmImgs();
        String[] imgVOS = imgVO.split(",");
        ImgVO imgVo = new ImgVO();
        imgVo.setMainImg(imgVOS[0]);
        imgVo.setImg01(imgVOS[1]);
        imgVo.setImg02(imgVOS[2]);
        imgVo.setImg03(imgVOS[3]);
        imgVo.setImg04(imgVOS[4]);

        info4.setImgVO(imgVo);
        map.put("info04",info4);
        map.put("score",mtimeFilmInfoT.getFilmScore());
        map.put("scoreNum",mtimeFilmInfoT.getFilmScoreNum());
        map.put("totalBox",mtimeFilmT.getFilmBoxOffice());
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("data",map);
        resultMap.put("imgPre","http://img.meetingshop.cn/");
        resultMap.put("status",0);

        return resultMap;
    }
}
