package com.stylefeng.guns.rest.modular.controller.order;

import com.stylefeng.guns.rest.utils.FailedBaseReqVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @RequestMapping("order/buyTickets")
    public FailedBaseReqVO buyTickets() {

        return FailedBaseReqVO.fail(1001, "请先登录, 点击确定跳转到登录页面");
    }
}
