package com.luren.usercenterapi.exception;

import com.luren.usercenterapi.common.BaseResponse;
import com.luren.usercenterapi.common.ErrorCode;
import com.luren.usercenterapi.util.ResultUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author lijianpan
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public BaseResponse<?> handleCustomException(CustomException ex) {
        return ResultUtils.error(ex.getCode(),ex.getMessage(),ex.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage());
    }
}