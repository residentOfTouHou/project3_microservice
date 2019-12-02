package com.stylefeng.guns.rest.common.persistence.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Actors implements Serializable {
    private static final long serialVersionUID = -9032733393328415577L;
    String directorName;
    String imgAddress;
    String roleName;
}
