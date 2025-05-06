package com.luren.usercenterapi.util;

import com.luren.usercenterapi.common.BaseResponse;
import com.luren.usercenterapi.common.ErrorCode;
import com.luren.usercenterapi.exception.CustomException;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回结果
 */
@Data
public class ResultUtils {
    
    public static <T> BaseResponse<T> ok() {
        return new BaseResponse<T>(200, "成功", null);
    }
    
    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<>(200, "成功", data);
    }

    public static <T> BaseResponse<T> ok(String message,T data) {
        return new BaseResponse<>(200, message, data);
    }
    
    public static <T> BaseResponse<T> error() {
        return new BaseResponse<>(500, "失败", null);
    }
    
    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<>(500, message, null);
    }

    public static <T> BaseResponse<T> error(int code,String message) {
        return new BaseResponse<>(code,message,null,null);
    }
} 