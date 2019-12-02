package com.stylefeng.guns.rest.persistence.dao;

import com.stylefeng.guns.rest.persistence.model.MoocOrderT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单信息表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-12-02
 */
public interface MoocOrderTMapper extends BaseMapper<MoocOrderT> {

    void insertAndReturnId(MoocOrderT moocOrderT);

}
