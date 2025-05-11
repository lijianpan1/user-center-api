package com.luren.usercenterapi.config;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luren.usercenterapi.common.ErrorCode;
import com.luren.usercenterapi.exception.CustomException;
import com.luren.usercenterapi.security.JwtAuthenticationFilter;
import com.luren.usercenterapi.security.JwtAuthenticationProvider;
import com.luren.usercenterapi.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security配置类
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启权限注解
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            // 允许对于网站静态资源的无授权访问
            .antMatchers("/static/**").permitAll()
            // 对登录注册和验证码接口要允许匿名访问
            .antMatchers("/auth/login", "/auth/register", "/auth/refresh-token").permitAll()
            // 除上面外的所有请求全部需要鉴权认证
            .anyRequest().authenticated()
                .and()
            .exceptionHandling()
            .authenticationEntryPoint(((request, response, authException) -> {
                // 将错误码封装到 request 属性
                request.setAttribute("errorCode", ErrorCode.NOT_LOGIN_ERROR);
                // 将请求转发到 /error 路径
                request.getRequestDispatcher("/error").forward(request, response);

//                response.setContentType("application/json;charset=UTF-8");
//                response.getWriter().println(JSONUtil.toJsonStr(ResultUtils.error(ErrorCode.NOT_LOGIN_ERROR)));
            }))
            .accessDeniedHandler((request, response, accessDeniedException) -> {
                request.setAttribute("errorCode", ErrorCode.NO_AUTH_ERROR);
                request.getRequestDispatcher("/error").forward(request, response);
            });
        
        // 添加JWT filter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 禁用缓存
        http.headers().cacheControl();
    }
} 