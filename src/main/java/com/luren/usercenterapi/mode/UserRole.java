package com.luren.usercenterapi.mode;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色关联实体类
 */
@Data
public class UserRole implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    private int userId;
    private int roleId;
} 