package com.qg.util.crudUtils;

import com.qg.util.connectPool.ConnectionPoolManager;
import com.qg.util.jdbcUtils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * CRUDUtils
 * @Author: xgsardh
 * @Date: 2024.3.20
 */
public class CRUDUtils {

    /**
     * 执行更新操作（包括INSERT、UPDATE、DELETE）
     * @param sql SQL语句
     * @param params 参数数组
     * @return 影响的行数
     */
    public static int update(String sql, Object... params) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConnectionPoolManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            return pstmt.executeUpdate();
        } finally {
            try {
                ConnectionPoolManager.releaseConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行查询操作, 要在外部给连接和归还连接,
     * @param sql SQL语句
     * @param params 参数数组
     * @return 查询结果集
     */
    public static ResultSet query(String sql,
                                  Connection connection,
                                  PreparedStatement preparedStatement,
                                  Object... params) {
        ResultSet rs = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            rs = preparedStatement.executeQuery();
            return rs; // 注意：不要在这里关闭连接，调用者需要处理ResultSet后再关闭
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 执行查询操作，返回单个值（例如计数或具体某个字段的值）
     * @param sql SQL语句
     * @param params 参数数组
     * @return 返回的单个结果值
     */
    public static Object querySingleValue(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getconntion();
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getObject(1);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if(rs != null){
                    rs.close();
                }
                if(pstmt != null){
                    pstmt.close();
                }
                ConnectionPoolManager.releaseConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行插入操作并返回生成的ID
     * @param sql SQL语句，用于插入数据
     * @param params 插入数据时使用的参数
     * @return 自动生成的主键值
     */
    public static Long save(String sql, Object... params) throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionPoolManager.getConnection();
            pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);  // 指定返回生成的键
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();  // 获取生成的键
            if (rs.next()) {
                return rs.getLong(1);  // 假设自动生成的键是一个长整型
            }
            return null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                ConnectionPoolManager.releaseConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
