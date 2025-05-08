package com.luren.usercenterapi.mapper;

import com.luren.usercenterapi.mode.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author lijianpan
* @description 针对表【user】的数据库操作Mapper
* @createDate 2025-04-26 22:09:58
*/
public interface UserMapper extends BaseMapper<User> {
    int restoreDeletedData(Integer userId);
}




