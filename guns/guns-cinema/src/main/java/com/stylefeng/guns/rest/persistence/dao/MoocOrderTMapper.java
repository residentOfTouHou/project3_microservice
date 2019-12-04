package com.stylefeng.guns.rest.persistence.dao;

import com.stylefeng.guns.rest.persistence.model.MoocOrderT;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 订单信息表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-12-03
 */
public interface MoocOrderTMapper extends BaseMapper<MoocOrderT> {

    List<String> selectSeatsByFieldId(int fieldId);
}
