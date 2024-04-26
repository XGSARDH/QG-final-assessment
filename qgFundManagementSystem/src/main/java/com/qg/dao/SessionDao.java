package com.qg.dao;

import com.qg.po.Session;

import java.sql.SQLException;
import java.util.List;

public interface SessionDao {
    List<Session> findByUserId(Long userId);

    Long save(Session session) throws SQLException;

    void update(Session session) throws SQLException;

    void delete(Long sessionId) throws SQLException;
}
