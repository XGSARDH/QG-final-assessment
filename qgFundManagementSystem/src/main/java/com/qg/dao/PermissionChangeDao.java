package com.qg.dao;

import com.qg.po.PermissionChange;

import java.util.List;

public interface PermissionChangeDao {
    List<PermissionChange> findAuditId(Long auditId);

    List<PermissionChange> findAll();

    Long save(PermissionChange change);

    void update(PermissionChange change);

    void delete(Long auditId);
}
