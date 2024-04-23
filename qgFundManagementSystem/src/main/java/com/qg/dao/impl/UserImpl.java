package com.qg.dao.impl;

import com.qg.dao.UserDao;
import com.qg.po.User;
import com.qg.util.connectPool.ConnectionPoolManager;
import com.qg.util.crudUtils.CRUDUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserImpl implements UserDao {

    private static volatile UserImpl instance;

    public static UserImpl getInstance() {
        // 双重检查锁定，确保线程安全
        if (instance == null) {
            synchronized (UserImpl.class) {
                if (instance == null) {
                    instance = new UserImpl();
                }
            }
        }
        return instance;
    }

    public UserImpl() {
    }

    @Override
    public List<User> findById(Long userId) {
        String sql = "SELECT `user_id`, `username`, `nickname`, `password_hash`, `email`, `phone_number`, `avatar_url`, `now_create_group`, `max_create_group`, `gmt_create`, `gmt_modified` FROM `users` WHERE `user_id` = ?";
        Connection connection = ConnectionPoolManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = CRUDUtils.query(sql, connection, preparedStatement, userId);
        List<User> users = new ArrayList<>();

        try {
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getLong("user_id"));
                user.setUserName(rs.getString("username"));
                user.setNickname(rs.getString("nickname"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setAvatarUrl(rs.getString("avatar_url"));
                user.setNowCreateGroup(rs.getInt("now_create_group"));
                user.setMaxCreateGroup(rs.getInt("max_create_group"));
                user.setGmtCreate(rs.getString("gmt_create"));
                user.setGmtModified(rs.getString("gmt_modified"));
                users.add(user);
            }
            if (rs != null) {
                rs.close(); // 关闭ResultSet
            }
            if (preparedStatement != null) {
                preparedStatement.close(); // 关闭PreparedStatement
            }
            ConnectionPoolManager.releaseConnection(connection); // 释放数据库连接
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findByPhoneNumber(String phoneNumber) {
        String sql = "SELECT `user_id`, `username`, `nickname`, `password_hash`, `email`, `phone_number`, `avatar_url`, `now_create_group`, `max_create_group`, `gmt_create`, `gmt_modified` FROM `users` WHERE `phone_number` = ?";
        Connection connection = ConnectionPoolManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = CRUDUtils.query(sql, connection, preparedStatement, phoneNumber);
        List<User> users = new ArrayList<>();

        try {
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getLong("user_id"));
                user.setUserName(rs.getString("username"));
                user.setNickname(rs.getString("nickname"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setAvatarUrl(rs.getString("avatar_url"));
                user.setNowCreateGroup(rs.getInt("now_create_group"));
                user.setMaxCreateGroup(rs.getInt("max_create_group"));
                user.setGmtCreate(rs.getString("gmt_create"));
                user.setGmtModified(rs.getString("gmt_modified"));
                users.add(user);
            }
            if (rs != null) {
                rs.close(); // 关闭ResultSet
            }
            if (preparedStatement != null) {
                preparedStatement.close(); // 关闭PreparedStatement
            }
            ConnectionPoolManager.releaseConnection(connection); // 释放数据库连接
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT `user_id`, `username`, `nickname`, `password_hash`, `email`, `phone_number`, `avatar_url`, `now_create_group`, `max_create_group`, `gmt_create`, `gmt_modified` FROM `users`";
        Connection connection = ConnectionPoolManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = CRUDUtils.query(sql, connection, preparedStatement);
        List<User> users = new ArrayList<>();

        try {
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getLong("user_id"));
                user.setUserName(rs.getString("username"));
                user.setNickname(rs.getString("nickname"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setAvatarUrl(rs.getString("avatar_url"));
                user.setNowCreateGroup(rs.getInt("now_create_group"));
                user.setMaxCreateGroup(rs.getInt("max_create_group"));
                user.setGmtCreate(rs.getString("gmt_create"));
                user.setGmtModified(rs.getString("gmt_modified"));
                users.add(user);
            }
            if (rs != null) {
                rs.close(); // 关闭ResultSet
            }
            if (preparedStatement != null) {
                preparedStatement.close(); // 关闭PreparedStatement
            }
            ConnectionPoolManager.releaseConnection(connection); // 释放数据库连接
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long save(User user) throws SQLException {
        String sql = "INSERT INTO `users` (`username`, `nickname`, `password_hash`, `email`, `phone_number`, `avatar_url`, `now_create_group`, `max_create_group`, `gmt_create`, `gmt_modified`) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String userName = user.getUserName();
        String nickname = user.getNickname();
        String passwordHash = user.getPasswordHash();
        String email = user.getEmail();
        String phoneNumber = user.getPhoneNumber();
        String avatarUrl = user.getAvatarUrl();
        Integer nowCreateGroup = user.getNowCreateGroup();
        Integer maxCreateGroup = user.getMaxCreateGroup();
        String gmtCreate = user.getGmtCreate();
        String gmtModified = user.getGmtModified();
        // 调用save函数，插入数据，并获取生成的ID

        return CRUDUtils.save(sql, userName, nickname, passwordHash, email, phoneNumber, avatarUrl, nowCreateGroup, maxCreateGroup, gmtCreate, gmtModified);
    }

    @Override
    public void update(User user) throws SQLException{
        String sql = "UPDATE users SET " +
                "`username` = ?, " +
                "`nickname` = ?, " +
                "`password_hash` = ?, " +
                "`email` = ?, " +
                "`phone_number` = ?, " +
                "`avatar_url` = ?, " +
                "`now_create_group` = ?, " +
                "`max_create_group` = ?, "+
                "`gmt_create` = ?, " +
                "`gmt_modified` = ? " +
                "WHERE `user_id` = ?";
        Long userId = user.getUserId();
        String userName = user.getUserName();
        String nickname = user.getNickname();
        String passwordHash = user.getPasswordHash();
        String email = user.getEmail();
        String phoneNumber = user.getPhoneNumber();
        String avatarUrl = user.getAvatarUrl();
        Integer nowCreateGroup = user.getNowCreateGroup();
        Integer maxCreateGroup = user.getMaxCreateGroup();
        String gmtCreate = user.getGmtCreate();
        String gmtModified = user.getGmtModified();
        CRUDUtils.update(sql, userName, nickname, passwordHash, email, phoneNumber, avatarUrl, nowCreateGroup, maxCreateGroup, gmtCreate, gmtModified, userId);
    }

    @Override
    public void delete(Long userId) throws SQLException {
        String sql = "DELETE FROM `users` WHERE user_id = ?";
        CRUDUtils.update(sql, userId);
    }

    @Override
    public List<User> findByLikeUsername(String userName) {
        userName = "%" + userName + "%";
        String sql = "SELECT `user_id`, `username`, `nickname`, `password_hash`, `email`, `phone_number`, `now_create_group`, `max_create_group`, `avatar_url`, `gmt_create`, `gmt_modified` FROM `users` WHERE `username` LIKE ?";
        Connection connection = ConnectionPoolManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = CRUDUtils.query(sql, connection, preparedStatement, userName);
        List<User> users = new ArrayList<>();

        try {
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getLong("user_id"));
                user.setUserName(rs.getString("username"));
                user.setNickname(rs.getString("nickname"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setAvatarUrl(rs.getString("avatar_url"));
                user.setNowCreateGroup(rs.getInt("now_create_group"));
                user.setMaxCreateGroup(rs.getInt("max_create_group"));
                user.setGmtCreate(rs.getString("gmt_create"));
                user.setGmtModified(rs.getString("gmt_modified"));
                users.add(user);
            }
            if (rs != null) {
                rs.close(); // 关闭ResultSet
            }
            if (preparedStatement != null) {
                preparedStatement.close(); // 关闭PreparedStatement
            }
            ConnectionPoolManager.releaseConnection(connection); // 释放数据库连接
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
