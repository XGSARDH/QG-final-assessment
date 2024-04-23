package com.qg.dao;

import com.qg.po.PermissionChange;

import java.sql.SQLException;
import java.util.List;

public interface PermissionChangeDao {
    List<PermissionChange> findAuditId(Long auditId);

    List<PermissionChange> findGroupId(Long groupId);

    List<PermissionChange> findUserId(Long userId);

    List<PermissionChange> findAll();

    Long save(PermissionChange change) throws SQLException;

    void update(PermissionChange change) throws SQLException;

    void delete(Long auditId) throws SQLException;
}
