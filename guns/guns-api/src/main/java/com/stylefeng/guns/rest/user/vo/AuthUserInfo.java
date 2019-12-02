/**
 *
 */
package com.stylefeng.guns.rest.user.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthUserInfo implements Serializable {
    private String userName;

    private Long expireTime;
}
