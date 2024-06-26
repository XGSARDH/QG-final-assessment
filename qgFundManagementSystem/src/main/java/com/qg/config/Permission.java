package com.qg.config;

public enum Permission {
    // 普通用户权限
    REGISTER_ACCOUNT,
    LOGIN,
    LOGOUT,
    VIEW_PROFILE,
    EDIT_PROFILE,
    SEARCH_GROUP,
    JOIN_GROUP,
    LEAVE_GROUP,
    VIEW_TRANSACTIONS,

    // 企业群组老板权限
    EDIT_GROUP_INFO_HIGHEST,

    // 企业群组负责人权限
    CREATE_GROUP,
    EDIT_GROUP_INFO,
    SET_GROUP_VISIBILITY,
    MANAGE_GROUP_FUNDS,
    DISSOLVE_GROUP,

    // 网站管理员权限
    BAN_USER,
    UNBAN_USER,
    REVIEW_RECOVERY_REQUEST,
    VIEW_TOTAL_BALANCES,
    VIEW_SUSPICIOUS_TRANSACTIONS
}
