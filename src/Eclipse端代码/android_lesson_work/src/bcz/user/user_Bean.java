package bcz.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bcz.vocb.DBCon;
/**
 * user_Bean��<br>
 * user���Model��<br>
 * @author �Խ���
 *
 */

public class user_Bean {

	/**
	 * �������user��<br>
	 * �Ǿ�̬������<br>
	 * ͨ��ʵ��������ø÷�����<br>
	 * @return
	 */
	public List<user_Info> getAll() {
		List<user_Info> data = new ArrayList<>();
		Connection con = DBCon.getConnection();
		try {
			String sql = "select account, username " + "from bcz_user_table";
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet rSet = statement.executeQuery();
			System.out.println("��ѯ���...");
			while (rSet.next()) {
				String account = rSet.getString("account");
				String username = rSet.getString("username");
				user_Info info = new user_Info(account, "******", username);
				data.add(info);
			}
		} catch (Exception e) {
			System.out.println("��ѯʧ�ܣ�" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getAll�Ͽ����ݿ�ʧ�ܣ�" + e2.getMessage());
				}
			}
		}
		return data;
	}

	/**
	 * ͨ��userName���User��<br>
	 * �Ǿ�̬������<br>
	 * ͨ��ʵ��������ø÷�����<br>
	 * @param queryUsername �û���
	 * @return
	 */
	public List<user_Info> getUserByUsername(String queryUsername) {
		List<user_Info> data = new ArrayList<>();
		Connection con = DBCon.getConnection();
		try {
			String sql = "select account,password,username " + "from bcz_user_table " + "where username = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index, queryUsername);
			ResultSet rSet = statement.executeQuery();
			System.out.println("��ѯ���...");
			while (rSet.next()) {
				String account = rSet.getString("account");
				String username = rSet.getString("username");
				String password = rSet.getString("password");
				user_Info info = new user_Info(account, password, username);
				data.add(info);
			}
		} catch (Exception e) {
			System.out.println("��ѯʧ�ܣ�" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("���ݿ�Ͽ�ʧ�ܣ�" + e2.getMessage());
				}
			}
		}
		return data;
	}

	/**
	 * ͨ��userAccount���user��<br>
	 * �Ǿ�̬������<br>
	 * ͨ��ʵ��������ø÷�����<br>
	 * @param queryAccount
	 * @return
	 */
	public List<user_Info> getUserByAccount(String queryAccount) {
		List<user_Info> data = new ArrayList<>();
		Connection con = DBCon.getConnection();
		try {
			String sql = "select account,password,username,isautologin,insist_day,plan_num,select_kind,collection " + "from bcz_user_table " + "where account = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index, queryAccount);
			ResultSet rSet = statement.executeQuery();
			System.out.println("��ѯ���...");
			while (rSet.next()) {
				String account = rSet.getString("account");
				String username = rSet.getString("username");
				String password = rSet.getString("password");
				int isAutoLogin = rSet.getInt("isautologin");
				int insistDay = rSet.getInt("insist_day");
				int planNum = rSet.getInt("plan_num");
				String selectKind = rSet.getString("select_kind");
				String collcetion = rSet.getString("collection");
				user_Info info = new user_Info(account, password, username, isAutoLogin, insistDay, planNum, selectKind, collcetion);
				data.add(info);
			}
		} catch (Exception e) {
			System.out.println("��ѯʧ�ܣ�" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("���ݿ�Ͽ�ʧ�ܣ�" + e2.getMessage());
				}
			}
		}
		return data;
	}

	public void autoLogin(String autoAccount) {
		if (autoAccount == null || autoAccount.length() == 0) {
			System.out.println("�����˺Ų���Ϊ�գ�");
			return;
		}
		List<user_Info> data = getUserByAccount(autoAccount);
		if (data.size() == 0) {
			System.out.println("û�и��˺ţ�");
			return;
		}
		Connection con = DBCon.getConnection();
		try {
			String sql = "update bcz_user_table " + "set isautologin = 1 " + "where account = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index, autoAccount);
			statement.executeUpdate();
			System.out.println("�޸����ݿ�isAutoLogin -> 1 �ɹ�...");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�޸����ݿ�isAutoLogin -> 1 ʧ�ܣ�");
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("isAutoLogin�Ͽ����ݿ�ʧ�ܣ�" + e2.getMessage());
				}
			}
		}
	}

	public void cancel_autoLogin(String cancelAutoAccount) {
		if (cancelAutoAccount == null || cancelAutoAccount.length() == 0) {
			System.out.println("�����˺Ų���Ϊ�գ�");
			return;
		}
		List<user_Info> data = getUserByAccount(cancelAutoAccount);
		if (data.size() == 0) {
			System.out.println("û�и��˺ţ�");
			return;
		}
		Connection con = DBCon.getConnection();
		try {
			String sql = "update bcz_user_table " + "set isautologin = 0 " + "where account = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index, cancelAutoAccount);
			statement.executeUpdate();
			System.out.println("�޸����ݿ�isautoLogin -> 0 �ɹ�...");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�޸����ݿ�isautoLogin -> 0 ʧ�ܣ�");
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("cancel_autoLogin�Ͽ����ݿ�ʧ�ܣ�" + e2.getMessage());
				}
			}
		}
	}

	public int getAutoLoginByAccount(String queryAccount) {
		int isAutoLogin = 0;
		Connection con = DBCon.getConnection();
		try {
			String sql = "select isautologin " + "from bcz_user_table " + "where account = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index, queryAccount);
			ResultSet rSet = statement.executeQuery();
			System.out.println("��ѯ���...");
			while (rSet.next()) {
				isAutoLogin = rSet.getInt("isautologin");
			}
		} catch (Exception e) {
			System.out.println("��ѯʧ�ܣ�" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getAutoLoginByAccount���ݿ�Ͽ�ʧ�ܣ�" + e2.getMessage());
				}
			}
		}
		return isAutoLogin;
	}

	/**
	 * �����û���<br>
	 * �Ǿ�̬������<br>
	 * ͨ��ʵ��������ø÷�����<br>
	 * @param addAccount �˺�
	 * @param password	����
	 * @param Username	�û���
	 */
	public void addUser(String addAccount, String password, String Username) {
		Connection con = DBCon.getConnection();
		try {
			String sql = "insert bcz_user_table " + "(account, password, username, isautologin, insist_day, plan_num) "
					+ "values(?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, addAccount);
			statement.setString(index++, password);
			statement.setString(index++, Username);
			statement.setInt(index++, 0);
			statement.setInt(index++, 0);
			statement.setInt(index, 0);
			statement.executeUpdate();
			System.out.println("addUser�ɹ�...");
		} catch (Exception e) {
			System.out.println("addUserʧ�ܣ�" + e.getMessage());
		}
	}

	/**
	 * �жϸ�Account�Ƿ���ڡ�<br>
	 * �Ǿ�̬������<br>
	 * ͨ��ʵ��������ø÷�����<br>
	 * @param Account �˺�
	 * @return
	 */
	public int isAccountExist(String Account) {
		List<user_Info> data = getUserByAccount(Account);
		if (data.isEmpty() || data.size() == 0) {
			return 0; // 0��ʾ������
		}
		return 1; // 1��ʾ����
	}

	/**
	 * ����Account����û����������<br>
	 * �Ǿ�̬������<br>
	 * ͨ��ʵ��������ø÷�����<br>
	 * @param Account �˻�
	 * @return
	 */
	public int getInsistDay(String Account) {
		int day = 0;
		if (Account == null) {
			return 0;
		}
		// �û�������
		List<user_Info> data = getUserByAccount(Account);
		if (data.isEmpty() || data.size() == 0) {
			return 0;
		}

		Connection con = DBCon.getConnection();
		try {
			String sql = "select insist_day " + "from bcz_user_table " + "where account = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index, Account);
			ResultSet rSet = statement.executeQuery();
			System.out.println("getInsistDay�ɹ�...");
			while (rSet.next()) {
				day = rSet.getInt("insist_day");
			}
		} catch (Exception e) {
			System.out.println("getInsistDayʧ��!" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getInsistDay���ݿ�Ͽ�ʧ�ܣ�" + e2.getMessage());
				}
			}
		}
		return day;
	}

	/**
	 * �����û���ΪAccount�ļ��������<br>
	 * �Ǿ�̬������<br>
	 * ͨ��ʵ��������ø÷�����<br>
	 * @param Account �˻�
	 * @return
	 */
	public int addInsistDay(String Account) {
		// �û�������
		List<user_Info> data = getUserByAccount(Account);
		if (data.isEmpty() || data.size() == 0) {
			return 0;
		}

		int day = getInsistDay(Account);
		Connection con = DBCon.getConnection();
		try {
			String sql = "update bcz_user_table " + "set insist_day = ? " + "where account = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setInt(index++, day + 1);
			statement.setString(index, Account);
			statement.executeUpdate();
			System.out.println("addInsistDay �ɹ�...");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("addInsistDay ʧ�ܣ�");
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("addInsistDay�Ͽ����ݿ�ʧ�ܣ�" + e2.getMessage());
				}
			}
		}
		return 1;
	}

	/**
	 * ����˻�ΪAccount�ƻ�������<br>
	 * �Ǿ�̬������<br>
	 * ͨ��ʵ��������ø÷�����<br>
	 * @param Account �˻�
	 * @return
	 */
	public int getPlanNum(String Account) {
		int num = 0;
		if (Account == null) {
			return 0;
		}
		// �û�������
		List<user_Info> data = getUserByAccount(Account);
		if (data.isEmpty() || data.size() == 0) {
			return 0;
		}

		Connection con = DBCon.getConnection();
		try {
			String sql = "select plan_num " + "from bcz_user_table " + "where account = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index, Account);
			ResultSet rSet = statement.executeQuery();
			System.out.println("getPlanNum�ɹ�...");
			while (rSet.next()) {
				num = rSet.getInt("plan_num");
			}
		} catch (Exception e) {
			System.out.println("getPlanNumʧ��!" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getPlanNum���ݿ�Ͽ�ʧ�ܣ�" + e2.getMessage());
				}
			}
		}
		return num;
	}

	/**
	 * �����˺�ΪAccount���û��ļ������Ϊnum��<br>
	 * @param num �������
	 * @param Account �˺�
	 * @return
	 */
	public int setPlanNum(String num, String Account) {
		// �û�������
		List<user_Info> data = getUserByAccount(Account);
		if (data.isEmpty() || data.size() == 0) {
			return 0;
		}

		Connection con = DBCon.getConnection();
		try {
			int planNum = Integer.parseInt(num);
			String sql = "update bcz_user_table " + "set plan_num = ? " + "where account = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setInt(index++, planNum);
			statement.setString(index, Account);
			statement.executeUpdate();
			System.out.println("setPlanNum �ɹ�...");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("setPlanNum ʧ�ܣ�");
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("setPlanNum�Ͽ����ݿ�ʧ�ܣ�" + e2.getMessage());
				}
			}
		}
		return 1;
	}

	public String getSelectKind(String Account) {
		if (Account == null) {
			return null;
		}
		// �û�������
		List<user_Info> data = getUserByAccount(Account);
		if (data.isEmpty() || data.size() == 0) {
			return null;
		}
		String selectKind = null;
		Connection con = DBCon.getConnection();
		try {
			String sql = "select select_kind " + "from bcz_user_table " + "where account = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index, Account);
			ResultSet rSet = statement.executeQuery();
			System.out.println("getSelectKind�ɹ�...");
			while (rSet.next()) {
				selectKind = rSet.getString("select_kind");
			}
		} catch (Exception e) {
			System.out.println("getSelectKindʧ��!" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getSelectKind���ݿ�Ͽ�ʧ�ܣ�" + e2.getMessage());
				}
			}
		}
		return selectKind;
	}

	public int setSelectKind(String selectKind, String Account) {
		// �û�������
		List<user_Info> data = getUserByAccount(Account);
		if (data.isEmpty() || data.size() == 0) {
			return 0;
		}

		Connection con = DBCon.getConnection();
		try {
			String sql = "update bcz_user_table " + "set select_kind = ? " + "where account = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, selectKind);
			statement.setString(index, Account);
			statement.executeUpdate();
			System.out.println("setSelectKind �ɹ�...");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("setSelectKind ʧ�ܣ�");
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("setSelectKind�Ͽ����ݿ�ʧ�ܣ�" + e2.getMessage());
				}
			}
		}
		return 1;
	}
	
	/**
	 * ����˺�Account���ղص��ʡ�<br>
	 * �Ǿ�̬������<br>
	 * ͨ��ʵ��������ø÷�����<br>
	 * @param Account
	 * @return
	 */
	public String getCollection(String Account) {
		if (Account == null) {
			return null;
		}
		// �û�������
		List<user_Info> data = getUserByAccount(Account);
		if (data.isEmpty() || data.size() == 0) {
			return null;
		}
		String collection = null;
		Connection con = DBCon.getConnection();
		try {
			String sql = "select collection " + "from bcz_user_table " + "where account = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index, Account);
			ResultSet rSet = statement.executeQuery();
			System.out.println("getCollection�ɹ�...");
			while (rSet.next()) {
				collection = rSet.getString("collection");
			}
		} catch (Exception e) {
			System.out.println("getCollectionʧ��!" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getCollection���ݿ�Ͽ�ʧ�ܣ�" + e2.getMessage());
				}
			}
		}
		return collection;
	}
	
	/**
	 * ����˺�Account����ѧϰ���ʡ�<br>
	 * �Ǿ�̬������<br>
	 * ͨ��ʵ��������ø÷�����<br>
	 * @param Account
	 * @return
	 */
	public String getStudied(String Account) {
		if (Account == null) {
			return null;
		}
		// �û�������
		List<user_Info> data = getUserByAccount(Account);
		if (data.isEmpty() || data.size() == 0) {
			return null;
		}
		String studied = null;
		Connection con = DBCon.getConnection();
		try {
			String sql = "select studied " + "from bcz_user_table " + "where account = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index, Account);
			ResultSet rSet = statement.executeQuery();
			System.out.println("getStudied�ɹ�...");
			while (rSet.next()) {
				studied = rSet.getString("studied");
			}
		} catch (Exception e) {
			System.out.println("getStudiedʧ��!" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getStudied���ݿ�Ͽ�ʧ�ܣ�" + e2.getMessage());
				}
			}
		}
		return studied;
	}
	
	public String getCutted(String Account) {
		if (Account == null) {
			return null;
		}
		// �û�������
		List<user_Info> data = getUserByAccount(Account);
		if (data.isEmpty() || data.size() == 0) {
			return null;
		}
		String cutted = null;
		Connection con = DBCon.getConnection();
		try {
			String sql = "select cutted " + "from bcz_user_table " + "where account = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index, Account);
			ResultSet rSet = statement.executeQuery();
			System.out.println("getCutted�ɹ�...");
			while (rSet.next()) {
				cutted = rSet.getString("cutted");
			}
		} catch (Exception e) {
			System.out.println("getCuttedʧ��!" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getStudied���ݿ�Ͽ�ʧ�ܣ�" + e2.getMessage());
				}
			}
		}
		return cutted;
	}
	
	public String getUnstudied(String Account) {
		if (Account == null) {
			return null;
		}
		// �û�������
		List<user_Info> data = getUserByAccount(Account);
		if (data.isEmpty() || data.size() == 0) {
			return null;
		}
		String _studied = "";
		String all = "";
		StringBuilder builder = new StringBuilder();
		Connection con = DBCon.getConnection();
		try {
			String sql = "select studied " + "from bcz_user_table " + "where account = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index, Account);
			ResultSet rSet = statement.executeQuery();
			while (rSet.next()) {
				_studied = rSet.getString("studied");	// �û�����ѧ����
			}
			
			sql = "select English,Chinese from bcz_vocb_table";
			statement = con.prepareStatement(sql);
			rSet = statement.executeQuery();
			while (rSet.next()) {
				all = all+rSet.getString("English")+"#";	// ���еĵ���
				all = all+rSet.getString("Chinese")+"#";
			}
			
			// System.out.println(_studied);
			// ���һ�����ʶ�ûѧ ȫ����
			if ( _studied == null) {
				return all;
			}
			
			String[] infos = _studied.split("#");
			ArrayList<String> studiedList = new ArrayList<>();
			int i, len;
			for (i=0, len=infos.length; i<len; i++) {
				studiedList.add(infos[i++]);
			}
			
			
			String[] allInfos = all.split("#");
					
			for (i=0, len=allInfos.length; i < len; i++) {
				if (!studiedList.contains(allInfos[i++])) {
					builder.append(allInfos[i-1]+"#").append(allInfos[i]+"#"); 
				}
			}
			
			System.out.println("getUnstudied�ɹ�...");
		} catch (Exception e) {
			System.out.println("getUnstudiedʧ��!" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getStudied���ݿ�Ͽ�ʧ�ܣ�" + e2.getMessage());
				}
			}
		}
		return builder.toString();
	}
	
	public void setCollection(String Account, String English) {
		if (Account == null) {
			return;
		}
		// �û�������
		List<user_Info> data = getUserByAccount(Account);
		if (data.isEmpty() || data.size() == 0) {
			System.out.println("�û�������");
			return;
		}
		Connection con = DBCon.getConnection();
		String Chinese = null;
		String myCollection = null;
		String Collection = null;
		try {
			
			String sql = "select Chinese " + "from bcz_vocb_table " + "where English = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index, English);
			ResultSet rSet = statement.executeQuery();
			while (rSet.next()) {
				Chinese = rSet.getString("Chinese");
			}

			sql = "select collection " + "from bcz_user_table " + "where account = ?";
			statement = con.prepareStatement(sql);
			statement.setString(index, Account);
			rSet = statement.executeQuery();
			while (rSet.next()) {
				myCollection = rSet.getString("collection");
			}
			
			if(myCollection == null) {
				myCollection = "";
			}
			Collection = myCollection+English+"#"+Chinese+"#";
			
			sql = "update bcz_user_table " + "set collection = ? " + "where account = ?"; 
			statement = con.prepareStatement(sql);
			statement.setString(index++, Collection);
			statement.setString(index, Account);
			statement.executeUpdate();
			System.out.println("setCollection�ɹ�...");
		} catch (Exception e) {
			System.out.println("setCollectionʧ��!" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("setCollection���ݿ�Ͽ�ʧ�ܣ�" + e2.getMessage());
				}
			}
		}
	}
	
	public void cancelCollection(String Account, String English) {
		if (Account == null) {
			return;
		}
		// �û�������
		List<user_Info> data = getUserByAccount(Account);
		if (data.isEmpty() || data.size() == 0) {
			System.out.println("�û�������");
			return;
		}
		Connection con = DBCon.getConnection();
		String myCollection = null;
		StringBuilder Collection = new StringBuilder();
		try {
			String sql = "select collection " + "from bcz_user_table " + "where account = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index, Account);
			ResultSet rSet = statement.executeQuery();
			while (rSet.next()) {
				myCollection = rSet.getString("collection");
			}
			
			String[] infos = myCollection.split("#");
			for (int i = 0, len = infos.length; i<len; i++) {
				if (!infos[i].equals(English)) {
					Collection.append(infos[i]+"#").append(infos[++i]+"#");
				}else {
					i++;
				}
			}
			
			sql = "update bcz_user_table " + "set collection = ? " + "where account = ?"; 
			statement = con.prepareStatement(sql);
			statement.setString(index++, Collection.toString());
			statement.setString(index, Account);
			statement.executeUpdate();
			
			System.out.println("cancelCollection�ɹ�...");
		} catch (Exception e) {
			System.out.println("cancelCollectionʧ��!" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("cancelCollection���ݿ�Ͽ�ʧ�ܣ�" + e2.getMessage());
				}
			}
		}
	}
	
	public void setStudied(String Account, String English) {
		if (Account == null) {
			return;
		}
		// �û�������
		List<user_Info> data = getUserByAccount(Account);
		if (data.isEmpty() || data.size() == 0) {
			System.out.println("�û�������");
			return;
		}
		Connection con = DBCon.getConnection();
		String Chinese = null;
		String myStudied = null;
		String Studied = null;
		try {
			
			String sql = "select Chinese " + "from bcz_vocb_table " + "where English = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index, English);
			ResultSet rSet = statement.executeQuery();
			while (rSet.next()) {
				Chinese = rSet.getString("Chinese");
			}

			sql = "select studied " + "from bcz_user_table " + "where account = ?";
			statement = con.prepareStatement(sql);
			statement.setString(index, Account);
			rSet = statement.executeQuery();
			while (rSet.next()) {
				myStudied = rSet.getString("studied");
			}
			
			if(myStudied == null) {
				myStudied = "";
			}
			Studied = myStudied+English+"#"+Chinese+"#";
			
			sql = "update bcz_user_table " + "set studied = ? " + "where account = ?"; 
			
			statement = con.prepareStatement(sql);
			statement.setString(index++, Studied);
			statement.setString(index, Account);
			statement.executeUpdate();
			System.out.println("setStudied�ɹ�...");
		} catch (Exception e) {
			System.out.println("setStudiedʧ��!" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("setStudied���ݿ�Ͽ�ʧ�ܣ�" + e2.getMessage());
				}
			}
		}
	}
	
	public int setCut(String Account, String English) {
		if (Account == null) {
			return -1;
		}
		// �û�������
		List<user_Info> data = getUserByAccount(Account);
		if (data.isEmpty() || data.size() == 0) {
			System.out.println("�û�������");
			return -1;
		}
		Connection con = DBCon.getConnection();
		String Chinese = "";
		String myCut = "";
		String Cut = "";
		try {
			String sql = "select Chinese " + "from bcz_vocb_table " + "where English = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index, English);
			ResultSet rSet = statement.executeQuery();
			while (rSet.next()) {
				Chinese = rSet.getString("Chinese");
			}

			sql = "select cutted " + "from bcz_user_table " + "where account = ?";
			statement = con.prepareStatement(sql);
			statement.setString(index, Account);
			rSet = statement.executeQuery();
			while (rSet.next()) {
				myCut = rSet.getString("cutted");
			}
			
			// ���myCut��Ϊ�� ���ж�����
			if (myCut != null) {
				String[] infos = myCut.split("#");
				for (int i = 0, len = infos.length; i < len; i+=2) {
					if (English.equals(infos[i])) {
						return 1;
					}
				}
			}
			
			if (myCut == null) {
				myCut = "";
			}
			Cut = myCut+English+"#"+Chinese+"#";
			
			sql = "update bcz_user_table " + "set cutted = ? " + "where account = ?"; 
			statement = con.prepareStatement(sql);
			statement.setString(index++, Cut);
			statement.setString(index, Account);
			statement.executeUpdate();
			System.out.println("setCut�ɹ�...");
		} catch (Exception e) {
			System.out.println("setCutʧ��!" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("setCut���ݿ�Ͽ�ʧ�ܣ�" + e2.getMessage());
				}
			}
		}
		return 0;
	}
}
