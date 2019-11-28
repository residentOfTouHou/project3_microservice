package com.stylefeng.guns.rest.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.rest.cinema.CinemaService;
import com.stylefeng.guns.rest.cinema.vo.CinemaVo;
import com.stylefeng.guns.rest.cinema.vo.CinemasRespVo;
import com.stylefeng.guns.rest.persistence.dao.MtimeCinemaTMapper;
import com.stylefeng.guns.rest.persistence.model.MtimeCinemaT;
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

    @Override
    public CinemasRespVo getCinemas(Integer brandId, Integer hallType, Integer areaId, Integer pageSize, Integer nowPage) {
        EntityWrapper<MtimeCinemaT> entityWrapper = new EntityWrapper<>();
        if(brandId!=99){
            entityWrapper.eq("uuid",brandId);
        }
        if(hallType!=99){
            entityWrapper.like("hall_ids", String.valueOf(hallType));
        }
        if(areaId!=99){
            entityWrapper.eq("area_id",areaId);
        }
        RowBounds rowBounds = new RowBounds(nowPage,pageSize);
        List<MtimeCinemaT> mtimeCinemaTS = mtimeCinemaTMapper.selectPage(rowBounds, entityWrapper);

        List<CinemaVo> cinemaVoList = new ArrayList<>();
        if(mtimeCinemaTS.size()!=0){
            BeanUtils.copyProperties(mtimeCinemaTS,cinemaVoList);
        }

        CinemasRespVo cinemasRespVo = new CinemasRespVo();
        cinemasRespVo.setData(cinemaVoList);
        cinemasRespVo.setNowPage(nowPage);
        if(cinemaVoList.size()!=0){
            int total = cinemaVoList.size() / pageSize + 1; //totalPage 向上取整
            cinemasRespVo.setTotalPage(total);
        }
        cinemasRespVo.setStatus(0);
        return cinemasRespVo;
    }
}
