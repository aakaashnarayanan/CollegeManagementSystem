package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnect {
	public final static String JDBC_URL = "jdbc:derby:internalMarkManagement;create=true";
	public static Connection conn;
	
	public static void connect() throws Exception {
		conn = DriverManager.getConnection(JDBC_URL);
		System.out.println("Connection created...");
	}
}
