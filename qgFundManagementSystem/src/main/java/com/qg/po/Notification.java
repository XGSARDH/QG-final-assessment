package com.qg.po;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notification {
    private Long notificationId;
    private Long userId;
    private Long groupId;
    private String title;
    private String message;
    private Integer readStatus;
    private String passiveType;
    private Long passiveId;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", userId=" + userId +
                ", groupId=" + groupId +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", readStatus=" + readStatus +
                ", passiveType='" + passiveType + '\'' +
                ", passiveId=" + passiveId +
                ", gmtCreate=" + getGmtCreate() +
                ", gmtModified=" + getGmtModified() +
                '}';
    }

    public Notification() {
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
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
