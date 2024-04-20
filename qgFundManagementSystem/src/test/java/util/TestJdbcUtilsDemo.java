package util;

import com.qg.util.jdbcUtils.JdbcUtils;

import java.sql.Connection;
import java.sql.Statement;

public class TestJdbcUtilsDemo {
    public static void main(String[] args)throws Exception {
        Connection conn = JdbcUtils.getconntion();

        //3. 定义sql语句
        String sql = "update account set money = 4000 where id = 2";

        //4. 获取执行sql的对象 Statement
        Statement stmt = conn.createStatement();

        //5. 执行sql
        //受影响的行数
        int count = stmt.executeUpdate(sql);

        //6. 处理结果
        System.out.println(count);

        //7. 释放资源
        //先释放后面打开的,后释放先打开的+
        JdbcUtils.close(null,stmt,conn);

    }

}
