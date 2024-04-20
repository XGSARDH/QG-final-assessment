package com.qg.po;

import java.time.LocalDateTime;

public class Group {
    private Long groupId;
    private String groupName;
    private String description;
    private Integer isPublic;
    private Long createdBy;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public Group() {
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", description='" + description + '\'' +
                ", isPublic=" + isPublic +
                ", createdBy=" + createdBy +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Integer isPublic) {
        this.isPublic = isPublic;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
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
