package com.qg.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.qg.config.Role;
import com.qg.factory.DaoFactory;
import com.qg.po.Group;
import com.qg.po.GroupMember;
import com.qg.po.PermissionChange;
import com.qg.po.UserFund;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GroupAdminService extends UserService {

    private static volatile GroupAdminService instance;

    public static GroupAdminService getInstance() {
        // 双重检查锁定，确保线程安全
        if (instance == null) {
            synchronized (GroupAdminService.class) {
                if (instance == null) {
                    instance = new GroupAdminService();
                }
            }
        }
        return instance;
    }

    public GroupAdminService() {
    }


    /**
     * 编辑群组信息
     */
    public void editGroupInfo() {

    }

    /**
     * 设置群组公开性
     */
    public void setGroupVisibility() {

    }

    /**
     * 注销群组
     */
    public void dissolveGroup() {

    }

    public Integer toBeAdmin(Long activeId, Long groupId, Long passiveId) {
        // 找到对应群组的成员表
        List<GroupMember> byGroupId = DaoFactory.getGroupMemberDao().findByGroupId(groupId);

        // 受影响者的id和权限
        GroupMember passive = null;
        int isExistPassivemember = 0;

        // 操作者的id和权限
        GroupMember actice = null;
        int isExistActicemember = 0;

        for (GroupMember groupMember : byGroupId) {
            if (groupMember.getUserId().equals(passiveId)) {
                // 受影响者的id和权限
                passive = groupMember;
                isExistPassivemember = 1;
            }
            if (groupMember.getUserId().equals(activeId)) {
                // 操作者的id和权限
                actice = groupMember;
                isExistActicemember = 1;
            }
        }

        if(isExistActicemember == 0 || isExistPassivemember == 0) {
            // 双方有一方不在群组内
            return 2;
        }

        String roleUser = String.valueOf(Role.USER);
        String roleGroupNormal = String.valueOf(Role.GROUP_NORMAL);
        String roleGroupAdmin = String.valueOf(Role.GROUP_ADMIN);
        String roleGroupHighest = String.valueOf(Role.GROUP_HIGHEST);

        System.out.println("active" + actice);
        System.out.println("passive" + passive);

        if(!actice.getRole().equals(roleGroupHighest)) {
            // 执行操作对象权限不足
            return 3;
        }

        if(!passive.getRole().equals(roleGroupNormal)) {
            if (passive.getRole().equals(roleUser)) {
                // 受影响操作对象已退出群组
                return 4;
            }
            if (passive.getRole().equals(roleGroupAdmin) || passive.getRole().equals(roleGroupHighest)) {
                // 权限已是管理员 或 最高管理者
                return 5;
            }
        }

        if(actice.getRole().equals(roleGroupHighest) && passive.getRole().equals(roleGroupNormal)) {
            // 执行让其成为管理员逻辑
            // 写入权限变动表
            PermissionChange permissionChange = new PermissionChange();
            permissionChange.setActionType("toBeAdmin");
            permissionChange.setStatus(1);
            permissionChange.setUserId(activeId);
            permissionChange.setGroupId(groupId);
            permissionChange.setDescription("toBeAdmin");
            permissionChange.setActiveType(actice.getRole());
            permissionChange.setActiveId(activeId);
            permissionChange.setPassiveType(passive.getRole());
            permissionChange.setPassiveId(passiveId);
            System.out.println(permissionChange);
            try {
                DaoFactory.getPermissionChangeDao().save(permissionChange);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // 更改群员权限表, 使其成为管理员
            passive.setRole(roleGroupAdmin);
            passive.setGmtModified(LocalDateTime.now());

            try {
                DaoFactory.getGroupMemberDao().update(passive);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // 更新权限变动表
            permissionChange.setStatus(1);
            permissionChange.setGmtModified(LocalDateTime.now());
            try {
                DaoFactory.getPermissionChangeDao().update(permissionChange);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // 正常处理完成
            return 1;
        }

        // 出现未知错误
        return 0;
    }

    public Integer toBeNormal(Long activeId, Long groupId, Long passiveId) {
        // 找到对应群组的成员表
        List<GroupMember> byGroupId = DaoFactory.getGroupMemberDao().findByGroupId(groupId);

        // 受影响者的id和权限
        GroupMember passive = null;
        int isExistPassivemember = 0;

        // 操作者的id和权限
        GroupMember actice = null;
        int isExistActicemember = 0;

        for (GroupMember groupMember : byGroupId) {
            if (groupMember.getUserId().equals(passiveId)) {
                // 受影响者的id和权限
                passive = groupMember;
                isExistPassivemember = 1;
            }
            if (groupMember.getUserId().equals(activeId)) {
                // 操作者的id和权限
                actice = groupMember;
                isExistActicemember = 1;
            }
        }

        if(isExistActicemember == 0 || isExistPassivemember == 0) {
            // 双方有一方不在群组内
            return 2;
        }

        String roleUser = String.valueOf(Role.USER);
        String roleGroupNormal = String.valueOf(Role.GROUP_NORMAL);
        String roleGroupAdmin = String.valueOf(Role.GROUP_ADMIN);
        String roleGroupHighest = String.valueOf(Role.GROUP_HIGHEST);

        System.out.println("active" + actice);
        System.out.println("passive" + passive);

        if(!actice.getRole().equals(roleGroupHighest)) {
            // 执行操作对象权限不足
            return 3;
        }

        // 如果受影响操作对象不是群组管理员
        if(!passive.getRole().equals(roleGroupAdmin)) {
            if (passive.getRole().equals(roleUser)) {
                // 受影响操作对象已退出群组
                return 4;
            }
            if (passive.getRole().equals(roleGroupNormal) || passive.getRole().equals(roleGroupHighest)) {
                // 权限已是普通成员 或 成员是最高管理者
                return 5;
            }
        }

        if(actice.getRole().equals(roleGroupHighest) && passive.getRole().equals(roleGroupAdmin)) {
            // 执行让其成为普通成员逻辑
            // 写入权限变动表
            PermissionChange permissionChange = new PermissionChange();
            permissionChange.setActionType("toBeNormal");
            permissionChange.setStatus(1);
            permissionChange.setUserId(activeId);
            permissionChange.setGroupId(groupId);
            permissionChange.setDescription("toBeNormal");
            permissionChange.setActiveType(actice.getRole());
            permissionChange.setActiveId(activeId);
            permissionChange.setPassiveType(passive.getRole());
            permissionChange.setPassiveId(passiveId);
            System.out.println(permissionChange);
            try {
                DaoFactory.getPermissionChangeDao().save(permissionChange);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // 更改群员权限表, 使其成为普通成员
            passive.setRole(roleGroupNormal);
            passive.setGmtModified(LocalDateTime.now());

            try {
                DaoFactory.getGroupMemberDao().update(passive);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // 更新权限变动表
            permissionChange.setStatus(1);
            permissionChange.setGmtModified(LocalDateTime.now());
            try {
                DaoFactory.getPermissionChangeDao().update(permissionChange);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // 正常处理完成
            return 1;
        }

        // 出现未知错误
        return 0;
    }

    public Integer toBeUser(Long activeId, Long groupId, Long passiveId) {
        // 找到对应群组的成员表
        List<GroupMember> byGroupId = DaoFactory.getGroupMemberDao().findByGroupId(groupId);

        // 受影响者的id和权限
        GroupMember passive = null;
        int isExistPassivemember = 0;

        // 操作者的id和权限
        GroupMember actice = null;
        int isExistActicemember = 0;

        for (GroupMember groupMember : byGroupId) {
            if (groupMember.getUserId().equals(passiveId)) {
                // 受影响者的id和权限
                passive = groupMember;
                isExistPassivemember = 1;
            }
            if (groupMember.getUserId().equals(activeId)) {
                // 操作者的id和权限
                actice = groupMember;
                isExistActicemember = 1;
            }
        }

        if(isExistActicemember == 0 || isExistPassivemember == 0) {
            // 双方有一方不在群组内
            return 2;
        }

        String roleUser = String.valueOf(Role.USER);
        String roleGroupNormal = String.valueOf(Role.GROUP_NORMAL);
        String roleGroupAdmin = String.valueOf(Role.GROUP_ADMIN);
        String roleGroupHighest = String.valueOf(Role.GROUP_HIGHEST);

        System.out.println("active" + actice);
        System.out.println("passive" + passive);

        if(
                !actice.getRole().equals(roleGroupHighest)
                || (actice.getRole().equals(roleGroupAdmin)) && passive.getRole().equals(roleGroupAdmin)
                || passive.getRole().equals(roleGroupHighest)
        ) {
            // 执行操作对象权限不足
            return 3;
        }

        if (passive.getRole().equals(roleUser)) {
            // 受影响操作对象已退出群组
            return 4;
        }

        // 执行让其成为普通成员逻辑
        // 写入权限变动表
        PermissionChange permissionChange = new PermissionChange();
        permissionChange.setActionType("toBeNormal");
        permissionChange.setStatus(1);
        permissionChange.setUserId(activeId);
        permissionChange.setGroupId(groupId);
        permissionChange.setDescription("toBeNormal");
        permissionChange.setActiveType(actice.getRole());
        permissionChange.setActiveId(activeId);
        permissionChange.setPassiveType(passive.getRole());
        permissionChange.setPassiveId(passiveId);
        System.out.println(permissionChange);
        try {
            DaoFactory.getPermissionChangeDao().save(permissionChange);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 更改群员权限表, 使其成为普通成员
        passive.setRole(roleUser);
        passive.setGmtModified(LocalDateTime.now());

        try {
            DaoFactory.getGroupMemberDao().update(passive);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 更新权限变动表
        permissionChange.setStatus(1);
        permissionChange.setGmtModified(LocalDateTime.now());
        try {
            DaoFactory.getPermissionChangeDao().update(permissionChange);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 正常处理完成
        return 1;
    }

    public Integer updateGroup(Long userId, Long groupId, String groupName, String description) {
        List<GroupMember> byGroupId = DaoFactory.getGroupMemberDao().findByGroupId(groupId);
        GroupMember groupMember = null;
        for (GroupMember curr : byGroupId) {
            if (curr.getUserId().equals(userId)) {
                groupMember = curr;
                break;
            }
        }

        if (groupMember == null) {
            // 群组与该成员没有关系
            return 2;
        }

        if (!groupMember.getRole().equals(String.valueOf(Role.GROUP_ADMIN)) && !groupMember.getRole().equals(String.valueOf(Role.GROUP_HIGHEST))) {
            return 3;
        }


        Group group = DaoFactory.getGroupDao().findByGroupId(groupId).get(0);
        group.setGroupName(groupName);
        group.setDescription(description);
        try {
            DaoFactory.getGroupDao().update(group);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // 成功
        return 1;
    }

    public String viewGroupAdmin(Long userId) {
        List<GroupMember> byUserId = DaoFactory.getGroupMemberDao().findByUserId(userId);
        List<Long> groupAdmin = new ArrayList<Long>();

        for (GroupMember groupMember : byUserId) {
            if (groupMember.getRole().equals(String.valueOf(Role.GROUP_ADMIN)) || groupMember.getRole().equals(String.valueOf(Role.GROUP_HIGHEST))) {
                groupAdmin.add(groupMember.getGroupId());
            }
        }

        JSONArray jsonArray = new JSONArray();
        for (Long aLong : groupAdmin) {
            Group byGroupId = DaoFactory.getGroupDao().findByGroupId(aLong).get(0);
            com.alibaba.fastjson2.JSONObject jsonObject = new JSONObject();
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


    public String initGroupDetailFund(Long userId, Long groudId) {
        Group group = DaoFactory.getGroupDao().findByGroupId(groudId).get(0);
        List<UserFund> byGroupId = DaoFactory.getUserFundDao().findByGroupId(groudId);

        UserFund userFund = null;

        for (UserFund fund : byGroupId) {
            if (fund.getUserId().equals(0L)) {
                userFund = fund;
            }
        }

        if (userFund == null) {
            return "账户出错";
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("group_name", group.getGroupName());
        jsonObject.put("created_by", group.getCreatedBy());
        jsonObject.put("description", group.getDescription());
        jsonObject.put("available_funds", userFund.getAvailableFunds());
        jsonObject.put("total_funds", userFund.getTotalFunds());
        jsonObject.put("frozen_funds", userFund.getFrozenFunds());

        return jsonObject.toJSONString();

    }
}
