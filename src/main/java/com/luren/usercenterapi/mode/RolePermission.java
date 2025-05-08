package com.luren.usercenterapi.mode;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色权限关联实体类
 */
@Data
public class RolePermission implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private Integer roleId;
    private Integer permissionId;
} 