package com.stylefeng.guns.rest.common.persistence.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ActorsAndDirector implements Serializable {
    private static final long serialVersionUID = -6783452234147305104L;
    List<Actors> actors;
    Actors director;
}
