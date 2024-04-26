package com.qg.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qg.config.Role;
import com.qg.factory.DaoFactory;
import com.qg.po.Group;
import com.qg.po.GroupMember;
import com.qg.po.PermissionChange;
import com.qg.po.UserFund;

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

        Long groudId;

        // 入Group数据库
        Group group = new Group();
        group.setGroupName("默认名");
        group.setCreatedBy(userId);
        group.setIsPublic(1);
        group.setGmtCreate(LocalDateTime.now());
        group.setGmtModified(LocalDateTime.now());
        try {
            groudId = DaoFactory.getGroupDao().save(group);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 群组账户入userFouds数据库
        UserFund grouduserFund = new UserFund();
        grouduserFund.setUserId(0L);
        grouduserFund.setGroupId(groudId);
        grouduserFund.setUserHealth(1);
        grouduserFund.setTotalFunds("10000");
        grouduserFund.setAvailableFunds("10000");
        grouduserFund.setFrozenFunds("0");
        try {
            DaoFactory.getUserFundDao().save(grouduserFund);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 用户账户入userFouds数据库
        UserFund createByUserFund = new UserFund();
        createByUserFund.setUserId(userId);
        createByUserFund.setGroupId(0L);
        createByUserFund.setUserHealth(1);
        createByUserFund.setTotalFunds("10000");
        createByUserFund.setAvailableFunds("10000");
        createByUserFund.setFrozenFunds("0");
        try {
            DaoFactory.getUserFundDao().save(createByUserFund);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // 入GroupMembers数据库
        GroupMember groupMember = new GroupMember();
        groupMember.setGroupId(groudId);
        groupMember.setUserId(userId);
        groupMember.setRole(String.valueOf(Role.GROUP_HIGHEST));
        try {
            DaoFactory.getGroupMemberDao().save(groupMember);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 入PermissionChange 数据库
        permissionChange.setGmtModified(LocalDateTime.now());
        permissionChange.setGroupId(groudId);
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
