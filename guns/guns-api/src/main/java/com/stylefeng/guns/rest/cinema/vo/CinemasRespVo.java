package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/11/28
 * @time 21:04
 */

/**
 * getCinemas 的响应
 */
@Data
public class CinemasRespVo implements Serializable {
    private static final long serialVersionUID = -2499970217380012419L;

    private List<CinemaVo> data;

    //private String imgPre;

    private String msg;

    private Integer nowPage;

    private Integer status;

    private Integer totalPage;
}
