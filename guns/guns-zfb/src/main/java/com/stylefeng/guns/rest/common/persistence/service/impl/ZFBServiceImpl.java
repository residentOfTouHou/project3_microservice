/**
 *
 */
package com.stylefeng.guns.rest.common.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.aliyun.oss.OSSClient;
import com.stylefeng.guns.rest.component.AliyunComponent;
import com.stylefeng.guns.rest.common.persistence.utils.ZFBUtils;
import com.stylefeng.guns.rest.order.vo.OrderVo;
import com.stylefeng.guns.rest.persistence.dao.OrderTMapper;
import com.stylefeng.guns.rest.persistence.model.MoocOrderT;
import com.stylefeng.guns.rest.zfb.ZFBService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Service(interfaceClass = ZFBService.class)
public class ZFBServiceImpl implements ZFBService {

    @Autowired
    AliyunComponent aliyunComponent;

    @Autowired
    OrderTMapper moocOrderTMapper;

   /* @Override
    public BaseVo getPayResult(String orderId, Integer tryNums) {
        BaseVo baseVo = new BaseVo();
        HashMap<String, Object> map = new HashMap<>();

        int result = ZFBUtils.queryPayStatus(orderId + "MtimeCinema");
        if(tryNums > 3){
            map.put("orderId",orderId);
            map.put("orderStatus",0);
            map.put("orderMsg","支付失败");
            baseVo.setStatus(0);
            baseVo.setData(map);
            return baseVo;
        }
        if(result == 0){
            map.put("orderId",orderId);
            map.put("orderStatus",1);
            map.put("orderMsg","支付成功");
            baseVo.setMsg("支付成功");
            baseVo.setStatus(0);
            baseVo.setData(map);
        }else{
            baseVo.setMsg("订单支付失败，请稍后重试");
            baseVo.setStatus(1);
        }
        return baseVo;
    }*/

    @Override
    public String generateQRCode(String orderId, String amount) {
        String filepath = ZFBUtils.generateQRcode(orderId, amount);
        File file = new File(filepath);
        String filename = "Mtime" + orderId.hashCode() + orderId;
        OSSClient ossClient = aliyunComponent.getOssClient();
        ossClient.putObject(aliyunComponent.getOss().getBucket(), filename, file);
        String url = "http://" + aliyunComponent.getOss().getBucket() + "." + aliyunComponent.getOss().getEndPoint() +
                "/" + filename;
        return url;
    }


    @Override
    public OrderVo isPay(int orderId) {
        // 查询订单状态
        MoocOrderT moocOrder = moocOrderTMapper.selectById(orderId);

        int result = ZFBUtils.queryPayStatus(orderId + "MtimeCinema");
        if (result == 0) {
            // 支付成功，修改订单状态
            int orderStatus = 1;
            moocOrder.setOrderStatus(1);
            moocOrderTMapper.updateOrderById(orderId, orderStatus);
            OrderVo orderVo = convert2OrderVo(moocOrder);
            return orderVo;
        }
        OrderVo orderVo = convert2OrderVo(moocOrder);
        return orderVo;
    }

    @Override
    public int updateOrderStatusById(int orderId) {
        int orderStatus = 2;
        int update = moocOrderTMapper.updateOrderById(orderId, orderStatus);
        if (update != 0) {
            return 1;
        }
        return 0;
    }

    private OrderVo convert2OrderVo(MoocOrderT moocOrder) {
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(moocOrder, orderVo);
        orderVo.setOrderId(Integer.parseInt(moocOrder.getUuid()));
        return orderVo;
    }
}


