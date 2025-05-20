package com.luren.usercenterapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luren.usercenterapi.mode.UserRole;
import com.luren.usercenterapi.service.UserRoleService;
import com.luren.usercenterapi.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author lijianpan
* @description 针对表【user_role】的数据库操作Service实现
* @createDate 2025-05-20 21:08:34
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService{
}




