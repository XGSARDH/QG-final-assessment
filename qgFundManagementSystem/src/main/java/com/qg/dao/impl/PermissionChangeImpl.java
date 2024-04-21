package com.qg.dao.impl;

import com.qg.dao.PermissionChangeDao;
import com.qg.po.PermissionChange;
import com.qg.util.connectPool.ConnectionPoolManager;
import com.qg.util.crudUtils.CRUDUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PermissionChangeImpl implements PermissionChangeDao {
    private static volatile PermissionChangeImpl instance;

    public static PermissionChangeImpl getInstance() {
        // 双重检查锁定，确保线程安全
        if (instance == null) {
            synchronized (PermissionChangeImpl.class) {
                if (instance == null) {
                    instance = new PermissionChangeImpl();
                }
            }
        }
        return instance;
    }

    public PermissionChangeImpl() {
    }

    @Override
    public List<PermissionChange> findAuditId(Long auditId) {
        String sql = "SELECT `audit_id`, `action_type`, `user_id`, `description`, `active_type`, `active_id`, `passive_type`, `passive_id`, `gmt_create`, `gmt_modified` FROM `permission_changes` WHERE audit_id = ?";
        Connection connection = ConnectionPoolManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = CRUDUtils.query(sql, connection, preparedStatement, auditId);
        List<PermissionChange> changes = new ArrayList<>();

        try {
            while (rs.next()) {
                PermissionChange change = new PermissionChange();
                change.setAuditId(rs.getLong("audit_id"));
                change.setActionType(rs.getString("action_type"));
                change.setUserId(rs.getLong("user_id"));
                change.setDescription(rs.getString("description"));
                change.setActiveType(rs.getString("active_type"));
                change.setActiveId(rs.getLong("active_id"));
                change.setPassiveType(rs.getString("passive_type"));
                change.setPassiveId(rs.getLong("passive_id"));
                change.setGmtCreate(rs.getString("gmt_create"));
                change.setGmtModified(rs.getString("gmt_modified"));
                changes.add(change);
            }
            if (rs != null) {
                rs.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            ConnectionPoolManager.releaseConnection(connection);
            return changes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PermissionChange> findAll() {
        String sql = "SELECT `audit_id`, `action_type`, `user_id`, `description`, `active_type`, `active_id`, `passive_type`, `passive_id`, `gmt_create`, `gmt_modified` FROM `permission_changes`";
        Connection connection = ConnectionPoolManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = CRUDUtils.query(sql, connection, preparedStatement);
        List<PermissionChange> changes = new ArrayList<>();

        try {
            while (rs.next()) {
                PermissionChange change = new PermissionChange();
                change.setAuditId(rs.getLong("audit_id"));
                change.setActionType(rs.getString("action_type"));
                change.setUserId(rs.getLong("user_id"));
                change.setDescription(rs.getString("description"));
                change.setActiveType(rs.getString("active_type"));
                change.setActiveId(rs.getLong("active_id"));
                change.setPassiveType(rs.getString("passive_type"));
                change.setPassiveId(rs.getLong("passive_id"));
                change.setGmtCreate(rs.getString("gmt_create"));
                change.setGmtModified(rs.getString("gmt_modified"));
                changes.add(change);
            }
            if (rs != null) {
                rs.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            ConnectionPoolManager.releaseConnection(connection);
            return changes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long save(PermissionChange change) {
        String sql = "INSERT INTO `permission_changes` ( `action_type`, `user_id`, `description`, `active_type`, `active_id`, `passive_type`, `passive_id`, `gmt_create`, `gmt_modified`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return CRUDUtils.save(sql, change.getActionType(), change.getUserId(), change.getDescription(), change.getActiveType(), change.getActiveId(), change.getPassiveType(), change.getPassiveId(), change.getGmtCreate(), change.getGmtModified());
    }

    @Override
    public void update(PermissionChange change) {
        String sql = "UPDATE `permission_changes` SET `action_type` = ?, `description` = ?, `active_type` = ?, `active_id` = ?, `passive_type` = ?, `passive_id` = ?, `gmt_create` = ?, `gmt_modified` = ? WHERE `audit_id` = ?";
        CRUDUtils.update(sql, change.getActionType(), change.getDescription(), change.getActiveType(), change.getActiveId(), change.getPassiveType(), change.getPassiveId(), change.getGmtCreate(), change.getGmtModified(), change.getAuditId());
    }

    @Override
    public void delete(Long auditId) {
        String sql = "DELETE FROM `permission_changes` WHERE `audit_id` = ?";
        CRUDUtils.update(sql, auditId);
    }
}
