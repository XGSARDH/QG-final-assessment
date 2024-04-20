package com.qg.po;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PermissionChange {
    private Long auditId;
    private String actionType;
    private Long userId;
    private String description;
    private String activeType;
    private Long activeId;
    private String passiveType;
    private Long passiveId;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public PermissionChange() {
    }

    @Override
    public String toString() {
        return "PermissionChange{" +
                "auditId=" + auditId +
                ", actionType='" + actionType + '\'' +
                ", userId=" + userId +
                ", description='" + description + '\'' +
                ", activeType='" + activeType + '\'' +
                ", activeId=" + activeId +
                ", passiveType='" + passiveType + '\'' +
                ", passiveId=" + passiveId +
                ", gmtCreate=" + getGmtCreate() +
                ", gmtModified=" + getGmtModified() +
                '}';
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActiveType() {
        return activeType;
    }

    public void setActiveType(String activeType) {
        this.activeType = activeType;
    }

    public Long getActiveId() {
        return activeId;
    }

    public void setActiveId(Long activeId) {
        this.activeId = activeId;
    }

    public String getPassiveType() {
        return passiveType;
    }

    public void setPassiveType(String passiveType) {
        this.passiveType = passiveType;
    }

    public Long getPassiveId() {
        return passiveId;
    }

    public void setPassiveId(Long passiveId) {
        this.passiveId = passiveId;
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
}
