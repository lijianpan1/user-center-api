package com.luren.usercenterapi.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应结果类
 *
 * @author lijianpan
 **/
@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private String message;
    private T data;
    private String description;

    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(int code, String message, T data, String description) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.description = description;
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public BaseResponse(ErrorCode errorCode,String description) {
        this(errorCode.getCode(), errorCode.getMessage(), null,description);
    }
}
