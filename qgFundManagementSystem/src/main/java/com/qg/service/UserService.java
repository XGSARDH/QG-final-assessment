package com.qg.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qg.config.Action;
import com.qg.config.Role;
import com.qg.dao.impl.UserImpl;
import com.qg.factory.DaoFactory;
import com.qg.po.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserService extends TouristService{

    private static volatile UserService instance;

    public static UserService getInstance() {
        // 双重检查锁定，确保线程安全
        if (instance == null) {
            synchronized (UserService.class) {
                if (instance == null) {
                    instance = new UserService();
                }
            }
        }
        return instance;
    }

    public UserService() {
    }

    /**
     * 登出
     */
    public void logOut() {

    }

    /**
     * 查看账户信息
     */
    public String viewProfile(Long userId) {
        User byId = DaoFactory.getUserDao().findById(userId).get(0);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id", byId.getUserId());
        jsonObject.put("username", byId.getUserName());
        jsonObject.put("nickname", byId.getNickname());
        jsonObject.put("email", byId.getEmail());
        jsonObject.put("phone_number", byId.getPhoneNumber());
        jsonObject.put("avatar_url", byId.getAvatarUrl());
        jsonObject.put("max_create_group", byId.getMaxCreateGroup());
        jsonObject.put("gmt_create", byId.getGmtCreate());
        jsonObject.put("gmt_modified", byId.getGmtModified());
        String output = jsonObject.toJSONString();
        return output;
    }

    /**
     * 修改账户信息
     */
    public String updateUser(Long userId, String nickname, String userName, String email, String newPassword, String oldPhoneNumber, String newPhoneNumber) {
        List<User> byId = DaoFactory.getUserDao().findById(userId);
        if (byId == null && byId.isEmpty()) {
            return "无法查找到该用户";
        }

        User user = byId.get(0);

        // 手机号校验
        if (!user.getPhoneNumber().equals(oldPhoneNumber)) {
            return "手机号错误";
        }

        user.setUserName(userName);
        user.setEmail(email);
        user.setNickname(nickname);
        user.setUserName(userName);
        user.setPhoneNumber(newPhoneNumber);
        user.setPasswordHash(newPassword);
        user.setGmtModified(LocalDateTime.now());

        try {
            DaoFactory.getUserDao().update(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "更新用户信息成功";

    }

    /**
     * 加入群组
     */
    public void joinGroup() {

    }

    /**
     * 申请创建群组
     */
    public Integer applyToGroup(Long userId) {
        User byId = DaoFactory.getUserDao().findById(userId).get(0);
        if(byId.getNowCreateGroup() < byId.getMaxCreateGroup()) {
            PermissionChange permissionChange = new PermissionChange();
            permissionChange.setStatus(0);
            permissionChange.setUserId(userId);
            permissionChange.setGroupId(0L);
            permissionChange.setGmtCreate(LocalDateTime.now());
            permissionChange.setGmtModified(LocalDateTime.now());
            permissionChange.setActionType(String.valueOf(Action.CreateGroup));
            try {
                System.out.println(permissionChange.toString());
                Long save = DaoFactory.getPermissionChangeDao().save(permissionChange);
                byId.setNowCreateGroup(byId.getNowCreateGroup() + 1);
                DaoFactory.getUserDao().update(byId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return 1;
        }
        else {
            return 0;
        }
    }

    /**
     * 离开群组
     */
    public void  leaveGroup() {

    }

    /**
     * 查看资金动向
     */
    public void viewTransactions() {

    }

    /**
     * 申请创建群组
     */
    public void createGroup() {

    }

    /**
     * 初始化群组页面
     */
    public String initGroupDetail(Long userId, Long groupId) {
        List<Group> byGroupId = DaoFactory.getGroupDao().findByGroupId(groupId);
        Group group = byGroupId.get(0);
        if (group == null) {
            return "";
        }

        // 权限验证
        List<GroupMember> byUserId = DaoFactory.getGroupMemberDao().findByUserId(userId);
        int isExistMember = 0;
        for (GroupMember groupMember : byUserId) {
            if (Objects.equals(groupMember.getGroupId(), groupId)) {
                isExistMember = 1;
            }
        }
        if (isExistMember == 0) {
            return "";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("group_name", group.getGroupName());
        jsonObject.put("created_by", group.getCreatedBy());
        jsonObject.put("description", group.getDescription());

        String output = jsonObject.toJSONString();
        return output;
    }

    public String viewGroupIdforMemberList(Long userId, Long groupId) {
        List<GroupMember> byGroupId = DaoFactory.getGroupMemberDao().findByGroupId(groupId);
        int isExistMember = 0;
        JSONArray jsonArray = new JSONArray();
        for (GroupMember groupMember : byGroupId) {
            if (groupMember.getUserId().equals(userId)) {
                isExistMember = 1;
            }
            String role = groupMember.getRole();
            // 这是干嘛用的?
            String s = String.valueOf(Role.GROUP_NORMAL);

            if (role.equals(String.valueOf(Role.USER))) {
                continue;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("member_id", groupMember.getMemberId());
            jsonObject.put("group_id", groupMember.getGroupId());
            jsonObject.put("user_id", groupMember.getUserId());
            jsonObject.put("role", groupMember.getRole());
            jsonObject.put("gmt_create", groupMember.getGmtCreate());
            jsonObject.put("gmt_modified", groupMember.getGmtModified());
            jsonArray.add(jsonObject);
        }
        if (isExistMember == 0) {
            return "";
        }
        return jsonArray.toJSONString();
    }

    public String ToBeGroupMember(Long groupId, Long userId, String description) {
        PermissionChange permissionChange = new PermissionChange();
        permissionChange.setUserId(userId);
        permissionChange.setDescription(description);
        permissionChange.setGroupId(groupId);
        permissionChange.setActiveType(String.valueOf(Action.ToBeMember));

        try {
            DaoFactory.getPermissionChangeDao().save(permissionChange);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "发送请求成功";
    }

    public String viewMyGroup(Long userId) {
        List<GroupMember> byUserId = DaoFactory.getGroupMemberDao().findByUserId(userId);
        List<Long> groupAdmin = new ArrayList<Long>();

        for (GroupMember groupMember : byUserId) {
            groupAdmin.add(groupMember.getGroupId());
        }

        JSONArray jsonArray = new JSONArray();
        for (Long aLong : groupAdmin) {
            Group byGroupId = DaoFactory.getGroupDao().findByGroupId(aLong).get(0);
            com.alibaba.fastjson2.JSONObject jsonObject = new com.alibaba.fastjson2.JSONObject();
            jsonObject.put("group_id", byGroupId.getGroupId());
            jsonObject.put("group_name", byGroupId.getGroupName());
            jsonObject.put("description", byGroupId.getDescription());
            jsonObject.put("is_public", byGroupId.getIsPublic());
            jsonObject.put("created_by", byGroupId.getCreatedBy());
            jsonArray.add(jsonObject);
        }

        String output  = JSON.toJSONString(jsonArray);
        System.out.println(output);
        return output;
    }

    public void ApplyToJoinGroup(Long groudid, Long userId) {
        GroupMember groupMember = new GroupMember();
        groupMember.setGroupId(groudid);
        groupMember.setGroupId(userId);
        groupMember.setRole(String.valueOf(Role.GROUP_NORMAL));
        try {
            DaoFactory.getGroupMemberDao().save(groupMember);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        UserFund userFund = new UserFund();
        userFund.setUserId(userId);
        userFund.setGroupId(groudid);
        userFund.setUserHealth(1);
        try {
            DaoFactory.getUserFundDao().save(userFund);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}
