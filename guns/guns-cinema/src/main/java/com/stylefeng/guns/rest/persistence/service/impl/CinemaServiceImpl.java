package com.stylefeng.guns.rest.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.cinema.CinemaService;
import com.stylefeng.guns.rest.cinema.vo.*;
import com.stylefeng.guns.rest.cinema.vo.FilmInfoVO;
import com.stylefeng.guns.rest.persistence.dao.*;
import com.stylefeng.guns.rest.persistence.model.*;
import com.stylefeng.guns.rest.utils.CinemaBaseReqVO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/11/27
 * @time 20:23
 */
@Component
@Service(interfaceClass = CinemaService.class)
public class CinemaServiceImpl implements CinemaService {

    private final String IMG_PRE = "http://img.meetingshop.cn/";

    @Autowired
    MtimeCinemaTMapper mtimeCinemaTMapper;

    @Autowired
    MtimeBrandDictTMapper mtimeBrandDictTMapper;

    @Autowired
    MtimeAreaDictTMapper mtimeAreaDictTMapper;

    @Autowired
    MtimeHallDictTMapper mtimeHallDictTMapper;

    @Autowired
    MtimeHallFilmInfoTMapper mtimeHallFilmInfoTMapper;

    @Autowired
    MtimeFieldTMapper mtimeFieldTMapper;

    @Override
    public CinemasRespVo getCinemas(Integer brandId, Integer hallType, Integer areaId, Integer pageSize, Integer nowPage) throws NullPointerException{
        EntityWrapper<MtimeCinemaT> entityWrapper = new EntityWrapper<>();
        if (brandId != 99) {
            entityWrapper.eq("brand_id", brandId);
        }
        if (hallType != 99) {
            entityWrapper.like("hall_ids", String.valueOf(hallType));
        }
        if (areaId != 99) {
            entityWrapper.eq("area_id", areaId);
        }
        RowBounds rowBounds = new RowBounds((nowPage - 1) * pageSize, pageSize);
        List<MtimeCinemaT> mtimeCinemaTS = mtimeCinemaTMapper.selectPage(rowBounds, entityWrapper);

        List<CinemaVo> cinemaVoList = new ArrayList<>();
        if (mtimeCinemaTS.size() != 0) {
            for (MtimeCinemaT mtimeCinemaT : mtimeCinemaTS) {
                CinemaVo cinemaVo = new CinemaVo();
                BeanUtils.copyProperties(mtimeCinemaT, cinemaVo);
                cinemaVoList.add(cinemaVo);
            }
        }

        CinemasRespVo cinemasRespVo = new CinemasRespVo();
        cinemasRespVo.setData(cinemaVoList);
        if (cinemaVoList.size() != 0) {
            cinemasRespVo.setNowPage(nowPage);

            int size = mtimeCinemaTMapper.selectList(entityWrapper).size();
            int total = size % pageSize == 0 ? size / pageSize : size / pageSize + 1; //totalPage 向上取整
            cinemasRespVo.setTotalPage(total);
        } else {
            cinemasRespVo.setNowPage(0);
            cinemasRespVo.setTotalPage(0);
        }
        cinemasRespVo.setStatus(0);
        cinemasRespVo.setImgPre("http://img.meetingshop.cn/");
        return cinemasRespVo;
    }

