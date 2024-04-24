package com.qg.po;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order {
    private Long orderId;
    private Long activeId;
    private Long passiveId;
    private String activeType;
    private String passiveType;
    private String changeType;
    private String description;
    private String amount;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public Order() {
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", activeId=" + activeId +
                ", passiveId=" + passiveId +
                ", activeType='" + activeType + '\'' +
                ", passiveType='" + passiveType + '\'' +
                ", changeType='" + changeType + '\'' +
                ", description='" + description + '\'' +
                ", amount='" + amount + '\'' +
                ", gmtCreate=" + getGmtCreate() +
                ", gmtModified=" + getGmtModified() +
                '}';
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getActiveId() {
        return activeId;
    }

    public void setActiveId(Long activeId) {
        this.activeId = activeId;
    }

    public Long getPassiveId() {
        return passiveId;
    }

    public void setPassiveId(Long passiveId) {
        this.passiveId = passiveId;
    }

    public String getActiveType() {
        return activeType;
    }

    public void setActiveType(String activeType) {
        this.activeType = activeType;
    }

    public String getPassiveType() {
        return passiveType;
    }

    public void setPassiveType(String passiveType) {
        this.passiveType = passiveType;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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
