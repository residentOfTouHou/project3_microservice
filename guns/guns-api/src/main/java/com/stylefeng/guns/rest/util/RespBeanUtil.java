package com.stylefeng.guns.rest.util;

import com.stylefeng.guns.rest.vo.GunsVo;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/12/1
 * @time 23:12
 */
public class RespBeanUtil {
    public static GunsVo beanUtil(Integer status,String msg,Object data){
        GunsVo gunsVo = new GunsVo();
        gunsVo.setStatus(status);
        gunsVo.setMsg(msg);
        if(data!=null){
            gunsVo.setData(data);
        }
        return gunsVo;
    }
}
