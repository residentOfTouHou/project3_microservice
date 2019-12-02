package com.stylefeng.guns.rest.persistence.dao;

import com.stylefeng.guns.rest.cinema.vo.FilmInfoVO;
import com.stylefeng.guns.rest.cinema.vo.HallInfoVO;
import com.stylefeng.guns.rest.persistence.model.MtimeFieldT;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 放映场次表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-11-30
 */
public interface MtimeFieldTMapper extends BaseMapper<MtimeFieldT> {

    List<Integer> selectFilmIds(int id);

    FilmInfoVO selectFilmByFieldId(int cinemaId, int fieldId);

    HallInfoVO selectHallByFieldId(int fieldId);
}
