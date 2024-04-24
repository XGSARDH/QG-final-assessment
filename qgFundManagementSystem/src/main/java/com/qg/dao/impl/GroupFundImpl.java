package com.qg.dao.impl;

import com.qg.dao.GroupFundDao;
import com.qg.po.GroupFund;
import com.qg.util.connectPool.ConnectionPoolManager;
import com.qg.util.crudUtils.CRUDUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupFundImpl implements GroupFundDao {

    private static volatile GroupFundImpl instance;

    public static GroupFundImpl getInstance() {
        if (instance == null) {
            synchronized (GroupFundImpl.class) {
                if (instance == null) {
                    instance = new GroupFundImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public List<GroupFund> findByGroupId(Long groupId) {
        String sql = "SELECT `group_fund_id`, `group_id`, `group_health`, `total_funds`, `available_funds`, `frozen_funds`, `gmt_create`, `gmt_modified` FROM `group_funds` WHERE `group_id` = ?";
        Connection connection = ConnectionPoolManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = CRUDUtils.query(sql, connection, preparedStatement, groupId);
        List<GroupFund> groupFunds = new ArrayList<>();

        try {
            while (rs.next()) {
                GroupFund groupFund = new GroupFund();
                groupFund.setGroupFundId(rs.getLong("group_fund_id"));
                groupFund.setGroupId(rs.getLong("group_id"));
                groupFund.setGroupHealth(rs.getInt("group_health"));
                groupFund.setTotalFunds(rs.getString("total_funds"));
                groupFund.setAvailableFunds(rs.getString("available_funds"));
                groupFund.setFrozenFunds(rs.getString("frozen_funds"));
                groupFund.setGmtCreate(rs.getString("gmt_create"));
                groupFund.setGmtModified(rs.getString("gmt_modified"));
                groupFunds.add(groupFund);
            }
            if (rs != null) {
                rs.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            ConnectionPoolManager.releaseConnection(connection);
            return groupFunds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long save(GroupFund groupFund) throws SQLException {
        String sql = "INSERT INTO `group_funds` (`group_id`, `group_health`, `total_funds`, `available_funds`, `frozen_funds`, `gmt_create`, `gmt_modified`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return CRUDUtils.save(sql, groupFund.getGroupId(), groupFund.getGroupHealth(), groupFund.getTotalFunds(), groupFund.getAvailableFunds(), groupFund.getFrozenFunds(), groupFund.getGmtCreate(), groupFund.getGmtModified());
    }

    @Override
    public void update(GroupFund groupFund) throws SQLException {
        String sql = "UPDATE `group_funds` SET `group_id` = ?, `group_health` = ?, `total_funds` = ?, `available_funds` = ?, `frozen_funds` = ?, `gmt_create` = ?, `gmt_modified` = ? WHERE `group_fund_id` = ?";
        CRUDUtils.update(sql, groupFund.getGroupId(), groupFund.getGroupHealth(), groupFund.getTotalFunds(), groupFund.getAvailableFunds(), groupFund.getFrozenFunds(), groupFund.getGmtCreate(), groupFund.getGmtModified(), groupFund.getGroupFundId());
    }

    @Override
    public void delete(Long groupFundId) throws SQLException {
        String sql = "DELETE FROM `group_funds` WHERE `group_fund_id` = ?";
        CRUDUtils.update(sql, groupFundId);
    }
}
