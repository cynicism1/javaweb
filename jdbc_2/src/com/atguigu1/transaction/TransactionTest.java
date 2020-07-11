package com.atguigu1.transaction;

import com.atguigu1.util.JDBCUtils;
import org.junit.Test;

import javax.xml.stream.XMLOutputFactory;
import java.lang.reflect.Field;
import java.sql.*;

/**
 * @author Cynicism
 * @Package_name
 * @since 2020/6/26 15:39
 */
/*
 *  1.  什么叫做数据事务？
 *      事务：一组逻辑操作单元,使数据从一种状态变换到另一种状态
 *          > 一组逻辑单元：一个或多个dml操作
 *  2. 事务处理的原则：
 *          事务处理（事务操作）：保证所有事务都作为一个工作单元来执行，
 *      即使出现了故障，都不能改变这种执行方式。当在一个事务中执行多个操作时，
 *      要么所有的事务都被提交(commit)，那么这些修改就永久地保存下来；
 *      要么数据库管理系统将放弃所作的所有修改，整个事务回滚(rollback)
 *      到最初状态。
 *  3. 数据一旦提交，就不可以回滚
 *
 *  4. 那些操作会导致数据自动提交
 *      > ddl操作一旦执行，都会自动提交
 *          > set autocommit = false 对ddl操作失败
 *      > dml默认情况下，一旦情况下，就会自动提交
 *          > 我们可以通过set autocommit = false 的方式取消dml操作的自动提交
 *      > 默认在关闭连接时，会自动的提交数据
 */
public class TransactionTest {
    //**********************************为考虑数据库事务情况下的转账操作**************************
    /*
    正对于数据表User——table
    aa给bb转账100
    update user_table set balance = balance - 100 where user = "AA";
    update user_table set balance = balance + 100 where user = "BB";
     */
    @Test
    public void testUpdate() {
        String sql1 = "update user_table set balance = balance - 100 where user = ?";
        update(sql1, "AA");

        //模拟网络异常
        System.out.println(10 / 0);


        String sql2 = "update user_table set balance = balance + 100 where user = ?";
        update(sql2, "BB");
        System.out.println("转账成功");
    }

    //通用的增删改操作--- version 1.0
    public int update(String sql, Object... args) {//SQL中
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.获取数据库的连接
            connection = JDBCUtils.getConnection();
            //2.预编译 sql语句，返回preparedStatement的实例
            preparedStatement = connection.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);//小数参数声明错误
            }
            //4. 执行
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //5.资源的关闭
            JDBCUtils.closeResource(connection, preparedStatement);
        }
        return 0;
    }

    //************************考虑数据库事务转账操作***********************
    @Test
    public void testUpdateWithtx() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            //设置数据不可以自动提交
            System.out.println(connection.getAutoCommit());//true
            //1.取消数据的自动提交
            connection.setAutoCommit(false);
            String sql1 = "update user_table set balance = balance - 100 where user = ?";
            update(connection, sql1, "AA");

            //模拟网络异常
            System.out.println(10 / 0);

            String sql2 = "update user_table set balance = balance + 100 where user = ?";
            update(connection, sql2, "BB");
            System.out.println("转账成功");
            //提交数据
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //回滚数据
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }

    //通用的增删改操作--- version 2.0
    public int update(Connection connection, String sql, Object... args) {
        PreparedStatement preparedStatement = null;
        try {
            //2.预编译 sql语句，返回preparedStatement的实例
            preparedStatement = connection.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);//小数参数声明错误
            }
            //4. 执行
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.资源的关闭
            JDBCUtils.closeResource(null, preparedStatement);
        }
        return 0;
    }

    //***********************************************************************
    @Test
    public void testTransactionSelect() throws Exception {
        Connection connection = JDBCUtils.getConnection();
        System.out.println(connection.getTransactionIsolation());
        //设置在数据库的隔离级别
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        connection.setAutoCommit(false);
        String sql = "select user,password,balance from user_table where user = ?";
        User cc = getInstance(connection, User.class, sql, "CC");
        System.out.println(cc);
    }

    @Test
    public void testTransactionUpdate() throws Exception {
        Connection connection = JDBCUtils.getConnection();
        connection.setAutoCommit(false);
        String sql = "update user_table set balance = ? where user = ?";
        update(connection, sql, 5000, "CC");
        Thread.sleep(15000);
        System.out.println("修改结束");
    }

    //通用的查询操作：用于返回数据库中的一条记录（verson 2.0,考虑事务）
    public static <T> T getInstance(Connection connection, Class<T> clazz, String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            //获取结果集的元数据 :ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            //通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();
            if (rs.next()) {
                T t = clazz.newInstance();
                //处理结果集一行数据中的每一个列
                for (int i = 0; i < columnCount; i++) {
                    //获取列值
                    Object columValue = rs.getObject(i + 1);
                    //获取每个列的列名
//					String columnName = rsmd.getColumnName(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    //给cust对象指定的columnName属性，赋值为columValue：通过反射
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, ps, rs);
        }
        return null;

    }
}
