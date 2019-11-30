package com.stylefeng.guns.rest.modular.controller.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.cinema.CinemaService;
import com.stylefeng.guns.rest.cinema.vo.CinemaConditionVo;
import com.stylefeng.guns.rest.cinema.vo.CinemasRespVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/11/27
 * @time 20:26
 */
@RestController
@RequestMapping("cinema")
public class CinemaController {

    @Reference(interfaceClass = CinemaService.class,check = false)
    private CinemaService cinemaService;

    /**
     * 根据条件获得影片信息
     *
     * @param brandId
     * @param hallType
     * @param areaId
     * @param pageSize
     * @param nowPage
     * @return
     */
    @RequestMapping("getCinemas")
    public CinemasRespVo getCinemas(Integer brandId, Integer hallType, Integer areaId, Integer pageSize, Integer nowPage){
        return cinemaService.getCinemas(brandId,hallType,areaId,pageSize,nowPage);
    }

    /**
     * 获得影院查询的信息
     * @param brandId
     * @param hallType
     * @param areaId
     * @return
     */
    @RequestMapping("getCondition")
    public CinemaConditionVo getCinemaCondition(Integer brandId, Integer hallType, Integer areaId){
        return cinemaService.getCinemaCondition(brandId,hallType,areaId);
    }
}
