package com.qg.dao.impl;

import com.qg.dao.GroupMemberDao;
import com.qg.po.GroupMember;
import com.qg.util.connectPool.ConnectionPoolManager;
import com.qg.util.crudUtils.CRUDUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupMemberImpl implements GroupMemberDao {
    private static volatile GroupMemberImpl instance;

    public static GroupMemberImpl getInstance() {
        // 双重检查锁定，确保线程安全
        if (instance == null) {
            synchronized (GroupMemberImpl.class) {
                if (instance == null) {
                    instance = new GroupMemberImpl();
                }
            }
        }
        return instance;
    }

    public GroupMemberImpl() {
    }

    @Override
    public List<GroupMember> findByMemberId(Long memberId) {
        String sql = "SELECT `member_id`, `group_id`, `user_id`, `role`, `gmt_create`, `gmt_modified` FROM `group_members` WHERE `member_id` = ?";
        Connection connection = ConnectionPoolManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = CRUDUtils.query(sql, connection, preparedStatement, memberId);
        List<GroupMember> members = new ArrayList<>();

        try {
            while (rs.next()) {
                GroupMember member = new GroupMember();
                member.setMemberId(rs.getLong("member_id"));
                member.setGroupId(rs.getLong("group_id"));
                member.setUserId(rs.getLong("user_id"));
                member.setRole(rs.getString("role"));
                member.setGmtCreate(rs.getString("gmt_create"));
                member.setGmtModified(rs.getString("gmt_modified"));
                members.add(member);
            }
            if(rs != null) {
                rs.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            ConnectionPoolManager.releaseConnection(connection);
            return members;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GroupMember> findByGroupId(Long groupId) {
        String sql = "SELECT `member_id`, `group_id`, `user_id`, `role`, `gmt_create`, `gmt_modified` FROM `group_members` WHERE `group_id` = ?";
        Connection connection = ConnectionPoolManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = CRUDUtils.query(sql, connection, preparedStatement, groupId);
        List<GroupMember> members = new ArrayList<>();

        try {
            while (rs.next()) {
                GroupMember member = new GroupMember();
                member.setMemberId(rs.getLong("member_id"));
                member.setGroupId(rs.getLong("group_id"));
                member.setUserId(rs.getLong("user_id"));
                member.setRole(rs.getString("role"));
                member.setGmtCreate(rs.getString("gmt_create"));
                member.setGmtModified(rs.getString("gmt_modified"));
                members.add(member);
            }
            if(rs != null) {
                rs.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            ConnectionPoolManager.releaseConnection(connection);
            return members;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GroupMember> findByUserId(Long userId) {
        String sql = "SELECT `member_id`, `group_id`, `user_id`, `role`, `gmt_create`, `gmt_modified` FROM `group_members` WHERE `user_id` = ?";
        Connection connection = ConnectionPoolManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = CRUDUtils.query(sql, connection, preparedStatement, userId);
        List<GroupMember> members = new ArrayList<>();

        try {
            while (rs.next()) {
                GroupMember member = new GroupMember();
                member.setMemberId(rs.getLong("member_id"));
                member.setGroupId(rs.getLong("group_id"));
                member.setUserId(rs.getLong("user_id"));
                member.setRole(rs.getString("role"));
                member.setGmtCreate(rs.getString("gmt_create"));
                member.setGmtModified(rs.getString("gmt_modified"));
                members.add(member);
            }
            if(rs != null) {
                rs.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            ConnectionPoolManager.releaseConnection(connection);
            return members;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(GroupMember member) throws SQLException {
        String sql = "INSERT INTO `group_members` (`group_id`, `user_id`, `role`, `gmt_create`, `gmt_modified`) VALUES (?, ?, ?, ?, ?)";
        CRUDUtils.update(sql, member.getGroupId(), member.getUserId(), member.getRole(), member.getGmtCreate(), member.getGmtModified());
    }

    @Override
    public void update(GroupMember member) throws SQLException {
        String sql = "UPDATE `group_members` SET `role` = ?, `gmt_create` = ?, `gmt_modified` = ? WHERE `member_id` = ?";
        CRUDUtils.update(sql, member.getRole(), member.getGmtCreate(), member.getGmtModified(), member.getMemberId());
    }

    @Override
    public void delete(Long memberId) throws SQLException {
        String sql = "DELETE FROM `group_members` WHERE `member_id` = ?";
        CRUDUtils.update(sql, memberId);
    }
}
