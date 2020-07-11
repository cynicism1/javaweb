package com.atguigu.perparestaement.crud;

import com.atguigu.bean.Order;
import com.atguigu.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * 针对Order
 *
 * @author Cynicism
 * @Package_name
 * @since 2020/6/22 16:52
 */
public class OrderForQuery {
    @Test
    public void testOrderForQuery() {
        String sql = "select order_id  orderId,order_name orderNname,order_date orderDate from `order` where order_id = ?";
        Order order = textOrderForQuery(sql, 1);
        System.out.println(order);
    }

    public Order textOrderForQuery(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            //执行获取结果集
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            //获取列数
            if (resultSet.next()) {
                Order order = new Order();
                for (int i = 0; i < columnCount; i++) {
                    //获取每个列值:通过ResultSet
                    Object object = resultSet.getObject(i + 1);
                    //获取列的别名：getColumnName()
                    //获取列的别名：getColumnLabel()
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    //通过反射，将对象指定columnName
                    Field declaredField = Order.class.getDeclaredField(columnLabel);
                    declaredField.setAccessible(true);//保证私有的也可以访问
                    declaredField.set(order, object);//将 order对象赋值columnName。
                }
                return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }
        return null;
    }

    @Test
    public void testQuery() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select order_id,order_name,order_date from `order` where order_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, 2);//一个占位符，order_id= （想要查询什么）
            resultSet = preparedStatement.executeQuery();//得到一个 结果集
            if (resultSet.next()) {
                int id = (int) resultSet.getObject(1);
                String name = (String) resultSet.getObject(2);
                Date date = (Date) resultSet.getObject(3);
                Order order = new Order(id, name, date);
                System.out.println(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }

    }

}
