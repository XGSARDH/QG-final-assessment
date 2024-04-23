package com.qg.dao.impl;

import com.qg.dao.NotificationDao;
import com.qg.po.Notification;
import com.qg.util.connectPool.ConnectionPoolManager;
import com.qg.util.crudUtils.CRUDUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationImpl implements NotificationDao {
    private static volatile NotificationImpl instance;

    public static NotificationImpl getInstance() {
        // 双重检查锁定，确保线程安全
        if (instance == null) {
            synchronized (NotificationImpl.class) {
                if (instance == null) {
                    instance = new NotificationImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public List<Notification> findByNotificationId(Long notificationId) {
        String sql = "SELECT `user_id`, `group_id`, `title`, `message`, `read_status`, `passive_type`, `passive_id`, `gmt_create`, `gmt_modified` FROM `notifications` WHERE `notification_id` = ?";
        Connection connection = ConnectionPoolManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = CRUDUtils.query(sql, connection, preparedStatement, notificationId);
        List<Notification> notifications = new ArrayList<>();

        try {
            while (rs.next()) {
                Notification notification = new Notification();
                notification.setNotificationId(rs.getLong("notification_id"));
                notification.setUserId(rs.getLong("user_id"));
                notification.setGroupId(rs.getLong("group_id"));
                notification.setTitle(rs.getString("title"));
                notification.setMessage(rs.getString("message"));
                notification.setReadStatus(rs.getInt("read_status"));
                notification.setPassiveType(rs.getString("passive_type"));
                notification.setPassiveId(rs.getLong("passive_id"));
                notification.setGmtCreate(rs.getString("gmt_create"));
                notification.setGmtModified(rs.getString("gmt_modified"));
                notifications.add(notification);
            }
            if (rs != null) {
                rs.close(); // 关闭ResultSet
            }
            if (preparedStatement != null) {
                preparedStatement.close(); // 关闭PreparedStatement
            }
            ConnectionPoolManager.releaseConnection(connection); // 释放数据库连接
            return notifications;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Notification> findByUserId(Long userId) {
        String sql = "SELECT `user_id`, `group_id`, `title`, `message`, `read_status`, `passive_type`, `passive_id`, `gmt_create`, `gmt_modified` FROM `notifications` WHERE `user_id` = ?";
        Connection connection = ConnectionPoolManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = CRUDUtils.query(sql, connection, preparedStatement, userId);
        List<Notification> notifications = new ArrayList<>();

        try {
            while (rs.next()) {
                Notification notification = new Notification();
                notification.setNotificationId(rs.getLong("notification_id"));
                notification.setUserId(rs.getLong("user_id"));
                notification.setGroupId(rs.getLong("group_id"));
                notification.setTitle(rs.getString("title"));
                notification.setMessage(rs.getString("message"));
                notification.setReadStatus(rs.getInt("read_status"));
                notification.setPassiveType(rs.getString("passive_type"));
                notification.setPassiveId(rs.getLong("passive_id"));
                notification.setGmtCreate(rs.getString("gmt_create"));
                notification.setGmtModified(rs.getString("gmt_modified"));
                notifications.add(notification);
            }
            if (rs != null) {
                rs.close(); // 关闭ResultSet
            }
            if (preparedStatement != null) {
                preparedStatement.close(); // 关闭PreparedStatement
            }
            ConnectionPoolManager.releaseConnection(connection); // 释放数据库连接
            return notifications;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long save(Notification notification) throws SQLException  {
        String sql = "INSERT INTO `notifications` (`user_id`, `group_id`, `title`, `message`, `read_status`, `passive_type`, `passive_id`, `gmt_create`, `gmt_modified`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return CRUDUtils.save(sql, notification.getUserId(), notification.getGroupId(), notification.getTitle(), notification.getMessage(), notification.getReadStatus(), notification.getPassiveType(), notification.getPassiveId(), notification.getGmtCreate(), notification.getGmtModified());
    }

    @Override
    public void update(Notification notification) throws SQLException {
        String sql = "UPDATE `notifications` SET `title` = ?, `message` = ?, `read_status` = ?, `passive_type` = ?, `passive_id` = ?, `gmt_create` = ?, `gmt_modified` = ? WHERE `notification_id` = ?";
        CRUDUtils.update(sql, notification.getTitle(), notification.getMessage(), notification.getReadStatus(), notification.getPassiveType(), notification.getPassiveId(), notification.getGmtCreate(), notification.getGmtModified(), notification.getNotificationId());
    }

    @Override
    public void delete(Long notificationId) throws SQLException{
        String sql = "DELETE FROM `notifications` WHERE `notification_id` = ?";
        CRUDUtils.update(sql, notificationId);
    }
}
