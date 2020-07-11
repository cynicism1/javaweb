package com.atguigu1.transaction;

import com.atguigu1.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author Cynicism
 * @Package_name
 * @since 2020/6/26 15:34
 */
public class ConnectionTest {
    @Test
    public void testgetConnection() throws Exception {
        Connection connection = JDBCUtils.getConnection();
        System.out.println(connection);

    }
}
