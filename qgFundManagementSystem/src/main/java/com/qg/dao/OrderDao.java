package com.qg.dao;

import com.qg.po.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    List<Order> findByActiveId(Long activeId);
    List<Order> findByPassiveId(Long activeId);

    List<Order> findByOrderId(Long orderId);

    Long save(Order order) throws SQLException;

    void update(Order order) throws SQLException;

    void delete(Long orderId) throws SQLException;
}
