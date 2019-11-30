package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/11/28
 * @time 22:16
 */
@Data
public class CinemaConditionListVo implements Serializable {
    private static final long serialVersionUID = 2215408994591478485L;

    private List<BrandVo> brandList;

    private List<AreaVo> areaList;

    private List<HallTypeVo> halltypeList;
}
