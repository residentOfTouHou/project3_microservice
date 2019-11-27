package com.stylefeng.guns.rest.film;

import com.stylefeng.guns.rest.film.vo.FilmVo;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/11/27
 * @time 17:35
 */
public interface FilmService {
    /**
     * 测试 可删除
     * @param id
     * @return
     */
    FilmVo getById(Integer id);
}
