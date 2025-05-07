create database user_center;

use user_center;

create table permissions
(
    id          int auto_increment comment '权限唯一标识'
        primary key,
    name        varchar(100) not null comment '权限名称',
    description varchar(255) null comment '权限描述'
)
    engine = InnoDB
    row_format = DYNAMIC;

create table role
(
    id          int auto_increment comment '角色唯一标识'
        primary key,
    name        varchar(50)  not null comment '角色名称',
    description varchar(255) null comment '角色描述'
)
    engine = InnoDB
    row_format = DYNAMIC;

create table role_permissions
(
    id      int auto_increment comment '关联记录唯一标识'
        primary key,
    user_id bigint not null comment '关联的用户ID',
    role_id int    not null comment '关联的角色ID'
)
    engine = InnoDB
    row_format = DYNAMIC;

create table user
(
    id              bigint auto_increment comment '用户唯一标识'
        primary key,
    username        varchar(256)      not null comment '用户名',
    password        varchar(256)      not null comment '用户密码（加密存储）',
    nickname        varchar(256)      null comment '昵称',
    avatar_url      varchar(1024)     null comment '头像',
    email           varchar(100)      null comment '用户邮箱',
    phone           varchar(50)       null comment '用户手机号',
    status          tinyint default 0 not null comment '用户状态（0：有效，1：无效）',
    created_date    datetime          not null on update CURRENT_TIMESTAMP comment '用户创建时间',
    updated_date    datetime          not null on update CURRENT_TIMESTAMP comment '用户信息更新时间',
    is_delete       tinyint default 1 not null comment '是否删除（0：否，1：是）',
    last_login_time datetime          null comment '最后登录时间'
)
    engine = InnoDB
    row_format = DYNAMIC;

create table user_role
(
    id            int auto_increment comment '关联记录唯一标识'
        primary key,
    role_id       int not null comment '关联的角色ID',
    permission_id int not null comment '关联的权限ID'
)
    engine = InnoDB
    row_format = DYNAMIC;

