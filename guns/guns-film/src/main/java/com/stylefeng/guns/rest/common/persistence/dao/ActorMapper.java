package com.stylefeng.guns.rest.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.rest.common.persistence.model.Actors;

import java.util.List;

public interface ActorMapper extends BaseMapper<Actors> {

    List<Actors> selectActors(Integer filmId);

    Actors selectDirector(Integer filmId);
}
