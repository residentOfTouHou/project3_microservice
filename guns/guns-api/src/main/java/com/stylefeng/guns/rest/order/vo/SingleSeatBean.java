package com.stylefeng.guns.rest.order.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/12/2
 * @time 14:12
 */
@Data
public class SingleSeatBean implements Serializable {
    private static final long serialVersionUID = 4520622521924756908L;

    private Integer seatId;

    private Integer row;

    private Integer column;
}
