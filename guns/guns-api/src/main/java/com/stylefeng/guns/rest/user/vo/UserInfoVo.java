/**
 *
 */
package com.stylefeng.guns.rest.user.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoVo implements Serializable {
    private Integer uuid;

    private String username;

    private String nickname;

    private String email;

    private String phone;

    private Integer sex;

    private String birthday;

    private Integer lifeState;

    private String biography;

    private String headAddress;

    private String address;

    private Long createTime;

    private Long updateTime;
}
