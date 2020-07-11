package com.atguigu1.util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author Cynicism
 * @Package_name
 * @since 2020/6/21 20:55
 * @deprecated 操作 数据库的连接
 */
public class JDBCUtils {

    /**
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    //关闭资源的操作
    public static void closeResource(Connection connection, Statement preparedStatement) {
        try {
            if (preparedStatement != null)
                preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    //关闭资源的操作
    public static void closeResource(Connection connection, Statement preparedStatement, ResultSet resultSet) {
        try {
            if (preparedStatement != null)
                preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (resultSet != null)
                resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