    @Override
    public CinemaConditionVo getCinemaCondition(Integer brandId, Integer hallType, Integer areaId) {
        EntityWrapper<MtimeBrandDictT> brandDictTEntityWrapper = new EntityWrapper<>();
        List<MtimeBrandDictT> mtimeBrandDictTS = mtimeBrandDictTMapper.selectList(brandDictTEntityWrapper);
        List<BrandVo> brandVoList = new ArrayList<>();
        if (mtimeBrandDictTS.size() > 0) {
            for (MtimeBrandDictT mtimeBrandDictT : mtimeBrandDictTS) {
                BrandVo brandVo = new BrandVo();
                brandVo.setBrandId(mtimeBrandDictT.getUuid());
                brandVo.setBrandName(mtimeBrandDictT.getShowName());
                if (mtimeBrandDictT.getUuid().equals(brandId)) {
                    brandVo.setActive(true);
                } else {
                    brandVo.setActive(false);
                }
                brandVoList.add(brandVo);
            }
        }

        EntityWrapper<MtimeAreaDictT> areaDictTEntityWrapper = new EntityWrapper<>();
        List<MtimeAreaDictT> mtimeAreaDictTS = mtimeAreaDictTMapper.selectList(areaDictTEntityWrapper);
        List<AreaVo> areaVoList = new ArrayList<>();
        if (mtimeAreaDictTS.size() > 0) {
            for (MtimeAreaDictT mtimeAreaDictT : mtimeAreaDictTS) {
                AreaVo areaVo = new AreaVo();
                areaVo.setAreaId(mtimeAreaDictT.getUuid());
                areaVo.setAreaName(mtimeAreaDictT.getShowName());
                if (mtimeAreaDictT.getUuid().equals(areaId)) {
                    areaVo.setActive(true);
                } else {
                    areaVo.setActive(false);
                }
                areaVoList.add(areaVo);
            }
        }

        EntityWrapper<MtimeHallDictT> hallDictTEntityWrapper = new EntityWrapper<>();
        List<MtimeHallDictT> mtimeHallDictTS = mtimeHallDictTMapper.selectList(hallDictTEntityWrapper);
        List<HallTypeVo> hallTypeVos = new ArrayList<>();
        if (mtimeHallDictTS.size() > 0) {
            for (MtimeHallDictT mtimeHallDictT : mtimeHallDictTS) {
                HallTypeVo hallTypeVo = new HallTypeVo();
                hallTypeVo.setHalltypeId(mtimeHallDictT.getUuid());
                hallTypeVo.setHalltypeName(mtimeHallDictT.getShowName());
                if (mtimeHallDictT.getUuid().equals(hallType)) { //显示请求参数的对应者
                    hallTypeVo.setActive(true);
                } else {
                    hallTypeVo.setActive(false);
                }
                hallTypeVos.add(hallTypeVo);
            }
        }

        CinemaConditionListVo cinemaConditionListVo = new CinemaConditionListVo();
        cinemaConditionListVo.setAreaList(areaVoList);
        cinemaConditionListVo.setBrandList(brandVoList);
        cinemaConditionListVo.setHalltypeList(hallTypeVos);

        CinemaConditionVo cinemaConditionVo = new CinemaConditionVo();
        cinemaConditionVo.setData(cinemaConditionListVo);
        cinemaConditionVo.setStatus(0);
        return cinemaConditionVo;
    }

