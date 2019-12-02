package service.impl;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.rest.persistence.model.MoocOrderT;
import com.stylefeng.guns.rest.persistence.dao.MoocOrderTMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单信息表 服务实现类
 * </p>
 *
 * @author shengyang
 * @since 2019-12-02
 */
@Service
public class MoocOrderTServiceImpl extends ServiceImpl<MoocOrderTMapper, MoocOrderT> implements IService<MoocOrderT> {

}
