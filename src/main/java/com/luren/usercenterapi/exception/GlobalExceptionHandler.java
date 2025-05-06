package com.luren.usercenterapi.exception;

import com.luren.usercenterapi.common.BaseResponse;
import com.luren.usercenterapi.common.ErrorCode;
import com.luren.usercenterapi.util.ResultUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public BaseResponse<ErrorCode> handleCustomException(CustomException ex) {
        return ResultUtils.error(ex.getCode(),ex.getMessage());
    }
}