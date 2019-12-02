package com.stylefeng.guns.rest.modular.controller.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.cinema.CinemaService;
import com.stylefeng.guns.rest.cinema.vo.CinemaConditionVo;
import com.stylefeng.guns.rest.cinema.vo.CinemasRespVo;
import com.stylefeng.guns.rest.utils.CinemaBaseReqVO;
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

    @Reference(interfaceClass = CinemaService.class)
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
    public CinemasRespVo getCinemas(Integer brandId, Integer hallType, Integer areaId, Integer pageSize, Integer nowPage) {
        return cinemaService.getCinemas(brandId, hallType, areaId, pageSize, nowPage);
    }

    /**
     * 获得影院查询的信息
     *
     * @param brandId
     * @param hallType
     * @param areaId
     * @return
     */
    @RequestMapping("getCondition")
    public CinemaConditionVo getCinemaCondition(Integer brandId, Integer hallType, Integer areaId) {
        return cinemaService.getCinemaCondition(brandId, hallType, areaId);
    }

    /**
     * 获取电影播放场次
     * 包括影院，影厅，电影的信息
     *
     * @param cinemaId
     * @return
     */
    @RequestMapping("getFields")
    public CinemaBaseReqVO getFields(int cinemaId) {
        CinemaBaseReqVO cinemaBaseReqVO = cinemaService.queryCinemaInfoById(cinemaId);
        return cinemaBaseReqVO;
    }

    /**
     * 获取某具体场次信息
     * 查看选择购票信息
     *
     * @param cinemaId
     * @param fieldId
     * @return
     */
    @RequestMapping("getFieldInfo")
    public CinemaBaseReqVO getFieldInfo(int cinemaId, int fieldId) {
        CinemaBaseReqVO cinemaBaseReqVO = cinemaService.queryFieldInfoById(cinemaId, fieldId);
        return cinemaBaseReqVO;
    }
}
