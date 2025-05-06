package com.luren.usercenterapi.exception;

import com.luren.usercenterapi.common.ErrorCode;
import lombok.Data;

@Data
public class CustomException extends RuntimeException {

    private int code;
    private String message;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public CustomException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
        this.message = message;
    }
}