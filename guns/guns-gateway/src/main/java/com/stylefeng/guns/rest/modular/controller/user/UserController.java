package com.stylefeng.guns.rest.modular.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.user.vo.RequestUser;
import com.stylefeng.guns.rest.user.UserService;
import com.stylefeng.guns.rest.vo.GunsVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/11/27
 * @time 20:02
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Reference(interfaceClass = UserService.class)
    UserService userService;

    @RequestMapping("register")
    public GunsVo userRegister(RequestUser user){
        GunsVo gunsVo = userService.register(user);
        return gunsVo;
    }

    @RequestMapping("check")
    public GunsVo userCheck(String username){
        GunsVo gunsVo = userService.check(username);
        return gunsVo;
    }


}
