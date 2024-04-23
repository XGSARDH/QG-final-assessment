package com.qg.factory;

import com.qg.dao.*;
import com.qg.dao.impl.*;

public class DaoFactory {
    private static UserDao userDao = UserImpl.getInstance();
    private static GroupDao groupDao = GroupImpl.getInstance();
    private static GroupMemberDao groupMemberDao = GroupMemberImpl.getInstance();
    private static NotificationDao notificationDao = NotificationImpl.getInstance();
    private static PermissionChangeDao permissionChangeDao = PermissionChangeImpl.getInstance();

    public static UserDao getUserDao() {
        return userDao;
    }

    public static GroupDao getGroupDao() {
        return groupDao;
    }

    public static GroupMemberDao getGroupMemberDao() {
        return groupMemberDao;
    }

    public static NotificationDao getNotificationDao() {
        return notificationDao;
    }

    public static PermissionChangeDao getPermissionChangeDao() {
        return permissionChangeDao;
    }
}
