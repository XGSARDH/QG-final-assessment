package com.qg.config;

public enum FondAction {
    // 待付款
    FONDGAVE,

    // 请求待付款
    APPLYTOFORMGAVE,

    // 请求已处理: 支付完成
    APPROVEAPPLICATION,

    // 请求已处理: 支付被拒绝
    REJECTAPPLICATION,

    // 完成付款
    APPROVEORDER,

    // 拒绝付款
    REJECTORDER
}
