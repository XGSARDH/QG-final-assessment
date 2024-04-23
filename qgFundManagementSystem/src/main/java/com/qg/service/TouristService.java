package com.qg.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qg.factory.DaoFactory;
import com.qg.po.Group;
import com.qg.po.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class TouristService {

    private static volatile TouristService instance;

    public static TouristService getInstance() {
        // 双重检查锁定，确保线程安全
        if (instance == null) {
            synchronized (TouristService.class) {
                if (instance == null) {
                    instance = new TouristService();
                }
            }
        }
        return instance;
    }

    public TouristService() {
    }

    /**
     * 注册
     */
    public Long register(String userName, String nickname, String passwordHash, String email, String phoneNumber) throws SQLException{
        User user = new User(
                userName,
                nickname,
                passwordHash,
                email,
                phoneNumber,
                "0",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        Long userId = null;
        userId = DaoFactory.getUserDao().save(user);
        return userId;
    }

    /**
     * 登录
     */
    public Integer login(String userId, String password) {
        System.out.println("userId: " + userId);
        System.out.println("password: " + password);
        if (!userId.matches("-?\\d+")) {
            return 0;
        }
        User user = DaoFactory.getUserDao().findById(Long.valueOf(userId)).get(0);
        if(user == null) {
            return 0;
        } else if (!user.getPasswordHash().equals(password)) {
            return 0;
        }
        // equal is success
        return 1;
    }

    /**
     * 找回密码: 依靠手机号来作为主要凭据
     */
    public Long retrievePassword(String phoneNumber, String email, String passwordHash) {
        User user = DaoFactory.getUserDao().findByPhoneNumber(phoneNumber).get(0);
        if (user.getEmail().equals(email)) {
            user.setPasswordHash(passwordHash);
            try {
                DaoFactory.getUserDao().update(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return user.getUserId();
        }
        return 0L;
    }

    /**
     * 搜索公开群组
     */
    public String SearchGruop(Long groupId) {
        List<Group> groups= DaoFactory.getGroupDao().findByGroupId(groupId);
        JSONArray jsonArray = new JSONArray();
        for (Group group : groups) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("group_id", group.getGroupId());
            jsonObject.put("group_name", group.getGroupName());
            jsonObject.put("create_by", group.getCreatedBy());
            jsonObject.put("description", group.getDescription());
            jsonArray.add(jsonObject);
        }
        String output  = JSON.toJSONString(jsonArray);
        System.out.println(output);
        return output;
    }

    /**
     * 查看公开群组
     */
    public String viewGroup() {
        List<Group> groups= DaoFactory.getGroupDao().findPublicAll();
        JSONArray jsonArray = new JSONArray();
        for (Group group : groups) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("group_id", group.getGroupId());
            jsonObject.put("group_name", group.getGroupName());
            jsonObject.put("create_by", group.getCreatedBy());
            jsonObject.put("description", group.getDescription());
            jsonArray.add(jsonObject);
        }
        String output  = JSON.toJSONString(jsonArray);
        System.out.println(output);
        return output;
    }

}
