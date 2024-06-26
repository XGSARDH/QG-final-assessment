package com.qg.po;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {
    private Long userId;
    private String userName;
    private String nickname;
    private String passwordHash;
    private String email;
    private String phoneNumber;
    private String avatarUrl;
    private Integer nowCreateGroup;
    private Integer maxCreateGroup;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", nowCreateGroup=" + nowCreateGroup +
                ", maxCreateGroup=" + maxCreateGroup +
                ", gmtCreate=" + getGmtCreate() +
                ", gmtModified=" + getGmtModified() +
                '}';
    }

    public User(String userName, String nickname, String passwordHash, String email, String phoneNumber, String avatarUrl, LocalDateTime gmtCreate, LocalDateTime gmtModified) {
        this.userName = userName;
        this.nickname = nickname;
        this.passwordHash = passwordHash;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.avatarUrl = avatarUrl;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.maxCreateGroup = 3;
        this.nowCreateGroup = 0;
    }

    public Integer getNowCreateGroup() {
        return nowCreateGroup;
    }

    public void setNowCreateGroup(Integer nowCreateGroup) {
        this.nowCreateGroup = nowCreateGroup;
    }

    public Integer getMaxCreateGroup() {
        return maxCreateGroup;
    }

    public void setMaxCreateGroup(Integer maxCreateGroup) {
        this.maxCreateGroup = maxCreateGroup;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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
