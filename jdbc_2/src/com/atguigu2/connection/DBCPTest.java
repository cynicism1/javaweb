package com.atguigu2.connection;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.hamcrest.core.Is;
import org.junit.Test;

import javax.sql.DataSource;
import javax.xml.transform.Source;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Cynicism
 * @Package_name
 * @since 2020/7/1 20:18
 */
public class DBCPTest {
    //方式一： 不推荐
    @Test
    public void testGetConnection() throws SQLException {
        //创建了DBCP的数据库的连接池
        BasicDataSource source = new BasicDataSource();
        //设置基本信息
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl("jdbc:mysql:///test");
        source.setUsername("root");
        source.setPassword("123456");
        //设置奇特设计数据库连接池的属性
        source.setInitialSize(10);
        source.setMaxActive(10);

        Connection connection = source.getConnection();
        System.out.println(connection);
    }
    //方式二：
    @Test
    public void testGetConnection1() throws Exception{
        Properties pros = new Properties();
       //InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
        FileInputStream is = new FileInputStream(new File("src/dbcp.properties"));
        pros.load(is);
        DataSource dataSource = BasicDataSourceFactory.createDataSource(pros);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}
