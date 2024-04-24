package com.qg.po;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserFund {
    private Long userFundId;
    private Long userId;
    private Integer userHealth;
    private Long groupId;
    private String totalFunds;
    private String availableFunds;
    private String frozenFunds;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public UserFund() {
    }

    @Override
    public String toString() {
        return "UserFund{" +
                "userFundId=" + userFundId +
                ", userId=" + userId +
                ", userHealth=" + userHealth +
                ", groupId=" + groupId +
                ", totalFunds='" + totalFunds + '\'' +
                ", availableFunds='" + availableFunds + '\'' +
                ", frozenFunds='" + frozenFunds + '\'' +
                ", gmtCreate=" + getGmtCreate() +
                ", gmtModified=" + getGmtModified() +
                '}';
    }

    public Long getUserFundId() {
        return userFundId;
    }

    public void setUserFundId(Long userFundId) {
        this.userFundId = userFundId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getUserHealth() {
        return userHealth;
    }

    public void setUserHealth(Integer userHealth) {
        this.userHealth = userHealth;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getTotalFunds() {
        return totalFunds;
    }

    public void setTotalFunds(String totalFunds) {
        this.totalFunds = totalFunds;
    }

    public String getAvailableFunds() {
        return availableFunds;
    }

    public void setAvailableFunds(String availableFunds) {
        this.availableFunds = availableFunds;
    }

    public String getFrozenFunds() {
        return frozenFunds;
    }

    public void setFrozenFunds(String frozenFunds) {
        this.frozenFunds = frozenFunds;
    }

    public String getGmtCreate() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateStr = gmtCreate.format(fmt);
        return dateStr;
    }

    public void setGmtCreate(String gmtCreate) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.gmtCreate = LocalDateTime.parse(gmtCreate, fmt);
    }

    public String getGmtModified() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateStr = gmtModified.format(fmt);
        return dateStr;
    }

    public void setGmtModified(String gmtModified) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.gmtModified = LocalDateTime.parse(gmtModified, fmt);
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }
}
