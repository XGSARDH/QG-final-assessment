package com.qg.config;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RolePermissions {
    private static final Map<String, Set<Permission>> rolePermissions = new HashMap<>();

    static {
        rolePermissions.put("USER", EnumSet.of(Permission.REGISTER_ACCOUNT, Permission.LOGIN, Permission.LOGOUT,
                Permission.VIEW_PROFILE, Permission.EDIT_PROFILE, Permission.SEARCH_GROUP,
                Permission.JOIN_GROUP, Permission.LEAVE_GROUP, Permission.VIEW_TRANSACTIONS));

        rolePermissions.put("GROUP_ADMIN", EnumSet.of(Permission.REGISTER_ACCOUNT, Permission.LOGIN, Permission.LOGOUT,
                Permission.VIEW_PROFILE, Permission.EDIT_PROFILE, Permission.SEARCH_GROUP,
                Permission.JOIN_GROUP, Permission.LEAVE_GROUP, Permission.VIEW_TRANSACTIONS,
                Permission.CREATE_GROUP, Permission.EDIT_GROUP_INFO, Permission.SET_GROUP_VISIBILITY,
                Permission.MANAGE_GROUP_FUNDS, Permission.DISSOLVE_GROUP));

        rolePermissions.put("ADMIN", EnumSet.allOf(Permission.class));
    }

    public static Set<Permission> getPermissions(String role) {
        return rolePermissions.getOrDefault(role, EnumSet.noneOf(Permission.class));
    }
}
