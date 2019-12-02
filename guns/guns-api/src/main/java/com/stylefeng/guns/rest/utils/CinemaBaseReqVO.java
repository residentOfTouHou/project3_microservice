package com.stylefeng.guns.rest.utils;

import lombok.Data;

import java.io.Serializable;


/**
 * @author 杨盛
 * @date 2019/11/28 21:14
 */

@Data
public class CinemaBaseReqVO<T> implements Serializable {


    /**
     * data : {"cinemaInfo":{"cinemaAdress":"北京市顺义区华联金街购物中心","cinemaId":1,"cinemaName":"大地影院(顺义店)","cinemaPhone":"18500003333","imgUrl":"cinema6.jpg"},"filmList":[{"actors":"程勇,曹斌,吕受益,刘思慧","filmCats":"喜剧,剧情","filmFields":[{"beginTime":"09:50","endTime":"11:20","fieldId":1,"hallName":"一号厅","language":"国语2D","price":"60"}],"filmId":2,"filmLength":"117","filmName":"我不是药神","filmType":"国语2D","imgAddress":"238e2dc36beae55a71cabfc14069fe78236351.jpg"},{"actors":"汤姆克鲁斯,舒淇,黄渤","filmCats":"喜剧,动作,冒险","filmFields":[{"beginTime":"11:50","endTime":"13:20","fieldId":2,"hallName":"IMAX厅","language":"国语3DIMAX","price":"60"},{"beginTime":"13:50","endTime":"15:20","fieldId":3,"hallName":"飞翔体验厅","language":"国语3DIMAX","price":"60"}],"filmId":3,"filmLength":"123","filmName":"跳舞吧大象","filmType":"国语3DIMAX","imgAddress":"1813b306280c0f37f9812afbbe631ae33681369.jpg"}]}
     * imgPre : http://img.meetingshop.cn/
     * msg :
     * nowPage :
     * status : 0
     * totalPage :
     */

    private T data;
    private String imgPre;
    private String msg;
    private String nowPage;
    private int status;
    private String totalPage;

    /**
     * 返回json数据
     * 无参
     *
     * @return
     */
    public static CinemaBaseReqVO success(String imgPre) {
        CinemaBaseReqVO baseReqVO = new CinemaBaseReqVO();
        baseReqVO.setImgPre(imgPre);
        baseReqVO.setMsg("");
        baseReqVO.setNowPage("");
        baseReqVO.setStatus(0);
        baseReqVO.setTotalPage("");
        return baseReqVO;
    }

    public static CinemaBaseReqVO success(Object data, String imgPre) {
        CinemaBaseReqVO baseReqVO = new CinemaBaseReqVO();
        baseReqVO.setImgPre(imgPre);
        baseReqVO.setMsg("");
        baseReqVO.setNowPage("");
        baseReqVO.setStatus(0);
        baseReqVO.setTotalPage("");
        baseReqVO.setData(data);
        return baseReqVO;
    }

}
