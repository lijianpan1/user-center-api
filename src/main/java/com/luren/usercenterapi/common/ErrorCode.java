package com.luren.usercenterapi.common;

/**
 * 错误码
 *
 * @author lijianpan
 **/
public enum ErrorCode {

    SUCCESS(200, "ok", ""),
    PARAMS_ERROR(40000, "请求参数错误", ""),
    NULL_ERROR(40001, "请求数据为空", ""),
    NOT_LOGIN_ERROR(40001, "未登录", ""),
    NO_AUTH_ERROR(40003, "无权限", ""),
    SYSTEM_ERROR(50000, "系统内部异常", ""),
    TOKEN_ERROR(40102, "token验证失败", ""),
    USER_NOT_EXIST(40002, "用户不存在", ""),
    USER_EXIST(40103, "用户已存在", ""),
    USER_PASSWORD_ERROR(40004, "用户名或密码错误", "");

    /**
     * 错误码
     */
    private final int code;

    /**
     * 错误信息
     */
    private final String message;

    /**
     * 错误描述
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
