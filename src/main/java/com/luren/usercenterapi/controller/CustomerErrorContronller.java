package com.luren.usercenterapi.controller;

import com.luren.usercenterapi.common.BaseResponse;
import com.luren.usercenterapi.common.ErrorCode;
import com.luren.usercenterapi.util.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 安全配置异常统一处理
 *
 * @author lijianpan
 */
@RestController
public class CustomerErrorContronller {

    @GetMapping("/error")
    public BaseResponse<String> handleError(HttpServletRequest request) {
        try {
            ErrorCode errorCode = (ErrorCode) request.getAttribute("errorCode");

            if (errorCode == null) {
                return ResultUtils.error(ErrorCode.SYSTEM_ERROR);
            }

            return ResultUtils.error(errorCode);
        }catch (Exception e){
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR);
        }
    }
}
