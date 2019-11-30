package com.stylefeng.guns.rest.user.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RequestUser implements Serializable {
    private static final long serialVersionUID = 5843846394461571710L;
    private String username;
    private String password;
    private String mobile;
    private String email;
    private String address;
}
