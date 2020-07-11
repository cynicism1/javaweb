package com.atguigu.exer;

import com.atguigu.bean.Customer;
import com.atguigu.util.JDBCUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;

/**
 * 测试使用preparedStatement操作Blob类型的数据
 *
 * @author Cynicism
 * @Package_name
 * @since 2020/6/26 10:28
 */
public class BlobTest {
    //向数据表customers中插入Blob字段
    @Test
    public void testInsert() throws Exception {
        Connection connection = JDBCUtils.getConnection();
        String sql = "insert into customers(name,email,birth,photo)values(?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, "袁浩");
        preparedStatement.setObject(2, "zhang@qq.com");
        preparedStatement.setObject(3, "1998-05-06");
        FileInputStream fileInputStream = new FileInputStream(new File("girl.jpg"));
        preparedStatement.setBlob(4, fileInputStream);
        preparedStatement.execute();
        JDBCUtils.closeResource(connection, preparedStatement);
    }

    //查询数据表customers中Blob来兴的字段
    @Test
    public void testQuert() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth,photo from customers where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, 21);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                //方式一：
                //            int id = resultSet.getInt(1);
                //            String name = resultSet.getString(2);
                //            String email = resultSet.getString(3);
                //            Date birth = resultSet.getDate(4);
                //方式二：
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                Date birth = resultSet.getDate("birth");
                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);
                Blob photo = resultSet.getBlob("photo");
                InputStream binaryStream = photo.getBinaryStream();
                FileOutputStream fileOutputStream = new FileOutputStream(new File("zhangyuhao.jpg"));
                byte[] buffer = new byte[1024];
                int len;
                while ((len = binaryStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, len);
                }
                fileOutputStream.close();
                binaryStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }

    }
}
