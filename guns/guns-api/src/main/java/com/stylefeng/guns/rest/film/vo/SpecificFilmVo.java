package com.stylefeng.guns.rest.film.vo;

import com.sun.org.apache.xml.internal.serializer.utils.SerializerMessages_zh_CN;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class SpecificFilmVo implements Serializable {
    private static final long serialVersionUID = 4677268131689573498L;
    String filmEnName;
    Integer filmId;
    String filmName;
    String imgAddress;
    String info1;
    String info2;
    String info3;
    Map<String,Object> info4;
    String imgPre;
    Integer status;
}