    @Override
    public CinemaBaseReqVO queryCinemaInfoById(int id) {
        MtimeCinemaT mtimeCinemaT = mtimeCinemaTMapper.selectById(id);
        Map<String, Object> data = new HashMap<>();
        // 影院信息
        if (mtimeCinemaT != null) {
            CinemaInfoVO cinemaInfoVO = convert2CinemaInfoVO(mtimeCinemaT);
            data.put("cinemaInfo", cinemaInfoVO);
        }
        // 电影信息
        List<FilmInfoVO> filmList = new ArrayList();
        List<Integer> filmIds = mtimeFieldTMapper.selectFilmIds(id);
        // 用set特性 list去重
        if (filmIds != null && filmIds.size() > 0) {
            Set set = new HashSet();
            set.addAll(filmIds);
            filmIds.clear();
            filmIds.addAll(set);
            for (Integer filmId : filmIds) {
                EntityWrapper<MtimeHallFilmInfoT> HallFilmInfoTWrapper = new EntityWrapper<>();
                HallFilmInfoTWrapper.eq("film_id", filmId);
                List<MtimeHallFilmInfoT> mtimeHallFilmInfoTS = mtimeHallFilmInfoTMapper.selectList(HallFilmInfoTWrapper);
                if (mtimeHallFilmInfoTS != null && mtimeHallFilmInfoTS.size() > 0) {
                    for (MtimeHallFilmInfoT mtimeHallFilmInfoT : mtimeHallFilmInfoTS) {
                        FilmInfoVO filmInfoVO = convert2FilmInfoVO(mtimeHallFilmInfoT);
                        filmList.add(filmInfoVO);
                    }
                }
            }
        }
        if (filmList != null && filmList.size() > 0) {
            for (FilmInfoVO filmInfoVO : filmList) {
                // 放映信息
                EntityWrapper<MtimeFieldT> fieldTWrapper = new EntityWrapper<MtimeFieldT>();
                fieldTWrapper.eq("film_id", filmInfoVO.getFilmId());
                List<MtimeFieldT> mtimeFieldTS = mtimeFieldTMapper.selectList(fieldTWrapper);
                List<FilmFieldVO> filmFieldVOS = new ArrayList<>();
                for (MtimeFieldT mtimeFieldT : mtimeFieldTS) {
                    FilmFieldVO filmFieldVO = convert2filmFieldVO(mtimeFieldT);
                    filmFieldVO.setLanguage(filmInfoVO.getFilmLength());
                    filmFieldVOS.add(filmFieldVO);
                }
                filmInfoVO.setFilmFields(filmFieldVOS);
            }
        }
        data.put("filmList", filmList);
        CinemaBaseReqVO baseReqVO = CinemaBaseReqVO.success(data, IMG_PRE);
        return baseReqVO;
    }

    @Override
    public CinemaBaseReqVO queryFieldInfoById(int cinemaId, int fieldId) {
        MtimeCinemaT mtimeCinemaT = mtimeCinemaTMapper.selectById(cinemaId);
        Map<String, Object> data = new HashMap<>();
        // 影院信息
        if (mtimeCinemaT != null) {
            CinemaInfoVO cinemaInfoVO = convert2CinemaInfoVO(mtimeCinemaT);
            data.put("cinemaInfo", cinemaInfoVO);
        }
        // 电影信息
        FilmInfoVO filmInfoVO = mtimeFieldTMapper.selectFilmByFieldId(cinemaId, fieldId);
        //放映厅信息
        HallInfoVO hallInfoVO = mtimeFieldTMapper.selectHallByFieldId(fieldId);
        hallInfoVO.setDiscountPrice("");
        hallInfoVO.setSoldSeats("5");
        data.put("filmInfo", filmInfoVO);
        data.put("hallInfo", hallInfoVO);
        CinemaBaseReqVO baseReqVO = CinemaBaseReqVO.success(data, IMG_PRE);
        return baseReqVO;
    }

    private FilmFieldVO convert2filmFieldVO(MtimeFieldT mtimeFieldT) {
        FilmFieldVO filmFieldVO = new FilmFieldVO();
        BeanUtils.copyProperties(mtimeFieldT, filmFieldVO);
        filmFieldVO.setFieldId(mtimeFieldT.getUuid());
        return filmFieldVO;
    }

    private FilmInfoVO convert2FilmInfoVO(MtimeHallFilmInfoT mtimeHallFilmInfoT) {
        FilmInfoVO filmInfoVO = new FilmInfoVO();
        BeanUtils.copyProperties(mtimeHallFilmInfoT, filmInfoVO);
        filmInfoVO.setFilmType(mtimeHallFilmInfoT.getFilmLanguage());
        return filmInfoVO;
    }

    private CinemaInfoVO convert2CinemaInfoVO(MtimeCinemaT mtimeCinemaT) {
        CinemaInfoVO cinemaInfoVO = new CinemaInfoVO();
        BeanUtils.copyProperties(mtimeCinemaT, cinemaInfoVO);
        cinemaInfoVO.setCinemaId(mtimeCinemaT.getUuid());
        cinemaInfoVO.setImgUrl(mtimeCinemaT.getImgAddress());
        cinemaInfoVO.setCinemaAdress(mtimeCinemaT.getCinemaAddress());
        return cinemaInfoVO;
    }
}
