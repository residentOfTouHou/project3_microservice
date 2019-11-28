package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/11/28
 * @time 22:21
 */
@Data
public class HallTypeVo implements Serializable {
    private static final long serialVersionUID = -9047964238443044985L;

    private Integer halltypeId;

    private String halltypeName;

    private boolean active;
}
