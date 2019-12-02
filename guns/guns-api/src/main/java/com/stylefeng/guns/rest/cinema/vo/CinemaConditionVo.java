package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/11/28
 * @time 23:07
 */
@Data
public class CinemaConditionVo implements Serializable {
    private static final long serialVersionUID = 2215408994591478485L;

    private Object data;

    private Integer status;

    private String nowPage;

    private String imgPre;

    private String msg;

    private String totalPage;
}
