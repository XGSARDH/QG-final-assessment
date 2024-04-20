package com.qg.util.jdbcUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * JdbcUtils
 * @Author: xgsardh
 * @Date: 2024.3.19
 */
public class JdbcUtils {
    static InputStream inputStream = null;
    static Properties properties = new Properties();

    //Connect to server
    static {
        try {
            inputStream = JdbcUtils.class.getResourceAsStream("/database.properties");
            // This could throw IOException
            properties.load(inputStream);
            // This could throw ClassNotFoundException
            Class.forName(properties.getProperty("jdbc.driver"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    // This could throw IOException
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *  @name        : public static Connection getconntion()throws Exception
     *	@description : Get available connection objects
     *	@param		 : None
     *	@return		 : Connection
     *  @notice      : None
     */
    public static Connection getconntion()throws Exception{
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        Connection conn = DriverManager.getConnection(url,username,password);
        return conn;
    }

    /**
     *  @name        : public static void close(ResultSet set, Statement statement, Connection connection)throws Exception
     *	@description : Close Connection
     *	@param		 : ResultSet set, Statement statement, Connection connection
     *	@return		 : None
     *  @notice      : None
     */
    public static void close(ResultSet set, Statement statement, Connection connection)throws Exception {
        if(set != null){
            set.close();
        }
        if(statement != null){
            statement.close();
        }
        if(connection != null){
            connection.close();
        }
    }



}
