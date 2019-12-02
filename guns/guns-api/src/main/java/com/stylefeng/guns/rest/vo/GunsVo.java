package com.stylefeng.guns.rest.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class GunsVo implements Serializable {

    private static final long serialVersionUID = 7689769123883461849L;

    private Integer status;
    private String msg;
    private Object data;
    private Integer totalPage;
    private Integer nowPage;
    private Integer imgPre;

    public GunsVo() {
    }

    public GunsVo(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
