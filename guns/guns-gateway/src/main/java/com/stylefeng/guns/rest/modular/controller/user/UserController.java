package com.stylefeng.guns.rest.modular.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.user.vo.AuthUserInfo;
import com.stylefeng.guns.rest.user.vo.BaseVo;
import com.stylefeng.guns.rest.user.vo.RequestUser;
import com.stylefeng.guns.rest.user.UserService;
import com.stylefeng.guns.rest.user.vo.UserInfoVo;
import com.stylefeng.guns.rest.vo.GunsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    RedisTemplate redisTemplate;

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

    @RequestMapping("logout")
    public BaseVo logout(HttpServletRequest request){
        BaseVo baseVo = new BaseVo();
        String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = requestHeader.substring(7);
        Boolean delete = redisTemplate.delete(authToken);
        if(delete){
            baseVo.setStatus(0);
            baseVo.setMsg("成功退出");
        }else{
            baseVo.setStatus(999);
            baseVo.setMsg("系统出现异常，请联系管理员");
        }
        return baseVo;
    }

    @RequestMapping("getUserInfo")
    public BaseVo getUserInfo(HttpServletRequest request){
        //gateway验证过了，无需判空
        String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = requestHeader.substring(7);
        Object o = redisTemplate.opsForValue().get(authToken);
        if(o != null){
            String s = (String) o;
            BaseVo baseVo = userService.getUserInfoByUsername(s);
            return baseVo;
        }else {
            BaseVo baseVo = new BaseVo();
            baseVo.setStatus(1);
            baseVo.setMsg("未登录");
            return baseVo;
        }
    }

    @RequestMapping("updateUserInfo")
    public BaseVo updateUserInfo(UserInfoVo userInfoVo){
        BaseVo baseVo = userService.updateUser(userInfoVo);
        return baseVo;
    }


}
