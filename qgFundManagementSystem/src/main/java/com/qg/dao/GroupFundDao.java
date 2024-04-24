package com.qg.dao;

import com.qg.po.GroupFund;

import java.sql.SQLException;
import java.util.List;

public interface GroupFundDao {
    List<GroupFund> findByGroupId(Long groupId);

    Long save(GroupFund groupFund) throws SQLException;

    void update(GroupFund groupFund) throws SQLException;

    void delete(Long groupFundId) throws SQLException;
}
