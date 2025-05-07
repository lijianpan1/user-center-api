package com.luren.usercenterapi.mode;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色实体类
 */
@Data
public class Role implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name;
    private String description;
}