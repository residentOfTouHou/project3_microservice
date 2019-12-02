package com.stylefeng.guns.rest.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/12/2
 * @time 15:39
 */
@Data
public class OrderVo implements Serializable {
    private static final long serialVersionUID = 3161998084529550015L;

    private Integer orderId;

    private String filmName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private String fieldTime;

    private String cinemaName;

    private String seatsName;

    private String orderPrice;

    private String orderTimestamp;

    private String orderStatus;
}
