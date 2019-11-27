package com.stylefeng.guns.rest.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.film.FilmService;
import com.stylefeng.guns.rest.film.vo.FilmVo;
import com.stylefeng.guns.rest.persistence.dao.MtimeFilmTMapper;
import com.stylefeng.guns.rest.persistence.model.MtimeFilmT;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/11/27
 * @time 17:38
 */
@Component
@Service(interfaceClass = FilmService.class)
public class FilmServiceImpl implements FilmService {

    @Autowired
    private MtimeFilmTMapper mtimeFilmTMapper;

    /**
     * 测试 可删除
     * @param id
     * @return
     */
    @Override
    public FilmVo getById(Integer id) {
        MtimeFilmT mtimeFilmT = mtimeFilmTMapper.selectById(id);
        FilmVo filmVo = convert2FilmVo(mtimeFilmT);
        return filmVo;
    }

    private FilmVo convert2FilmVo(MtimeFilmT mtimeFilmT) {
        FilmVo filmVo = new FilmVo();
        if(mtimeFilmT==null){
            return  filmVo;
        }
        BeanUtils.copyProperties(mtimeFilmT,filmVo);
        return filmVo;
    }
}
