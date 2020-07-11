package com.atguigu1.dao01;

import com.atguigu1.bean.Customer;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * @author Cynicism
 * @Package_name
 * @since 2020/6/30 22:02
 */
public class CustomerDaoImpl extends BaseDAO<Customer> implements CustomerDao {

    @Override
    public void insert(Connection connection, Customer customer) {
        String sql = "insert into customers(name,email,birth)values(?,?,?)";
        update(connection, sql, customer.getName(), customer.getEmail(), customer.getBirth());
    }

    @Override
    public void deleteById(Connection connection, int id) {
        String sql = "delete from customers where id =?";
        update(connection, sql, id);
    }

    @Override
    public void update(Connection connection, Customer customer) {
        String sql = "update customers set name =?,email = ?,birth = ? where id =?";
        update(connection, sql, customer.getName(), customer.getEmail(), customer.getBirth(), customer.getId());
    }

    @Override
    public Customer getCustomerById(Connection connection, int id) {
        String sql = "select id,name,email,birth from customers where id =?";
        Customer customer = getInstance(connection,sql, id);
        return customer;
    }

    @Override
    public List<Customer> getAll(Connection connection) {
        String sql = "select id,name,email,birth from customers";
        List<Customer> list = getForList(connection, sql);
        return list;
    }

    @Override
    public Long getCount(Connection connection) {
        String sql = "select count(*) from customers";
        return getValue(connection, sql);
    }

    @Override
    public Date getMaxBirth(Connection connection) {
        String sql = "select max(birth) from customers";
        return getValue(connection, sql);
    }
}
