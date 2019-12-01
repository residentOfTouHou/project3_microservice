package com.stylefeng.guns.rest.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.persistence.dao.MtimeUserTMapper;
import com.stylefeng.guns.rest.persistence.model.MtimeUserT;
import com.stylefeng.guns.rest.user.UserService;
import com.stylefeng.guns.rest.user.vo.RequestUser;
import com.stylefeng.guns.rest.user.vo.UserVo;
import com.stylefeng.guns.rest.vo.GunsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/11/27
 * @time 20:37
 */
@Component
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    UserService userService;

    @Autowired
    MtimeUserTMapper mtimeUserTMapper;


    /**
     * 注册方法
     */
    @Override
    public GunsVo register(RequestUser user) {
        //给数据据库表对象赋值
        MtimeUserT mtimeUserT = new MtimeUserT();
        mtimeUserT.setUserName(user.getUsername());
        mtimeUserT.setUserPwd(MD5Util.encrypt(user.getPassword()));
        mtimeUserT.setUserPhone(user.getMobile());
        mtimeUserT.setAddress(user.getAddress());
        mtimeUserT.setEmail(user.getEmail());
        mtimeUserT.setBeginTime(new Date());
        mtimeUserT.setUpdateTime(new Date());

        String username = user.getUsername();
        List<MtimeUserT> userTList = mtimeUserTMapper.selectList(new EntityWrapper<MtimeUserT>().eq("user_name", username));
        //用户已经存在
        try{
            if(!CollectionUtils.isEmpty(userTList)){
                return new GunsVo(1,"用户已存在");
            }
            Integer result = mtimeUserTMapper.insert(mtimeUserT);
            return new GunsVo(0,"注册成功");
        }catch (Exception e){
            return new GunsVo(999,"系统出现异常，请联系管理员");
        }
    }

    /**
     * 校验用户名方法
     */
    @Override
    public GunsVo check(String username) {
        //用用户名去数据库查找
        List<MtimeUserT> userTList = mtimeUserTMapper.selectList(new EntityWrapper<MtimeUserT>().eq("user_name", username));
        if(!CollectionUtils.isEmpty(userTList)){
            return new GunsVo(1,"用户已经注册");
        }else if (CollectionUtils.isEmpty(userTList)){
            return new GunsVo(0,"用户名不存在");
        }else {
            return new GunsVo(999,"系统出现异常，请联系管理员");
        }
    }

    /**
     * 登陆验证
     * 返回0代表校验正确 1代表错误 -1代表异常
     */
    @Override
    public Integer login(String userName, String password) {
        try{
            List<MtimeUserT> userTList = mtimeUserTMapper.selectList(new EntityWrapper<MtimeUserT>()
                    .eq("user_name",userName).eq("user_pwd",password));
            if(!CollectionUtils.isEmpty(userTList)){
                return 0;
            }else if(CollectionUtils.isEmpty(userTList)){
                return 1;
            }else {
                return -1;
            }
        }catch (Exception e){
            return -1;
        }
    }
}
