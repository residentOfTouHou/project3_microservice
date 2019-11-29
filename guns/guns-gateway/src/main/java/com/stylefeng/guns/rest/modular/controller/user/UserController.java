package com.stylefeng.guns.rest.modular.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.user.UserService;
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

    @Reference(interfaceClass = UserService.class,check = false)
    UserService userService;


}
