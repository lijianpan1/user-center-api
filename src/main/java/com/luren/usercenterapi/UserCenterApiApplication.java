package com.luren.usercenterapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lijianpan
 */
@SpringBootApplication
@MapperScan("com.luren.usercenterapi.mapper")
public class UserCenterApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterApiApplication.class, args);
    }

}
