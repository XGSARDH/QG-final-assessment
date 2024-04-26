package com.qg.dao.impl;

import com.qg.dao.SessionDao;
import com.qg.po.Session;
import com.qg.util.connectPool.ConnectionPoolManager;
import com.qg.util.crudUtils.CRUDUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class SessionImpl implements SessionDao {

    private static volatile SessionImpl instance;

    private final ConcurrentHashMap<Long, ReentrantLock> locks = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static SessionImpl getInstance() {
        // 双重检查锁定，确保线程安全
        if (instance == null) {
            synchronized (SessionImpl.class) {
                if (instance == null) {
                    instance = new SessionImpl();
                }
            }
        }
        return instance;
    }

    public SessionImpl() {
        // 启动定时任务以定期清理锁
        scheduler.scheduleAtFixedRate(() -> {
            locks.entrySet().removeIf(entry -> {
                ReentrantLock lock = entry.getValue();
                return !lock.isLocked() && !lock.hasQueuedThreads();
            });
        }, 1, 1, TimeUnit.HOURS);  // 每小时清理一次
    }

    @Override
    public List<Session> findByUserId(Long userId) {
        String sql = "SELECT * FROM `user_sessions` WHERE `user_id` = ?";
        Connection connection = ConnectionPoolManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = CRUDUtils.query(sql, connection, preparedStatement, userId);
        List<Session> sessions = new ArrayList<>();

        try {
            while (rs.next()) {
                Session session = new Session();
                session.setSessionId(rs.getLong("session_id"));
                session.setUserId(rs.getLong("user_id"));
                session.setIpAddress(rs.getString("ip_address"));
                session.setDeviceInfo(rs.getString("device_info"));
                session.setToken(rs.getString("token"));
                session.setExpiresAt(rs.getString("expires_at"));
                session.setGmtCreate(rs.getString("gmt_create"));
                session.setGmtModified(rs.getString("gmt_modified"));
                sessions.add(session);
            }
            if (rs != null) {
                rs.close(); // 关闭ResultSet
            }
            if (preparedStatement != null) {
                preparedStatement.close(); // 关闭PreparedStatement
            }
            ConnectionPoolManager.releaseConnection(connection); // 释放数据库连接
            return sessions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long save(Session session) throws SQLException {
        String sql = "INSERT INTO `user_sessions` (`user_id`, `ip_address`, `device_info`, `token`, `expires_at`, `gmt_create`, `gmt_modified`) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?)";
        Long userId = session.getUserId();
        String ipAddress = session.getIpAddress();
        String deviceInfo = session.getDeviceInfo();
        String token = session.getToken();
        String expiresAt = session.getExpiresAt();
        String gmtCreate = session.getGmtCreate();
        String gmtModified = session.getGmtModified();

        return CRUDUtils.save(sql, userId, ipAddress, deviceInfo, token, expiresAt, gmtCreate, gmtModified);
    }

    @Override
    public void update(Session session) throws SQLException {
        Long sessionId = session.getSessionId();
        ReentrantLock lock = locks.computeIfAbsent(sessionId, k -> new ReentrantLock());

        lock.lock();
        try {
            String sql = "UPDATE `user_sessions` SET " +
                    "`user_id` = ?, " +
                    "`ip_address` = ?, " +
                    "`device_info` = ?, " +
                    "`token` = ?, " +
                    "`expires_at` = ?, " +
                    "`gmt_create` = ?, " +
                    "`gmt_modified` = ? " +
                    "WHERE `session_id` = ?";
            CRUDUtils.update(sql, session.getUserId(), session.getIpAddress(), session.getDeviceInfo(),
                    session.getToken(), session.getExpiresAt(), session.getGmtCreate(), LocalDateTime.now(), sessionId);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void delete(Long sessionId) throws SQLException {
        String sql = "DELETE FROM `user_sessions` WHERE `session_id` = ?";
        CRUDUtils.update(sql, sessionId);
    }

}
