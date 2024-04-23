package com.qg.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qg.config.Action;
import com.qg.dao.impl.UserImpl;
import com.qg.factory.DaoFactory;
import com.qg.po.Group;
import com.qg.po.PermissionChange;
import com.qg.po.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

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
        jsonObject.put("username", byId.getUserId());
        jsonObject.put("nickname", byId.getUserId());
        jsonObject.put("email", byId.getUserId());
        jsonObject.put("phone_number", byId.getUserId());
        jsonObject.put("avatar_url", byId.getUserId());
        jsonObject.put("max_create_group", byId.getUserId());
        jsonObject.put("gmt_create", byId.getUserId());
        jsonObject.put("gmt_modified", byId.getUserId());
        String output = jsonObject.toJSONString();
        return output;
    }

    /**
     * 修改账户信息
     */
    public void editProfile() {

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
            permissionChange.setUserId(userId);
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
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("group_name", group.getGroupName());
        jsonObject.put("created_by", group.getCreatedBy());
        jsonObject.put("description", group.getDescription());

        String output = jsonObject.toJSONString();
        return output;
    }

}
