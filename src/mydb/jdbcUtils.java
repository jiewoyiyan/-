package mydb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class jdbcUtils {
	
	public static Connection getConnection()
	{
		Connection conn=null;
		String url="jdbc:mysql://localhost:3306/shoppingmalldb?useSSL=false&serverTimezone=GMT%2B8";
		String user="root";
		String password="root";
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=DriverManager.getConnection(url, user, password);
		}
		catch(ClassNotFoundException|SQLException e)
		{
			e.printStackTrace();
			System.out.println("未连接到数据库");
//			System.exit(0);
		}
		return conn;
	}
}
