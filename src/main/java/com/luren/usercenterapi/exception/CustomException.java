package com.luren.usercenterapi.exception;

import com.luren.usercenterapi.common.ErrorCode;
import lombok.Data;

/**
 * 自定义异常
 *
 * @author lijianpan
 **/
@Data
public class CustomException extends RuntimeException {

    private int code;
    /**
     * 描述
     */
    private String description;


    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public CustomException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }
}