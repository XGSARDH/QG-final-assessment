package com.qg.service;

import com.qg.dao.SessionDao;
import com.qg.factory.DaoFactory;
import com.qg.po.Session;

import java.sql.SQLException;
import java.util.List;

public class SessionService {

    private static volatile SessionService instance;

    public static SessionService getInstance() {
        // 双重检查锁定，确保线程安全
        if (instance == null) {
            synchronized (SessionService.class) {
                if (instance == null) {
                    instance = new SessionService();
                }
            }
        }
        return instance;
    }

    public SessionService() {
    }

    // 更新JWT令牌
    public void updateJwtToken(String userId, String jwtToken) {
        List<Session> byUserId = DaoFactory.getSessionDao().findByUserId(Long.valueOf(userId));
        if (byUserId != null && !byUserId.isEmpty()) {
            byUserId.get(0).setToken(jwtToken);
            try {
                DaoFactory.getSessionDao().update(byUserId.get(0));
                return;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            Session session = new Session();
            session.setUserId(Long.valueOf(userId));
            session.setToken(jwtToken);
            try {
                DaoFactory.getSessionDao().save(session);
                return;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 保存新会话
    // 注册用户时使用
    public void saveNewSession(String userId, String jwtToken) throws SQLException {
        Session session = new Session();
        session.setUserId(Long.valueOf(userId));
        session.setToken(jwtToken);
        DaoFactory.getSessionDao().save(session);
    }

    // 验证JWT令牌
    public int verifyJwtToken(Long userId, String jwtToken) {
        Session existingSession = DaoFactory.getSessionDao().findByUserId(userId).stream()
                .findFirst()
                .orElse(null);
        if (existingSession == null || !existingSession.getToken().equals(jwtToken)) {
            return 0;  // JWT不匹配或无会话
        }
        return 1;  // JWT匹配
    }

}
