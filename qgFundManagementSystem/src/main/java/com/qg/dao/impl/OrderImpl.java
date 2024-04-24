package com.qg.dao.impl;

import com.qg.dao.OrderDao;
import com.qg.po.Order;
import com.qg.util.connectPool.ConnectionPoolManager;
import com.qg.util.crudUtils.CRUDUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderImpl implements OrderDao {

    private static volatile OrderImpl instance;

    public static OrderImpl getInstance() {
        if (instance == null) {
            synchronized (OrderImpl.class) {
                if (instance == null) {
                    instance = new OrderImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public List<Order> findByActiveId(Long activeId) {
        String sql = "SELECT `order_id`, `active_type`, `active_id`, `passive_type`, `passive_id`, `change_type`, `description`, `amount`, `gmt_create`, `gmt_modified` FROM `orders` WHERE `active_id` = ?";
        Connection connection = ConnectionPoolManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = CRUDUtils.query(sql, connection, preparedStatement, activeId);
        List<Order> orders = new ArrayList<>();

        try {
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getLong("order_id"));
                order.setActiveType(rs.getString("active_type"));
                order.setActiveId(rs.getLong("active_id"));
                order.setPassiveType(rs.getString("passive_type"));
                order.setPassiveId(rs.getLong("passive_id"));
                order.setChangeType(rs.getString("change_type"));
                order.setDescription(rs.getString("description"));
                order.setAmount(rs.getString("amount"));
                order.setGmtCreate(rs.getString("gmt_create"));
                order.setGmtModified(rs.getString("gmt_modified"));
                orders.add(order);
            }
            if (rs != null) {
                rs.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            ConnectionPoolManager.releaseConnection(connection);
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long save(Order order) throws SQLException {
        String sql = "INSERT INTO `orders` (`active_type`, `active_id`, `passive_type`, `passive_id`, `change_type`, `description`, `amount`, `gmt_create`, `gmt_modified`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return CRUDUtils.save(sql, order.getActiveType(), order.getActiveId(), order.getPassiveType(), order.getPassiveId(), order.getChangeType(), order.getDescription(), order.getAmount(), order.getGmtCreate(), order.getGmtModified());
    }

    @Override
    public void update(Order order) throws SQLException {
        String sql = "UPDATE `orders` SET `active_type` = ?, `active_id` = ?, `passive_type` = ?, `passive_id` = ?, `change_type` = ?, `description` = ?, `amount` = ?, `gmt_create` = ?, `gmt_modified` = ? WHERE `order_id` = ?";
        CRUDUtils.update(sql, order.getActiveType(), order.getActiveId(), order.getPassiveType(), order.getPassiveId(), order.getChangeType(), order.getDescription(), order.getAmount(), order.getGmtCreate(), order.getGmtModified(), order.getOrderId());
    }

    @Override
    public void delete(Long orderId) throws SQLException {
        String sql = "DELETE FROM `orders` WHERE `order_id` = ?";
        CRUDUtils.update(sql, orderId);
    }
}
