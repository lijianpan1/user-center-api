package com.luren.usercenterapi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionHandlingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @AfterThrowing(pointcut = "execution(* com.luren.usercenterapi.service.*.*(..))", throwing = "ex")
    public void handleException(JoinPoint joinPoint, Exception ex) {
        // 记录异常日志，包括堆栈跟踪
        logger.error("Exception occurred in {}.{}: {}", 
                joinPoint.getTarget().getClass().getSimpleName(), 
                joinPoint.getSignature().getName(), 
                ex.getMessage(), ex);

        // 可以在此处添加更多处理逻辑，例如返回标准化错误响应
        // 注意：此处仅为日志记录，实际响应需结合全局异常处理器实现
    }
}