package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeBannerTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeFilmTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeBannerT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmT;
import com.stylefeng.guns.rest.film.FilmServiceAPI;
import com.stylefeng.guns.rest.film.vo.BannerVO;
import com.stylefeng.guns.rest.film.vo.FilmInfoVO;
import com.stylefeng.guns.rest.film.vo.FilmVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@Service(interfaceClass = FilmServiceAPI.class)
public class FilmServiceApiImpl implements FilmServiceAPI {

    @Autowired
    private MtimeBannerTMapper bannerTMapper;

    @Autowired
    private MtimeFilmTMapper filmTMapper;

    @Override
    public List<BannerVO> getBanners() {
        ArrayList<BannerVO> bannerVOS = new ArrayList<>();
        EntityWrapper<MtimeBannerT> wrapper = new EntityWrapper<>();
        wrapper.eq("is_valid", 1);
        List<MtimeBannerT> mtimeBannerTS = bannerTMapper.selectList(wrapper);

        if (CollectionUtils.isEmpty(mtimeBannerTS)) {
            return bannerVOS;
        }
        for (MtimeBannerT bannerT : mtimeBannerTS) {
            BannerVO bannerVO = new BannerVO();
            bannerVO.setBannerAddress(bannerT.getBannerAddress());
            bannerVO.setBannerId(bannerT.getUuid() + "");
            bannerVO.setBannerUrl(bannerT.getBannerUrl());

            bannerVOS.add(bannerVO);
        }
        return bannerVOS;
    }

    @Override
    public FilmVO getHotFilm(Integer showNum, Boolean showAll) {

        FilmVO filmVO = new FilmVO();

        EntityWrapper<MtimeFilmT> wrapper = new EntityWrapper<>();
        wrapper.eq("film_status", 1);
        Integer count = filmTMapper.selectCount(wrapper);
//分页
        Page<MtimeFilmT> page = new Page<>(1, showNum);
        List<MtimeFilmT> mtimeFilmTS = null;
        if (showAll) {
            mtimeFilmTS = filmTMapper.selectList(wrapper);
        } else {
            mtimeFilmTS = filmTMapper.selectPage(page, wrapper);
        }
        List<FilmInfoVO> list = convert2HotFilmInfoVO(mtimeFilmTS);
        filmVO.setFilmNum(count);
        filmVO.setFilmInfo(list);
        return filmVO;
    }

    private List<FilmInfoVO> convert2HotFilmInfoVO(List<MtimeFilmT> mtimeFilmTS) {

        ArrayList<FilmInfoVO> filmInfoVOS = new ArrayList<>();
        if (CollectionUtils.isEmpty(mtimeFilmTS)){
            return filmInfoVOS;
        }
        for (MtimeFilmT filmT : mtimeFilmTS) {
            FilmInfoVO filmInfoVO = new FilmInfoVO();

            filmInfoVO.setFilmId(filmT.getUuid() + "");
            filmInfoVO.setFilmType(filmT.getFilmType());
            filmInfoVO.setImgAddress(filmInfoVO.getImgAddress());
            filmInfoVO.setFilmName(filmT.getFilmName());
            filmInfoVO.setFilmScore(filmT.getFilmScore());

            filmInfoVOS.add(filmInfoVO);
        }
        return filmInfoVOS ;
    }

    @Override
    public FilmVO getSoonFilm(Integer showNum, Boolean showAll) {
        FilmVO filmVO = new FilmVO();

        EntityWrapper<MtimeFilmT> wrapper = new EntityWrapper<>();
        wrapper.eq("film_status", 2);
        Integer count = filmTMapper.selectCount(wrapper);
//分页
        Page<MtimeFilmT> page = new Page<>(1, showNum);
        List<MtimeFilmT> mtimeFilmTS = null;
        if (showAll) {
            mtimeFilmTS = filmTMapper.selectList(wrapper);
        } else {
            mtimeFilmTS = filmTMapper.selectPage(page, wrapper);
        }
        List<FilmInfoVO> list = convert2SoonFilmInfoVO(mtimeFilmTS);
        filmVO.setFilmNum(count);
        filmVO.setFilmInfo(list);
        return filmVO;
    }

    private List<FilmInfoVO> convert2SoonFilmInfoVO(List<MtimeFilmT> mtimeFilmTS) {
        ArrayList<FilmInfoVO> filmInfoVOS = new ArrayList<>();
        if (CollectionUtils.isEmpty(mtimeFilmTS)){
            return filmInfoVOS;
        }

        for (MtimeFilmT filmT : mtimeFilmTS) {
            FilmInfoVO filmInfoVO = new FilmInfoVO();

            filmInfoVO.setFilmId(filmT.getUuid() + "");
            filmInfoVO.setFilmType(filmT.getFilmType());
            filmInfoVO.setImgAddress(filmInfoVO.getImgAddress());
            filmInfoVO.setFilmName(filmT.getFilmName());
            filmInfoVO.setExpectNum(filmT.getFilmPresalenum());
            filmInfoVO.setShowTime(filmT.getFilmTime());

            filmInfoVOS.add(filmInfoVO);
        }
        return filmInfoVOS ;
    }

