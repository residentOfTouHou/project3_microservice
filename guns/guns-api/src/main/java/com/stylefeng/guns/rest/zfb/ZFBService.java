package com.stylefeng.guns.rest.zfb;

import com.stylefeng.guns.rest.user.vo.BaseVo;

public interface ZFBService {
    BaseVo getPayResult(String orderId, Integer tryNums);

    String generateQRCode(String orderId, String amount);
}
