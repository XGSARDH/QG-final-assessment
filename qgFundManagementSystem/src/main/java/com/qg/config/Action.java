package com.qg.config;

public enum Action {
    // 非群组成员成为该群组成员
    ToBeMember,

    // 群组普通成员成为该群组管理员
    MemberToBeAAdmin,

    // 群组管理员成为该群组普通成员
    AdminToBeMember,

    // 退出群组
    OutTheGroup,

    // 创建群组
    CreateGroup
}