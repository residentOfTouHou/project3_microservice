package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeCatDictTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeSourceDictTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeYearDictTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeCatDictT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeSourceDictT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeYearDictT;
import com.stylefeng.guns.rest.film.FilmCatServiceAPI;
import com.stylefeng.guns.rest.film.vo.CatInfoVO;
import com.stylefeng.guns.rest.film.vo.SourceInfoVO;
import com.stylefeng.guns.rest.film.vo.YearInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


@Component
@Service(interfaceClass = FilmCatServiceAPI.class)
public class FilmCatServiceAPIImpl implements FilmCatServiceAPI {

    @Autowired
    private MtimeCatDictTMapper catDictTMapper;

    @Autowired
    private MtimeSourceDictTMapper sourceDictTMapper;

    @Autowired
    private MtimeYearDictTMapper yearDictTMapper;

    @Override
    public List<CatInfoVO> getCatsInfo() {

        EntityWrapper<MtimeCatDictT> wrapper = new EntityWrapper<>();

        List<MtimeCatDictT> mtimeCatDictTS = catDictTMapper.selectList(wrapper);

        List<CatInfoVO> list = convert2CatInfoVO(mtimeCatDictTS);
        return list;
    }

    private List<CatInfoVO> convert2CatInfoVO(List<MtimeCatDictT> mtimeCatDictTS) {
        ArrayList<CatInfoVO> catInfoVOS = new ArrayList<>();
        if (CollectionUtils.isEmpty(mtimeCatDictTS)){
            return catInfoVOS;
        }
        for (MtimeCatDictT dictT: mtimeCatDictTS) {
            CatInfoVO catInfoVO = new CatInfoVO();

            catInfoVO.setCatId(dictT.getUuid() + "");
            catInfoVO.setCatName(dictT.getShowName());
            catInfoVO.setActive(false);

            catInfoVOS.add(catInfoVO);
        }
        return catInfoVOS;
    }

    @Override
    public List<SourceInfoVO> getSourceInfo() {
        EntityWrapper<MtimeSourceDictT> wrapper = new EntityWrapper<>();

        List<MtimeSourceDictT> mtimeSourceDictTS = sourceDictTMapper.selectList(wrapper);

        List<SourceInfoVO> list = convert2SourceInfoVO(mtimeSourceDictTS);
        return list;
    }

    private List<SourceInfoVO> convert2SourceInfoVO(List<MtimeSourceDictT> mtimeSourceDictTS) {
        ArrayList<SourceInfoVO> sourceInfoVOS = new ArrayList<>();
        if (CollectionUtils.isEmpty(mtimeSourceDictTS)){
            return sourceInfoVOS;
        }
        for (MtimeSourceDictT dictT: mtimeSourceDictTS) {
            SourceInfoVO sourceInfoVO = new SourceInfoVO();

            sourceInfoVO.setSourceId(dictT.getUuid() + "");
            sourceInfoVO.setSourceName(dictT.getShowName());
            sourceInfoVO.setActive(false);

            sourceInfoVOS.add(sourceInfoVO);
        }
        return sourceInfoVOS;

    }

    @Override
    public List<YearInfoVO> getYearInfo() {
        EntityWrapper<MtimeYearDictT> wrapper = new EntityWrapper<>();

        List<MtimeYearDictT> mtimeYearDictTS = yearDictTMapper.selectList(wrapper);

        List<YearInfoVO> list = convert2YearInfoVO(mtimeYearDictTS);
        return list;
    }

    private List<YearInfoVO> convert2YearInfoVO(List<MtimeYearDictT> mtimeYearDictTS) {

        ArrayList<YearInfoVO> yearInfoVOS = new ArrayList<>();
        if (CollectionUtils.isEmpty(mtimeYearDictTS)){
            return yearInfoVOS;
        }
        for (MtimeYearDictT dictT: mtimeYearDictTS) {
            YearInfoVO yearInfoVO = new YearInfoVO();

            yearInfoVO.setYearId(dictT.getUuid() + "");
            yearInfoVO.setYearName(dictT.getShowName());
            yearInfoVO.setActive(false);

            yearInfoVOS.add(yearInfoVO);
        }
        return yearInfoVOS;

    }
}
