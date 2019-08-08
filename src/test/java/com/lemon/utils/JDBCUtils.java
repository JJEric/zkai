package com.lemon.utils;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;

/**
 * JDBC工具类    用来获取数据库连接
 * @author zkai
 *
 */
public class JDBCUtils {
	public static final String JDBC_URL = "jdbc:mysql://test.lemonban.com:3306/future?useUnicode=true&characterEncoding=utf-8";
	public static final String JDBC_USER = "test";
	public static final String PASSWORD = "test";
	
	public static Connection getconnection() {
		//定义数据库连接对象connection   
		Connection connection = null;
		try {
			connection = (Connection) DriverManager.getConnection(JDBC_URL, JDBC_USER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
}