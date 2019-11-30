package com.stylefeng.guns.rest.user;

import com.stylefeng.guns.rest.user.vo.RequestUser;
import com.stylefeng.guns.rest.vo.GunsVo;

/**
 * Created by IntelliJ IDEA
 *
 * @author zhanghj
 * @date 2019/11/27
 * @time 17:40
 */
public interface UserService {

    GunsVo register(RequestUser user);

    GunsVo check(String username);

    Integer login(String userName, String password);
}
