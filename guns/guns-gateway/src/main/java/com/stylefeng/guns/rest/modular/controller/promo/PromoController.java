package com.stylefeng.guns.rest.modular.controller.promo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.common.RespEnum;
import com.stylefeng.guns.rest.promo.PromoService;
import com.stylefeng.guns.rest.promo.vo.PromoVo;
import com.stylefeng.guns.rest.util.RespBeanUtil;
import com.stylefeng.guns.rest.vo.GunsVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/12/3
 * @time 16:50
 */
@RestController
@RequestMapping("promo")
public class PromoController {

    @Reference(interfaceClass = PromoService.class,check = false)
    PromoService promoService;

    @RequestMapping("getPromo")
    public GunsVo getPromo(Integer brandId,Integer hallType,Integer areaId,Integer pageSize,Integer nowPage){
        List<PromoVo> result = promoService.getPromo(brandId,hallType,areaId,pageSize,nowPage);
        if(result.size()!=0){
            return RespBeanUtil.beanUtil(RespEnum.NORMAL_RESP.getStatus(),RespEnum.NORMAL_RESP.getMsg(),result);
        }else{
            return RespBeanUtil.beanUtil(RespEnum.PROMO_NULL.getStatus(),RespEnum.PROMO_NULL.getMsg(),null);
        }
    }
}
