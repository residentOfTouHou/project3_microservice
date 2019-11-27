package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/11/27
 * @time 17:41
 */

/**
 * 测试类  可删除
 */
@Data
public class FilmVo implements Serializable {

    private static final long serialVersionUID = 2316343178374613037L;

    private Integer uuid;

    private String fileName;
}
