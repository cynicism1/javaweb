package com.atguigu.perparestaement.crud;

import com.atguigu.bean.Customer;
import com.atguigu.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;


/**
 * @author Cynicism
 * @Package_name
 * @since 2020/6/21 22:31
 * @deprecated 针对Customer表的查询操作
 */
public class CustomerForQuery {
    @Test
    public void testQuesForCustomer() {
        String sql = "select id, name,email,birth from customers where id = ? ";
        String sql1 = "select name,email from customers where name= ? ";
        Customer customer = quesForCustomer(sql, 13);
        Customer customer1 = quesForCustomer(sql1, "周杰伦");
        System.out.println(customer);
        System.out.println(customer1);
    }

    /**
     * 针对于customers 表的通用的查询操作
     */
    public Customer quesForCustomer(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            //获取结果集的元数据：ResultSetMetaData
            ResultSetMetaData metaData = resultSet.getMetaData();
            //通过ResultSetMetaData获取结果集的列数
            int columnCount = metaData.getColumnCount();
            if (resultSet.next()) {
                Customer customer = new Customer();
                for (int i = 0; i < columnCount; i++) {
                    Object catalovalue = resultSet.getObject(i + 1);
                    //获取每个列的列名
                    String catalogName = metaData.getColumnName(i + 1);
                    //给customer对象指定某个属性，赋值为catalovalue  ：通过反射
                    Field declaredField = Customer.class.getDeclaredField(catalogName);
                    declaredField.setAccessible(true);
                    declaredField.set(customer, catalovalue);

                }
                return customer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }
        return null;
    }

    @Test
    public void testQuery1() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select id, name,email,birth from customers where id = ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, 1);
            //执行，并返回结果集
            resultSet = preparedStatement.executeQuery();
            //处理结果集
            if (resultSet.next()) {//nest():判断结果集下一条是否有数据，如果有数据返回true，并指针下移；如果返回false，指针不会下移。
                //获取当前数据的各个字段的值
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);
                //方式一：
                //System.out.println("id = "+ id + ",name = "+  name +",email =" + email + ", birth  =" + birth );
                //方式二：
                Object[] data = new Object[]{id, name, email, birth};
                //方式三：将数据封装为一个对象
                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源的操作
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }

    }
}
