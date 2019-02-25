package uitl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bcz.vocb.DBCon;

public class recent_account_bean {
	public String getRecentAccount() {
		Connection con = DBCon.getConnection();
		String account = null;
		try {
			String sql = "select account " 
					+ "from bcz_recent_account";
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet rSet = statement.executeQuery();
			System.out.println("��ѯ���...");
			while (rSet.next()) {
				account = rSet.getString("account");
			}
		} catch (Exception e) {
			System.out.println("��ѯʧ�ܣ�" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getRecentAccount�Ͽ����ݿ�ʧ�ܣ�" + e2.getMessage());
				}
			}
		}
		return account;
	}
	
	public void setRecentAccount(String recentAccount) {
		Connection con = DBCon.getConnection();
		try {
			String sql = "update bcz_recent_account "
					+ "set account = ? ";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index, recentAccount);
			statement.executeUpdate();
			System.out.println("�޸����ݿ�recentAccount�ɹ�...");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�޸����ݿ�recentAccountʧ�ܣ�");
		} finally {
			if (con != null) {
				try {
					con.close();
				}catch (Exception e2) {
					System.out.println("cancel_autoLogin�Ͽ����ݿ�ʧ�ܣ�"+e2.getMessage());
				}
			}
		}
	}
}
