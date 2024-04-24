package com.qg.dao.impl;

import com.qg.dao.UserFundDao;
import com.qg.po.UserFund;
import com.qg.util.connectPool.ConnectionPoolManager;
import com.qg.util.crudUtils.CRUDUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserFundImpl implements UserFundDao {

    private static volatile UserFundImpl instance;

    public static UserFundImpl getInstance() {
        if (instance == null) {
            synchronized (UserFundImpl.class) {
                if (instance == null) {
                    instance = new UserFundImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public List<UserFund> findByUserId(Long userId) {
        String sql = "SELECT `user_fund_id`, `user_id`, `user_health`, `group_id`, `total_funds`, `available_funds`, `frozen_funds`, `gmt_create`, `gmt_modified` FROM `user_funds` WHERE `user_id` = ?";
        Connection connection = ConnectionPoolManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = CRUDUtils.query(sql, connection, preparedStatement, userId);
        List<UserFund> userFunds = new ArrayList<>();

        try {
            while (rs.next()) {
                UserFund userFund = new UserFund();
                userFund.setUserFundId(rs.getLong("user_fund_id"));
                userFund.setUserId(rs.getLong("user_id"));
                userFund.setUserHealth(rs.getInt("user_health"));
                userFund.setGroupId(rs.getLong("group_id"));
                userFund.setTotalFunds(rs.getString("total_funds"));
                userFund.setAvailableFunds(rs.getString("available_funds"));
                userFund.setFrozenFunds(rs.getString("frozen_funds"));
                userFund.setGmtCreate(rs.getString("gmt_create"));
                userFund.setGmtModified(rs.getString("gmt_modified"));
                userFunds.add(userFund);
            }
            if (rs != null) {
                rs.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            ConnectionPoolManager.releaseConnection(connection);
            return userFunds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long save(UserFund userFund) throws SQLException {
        String sql = "INSERT INTO `user_funds` (`user_id`, `user_health`, `group_id`, `total_funds`, `available_funds`, `frozen_funds`, `gmt_create`, `gmt_modified`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return CRUDUtils.save(sql, userFund.getUserId(), userFund.getUserHealth(), userFund.getGroupId(), userFund.getTotalFunds(), userFund.getAvailableFunds(), userFund.getFrozenFunds(), userFund.getGmtCreate(), userFund.getGmtModified());
    }

    @Override
    public void update(UserFund userFund) throws SQLException {
        String sql = "UPDATE `user_funds` SET `user_id` = ?, `user_health` = ?, `group_id` = ?, `total_funds` = ?, `available_funds` = ?, `frozen_funds` = ?, `gmt_create` = ?, `gmt_modified` = ? WHERE `user_fund_id` = ?";
        CRUDUtils.update(sql, userFund.getUserId(), userFund.getUserHealth(), userFund.getGroupId(), userFund.getTotalFunds(), userFund.getAvailableFunds(), userFund.getFrozenFunds(), userFund.getGmtCreate(), userFund.getGmtModified(), userFund.getUserFundId());
    }

    @Override
    public void delete(Long userFundId) throws SQLException {
        String sql = "DELETE FROM `user_funds` WHERE `user_fund_id` = ?";
        CRUDUtils.update(sql, userFundId);
    }
}
