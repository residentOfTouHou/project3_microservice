package com.stylefeng.guns.rest.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.cinema.CinemaService;
import com.stylefeng.guns.rest.cinema.vo.*;
import com.stylefeng.guns.rest.persistence.dao.MtimeAreaDictTMapper;
import com.stylefeng.guns.rest.persistence.dao.MtimeBrandDictTMapper;
import com.stylefeng.guns.rest.persistence.dao.MtimeCinemaTMapper;
import com.stylefeng.guns.rest.persistence.dao.MtimeHallDictTMapper;
import com.stylefeng.guns.rest.persistence.model.MtimeAreaDictT;
import com.stylefeng.guns.rest.persistence.model.MtimeBrandDictT;
import com.stylefeng.guns.rest.persistence.model.MtimeCinemaT;
import com.stylefeng.guns.rest.persistence.model.MtimeHallDictT;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    MtimeCinemaTMapper mtimeCinemaTMapper;

    @Autowired
    MtimeBrandDictTMapper mtimeBrandDictTMapper;

    @Autowired
    MtimeAreaDictTMapper mtimeAreaDictTMapper;

    @Autowired
    MtimeHallDictTMapper mtimeHallDictTMapper;

    @Override
    public CinemasRespVo getCinemas(Integer brandId, Integer hallType, Integer areaId, Integer pageSize, Integer nowPage) {
        EntityWrapper<MtimeCinemaT> entityWrapper = new EntityWrapper<>();
        if(brandId!=99){
            entityWrapper.eq("brand_id",brandId);
        }
        if(hallType!=99){
            entityWrapper.like("hall_ids", String.valueOf(hallType));
        }
        if(areaId!=99){
            entityWrapper.eq("area_id",areaId);
        }
        RowBounds rowBounds = new RowBounds((nowPage-1)*pageSize,pageSize);
        List<MtimeCinemaT> mtimeCinemaTS = mtimeCinemaTMapper.selectPage(rowBounds, entityWrapper);

        List<CinemaVo> cinemaVoList = new ArrayList<>();
        if(mtimeCinemaTS.size()!=0){
            for (MtimeCinemaT mtimeCinemaT : mtimeCinemaTS) {
                CinemaVo cinemaVo = new CinemaVo();
                BeanUtils.copyProperties(mtimeCinemaT,cinemaVo);
                cinemaVoList.add(cinemaVo);
            }

        }

        CinemasRespVo cinemasRespVo = new CinemasRespVo();
        cinemasRespVo.setData(cinemaVoList);
        if(cinemaVoList.size()!=0){
            cinemasRespVo.setNowPage(nowPage);

            int size = mtimeCinemaTMapper.selectList(entityWrapper).size();
            int total = size % pageSize == 0 ? size/pageSize : size/pageSize + 1; //totalPage 向上取整
            cinemasRespVo.setTotalPage(total);
        }else{
            cinemasRespVo.setNowPage(0);
            cinemasRespVo.setTotalPage(0);
        }
        cinemasRespVo.setStatus(0);
        return cinemasRespVo;
    }

    @Override
    public CinemaConditionVo getCinemaCondition(Integer brandId, Integer hallType, Integer areaId) {
        EntityWrapper<MtimeBrandDictT> brandDictTEntityWrapper = new EntityWrapper<>();
        List<MtimeBrandDictT> mtimeBrandDictTS = mtimeBrandDictTMapper.selectList(brandDictTEntityWrapper);
        List<BrandVo> brandVoList = new ArrayList<>();
        if(mtimeBrandDictTS.size()>0){
            for (MtimeBrandDictT mtimeBrandDictT : mtimeBrandDictTS) {
                BrandVo brandVo = new BrandVo();
                brandVo.setBrandId(mtimeBrandDictT.getUuid());
                brandVo.setBrandName(mtimeBrandDictT.getShowName());
                if(mtimeBrandDictT.getUuid().equals(brandId)){
                    brandVo.setActive(true);
                }else{
                    brandVo.setActive(false);
                }
                brandVoList.add(brandVo);
            }
        }

        EntityWrapper<MtimeAreaDictT> areaDictTEntityWrapper = new EntityWrapper<>();
        List<MtimeAreaDictT> mtimeAreaDictTS = mtimeAreaDictTMapper.selectList(areaDictTEntityWrapper);
        List<AreaVo> areaVoList = new ArrayList<>();
        if(mtimeAreaDictTS.size()>0){
            for ( MtimeAreaDictT mtimeAreaDictT : mtimeAreaDictTS) {
                AreaVo areaVo = new AreaVo();
                areaVo.setAreaId(mtimeAreaDictT.getUuid());
                areaVo.setAreaName(mtimeAreaDictT.getShowName());
                if(mtimeAreaDictT.getUuid().equals(areaId)){
                    areaVo.setActive(true);
                }else{
                    areaVo.setActive(false);
                }
                areaVoList.add(areaVo);
            }
        }

        EntityWrapper<MtimeHallDictT> hallDictTEntityWrapper = new EntityWrapper<>();
        List<MtimeHallDictT> mtimeHallDictTS = mtimeHallDictTMapper.selectList(hallDictTEntityWrapper);
        List<HallTypeVo> hallTypeVos = new ArrayList<>();
        if(mtimeHallDictTS.size()>0){
            for ( MtimeHallDictT mtimeHallDictT : mtimeHallDictTS) {
                HallTypeVo hallTypeVo = new HallTypeVo();
                hallTypeVo.setHalltypeId(mtimeHallDictT.getUuid());
                hallTypeVo.setHalltypeName(mtimeHallDictT.getShowName());
                if(mtimeHallDictT.getUuid().equals(hallType)){ //显示请求参数的对应者
                    hallTypeVo.setActive(true);
                }else{
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
}
