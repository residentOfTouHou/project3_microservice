/**
 *
 */
package com.stylefeng.guns.rest.user.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseVo implements Serializable {
    private String msg;

    private Integer status;

    private Object data;

    private String imgPre;

    private Integer nowPage;

    private Integer totalPage;
}
