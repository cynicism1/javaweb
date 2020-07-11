package com.atguigu.perparestaement.crud;

import com.atguigu.connection.ConnectionTest;
import com.atguigu.util.JDBCUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * @author Cynicism
 * @Package_name
 * @since 2020/6/21 20:29
 */
public class PrepareStatementUpdateTest {
    @Test
    public void testCommonUpdate(){
//      String sql = "delete from customers where id = ?";
//      update(sql,21);
        String sql = "update `order` set order_name = ? where order_id = ?";
        update(sql,"DD",2);
    }
    //通用的增删改查操作
    public void update(String sql,Object ...args){//SQL中
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.获取数据库的连接
            connection = JDBCUtils.getConnection();
            //2.预编译 sql语句，返回preparedStatement的实例
            preparedStatement = connection.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i+1,args[i]);//小数参数声明错误
            }
            //4. 执行
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.资源的关闭
            JDBCUtils.closeResource(connection, preparedStatement);
        }
    }
    //修改customers表中的一条记录
    @Test
    public void testUpdate() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.获取数据库的连接
            connection = JDBCUtils.getConnection();
            //2.预编译 sql语句，返回preparedStatement的实例
            String sql = "update customers set name =? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            //3.填充占位符
            preparedStatement.setString(1, "莫扎特");
            preparedStatement.setObject(2, 18);
            //4. 执行
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.资源的关闭
            JDBCUtils.closeResource(connection, preparedStatement);
        }
    }

    //向customer表中添加一条记录
    @Test
    public void textInsert() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            InputStream resourceAsStream = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            String url = properties.getProperty("url");
            String driverClass = properties.getProperty("driverClass");
            Class.forName(driverClass);
            connection = DriverManager.getConnection(url, user, password);
            System.out.println(connection);
            String sql = "insert into customers(name,email,birth)value(?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            //填充占位符
            preparedStatement.setString(1, "哪咤");
            preparedStatement.setString(2, "nezha@126.com");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parse = simpleDateFormat.parse("1000-01-01");
            preparedStatement.setDate(3, new Date(parse.getTime()));
            //执行sql  操作
            preparedStatement.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            //7.资源的关闭
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
    }
}
