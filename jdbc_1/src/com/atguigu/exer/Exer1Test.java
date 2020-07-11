package com.atguigu.exer;

import com.atguigu.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author Cynicism
 * @Package_name
 * @since 2020/6/25 14:01
 */
public class Exer1Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入用户名:");
        String name = scanner.next();
        System.out.print("请输入邮箱:");
        String email = scanner.next();
        System.out.print("请输入生日:");
        String birthday = scanner.next();
        String sql = "insert into customers(name,email,birth)values(?,?,?)";
        int update = update(sql, name, email, birthday);
        if (update>0){
            System.out.println("添加成功");
        }else{
            System.out.println("添加失败");
        }
    }
    //通用的增删改操作

    public static int update(String sql,Object ...args){//SQL中
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
           return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.资源的关闭
            JDBCUtils.closeResource(connection, preparedStatement);
        }
        return 0;
    }
}
