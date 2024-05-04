package com.qg.dao.impl;

import com.qg.dao.GroupDao;
import com.qg.po.Group;
import com.qg.util.connectPool.ConnectionPoolManager;
import com.qg.util.crudUtils.CRUDUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupImpl implements GroupDao {

    private static volatile GroupImpl instance;

    public static GroupImpl getInstance() {
        // 双重检查锁定，确保线程安全
        if (instance == null) {
            synchronized (GroupImpl.class) {
                if (instance == null) {
                    instance = new GroupImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public List<Group> findByGroupId(Long groupId) {
        String sql = "SELECT `group_id`, `group_name`, `description`, `is_public`, `created_by`, `gmt_create`, `gmt_modified` FROM `groups` WHERE `group_id` = ?";
        Connection connection = ConnectionPoolManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = CRUDUtils.query(sql, connection, preparedStatement, groupId);
        List<Group> groups = new ArrayList<>();

        try {
            while (rs.next()) {
                Group group = new Group();
                group.setGroupId(rs.getLong("group_id"));
                group.setGroupName(rs.getString("group_name"));
                group.setDescription(rs.getString("description"));
                group.setIsPublic(rs.getInt("is_public"));
                group.setCreatedBy(rs.getLong("created_by"));
                group.setGmtCreate(rs.getString("gmt_create"));
                group.setGmtModified(rs.getString("gmt_modified"));
                groups.add(group);
            }
            if(rs != null) {
                rs.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            ConnectionPoolManager.releaseConnection(connection);
            return groups;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Group> findByCreateBy(Long createdBy) {
        String sql = "SELECT `group_id`, `group_name`, `description`, `is_public`, `created_by`, `gmt_create`, `gmt_modified` FROM `groups` WHERE `created_by` = ?";
        Connection connection = ConnectionPoolManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = CRUDUtils.query(sql, connection, preparedStatement, createdBy);
        List<Group> groups = new ArrayList<>();

        try {
            while (rs.next()) {
                Group group = new Group();
                group.setGroupId(rs.getLong("group_id"));
                group.setGroupName(rs.getString("group_name"));
                group.setDescription(rs.getString("description"));
                group.setIsPublic(rs.getInt("is_public"));
                group.setCreatedBy(rs.getLong("created_by"));
                group.setGmtCreate(rs.getString("gmt_create"));
                group.setGmtModified(rs.getString("gmt_modified"));
                groups.add(group);
            }
            if(rs != null) {
                rs.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            ConnectionPoolManager.releaseConnection(connection);
            return groups;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Group> findAll() {
        String sql = "SELECT `group_id`, `group_name`, `description`, `is_public`, `created_by`, `gmt_create`, `gmt_modified` FROM `groups`";
        return findGroupsBySql(sql);
    }

    @Override
    public List<Group> findPublicAll() {
        String sql = "SELECT `group_id`, `group_name`, `description`, `is_public`, `created_by`, `gmt_create`, `gmt_modified` FROM `groups` WHERE is_public = 1";
        return findGroupsBySql(sql);
    }

    private List<Group> findGroupsBySql(String sql) {
        Connection connection = ConnectionPoolManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = CRUDUtils.query(sql, connection, preparedStatement);
        List<Group> groups = new ArrayList<>();

        try {
            while (rs.next()) {
                Group group = new Group();
                group.setGroupId(rs.getLong("group_id"));
                group.setGroupName(rs.getString("group_name"));
                group.setDescription(rs.getString("description"));
                group.setIsPublic(rs.getInt("is_public"));
                group.setCreatedBy(rs.getLong("created_by"));
                group.setGmtCreate(rs.getString("gmt_create"));
                group.setGmtModified(rs.getString("gmt_modified"));
                groups.add(group);
            }
            if(rs != null) {
                rs.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            ConnectionPoolManager.releaseConnection(connection);
            return groups;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long save(Group group) throws SQLException {
        String sql = "INSERT INTO `groups` (`group_name`, `description`, `is_public`, `created_by`, `gmt_create`, `gmt_modified`) VALUES (?, ?, ?, ?, ?, ?)";
        return CRUDUtils.save(sql, group.getGroupName(), group.getDescription(), group.getIsPublic(), group.getCreatedBy(), group.getGmtCreate(), group.getGmtModified());
    }

    @Override
    public void update(Group group) throws SQLException {
        String sql = "UPDATE `groups` SET `group_name` = ?, `description` = ?, `is_public` = ?, `gmt_create` = ?, `gmt_modified` = ? WHERE `group_id` = ?";
        CRUDUtils.update(sql, group.getGroupName(), group.getDescription(), group.getIsPublic(), group.getGmtCreate(), group.getGmtModified(), group.getGroupId());
    }

    @Override
    public void delete(Long groupId) throws SQLException {
        String sql = "DELETE FROM `groups` WHERE `group_id` = ?";
        CRUDUtils.update(sql, groupId);
    }

    @Override
    public List<Group> findByLikeGroupName(String groupName) {
        String sql = "SELECT `group_id`, `group_name`, `description`, `is_public`, `created_by`, `gmt_create`, `gmt_modified` FROM `groups` WHERE `group_name` LIKE ?";
        Connection connection = ConnectionPoolManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = CRUDUtils.query(sql, connection, preparedStatement, groupName);
        List<Group> groups = new ArrayList<>();

        try {
            while (rs.next()) {
                Group group = new Group();
                group.setGroupId(rs.getLong("group_id"));
                group.setGroupName(rs.getString("group_name"));
                group.setDescription(rs.getString("description"));
                group.setIsPublic(rs.getInt("is_public"));
                group.setCreatedBy(rs.getLong("created_by"));
                group.setGmtCreate(rs.getString("gmt_create"));
                group.setGmtModified(rs.getString("gmt_modified"));
                groups.add(group);
            }
            if(rs != null) {
                rs.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            ConnectionPoolManager.releaseConnection(connection);
            return groups;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
