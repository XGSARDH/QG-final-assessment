package com.qg.dao;

import com.qg.po.Notification;

import java.sql.SQLException;
import java.util.List;

public interface NotificationDao {
    List<Notification> findByNotificationId(Long notificationId);

    List<Notification> findByUserId(Long userId);

    Long save(Notification notification) throws SQLException;

    void update(Notification notification) throws SQLException;

    void delete(Long notificationId) throws SQLException;
}
