package com.qg.dao.impl;

import com.qg.dao.FundChangeDao;
import com.qg.po.FundChange;
import com.qg.util.connectPool.ConnectionPoolManager;
import com.qg.util.crudUtils.CRUDUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FundChangeImpl implements FundChangeDao {

    private static volatile FundChangeImpl instance;

    public static FundChangeImpl getInstance() {
        if (instance == null) {
            synchronized (FundChangeImpl.class) {
                if (instance == null) {
                    instance = new FundChangeImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public List<FundChange> findByActiveId(Long activeId) {
        String sql = "SELECT `fund_change_id`, `user_id`, `active_type`, `active_id`, `passive_type`, `passive_id`, `change_type`, `description`, `amount`, `order_id`, `gmt_create`, `gmt_modified` FROM `fund_changes` WHERE `active_id` = ?";
        Connection connection = ConnectionPoolManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = CRUDUtils.query(sql, connection, preparedStatement, activeId);
        List<FundChange> fundChanges = new ArrayList<>();

        try {
            while (rs.next()) {
                FundChange fundChange = new FundChange();
                fundChange.setFundChangeId(rs.getLong("fund_change_id"));
                fundChange.setUserId(rs.getLong("user_id"));
                fundChange.setActiveType(rs.getString("active_type"));
                fundChange.setActiveId(rs.getLong("active_id"));
                fundChange.setPassiveType(rs.getString("passive_type"));
                fundChange.setPassiveId(rs.getLong("passive_id"));
                fundChange.setChangeType(rs.getString("change_type"));
                fundChange.setDescription(rs.getString("description"));
                fundChange.setAmount(rs.getString("amount"));
                fundChange.setOrderId(rs.getLong("order_id"));
                fundChange.setGmtCreate(rs.getString("gmt_create"));
                fundChange.setGmtModified(rs.getString("gmt_modified"));
                fundChanges.add(fundChange);
            }
            if (rs != null) {
                rs.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            ConnectionPoolManager.releaseConnection(connection);
            return fundChanges;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long save(FundChange fundChange) throws SQLException {
        String sql = "INSERT INTO `fund_changes` (`user_id`, `active_type`, `active_id`, `passive_type`, `passive_id`, `change_type`, `description`, `amount`, `order_id`, `gmt_create`, `gmt_modified`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return CRUDUtils.save(sql, fundChange.getUserId(), fundChange.getActiveType(), fundChange.getActiveId(), fundChange.getPassiveType(), fundChange.getPassiveId(), fundChange.getChangeType(), fundChange.getDescription(), fundChange.getAmount(), fundChange.getOrderId(), fundChange.getGmtCreate(), fundChange.getGmtModified());
    }

    @Override
    public void update(FundChange fundChange) throws SQLException {
        String sql = "UPDATE `fund_changes` SET `user_id` = ?, `active_type` = ?, `active_id` = ?, `passive_type` = ?, `passive_id` = ?, `change_type` = ?, `description` = ?, `amount` = ?, `order_id` = ?, `gmt_create` = ?, `gmt_modified` = ? WHERE `fund_change_id` = ?";
        CRUDUtils.update(sql, fundChange.getUserId(), fundChange.getActiveType(), fundChange.getActiveId(), fundChange.getPassiveType(), fundChange.getPassiveId(), fundChange.getChangeType(), fundChange.getDescription(), fundChange.getAmount(), fundChange.getOrderId(), fundChange.getGmtCreate(), fundChange.getGmtModified(), fundChange.getFundChangeId());
    }

    @Override
    public void delete(Long fundChangeId) throws SQLException {
        String sql = "DELETE FROM `fund_changes` WHERE `fund_change_id` = ?";
        CRUDUtils.update(sql, fundChangeId);
    }
}
