package com.stylefeng.guns.rest.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.persistence.dao.MtimeCinemaTMapper;
import com.stylefeng.guns.rest.persistence.dao.MtimePromoMapper;
import com.stylefeng.guns.rest.persistence.model.MtimeCinemaT;
import com.stylefeng.guns.rest.persistence.model.MtimePromo;
import com.stylefeng.guns.rest.promo.PromoService;
import com.stylefeng.guns.rest.promo.vo.PromoVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/12/3
 * @time 16:49
 */
@Component
@Service(interfaceClass = PromoService.class)
public class PromoServiceImpl implements PromoService {

    @Autowired
    MtimePromoMapper mtimePromoMapper;

    @Autowired
    MtimeCinemaTMapper mtimeCinemaTMapper;

    @Override
    public List<PromoVo> getPromo(Integer brandId, Integer hallType, Integer areaId, Integer pageSize, Integer nowPage) {

        EntityWrapper<MtimePromo> entityWrapper = new EntityWrapper<>();
        if(brandId!=99){
            entityWrapper.eq("cinema_id",brandId);
        }
        RowBounds rowBounds = new RowBounds((nowPage - 1) * pageSize, pageSize);
        List<MtimePromo> mtimePromos = mtimePromoMapper.selectPage(rowBounds, entityWrapper);
        List<PromoVo> promoVoList = new ArrayList<>();
        if(mtimePromos.size()!=0){
            for (MtimePromo mtimePromo : mtimePromos) {
                PromoVo promoVo = new PromoVo();
                promoVo.setUuid(mtimePromo.getUuid());
                MtimeCinemaT mtimeCinemaT = mtimeCinemaTMapper.selectById(mtimePromo.getCinemaId());
                promoVo.setCinemaAddress(mtimeCinemaT.getCinemaAddress());
            }
        }else{
            return promoVoList;
        }

        return promoVoList;
    }
}
