package com.qg.dao;

import com.qg.po.UserFund;

import java.sql.SQLException;
import java.util.List;

public interface UserFundDao {
    List<UserFund> findByUserId(Long userId);
    List<UserFund> findByGroupId(Long userId);
    List<UserFund> findByUserFundid(Long userFundId);

    Long save(UserFund userFund) throws SQLException;

    void update(UserFund userFund) throws SQLException;

    void delete(Long userFundId) throws SQLException;
}
