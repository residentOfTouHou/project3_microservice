package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/11/28
 * @time 22:18
 */
@Data
public class BrandVo implements Serializable {
    private static final long serialVersionUID = 2118936439887909988L;

    private Integer brandId;

    private String brandName;

    private boolean active;
}
