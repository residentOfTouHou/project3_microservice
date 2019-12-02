package com.stylefeng.guns.rest.order.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/12/2
 * @time 9:20
 */
@Data
public class SeatBean implements Serializable {
    private static final long serialVersionUID = 2728941378241188201L;

    private Integer limit;

    private String ids;

    private List<List<SingleSeatBean>> single;

    private List<List<SingleSeatBean>> couple;
}
