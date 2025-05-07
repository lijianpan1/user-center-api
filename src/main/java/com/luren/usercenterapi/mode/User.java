package com.luren.usercenterapi.mode;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User {
    /**
     * 用户唯一标识
     */
    @TableId(type = IdType.AUTO)
    private int id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码（加密存储）
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户状态（0：有效，1：无效）
     */
    private Integer status;

    /**
     * 用户创建时间
     */
    private Date createdDate;

    /**
     * 用户信息更新时间
     */
    private Date updatedDate;

    /**
     * 是否删除（0：否，1：是）
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 密码校验字段
     */
    @TableField(exist = false)
    private String checkPassword;
}