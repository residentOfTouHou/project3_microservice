package com.stylefeng.guns.rest.modular.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.vo.GunsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RedisTemplate redisTemplate;

    @Reference(interfaceClass = OrderService.class,check = false)
    OrderService orderService;

    @RequestMapping("getOrderInfo")
    public GunsVo getOrderInfo(HttpServletRequest request,Integer nowPage,Integer pageSize){
        //在请求头中拿到token
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String token = null;
        String username = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
            //用token去redis中拿到username
            username = (String) redisTemplate.opsForValue().get(token);
        }
        //调用方法获取订单信息
        GunsVo gunsVo = orderService.getOrderInfoByUserName(username,nowPage,pageSize);
        return gunsVo;
    }
}
