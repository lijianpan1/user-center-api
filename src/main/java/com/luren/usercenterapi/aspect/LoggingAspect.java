package com.luren.usercenterapi.aspect;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.luren.usercenterapi.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            args[i] = maskSensitiveData(args[i]); // 调用统一的敏感信息过滤方法
        }
        log.info("method before");
        log.info("Entering method: {}.{} with arguments: {}",
                joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName(), 
                Arrays.toString(args)); // 使用Arrays.toString()处理数组类型的数据
    }

    @AfterReturning(pointcut = "execution(* com.luren.usercenterapi.service.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        result = maskSensitiveData(result); // 调用统一的敏感信息过滤方法
        String resultString;
        if (result != null && result.getClass().isArray()) {
            resultString = Arrays.toString((Object[]) result); // 使用Arrays.toString()处理数组类型的数据
        } else {
            resultString = String.valueOf(result);
        }
        log.info("method after");
        log.info("Exiting method: {}.{} with result: {}",
                joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName(),
                resultString);
    }

    @AfterThrowing(pointcut = "execution(* com.luren.usercenterapi.service.*.*(..))", throwing = "ex")
    public void handleException(JoinPoint joinPoint, Exception ex) {
        // 记录异常日志，包括堆栈跟踪
        log.error("Exception occurred in {}.{}: {}",
                joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName(),
                ex.getMessage(), ex);

        // 可以在此处添加更多处理逻辑，例如返回标准化错误响应
        // 注意：此处仅为日志记录，实际响应需结合全局异常处理器实现
    }

    // 统一的敏感信息过滤方法
    private Object maskSensitiveData(Object data) {
        if (data instanceof String) {
            String str = (String) data;
            if (str.contains("password") || str.contains("token") || str.contains("secret")) {
                return "*****";
            }
        } else if (data instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) data;
            Map<Object, Object> maskedMap = new HashMap<>();
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                maskedMap.put(entry.getKey(), maskSensitiveData(entry.getValue()));
            }
            return maskedMap;
        } else if (data instanceof Collection) {
            Collection<?> collection = (Collection<?>) data;
            Collection<Object> maskedCollection = new ArrayList<>();
            for (Object item : collection) {
                maskedCollection.add(maskSensitiveData(item));
            }
            return maskedCollection;
        } else if (data != null && data.getClass().isArray()) {
            Object[] array = (Object[]) data;
            Object[] maskedArray = new Object[array.length];
            for (int i = 0; i < array.length; i++) {
                maskedArray[i] = maskSensitiveData(array[i]);
            }
            return maskedArray;
        } else if (data instanceof Iterable) {
            Iterable<?> iterable = (Iterable<?>) data;
            Collection<Object> maskedCollection = new ArrayList<>();
            for (Object item : iterable) {
                maskedCollection.add(maskSensitiveData(item));
            }
            return maskedCollection;
        }else if(data instanceof QueryWrapper) {
            QueryWrapper<?> queryWrapper = (QueryWrapper<?>) data;
            return queryWrapper.getSqlSegment();
        }
        // 可以继续添加其他类型的处理逻辑
        return data;
    }
}