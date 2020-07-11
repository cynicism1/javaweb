package com.atguigu1.dao01;

import com.atguigu1.bean.Customer;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * @author Cynicism
 * @Package_name
 * @since 2020/6/30 21:47
 * 在此接口用于规范customer表的通用方法
 */
public interface CustomerDao {
    /**
     * 将customer对象添加到数据库中
     * @param connection
     * @param customer
     */
    void insert(Connection connection, Customer customer);

    /**
     * 针对指定的id，删除表中的一条记录
     * @param connection
     * @param id
     */
    void deleteById(Connection connection, int id);

    /**
     * 针对内存中的customer对象，去修改数据表中指定的对象
     * @param connection
     * @param customer
     */
    void update(Connection connection, Customer customer);

    /**
     * 根据指定的id查询得到对应的customer对象
     * @param connection
     * @param id
     */
    Customer getCustomerById(Connection connection,int id);

    /**
     * 查询表中所有的记录过程的集合
     * @param connection
     * @return
     */
    List<Customer> getAll(Connection connection);

    /**
     * 返回数据库的数据的条目数
     * @param connection
     * @return
     */
    Long getCount(Connection connection);

    /**
     * 返回数据表中最大的生日
     * @param connection
     * @return
     */
    Date getMaxBirth(Connection connection);

}
