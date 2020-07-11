package com.atguigu.staement.crud;

import com.atguigu.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

/**
 * 演示PreparedStatement替换Statement，解决sql注入问题
 * @author Cynicism
 * @Package_name
 * @since 2020/6/25 10:47
 *
 */
public  class PreparedStatementTest {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("用户名：");
        String userName = scan.nextLine();
        System.out.print("密   码：");
        String password = scan.nextLine();

        // SELECT user,password FROM user_table WHERE USER = '1' or ' AND PASSWORD = '
        // ='1' or '1' = '1';
        String sql = "SELECT user,password FROM user_table WHERE USER = ? AND PASSWORD = ?";
        User user =getInstance(User.class, sql, userName, password);
        if (user != null) {
            System.out.println("登陆成功!");
        } else {
            System.out.println("用户名或密码错误！");
        }

    }
        /**
         * 针对与不同的表的通用查询操作，返回表中的一条记录
         * @param clazz
         * @param sql
         * @param args
         * @param <T>
         * @return
         */
        public static  <T > T getInstance(Class < T > clazz, String sql, Object...args){
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
                JDBCUtils.closeResource(conn, ps, rs);
            }
            return null;

        }
    }
