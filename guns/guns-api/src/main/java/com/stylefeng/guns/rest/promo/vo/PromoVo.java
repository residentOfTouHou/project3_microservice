package com.stylefeng.guns.rest.promo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/12/3
 * @time 17:13
 */
@Data
public class PromoVo implements Serializable {
    private static final long serialVersionUID = -4108815456039578698L;

    private Integer uuid;

    private String cinemaId;

    private String cinemaAddress;

    private String cinemaName;

    private String description;

    private String imgAddress;

    private Double price;

    private Integer status;

    private Integer stock;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
