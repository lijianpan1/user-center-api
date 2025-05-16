package com.luren.usercenterapi.aspect;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * MyBatis-Plus 自动填充处理器
 *
 * @author Luren
 **/
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 自动填充创建时间（严格模式，确保字段存在且类型匹配）
        this.strictInsertFill(metaObject, "createdDate", Date.class, new Date());
        this.strictUpdateFill(metaObject, "updatedDate", Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 自动填充更新时间
        this.strictUpdateFill(metaObject, "updatedDate", Date.class, new Date());
    }
}