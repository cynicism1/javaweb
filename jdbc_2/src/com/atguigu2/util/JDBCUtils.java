package com.atguigu2.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.atguigu1.bean.Customer;
import com.atguigu1.dao.CustomerDaoImpl;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 使用的是c3p0数据库
 *
 * @author Cynicism
 * @Package_name
 * @since 2020/7/1 15:41
 */
public class JDBCUtils {
    /**
     * 使用c3p0的数据库连接池技术
     *
     * @return
     * @throws SQLException
     */
    private static ComboPooledDataSource cpds = new ComboPooledDataSource("Helloc3p0");
    public static Connection getConnection1() throws SQLException {
        Connection connection = cpds.getConnection();
        return connection;
    }

    /**
     * 使用dbcp的数据库连接池技术
     * @return
     * @throws Exception
     */
    //***********************************************************************
    private static DataSource dataSource;
    static {
        try {
            Properties pros = new Properties();
            FileInputStream is = new FileInputStream(new File("src/dbcp.properties"));
            pros.load(is);
            //创建一个数据连接池
            dataSource = BasicDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection2() throws Exception {

        Connection connection = dataSource.getConnection();
       // System.out.println(connection);
        return connection;
    }
    //**********************************************************************************************
    /**
     * 使用Druid的数据库连接池技术
     *@return
     * @throws Exception
     */
    private static DataSource source1;
    static {
        try {
            Properties pros = new Properties();
            InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
            pros.load(resourceAsStream);
            source1 = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection3() throws Exception {
        Connection connection = source1.getConnection();
        System.out.println(connection);
        return connection;
    }
    //***********************************************************************************************
    public class CustomerDaoImplTest {
        private CustomerDaoImpl dao = new CustomerDaoImpl();

        @Test
        public void insert() {
            Connection connection = null;
            try {
                connection = com.atguigu1.util.JDBCUtils.getConnection();
                Customer customer = new Customer(1, "于小飞", "xaiofei@126.com", new Date(4363464635L));
                dao.insert(connection, customer);
                System.out.println("添加成功");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                com.atguigu1.util.JDBCUtils.closeResource(connection, null);
            }

        }

        //关闭资源的操作
        public void closeResource(Connection connection, Statement preparedStatement) {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        //关闭资源的操作
        public void closeResource(Connection connection, Statement preparedStatement, ResultSet resultSet) {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static Connection getConnection() throws Exception {
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    //关闭资源的操作
    public static void closeResource(Connection connection, Statement preparedStatement) {
        try {
            if (preparedStatement != null)
                preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void closeResource(Connection connection, Statement preparedStatement, ResultSet resultSet) {
        try {
            if (preparedStatement != null)
                preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (resultSet != null)
                resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
