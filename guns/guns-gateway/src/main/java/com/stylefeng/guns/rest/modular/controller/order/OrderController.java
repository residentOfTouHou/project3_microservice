package com.stylefeng.guns.rest.modular.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.utils.OrderBaseReqVO;
import org.springframework.data.domain.Sort;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.common.RespEnum;
import com.stylefeng.guns.rest.order.vo.OrderVo;
import com.stylefeng.guns.rest.util.RespBeanUtil;
import com.stylefeng.guns.rest.vo.GunsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("order")
public class OrderController {

    private final String IMG_PRE = "http://img.meetingshop.cn/";

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RedisTemplate redisTemplate;

    @Reference(interfaceClass = OrderService.class, check = false)
    OrderService orderService;

    @RequestMapping("getOrderInfo")
    public GunsVo getOrderInfo(HttpServletRequest request, Integer nowPage, Integer pageSize) {
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
        GunsVo gunsVo = orderService.getOrderInfoByUserName(username, nowPage, pageSize);
        return gunsVo;
    }

    @RequestMapping("buyTickets")
    public GunsVo buyTickets(String fieldId, String soldSeats, String seatsName, HttpServletRequest request) {
        //验证座位号
        Integer trueSeats = orderService.isTrueSeats(fieldId, soldSeats);
        if (trueSeats == -1) {
            return RespBeanUtil.beanUtil(RespEnum.SEAT_ERROR.getStatus(), RespEnum.SEAT_ERROR.getMsg(), null);
        } else if (trueSeats == -9) {
            return RespBeanUtil.beanUtil(RespEnum.SEAT_LIMIT.getStatus(), RespEnum.SEAT_LIMIT.getMsg(), null);
        }
        //验证是否卖出
        boolean notSoldSeats = orderService.isNotSoldSeats(fieldId, soldSeats);
        if (notSoldSeats) {
            return RespBeanUtil.beanUtil(RespEnum.SEAT_BUYED.getStatus(), RespEnum.SEAT_BUYED.getMsg(), null);
        }
        //创建订单
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
        }
        //校验token抛出异常
        String userName = (String) redisTemplate.opsForValue().get(authToken);

        OrderVo orderVo = orderService.saveOrderInfo(fieldId, soldSeats, seatsName, userName);
        return RespBeanUtil.beanUtil(RespEnum.NORMAL_RESP.getStatus(), "", orderVo);
    }

    /**
     * 获取支付结果
     * 三次请求后未支付订单会关闭
     *
     * @param orderId
     * @param tryNums
     * @return
     */
    @RequestMapping("getPayResult")
    public OrderBaseReqVO getPayResult(Integer orderId, Integer tryNums) {
       /* // 查询订单状态
        OrderVo orderVo = orderService.selectOrderById(orderId);*/
        // 是否支付成功, 返回订单详情
        OrderVo orderVo = orderService.isPay(orderId);

        if (orderVo.getOrderStatus() == 1 && tryNums <= 3) {
            // 支付成功
            Map<String, Object> data = new HashMap<>();
            data.put("orderId", orderVo.getOrderId());
            data.put("orderMsg", "支付成功！");
            data.put("orderStatus", orderVo.getOrderStatus());
            return OrderBaseReqVO.success(data, IMG_PRE);
        } else if (tryNums < 3 && orderVo.getOrderStatus() == 0) {
            // 未支付
            String massage = "支付失败！";
            return OrderBaseReqVO.fail(0, massage);
        }
        // 三次后修改订单状态，订单关闭
        orderService.updateOrderStatusById();
        String message = "支付失败，订单即将关闭，请重新下单";
        return OrderBaseReqVO.fail(2, message);
    }

}
