package service.impl;

import com.stylefeng.guns.rest.persistence.model.MoocOrderT;
import com.stylefeng.guns.rest.persistence.dao.OrderTMapper;
import service.IMoocOrderTService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单信息表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2019-12-03
 */
@Service
public class MoocOrderTServiceImpl extends ServiceImpl<OrderTMapper, MoocOrderT> implements IMoocOrderTService {

}
