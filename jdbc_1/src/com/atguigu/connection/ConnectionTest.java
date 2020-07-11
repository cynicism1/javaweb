package com.atguigu.connection;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static java.lang.Class.forName;

/**
 * @auther Cynicism
 * @create 2020-06-21 14:38
 * @packge-name com.atguigu.connection
 */
public class ConnectionTest {
    //方式一：
    @Test
    public void testConnection1() throws SQLException {

        Driver driver = new com.mysql.jdbc.Driver();
        //url:http://localhost:8080/gmall/keyboard.jpg
        //jdbc:mysql:协议
        //localhost:ip地址
        //3306:端口号
        //test:test数据库
        String url = "jdbc:mysql://localhost:3306/test";
        //将用户和密码封装在Properties中
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456");
        Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }

    //方式二：对方式一的迭代:在如下的程序中不出现第三方的api，使得程序具有更好的移植性
    @Test
    public void testConnection2() throws Exception {
        //1.获取Driver实现类对象，使用反射
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();
        //2.提供要连接 的数据库
        String url = "jdbc:mysql://localhost:3306/test";
        //3.提供连接数据库的账户和密码
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456");
        //4.获取连接
        Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }

    //方式三：使用DriverManager替换Driver
    @Test
    public void testConnection3() throws Exception {
        //1.获取driver实现类对象
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();
        //2.提供另外三个连接的基本信息
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "123456";
        //注册驱动
        DriverManager.registerDriver(driver);
        //获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

    //方式四
    @Test
    public void testConnection4() throws Exception {
        //1.提供另外三个连接的基本信息
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "123456";
        //2.获取driver实现类对象
        Class.forName("com.mysql.jdbc.Driver");
//        Driver driver = (Driver) clazz.newInstance();
//        //注册驱动
//        DriverManager.registerDriver(driver);
        //获取连接
        Connection connection = DriverManager.getConnection(url,user,password);
        System.out.println(connection);
    }

    //方式五 ：最终版：将数据库需要连接的4个基本信息声明在配置文件中，通过读取配置文件的方式，获取连接
    @Test
    public void testConnection5() throws Exception {
        //1.读取配置文件的4 个基本信息
        InputStream resourceAsStream = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }
}

