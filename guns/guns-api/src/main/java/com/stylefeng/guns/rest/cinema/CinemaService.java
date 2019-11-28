package com.stylefeng.guns.rest.cinema;

import com.stylefeng.guns.rest.cinema.vo.CinemaConditionVo;
import com.stylefeng.guns.rest.cinema.vo.CinemasRespVo;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/11/27
 * @time 17:41
 */
public interface CinemaService {
    CinemasRespVo getCinemas(Integer brandId, Integer hallType, Integer areaId, Integer pageSize, Integer nowPage);

    CinemaConditionVo getCinemaCondition(Integer brandId, Integer hallType, Integer areaId);
}
