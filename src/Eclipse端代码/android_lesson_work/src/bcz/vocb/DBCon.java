package bcz.vocb;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCon {
	
	// 获得数据库连接
	public static Connection getConnection() {
		
		Connection con = null;
    	try{
    		// 加载MySql数据驱动
    		Class.forName( "com.mysql.jdbc.Driver" );
    		System.out.println("JDBC数据库驱动加载成功...");
    	}catch (Exception e) {
    			System.out.println("忘记把MySQL数据库的JDBC数据库驱动程序复制到JDK的扩展目录中");
		}
    	try {
    		// 建立链接
    		String uri = "jdbc:mysql://127.0.0.1/android_lesson?useUnicode=true&characterEncoding=UTF-8";
			String user = "root";
			String password = "";
			con = DriverManager.getConnection(uri,user,password);
   			System.out.println("数据库连接成功...");
    	    // 创建数据库连接
    	}catch( Exception e ){
    		System.out.printf( "数据库连接失败\n" );
    		e.printStackTrace();
    	}
    	return con;
	}
	
	public static void close(ResultSet rs, Statement st, Connection conn) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
