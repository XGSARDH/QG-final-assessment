package com.qg.dao;

import com.qg.po.Notification;

import java.util.List;

public interface NotificationDao {
    List<Notification> findByNotificationId(Long notificationId);

    List<Notification> findByUserId(Long userId);

    Long save(Notification notification);

    void update(Notification notification);

    void delete(Long notificationId);
}
