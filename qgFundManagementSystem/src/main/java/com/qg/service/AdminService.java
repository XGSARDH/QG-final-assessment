package com.qg.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qg.factory.DaoFactory;
import com.qg.po.PermissionChange;

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
        System.out.println(output);
        return output;
    }
}
