package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class IndexVO implements Serializable {
    private static final long serialVersionUID = -5297486005232262038L;

    private List<BannerVO> banners;

    private FilmVO HotFilms;

    private FilmVO SoonFilms;

    private List<FilmInfoVO> BoxRanking;

    private List<FilmInfoVO> ExpectRanking;

    private List<FilmInfoVO> Top100;
}
