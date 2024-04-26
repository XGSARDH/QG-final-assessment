package com.qg.dao;

import com.qg.factory.DaoFactory;
import com.qg.po.UserFund;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class TestGroupFund {
    @Test
    public void TestStringAndBigDecimal() throws SQLException {
        List<UserFund> byUserId = DaoFactory.getUserFundDao().findByUserId(13L);

        for (UserFund userFund : byUserId) {
            BigDecimal bigDecimal = new BigDecimal(userFund.getTotalFunds().toString());
            bigDecimal = bigDecimal.add(bigDecimal);
            userFund.setTotalFunds(bigDecimal.toString());
            DaoFactory.getUserFundDao().update(userFund);
        }

    }
}
