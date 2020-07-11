package com.atguigu1.dao01.junit;

import com.atguigu1.bean.Customer;
import com.atguigu1.dao.CustomerDaoImpl;
import com.atguigu2.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;


/**
 * @author Cynicism
 * @Package_name
 * @since 2020/6/30 22:24
 */
public class CustomerDaoImplTest {
    private CustomerDaoImpl dao = new CustomerDaoImpl();

    @Test
    public void insert() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            Customer customer = new Customer(1, "于小飞", "xaiofei@126.com", new Date(4363464635L));
            dao.insert(connection, customer);
            System.out.println("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }

    }

    @Test
    public void deleteById() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            dao.deleteById(connection, 13);
            System.out.println("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }

    }

    @Test
    public void update() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            Customer customer = new Customer(18, "贝多芬", "beiduofeng@126.com", new Date(15615641541L));
            dao.update(connection, customer);
            System.out.println("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }

    }

    @Test
    public void getCustomerById() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection3();
            Customer customerById = dao.getCustomerById(connection, 19);
            System.out.println(customerById);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }

    }

    @Test
    public void getAll() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection2();
            List<Customer> all = dao.getAll(connection);
            all.forEach(System.out::println);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }

    }

    @Test
    public void getCount() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            Long count = dao.getCount(connection);
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }

    }

    @Test
    public void getMaxBirth() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            java.util.Date maxBirth = dao.getMaxBirth(connection);
            System.out.println("最大的生日为" + maxBirth);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }

    }
}