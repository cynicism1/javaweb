package com.atguigu3.dbutils;

import com.atguigu1.bean.Customer;
import com.atguigu2.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

/**
 * commons-dbutils 是 Apache 组织提供的一个开源 JDBC工具类库，封装了针对于数据库的增删改查操作
 *
 * @author Cynicism
 * @Package_name
 * @since 2020/7/1 22:42
 */
public class QueryRunnerTest {
    @Test
    public void testInsert() {
        Connection connection3 = null;
        try {
            QueryRunner queryRunner = new QueryRunner();
            connection3 = JDBCUtils.getConnection3();
            String sql = "insert into customers(name,email,birth)values(?,?,?)";
            int update = queryRunner.update(connection3, sql, "蔡徐坤", "caixunkui@126.com", "1997-05-26");
            System.out.println("添加了" + update + "记录");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection3, null);
        }
    }

    //测试查询

    /**
     * BeanHandler:是ResultSetHandler接口的实现类，用于封装表中的一条记录
     * @throws Exception
     */
    @Test
    public void query()  {
        Connection connection3 = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection3 = JDBCUtils.getConnection3();
            String sql = "select id,name,email,birth from customers where id = ?";
            BeanHandler<Customer> handler = new BeanHandler<>(Customer.class);
            Customer customer = runner.query(connection3, sql, handler, 31);
            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection3,null);
        }

    }

    /**+
     * BeanListHandler:是ResultSetHandler接口的实现类，用于封装表中的多条记录构成的集合
     * @throws Exception
     */
    @Test
    public void query1()  {
        Connection connection3 = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection3 = JDBCUtils.getConnection3();
            String sql = "select id,name,email,birth from customers where id < ?";
            BeanListHandler<Customer> handler = new BeanListHandler<>(Customer.class);
            List<Customer> list = runner.query(connection3, sql, handler, 31);
            list.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection3,null);
        }

    }
}