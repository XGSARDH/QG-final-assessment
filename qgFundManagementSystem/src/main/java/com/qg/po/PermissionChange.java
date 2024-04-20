package com.qg.po;

import java.time.LocalDateTime;

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
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
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

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }
}
