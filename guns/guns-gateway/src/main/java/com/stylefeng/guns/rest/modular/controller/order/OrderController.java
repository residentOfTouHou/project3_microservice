package com.stylefeng.guns.rest.modular.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.common.RespEnum;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.order.vo.OrderVo;
import com.stylefeng.guns.rest.util.RespBeanUtil;
import com.stylefeng.guns.rest.vo.GunsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("order")
public class OrderController {

    @Reference(interfaceClass = OrderService.class,check = false)
    OrderService orderService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    JwtProperties jwtProperties;

    @RequestMapping("buyTickets")
    public GunsVo buyTickets(String fieldId, String soldSeats, String seatsName, HttpServletRequest request){
        //验证座位号
        Integer trueSeats = orderService.isTrueSeats(fieldId, soldSeats);
        if(trueSeats==-1){
            return RespBeanUtil.beanUtil(RespEnum.SEAT_ERROR.getStatus(),RespEnum.SEAT_ERROR.getMsg(),null);
        }else if(trueSeats == -9) {
            return RespBeanUtil.beanUtil(RespEnum.SEAT_LIMIT.getStatus(),RespEnum.SEAT_LIMIT.getMsg(),null);
        }
        //验证是否卖出
        boolean notSoldSeats = orderService.isNotSoldSeats(fieldId, soldSeats);
        if(notSoldSeats){
            return RespBeanUtil.beanUtil(RespEnum.SEAT_BUYED.getStatus(),RespEnum.SEAT_BUYED.getMsg(),null);
        }
        //创建订单
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
        }
        //校验token抛出异常
        String userName = (String) redisTemplate.opsForValue().get(authToken);
        if(userName==null){
            //调用logout 删除 token 退出
            return RespBeanUtil.beanUtil(RespEnum.TOKEN_OUTTIME.getStatus(),RespEnum.TOKEN_OUTTIME.getMsg(),null);
        }

        OrderVo orderVo = orderService.saveOrderInfo(fieldId, soldSeats, seatsName, userName);
        return RespBeanUtil.beanUtil(RespEnum.NORMAL_RESP.getStatus(),"",orderVo);
    }
}
