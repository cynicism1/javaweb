package com.atguigu2.connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Cynicism
 * @Package_name
 * @since 2020/7/1 14:40
 */
public class C3P0Test {
    //方法一
    @Test
    public void testGetconnection() throws Exception {
        //获取C3P0数据库连接池
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.jdbc.Driver"); //loads the jdbc driver
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        cpds.setUser("root");
        cpds.setPassword("123456");
        //通过设置相关的参数，对数据库连接池进行管理
        //设置初始化的数据库连接处的连接数
        cpds.setInitialPoolSize(10);
        Connection connection = cpds.getConnection();
        System.out.println(connection);

//        DataSources.destroy(cpds);
    }
    //方法二，推荐：使用配置文件
    @Test
    public void testGetconnection1() throws SQLException {
        ComboPooledDataSource cpds = new ComboPooledDataSource("Helloc3p0");
        Connection connection = cpds.getConnection();
        System.out.println(connection);
    }

}
