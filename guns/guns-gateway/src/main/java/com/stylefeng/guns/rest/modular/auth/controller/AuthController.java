package com.stylefeng.guns.rest.modular.auth.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.exception.BizExceptionEnum;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthRequest;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.modular.auth.validator.IReqValidator;
import com.stylefeng.guns.rest.user.UserService;
import com.stylefeng.guns.rest.user.vo.UserVo;
import com.stylefeng.guns.rest.vo.GunsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 请求验证的
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:22
 */
@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisTemplate redisTemplate;

<<<<<<< HEAD
    @Reference(interfaceClass = UserService.class, check = false)
=======
    @Reference(interfaceClass = UserService.class,check = false)
>>>>>>> cb163589c4a5164d6225c8930191bd0d21933170
    UserService userService;

    @Resource(name = "simpleValidator")
    private IReqValidator reqValidator;

    @RequestMapping(value = "${jwt.auth-path}")
    //public ResponseEntity<?> createAuthenticationToken(AuthRequest authRequest,String userName,String password) {
    public GunsVo createAuthenticationToken(AuthRequest authRequest, String userName, String password) {
        //验证用户名和密码  返回0代表校验正确 1代表错误 -1代表异常
<<<<<<< HEAD
        String password2 = MD5Util.encrypt(password);
        Integer result = userService.login(userName, password2);
=======
        String password2 = null;
        //判空
        if(userName != null && password != null){
           password2 = MD5Util.encrypt(password);
        }else {
            return new GunsVo(999,"账号密码不能为空！");
        }
        Integer result = userService.login(userName,password2);
>>>>>>> cb163589c4a5164d6225c8930191bd0d21933170
        if (result == 0) {
            final String randomKey = jwtTokenUtil.getRandomKey();
            final String token = jwtTokenUtil.generateToken(authRequest.getUserName(), randomKey);

            //将token和用户信息存储到redis中 并设置过期时间
<<<<<<< HEAD
            redisTemplate.opsForValue().set(token, userName);
            redisTemplate.expire(token, 5 * 60, TimeUnit.SECONDS);
=======
            redisTemplate.opsForValue().set(token,userName);
            redisTemplate.expire(token,100 * 60, TimeUnit.SECONDS);
>>>>>>> cb163589c4a5164d6225c8930191bd0d21933170

            Map<String, String> map = new HashMap<>();
            map.put("randomKey", randomKey);
            map.put("token", token);

            GunsVo gunsVo = new GunsVo();
            gunsVo.setStatus(0);
            gunsVo.setData(map);
            return gunsVo;
            // return ResponseEntity.ok(new AuthResponse(token, randomKey));
        } else if (result == 1) {
            return new GunsVo(1, "用户名或密码错误");
            //throw new GunsException(BizExceptionEnum.AUTH_REQUEST_ERROR);
        } else {
            return new GunsVo(999, "系统出现异常，请联系管理员");
        }
    }
}
