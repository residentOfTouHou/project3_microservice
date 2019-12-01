package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 杨盛
 * @date 2019/11/29 22:31
 */

@Data
public class HallInfoVO implements Serializable {

    private static final long serialVersionUID = 5273884430275483034L;

    Integer hallFieldId;

    String discountPrice;

    String hallName;

    Integer price;

    String seatFile;

    String soldSeats;

}
