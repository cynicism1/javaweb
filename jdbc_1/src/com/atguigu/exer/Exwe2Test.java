package com.atguigu.exer;

import com.atguigu.util.JDBCUtils;
import org.junit.Test;
import sun.java2d.pipe.SpanIterator;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Scanner;

/**
 * @author Cynicism
 * @Package_name
 * @since 2020/6/25 15:33
 */
public class Exwe2Test {
    //    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("四级和六级：");
//        int type = scanner.nextInt();
//        System.out.print("身份证号：");
//        String IDCard = scanner.next();
//        System.out.print("准考证号：");
//        String ExamCard = scanner.next();
//        System.out.print("学生姓名：");
//        String StudentName = scanner.next();
//        System.out.print("所在城市：");
//        String Location = scanner.next();
//        System.out.print("考试成绩：");
//        String Grade = scanner.next();
//        String sql = "insert into examstudent(type,IDCard,ExamCard,StudentName,Location,Grade)values(?,?,?,?,?,?)";
//        int update = update(sql, type, IDCard, ExamCard, StudentName, Location, Grade);
//         if (update>0){
//             System.out.println("添加成功");
//         }else{
//             System.out.println("添加失败");
//         }
//    }
    //向examstudent添加一条记录
    /*
    Type:
    IDCard:
    ExamCard:
    StudentName:
    Location:
    Grade:
     */
    public static int update(String sql, Object... args) {//SQL中
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
            //5.资源的关闭
            JDBCUtils.closeResource(connection, preparedStatement);
        }
        return 0;
    }
    //根据准考证号和省份证号查询学生的成绩信息
//    public static void main(String[] args) {
//        System.out.println("请选择你要输入的类型：");
//        System.out.println("a.准考证号");
//        System.out.println("b.身份证号");
//        Scanner scanner = new Scanner(System.in);
//        String selection = scanner.next();
//        if ("a".equalsIgnoreCase(selection)) {
//            System.out.println("请输入准考证号：");
//            String examCard = scanner.next();
//            String sql = "select FlowID flowID,type,IDcard,examCard,StudentName name,Location location,Grade grade from examstudent where ExamCard = ? ";
//            Student instance = getInstance(Student.class, sql, examCard);
//            System.out.println(instance);
//        }else if ("b".equalsIgnoreCase(selection)){
//            System.out.println("请输入身份证号：");
//            String IDCard = scanner.next();
//            String sql = "select FlowID flowID,type,IDcard,examCard,StudentName name,Location location,Grade grade from examstudent where IDcard = ?  ";
//            Student instance = getInstance(Student.class, sql, IDCard);
//            System.out.println(instance);
//        }else {
//            System.out.println("你的输入有误，请重新进入程序");
//        }
//    }


    public static <T> T getInstance(Class<T> clazz, String sql, Object... args) {
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

    //删除指定的学生信息
    public static void main(String[] args) {
        System.out.println("请输入学生的考号：");
        Scanner scanner = new Scanner(System.in);
        String examCard = scanner.next();
        String sql = "select FlowID flowID,type,IDcard,examCard,StudentName name,Location location,Grade grade from examstudent where ExamCard = ? ";
        Student instance = getInstance(Student.class, sql, examCard);
        if (instance == null) {
            System.out.println("查无此人，请重新输入");
        } else {
            String sql1 = "delete from examstudent where examCard = ? ";
            int update = update(sql1, examCard);
            if (update > 0) {
                System.out.println("删除成功");
            } else {
                System.out.println("删除失败");
            }
        }
    }
}
