package com.qg.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qg.factory.DaoFactory;
import com.qg.po.Group;
import com.qg.po.PermissionChange;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class AdminService {

    private static volatile AdminService instance;

    public static AdminService getInstance() {
        // 双重检查锁定，确保线程安全
        if (instance == null) {
            synchronized (AdminService.class) {
                if (instance == null) {
                    instance = new AdminService();
                }
            }
        }
        return instance;
    }

    public AdminService() {
    }

    public String viewCreateGroupList() {
        List<PermissionChange> createGroupList = DaoFactory.getPermissionChangeDao().findGroupId(0L);
        JSONArray jsonArray = new JSONArray();
        for (PermissionChange permissionChange : createGroupList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("permission_change_id", permissionChange.getAuditId());
            jsonObject.put("user_id", permissionChange.getUserId());
            jsonObject.put("gmt_create", permissionChange.getGmtCreate());
            jsonArray.add(jsonObject);
        }
        String output  = JSON.toJSONString(jsonArray);
        return output;
    }

    public String sureCreateGroup(Long userId, Long permissionChangeId) {
        PermissionChange permissionChange = DaoFactory.getPermissionChangeDao().findAuditId(permissionChangeId).get(0);
        if(permissionChange.getStatus() != 0) {
            return "已处理";
        }
        Group group = new Group();
        group.setGroupName("默认名");
        group.setCreatedBy(userId);
        group.setIsPublic(1);
        group.setGmtCreate(LocalDateTime.now());
        group.setGmtModified(LocalDateTime.now());
        try {
            permissionChange.setGroupId(DaoFactory.getGroupDao().save(group));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        permissionChange.setGmtModified(LocalDateTime.now());
        permissionChange.setStatus(1);
        try {
            DaoFactory.getPermissionChangeDao().update(permissionChange);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "成功通过";
    }
    public String refuseCreateGroup(Long userId, Long permissionChangeId) {
        PermissionChange permissionChange = DaoFactory.getPermissionChangeDao().findAuditId(permissionChangeId).get(0);
        if(permissionChange.getStatus() != 0) {
            return "已拒绝";
        }
        permissionChange.setGmtModified(LocalDateTime.now());
        permissionChange.setStatus(-1);
        permissionChange.setGroupId(null);
        try {
            DaoFactory.getPermissionChangeDao().update(permissionChange);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "成功通过";
    }
}
