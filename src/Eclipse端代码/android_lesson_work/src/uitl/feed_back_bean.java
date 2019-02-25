package uitl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import bcz.vocb.DBCon;

public class feed_back_bean {
	
	public int setFeedBack(String text, String time) {
		Connection con = DBCon.getConnection();
		try {
			String sql = "insert bcz_feed_back " + "(feed_back_text, feed_back_time) "
					+ "values(?, ?)";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, text);
			statement.setString(index, time);
			statement.executeUpdate();
			System.out.println("�޸����ݿ�setFeedBack�ɹ�...");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�޸����ݿ�setFeedBackʧ�ܣ�");
			return 1;
		} finally {
			if (con != null) {
				try {
					con.close();
				}catch (Exception e2) {
					System.out.println("setFeedBack�Ͽ����ݿ�ʧ�ܣ�"+e2.getMessage());
					return 2;
				}
			}
		}
		return 0;
	}
}
