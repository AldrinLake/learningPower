package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conJDBC {
	 private static final String DBNAME="learnforchina";
	    private static final String URL="jdbc:mysql://localhost:3306/"+DBNAME+"?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true";
		private static final String NAME="root"; 
		private static final String PASS="441098"; 
		public static Connection getcon()
		{
			Connection con=null;	
			try
			{	//获取驱动
				Class.forName("com.mysql.cj.jdbc.Driver");
				//获取连接
				con= DriverManager.getConnection(URL,NAME,PASS); 
			} catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("con_JDBC连接数据库失败");
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("con_JDBC连接数据库失败");
			}
			return con;//返回连接对象
		}
		public static void main(String[] args) {
			getcon();
		}
}