    @Override
    public List<FilmInfoVO> getBoxRanking(Integer showNum, Boolean showAll) {

        ArrayList<FilmInfoVO> filmInfoVOS = new ArrayList<>();


        Page<MtimeFilmT> page = new Page<>(1,showNum,"film_box_office",false);

        EntityWrapper<MtimeFilmT> wrapper = new EntityWrapper<>();
        List<MtimeFilmT> mtimeFilmTS = null;
        if (showAll) {
            mtimeFilmTS = filmTMapper.selectList(wrapper);
        } else {
            mtimeFilmTS = filmTMapper.selectPage(page, wrapper);
        }

        List<FilmInfoVO> list = convert2BOXRankingFilmInfo(mtimeFilmTS);
        return list;
    }

    private List<FilmInfoVO> convert2BOXRankingFilmInfo(List<MtimeFilmT> mtimeFilmTS) {
        ArrayList<FilmInfoVO> filmInfoVOS = new ArrayList<>();
        if (CollectionUtils.isEmpty(mtimeFilmTS)){
            return filmInfoVOS;
        }

        for (MtimeFilmT filmT : mtimeFilmTS) {
            FilmInfoVO filmInfoVO = new FilmInfoVO();

            filmInfoVO.setFilmId(filmT.getUuid() + "");
            filmInfoVO.setImgAddress(filmInfoVO.getImgAddress());
            filmInfoVO.setFilmName(filmT.getFilmName());
            filmInfoVO.setBoxNum(filmT.getFilmBoxOffice());

            filmInfoVOS.add(filmInfoVO);
        }
        return filmInfoVOS ;
    }

    @Override
    public List<FilmInfoVO> getExpectRanking(Integer showNum, Boolean showAll) {
        ArrayList<FilmInfoVO> filmInfoVOS = new ArrayList<>();


        Page<MtimeFilmT> page = new Page<>(1,showNum,"film_preSaleNum",false);

        EntityWrapper<MtimeFilmT> wrapper = new EntityWrapper<>();
        List<MtimeFilmT> mtimeFilmTS = null;
        if (showAll) {
            mtimeFilmTS = filmTMapper.selectList(wrapper);
        } else {
            mtimeFilmTS = filmTMapper.selectPage(page, wrapper);
        }

        List<FilmInfoVO> list = convert2ExpectRankingFilmInfo(mtimeFilmTS);
        return list;
    }

    private List<FilmInfoVO> convert2ExpectRankingFilmInfo(List<MtimeFilmT> mtimeFilmTS) {
        ArrayList<FilmInfoVO> filmInfoVOS = new ArrayList<>();
        if (CollectionUtils.isEmpty(mtimeFilmTS)){
            return filmInfoVOS;
        }

        for (MtimeFilmT filmT : mtimeFilmTS) {
            FilmInfoVO filmInfoVO = new FilmInfoVO();

            filmInfoVO.setFilmId(filmT.getUuid() + "");
            filmInfoVO.setImgAddress(filmInfoVO.getImgAddress());
            filmInfoVO.setFilmName(filmT.getFilmName());
            filmInfoVO.setExpectNum(filmT.getFilmPresalenum());

            filmInfoVOS.add(filmInfoVO);
        }
        return filmInfoVOS ;
    }

    @Override
    public List<FilmInfoVO> getTop100(Integer showNum, Boolean showAll) {
        ArrayList<FilmInfoVO> filmInfoVOS = new ArrayList<>();


        Page<MtimeFilmT> page = new Page<>(1,showNum,"film_score",false);

        EntityWrapper<MtimeFilmT> wrapper = new EntityWrapper<>();
        List<MtimeFilmT> mtimeFilmTS = null;
        if (showAll) {
            mtimeFilmTS = filmTMapper.selectList(wrapper);
        } else {
            mtimeFilmTS = filmTMapper.selectPage(page, wrapper);
        }

        List<FilmInfoVO> list = convert2Top100FilmInfo(mtimeFilmTS);
        return list;
    }

    private List<FilmInfoVO> convert2Top100FilmInfo(List<MtimeFilmT> mtimeFilmTS) {
        ArrayList<FilmInfoVO> filmInfoVOS = new ArrayList<>();
        if (CollectionUtils.isEmpty(mtimeFilmTS)){
            return filmInfoVOS;
        }

        for (MtimeFilmT filmT : mtimeFilmTS) {
            FilmInfoVO filmInfoVO = new FilmInfoVO();

            filmInfoVO.setFilmId(filmT.getUuid() + "");
            filmInfoVO.setImgAddress(filmInfoVO.getImgAddress());
            filmInfoVO.setFilmName(filmT.getFilmName());
            filmInfoVO.setFilmScore(filmT.getFilmScore());

            filmInfoVOS.add(filmInfoVO);
        }
        return filmInfoVOS ;
    }
}
