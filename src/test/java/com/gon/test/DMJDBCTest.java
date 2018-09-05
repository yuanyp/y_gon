package com.gon.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DMJDBCTest {

    // 定义 DM JDBC 驱动串
    String jdbcString = "dm.jdbc.driver.DmDriver";
    // 定义 DM URL 连接串
    String urlString = "jdbc:dm://192.188.188.33:5236";
    // 定义连接用户名
    String userName = "SYSDBA";
    // 定义连接用户口令
    String password = "SYSDBA";
    // 定义连接对象
    Connection conn = null;

    // /* 加载 JDBC 驱动程序   * @throws SQLException 异常 */
    public void loadJdbcDriver() throws SQLException {
        try {
            System.out.println("Loading JDBC Driver...");
            //加载 JDBC 驱动程序
            Class.forName(jdbcString);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Load JDBC Driver Error : " + e.getMessage());
        } catch (Exception ex) {
            throw new SQLException("Load JDBC Driver Error : " + ex.getMessage());
        }
    }

    /* 连接 DM 数据库
     * * @throws SQLException 异常 */
    public void connect() throws SQLException {
        try {
            System.out.println("Connecting to DM Server...");
            // 连接 DM 数据库
            conn = DriverManager.getConnection(urlString, userName, password);
        } catch (SQLException e) {
            throw new SQLException("Connect to DM Server Error : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            new DMJDBCTest().connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


