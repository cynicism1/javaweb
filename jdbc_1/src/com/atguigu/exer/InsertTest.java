package com.atguigu.exer;

/**
 * 使用preparedStatement实现批量数据的操作
 * update , delate 本身就具有批量操作的效果
 * @author Cynicism
 * @Package_name
 * @since 2020/6/26 11:27
 */

import com.atguigu.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * 题目：向goods表中插入2万条数据
 * CREATE TABLE goods(
 * id INT PRIMARY KEY AUTO_INCREMENT,
 * NAME VARCHAR(25));
 */
public class InsertTest {
    //批量插入的方式二
    @Test
    public void testInsert1() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            long start = System.currentTimeMillis();
            connection = JDBCUtils.getConnection();
            String sql = "insert into goods(name)values(?)";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 1; i <= 1000000; i++) {
                preparedStatement.setObject(1,"name_"+i);
                preparedStatement.execute();
            }
            long end = System.currentTimeMillis();
            System.out.println("花费的时间为："+ (end-start));//11643
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection,preparedStatement);
        }
    }
    //批量插入的方式三
    @Test
    public void testInsert2() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            long start = System.currentTimeMillis();
            connection = JDBCUtils.getConnection();
            String sql = "insert into goods(name)values(?)";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 1; i <= 1000000; i++) {
                preparedStatement.setObject(1,"name_"+i);
                //1.“赞”sql
                preparedStatement.addBatch();
                if ( i % 500 == 0){
                    //2.执行
                    preparedStatement.executeBatch();
                    //3.清空batch
                    preparedStatement.clearBatch();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("花费的时间为："+ (end-start));//20000:11634 -- 364
        } catch (Exception e) {                             //1000000:599214 -- 8855
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection,preparedStatement);
        }
    }
    //批量插入的方式四:设置不允许提交数据
    @Test
    public void testInsert3() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            long start = System.currentTimeMillis();
            connection = JDBCUtils.getConnection();
            //设置不允许提交数据
            connection.setAutoCommit(false);
            String sql = "insert into goods(name)values(?)";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 1; i <= 100000000; i++) {
                preparedStatement.setObject(1,"name_"+i);
                //1.“赞”sql
                preparedStatement.addBatch();
                if ( i % 50000 == 0){
                    //2.执行
                    preparedStatement.executeBatch();
                    //3.清空batch
                    preparedStatement.clearBatch();
                }
            }
            //提交数据
            connection.commit();
            long end = System.currentTimeMillis();
            System.out.println("花费的时间为："+ (end-start));//20000:11634 -- 364
        } catch (Exception e) {                             //1000000:599214 -- 8855 -- 3938
            e.printStackTrace();                            //100000000:402930
        } finally {
            JDBCUtils.closeResource(connection,preparedStatement);
        }
    }
}
