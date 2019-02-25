package bcz.vocb;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCon {
	
	// ������ݿ�����
	public static Connection getConnection() {
		
		Connection con = null;
    	try{
    		// ����MySql��������
    		Class.forName( "com.mysql.jdbc.Driver" );
    		System.out.println("JDBC���ݿ��������سɹ�...");
    	}catch (Exception e) {
    			System.out.println("���ǰ�MySQL���ݿ��JDBC���ݿ����������Ƶ�JDK����չĿ¼��");
		}
    	try {
    		// ��������
    		String uri = "jdbc:mysql://127.0.0.1/android_lesson?useUnicode=true&characterEncoding=UTF-8";
			String user = "root";
			String password = "";
			con = DriverManager.getConnection(uri,user,password);
   			System.out.println("���ݿ����ӳɹ�...");
    	    // �������ݿ�����
    	}catch( Exception e ){
    		System.out.printf( "���ݿ�����ʧ��\n" );
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
