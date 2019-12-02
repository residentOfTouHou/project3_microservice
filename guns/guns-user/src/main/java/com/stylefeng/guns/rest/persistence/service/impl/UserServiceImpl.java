package com.stylefeng.guns.rest.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.persistence.dao.MtimeUserTMapper;
import com.stylefeng.guns.rest.persistence.model.MtimeUserT;
import com.stylefeng.guns.rest.user.UserService;
import com.stylefeng.guns.rest.user.vo.BaseVo;
import com.stylefeng.guns.rest.user.vo.RequestUser;
import com.stylefeng.guns.rest.user.vo.UserInfoVo;
import com.stylefeng.guns.rest.user.vo.UserVo;
import com.stylefeng.guns.rest.vo.GunsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Time;
import java.util.Collections;
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

    @Override
    public BaseVo getUserInfoByUsername(String usernameFromToken) {
        BaseVo baseVo = new BaseVo();
        EntityWrapper<MtimeUserT> mtimeUserTEntityWrapper = new EntityWrapper<>();
        mtimeUserTEntityWrapper.eq("user_name", usernameFromToken);
        List<MtimeUserT> mtimeUserTS = mtimeUserTMapper.selectList(mtimeUserTEntityWrapper);
        if (mtimeUserTS.size() != 1) {
            baseVo.setStatus(999);
            baseVo.setMsg("系统出现异常，请联系管理员");
            return baseVo;
        } else {
            MtimeUserT user = mtimeUserTS.get(0);
            UserInfoVo userInfo = new UserInfoVo();
            userInfo.setUuid(user.getUuid());
            userInfo.setUsername(user.getUserName());
            userInfo.setNickname(user.getNickName());
            userInfo.setEmail(user.getEmail());
            userInfo.setPhone(user.getUserPhone());
            userInfo.setSex(user.getUserSex());
            userInfo.setBirthday(user.getBirthday());
            userInfo.setLifeState(user.getLifeState());
            userInfo.setBiography(user.getBiography());
            userInfo.setHeadAddress(user.getAddress());
            userInfo.setCreateTime(user.getBeginTime().getTime());
            userInfo.setUpdateTime(user.getUpdateTime().getTime());
            baseVo.setStatus(0);
            baseVo.setData(userInfo);

            return baseVo;
        }
    }

    @Override
    public BaseVo updateUser(UserInfoVo userInfoVo) {
        BaseVo baseVo = new BaseVo();
        MtimeUserT userT = new MtimeUserT();
        if (userInfoVo.getUuid() != null) {
            userT.setUuid(userInfoVo.getUuid());

            if (userInfoVo.getNickname() != null) {
                userT.setNickName(userInfoVo.getNickname());
            }
            if (userInfoVo.getSex() != null) {
                userT.setUserSex(userInfoVo.getSex());
            }
            if (userInfoVo.getBirthday() != null) {
                userT.setBirthday(userInfoVo.getBirthday());
            }
            if (userInfoVo.getEmail() != null) {
                userT.setEmail(userInfoVo.getEmail());
            }
            if (userInfoVo.getPhone() != null) {
                userT.setUserPhone(userInfoVo.getPhone());
            }
            if (userInfoVo.getAddress() != null) {
                userT.setAddress(userInfoVo.getAddress());
            }
            if (userInfoVo.getBiography() != null) {
                userT.setBiography(userInfoVo.getBiography());
            }
            if (userInfoVo.getLifeState() != null) {
                userT.setLifeState(userInfoVo.getLifeState());
            }
            userT.setUpdateTime(new Date());
        } else {
            baseVo.setMsg("用户信息修改失败");
            baseVo.setStatus(1);
            return baseVo;
        }
        Integer integer = mtimeUserTMapper.updateById(userT);
        if (integer == 1) {
            baseVo.setStatus(0);
            baseVo.setMsg("信息修改成功");
            MtimeUserT userT1 = mtimeUserTMapper.selectById(userT);
            return getUserInfoByUsername(userT1.getUserName());
        } else {
            baseVo.setMsg("用户信息修改失败");
            baseVo.setStatus(1);
            return baseVo;
        }
    }
}
