package com.stylefeng.guns.rest.persistence.dao;

import com.stylefeng.guns.rest.persistence.model.MoocOrderT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.rest.vo.OrderVo3;
import com.stylefeng.guns.rest.vo.OrderVo2;

import java.util.List;


/**
 * <p>
 * 订单信息表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-12-01
 */
public interface MoocOrderTMapper extends BaseMapper<MoocOrderT> {

    List<OrderVo3> selectOrderInfoByUserId(Integer userId);

    List<OrderVo2> selectOrderInfoByUserId2(Integer userId);

    void insertAndReturnId(MoocOrderT moocOrderT);

}
