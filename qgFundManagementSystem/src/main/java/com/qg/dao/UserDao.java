package com.qg.dao;

import com.qg.po.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    // 根据ID查找用户
    List<User> findById(Long userId);

    // 根据手机号查找用户
    List<User> findByPhoneNumber(String phoneNumber);

    // 查找所有用户
    List<User> findAll();

    // 保存用户（新增或更新）
    Long save(User user) throws SQLException;

    // 更新用户信息
    void update(User user) throws SQLException;

    // 删除用户
    void delete(Long userId) throws SQLException;

    // 根据用户名查找用户
    List<User> findByLikeUsername(String userName);

}
