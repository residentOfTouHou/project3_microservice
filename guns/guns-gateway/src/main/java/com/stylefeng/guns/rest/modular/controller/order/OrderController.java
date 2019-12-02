package com.stylefeng.guns.rest.modular.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.cinema.vo.BaseRespVo;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.user.vo.BaseVo;
import com.stylefeng.guns.rest.zfb.ZFBService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("order")
public class OrderController {

    @Reference(interfaceClass = ZFBService.class)
    ZFBService zfbService;

//    @Reference(interfaceClass = OrderService.class)
//    OrderService orderService;

    @RequestMapping("getPayInfo")
    public BaseRespVo getPayInfo(@Param("orderId")String orderId){
        BaseRespVo baseRespVo = new BaseRespVo();
        HashMap<String, Object> map = new HashMap<>();
        String amount = "30";
        String address = zfbService.generateQRCode(orderId,amount);
        if(address != null && !"".equals(address)){
            baseRespVo.setStatus(0);
            baseRespVo.setImgPre("");
            map.put("orderId",orderId);
            map.put("qRCodeAddress",address);
            baseRespVo.setData(map);
            return baseRespVo;
        }
        baseRespVo.setStatus(1);
        baseRespVo.setMsg("获取二维码失败");
        return baseRespVo;
    }

    @RequestMapping("getPayResult")
    public BaseVo getPayResult(String orderId,Integer tryNums){
        return zfbService.getPayResult(orderId,tryNums);
    }

}
