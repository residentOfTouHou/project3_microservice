/**
 *
 */
package com.stylefeng.guns.rest.common.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.aliyun.oss.OSSClient;
import com.stylefeng.guns.rest.component.AliyunComponent;
import com.stylefeng.guns.rest.common.persistence.utils.ZFBUtils;
import com.stylefeng.guns.rest.user.vo.BaseVo;
import com.stylefeng.guns.rest.zfb.ZFBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;

@Component
@Service(interfaceClass = ZFBService.class)
public class ZFBServiceImpl implements ZFBService {

    @Autowired
    AliyunComponent aliyunComponent;

    @Override
    public BaseVo getPayResult(String orderId, Integer tryNums) {
        BaseVo baseVo = new BaseVo();
        String result = ZFBUtils.queryPayStatus(orderId);
        if ("支付成功".equals(result)) {
            baseVo.setStatus(0);
            HashMap<String, Object> map = new HashMap<>();
            map.put("orderId", orderId);
            map.put("orderStatus", 1);
            map.put("orderMsg", "支付成功");
            baseVo.setData(map);
        } else {
            baseVo.setStatus(1);
            baseVo.setMsg("订单支付失败，请稍后重试");
        }
        return baseVo;
    }

    @Override
    public String generateQRCode(String orderId, String amount) {
        String filepath = ZFBUtils.generateQRcode(orderId, amount);
        File file = new File(filepath);
        String filename = "Mtime" + orderId.hashCode() + orderId;
        OSSClient ossClient = aliyunComponent.getOssClient();
        ossClient.putObject(aliyunComponent.getOss().getBucket(),filename,file);
        String url = "http://" + aliyunComponent.getOss().getBucket() + "." + aliyunComponent.getOss().getEndPoint() +
                "/" + filename;
        return url;
    }
}


