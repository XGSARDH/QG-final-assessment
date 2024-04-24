package com.qg.dao;

import com.qg.po.FundChange;

import java.sql.SQLException;
import java.util.List;

public interface FundChangeDao {
    List<FundChange> findByActiveId(Long activeId);

    Long save(FundChange fundChange) throws SQLException;

    void update(FundChange fundChange) throws SQLException;

    void delete(Long fundChangeId) throws SQLException;
}
