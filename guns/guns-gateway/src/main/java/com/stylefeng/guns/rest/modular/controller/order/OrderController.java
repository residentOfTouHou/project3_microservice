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
import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.cinema.vo.BaseRespVo;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.user.vo.BaseVo;
import com.stylefeng.guns.rest.zfb.ZFBService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
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

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("order")
public class OrderController {

    @Reference(interfaceClass = OrderService.class,check = false)
    OrderService orderService;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RedisTemplate redisTemplate;

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
        if(userName==null){
            //调用logout 删除 token 退出
            return RespBeanUtil.beanUtil(RespEnum.TOKEN_OUTTIME.getStatus(),RespEnum.TOKEN_OUTTIME.getMsg(),null);
        }

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
   /* @RequestMapping("getPayResult")
    public OrderBaseReqVO getPayResult(Integer orderId, Integer tryNums) {
        // 查询订单状态
        OrderVO orderVO = orderService.selectOrderById();
        // 是否支付
        boolean payStatus =

        if (orderVO.getOrderStatus == 1 && tryNums <= 3) {
            // 支付成功
            return OrderBaseReqVO.success();

        } else if (tryNums < 3 && orderVO.orderStatus == 0) {
            // 未支付
            return OrderBaseReqVO.fail();
        }
        // 三次后修改订单状态，订单关闭
        orderService.updateOrderStatusById();
        return OrderBaseReqVO.fail();
    }
*/
    @Reference(interfaceClass = ZFBService.class)
    ZFBService zfbService;

//    @Reference(interfaceClass = OrderService.class)
//    OrderService orderService;

    @RequestMapping("getPayInfo")
    public BaseRespVo getPayInfo(@Param("orderId")String orderId){
        BaseRespVo baseRespVo = new BaseRespVo();
        HashMap<String, String> map = new HashMap<>();
        String amount = orderService.getAmountById(orderId);
        if(amount != null) {
            String address = zfbService.generateQRCode(orderId, amount);
            if (address != null && !"".equals(address)) {
                baseRespVo.setStatus(0);
                baseRespVo.setImgPre("/http://www.duolaima.com");
                map.put("orderId", orderId);
                map.put("qRCodeAddress", address);
                baseRespVo.setData(map);
                return baseRespVo;
            }
        }
        baseRespVo.setStatus(1);
        baseRespVo.setMsg("获取二维码失败");
        return baseRespVo;
    }

    @RequestMapping("getPayResult")
    public BaseVo getPayResult(String orderId,Integer tryNums){
        BaseVo payResult = zfbService.getPayResult(orderId, tryNums);
        if(payResult.getStatus() == 0 && "支付成功".equals(payResult.getMsg())){
            orderService.updateOrderStatus(orderId,1);
        }

        return payResult;
    }
}
