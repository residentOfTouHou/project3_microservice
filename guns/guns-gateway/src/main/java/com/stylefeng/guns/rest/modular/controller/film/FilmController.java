package com.stylefeng.guns.rest.modular.controller.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.film.FilmService;
import com.stylefeng.guns.rest.film.vo.FilmVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/11/27
 * @time 19:40
 */

//@RestController
//@RequestMapping("film")
public class FilmController {

    @Reference(interfaceClass = FilmService.class)
    FilmService filmService;

    /**
     * 测试用 可删除
     */
    @RequestMapping("query")
    public FilmVo queryId(Integer id){
        FilmVo vo = filmService.getById(id);
        return vo;
    }
}
