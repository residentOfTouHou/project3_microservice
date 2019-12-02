package com.stylefeng.guns.rest.modular.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.utils.OrderBaseReqVO;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

    @Reference(interfaceClass = OrderService.class, check = false)
    OrderService orderService;

    @RequestMapping("buyTickets")
    public OrderBaseReqVO buyTickets() {

        return OrderBaseReqVO.fail(1001, "请先登录, 点击确定跳转到登录页面");
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
}
