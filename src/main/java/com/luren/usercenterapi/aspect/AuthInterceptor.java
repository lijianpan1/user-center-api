package com.luren.usercenterapi.aspect;

import com.luren.usercenterapi.annotation.AuthCheck;
import com.luren.usercenterapi.mode.dto.LoginResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: AuthInterceptor
 * @Description: TODO
 * @Author: lijianpan
 * @CreateTime: 2025-06-04  15:20
 * @Version: 1.0
 */
@Aspect
@Component
public class AuthInterceptor {

    @Around("@annotation(authCheck)")
    public void checkAuth(JoinPoint joinPoint, AuthCheck authCheck) {
        System.out.println("用户角色检验");
        String role = authCheck.mustRole();

        //获取全局上下文信息
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        System.out.println(request.getHeader("Authorization"));
    }
}