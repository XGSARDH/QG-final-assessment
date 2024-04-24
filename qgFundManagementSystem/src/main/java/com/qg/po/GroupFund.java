package com.qg.po;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GroupFund {
    private Long groupFundId;
    private Long groupId;
    private Integer groupHealth;
    private String totalFunds;
    private String availableFunds;
    private String frozenFunds;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public GroupFund() {
    }

    @Override
    public String toString() {
        return "GroupFund{" +
                "groupFundId=" + groupFundId +
                ", groupId=" + groupId +
                ", groupHealth=" + groupHealth +
                ", totalFunds='" + totalFunds + '\'' +
                ", availableFunds='" + availableFunds + '\'' +
                ", frozenFunds='" + frozenFunds + '\'' +
                ", gmtCreate=" + getGmtCreate() +
                ", gmtModified=" + getGmtModified() +
                '}';
    }

    public Long getGroupFundId() {
        return groupFundId;
    }

    public void setGroupFundId(Long groupFundId) {
        this.groupFundId = groupFundId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Integer getGroupHealth() {
        return groupHealth;
    }

    public void setGroupHealth(Integer groupHealth) {
        this.groupHealth = groupHealth;
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
