package com.qg.util;

import com.qg.util.connectPool.ConnectionPoolManager;
import com.qg.util.crudUtils.CRUDUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestCrudUtils {
    @Test
    public void TestUpdate() throws SQLException {
        String sql = null;

        sql = "INSERT INTO account (name, money) VALUES (?, ?)";
        // 调用save函数，插入数据，并获取生成的ID
        Long generatedId = CRUDUtils.save(sql, "Gogo", 2000);

        if (generatedId != null) {
            System.out.println("Inserted new user with ID: " + generatedId);
        } else {
            System.out.println("Failed to insert new user.");
        }

        sql = "UPDATE account SET money = ? WHERE id = ?";
        CRUDUtils.update(sql, 2000, 3);

        sql = "DELETE FROM account WHERE id = ?";
        CRUDUtils.update(sql, 1004);


    }

    @Test
    public void TestQuery() throws SQLException {
        String sql;
        sql = "SELECT * FROM account WHERE name = ?";
        Connection connection = ConnectionPoolManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = CRUDUtils.query(sql, connection, preparedStatement, "Gogo");
        List<Account> acc = new ArrayList<>();

        while (rs.next()) {
            Account account = new Account();
            account.setId(rs.getInt("id"));
            account.setName(rs.getString("name"));
            account.setMoney(rs.getInt("money"));
            acc.add(account);
        }
        if (rs != null) {
            rs.close(); // 关闭ResultSet
        }
        if (preparedStatement != null) {
            preparedStatement.close(); // 关闭PreparedStatement
        }
        ConnectionPoolManager.releaseConnection(connection); // 释放数据库连接

        for (Account account : acc) {
            System.out.println(account.toString());
        }
    }
}

class Account{
    private int id;
    private int money;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Account() {
    }

    public Account(int id, int money, String name) {
        this.id = id;
        this.money = money;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", money=" + money +
                ", name='" + name + '\'' +
                '}';
    }
}
