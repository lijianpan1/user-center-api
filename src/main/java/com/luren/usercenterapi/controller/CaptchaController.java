package com.luren.usercenterapi.controller;

import com.luren.usercenterapi.common.BaseResponse;
import com.luren.usercenterapi.util.CaptchaUtil;
import com.luren.usercenterapi.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码控制器
 */
@RestController
@RequestMapping("/api/captcha")
public class CaptchaController {

    @Autowired
    private CaptchaUtil captchaUtil;
    
    /**
     * 获取验证码
     */
    @GetMapping("/get")
    public BaseResponse<CaptchaUtil.CaptchaResult> getCaptcha() {
        CaptchaUtil.CaptchaResult captchaResult = captchaUtil.generateCaptcha();
        return ResultUtils.ok(captchaResult);
    }
} 