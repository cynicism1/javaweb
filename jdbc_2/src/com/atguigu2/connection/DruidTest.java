package com.atguigu2.connection;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author Cynicism
 * @Package_name
 * @since 2020/7/1 22:00
 */
public class DruidTest {
    @Test
    public void getConnection() throws Exception {
        Properties pros = new Properties();
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
        pros.load(resourceAsStream);
        DataSource dataSource = DruidDataSourceFactory.createDataSource(pros);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}
