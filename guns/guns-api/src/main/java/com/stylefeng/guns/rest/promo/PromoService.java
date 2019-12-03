package com.stylefeng.guns.rest.promo;

import com.stylefeng.guns.rest.promo.vo.PromoVo;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/12/3
 * @time 16:48
 */
public interface PromoService {
    List<PromoVo> getPromo(Integer brandId, Integer hallType, Integer areaId, Integer pageSize, Integer nowPage);


}
