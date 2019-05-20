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
 * user_Bean。<br>
 * user类的Model。<br>
 * @author 赵江江
 *
 */

public class user_Bean {

	/**
	 * 获得所有user。<br>
	 * 非静态方法。<br>
	 * 通过实例对象调用该方法。<br>
	 * @return
	 */
	public List<user_Info> getAll() {
		List<user_Info> data = new ArrayList<>();
		Connection con = DBCon.getConnection();
		try {
			String sql = "select account, username " + "from bcz_user_table";
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet rSet = statement.executeQuery();
			System.out.println("查询完成...");
			while (rSet.next()) {
				String account = rSet.getString("account");
				String username = rSet.getString("username");
				user_Info info = new user_Info(account, "******", username);
				data.add(info);
			}
		} catch (Exception e) {
			System.out.println("查询失败！" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getAll断开数据库失败！" + e2.getMessage());
				}
			}
		}
		return data;
	}

	/**
	 * 通过userName获得User。<br>
	 * 非静态方法。<br>
	 * 通过实例对象调用该方法。<br>
	 * @param queryUsername 用户名
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
			System.out.println("查询完成...");
			while (rSet.next()) {
				String account = rSet.getString("account");
				String username = rSet.getString("username");
				String password = rSet.getString("password");
				user_Info info = new user_Info(account, password, username);
				data.add(info);
			}
		} catch (Exception e) {
			System.out.println("查询失败！" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("数据库断开失败！" + e2.getMessage());
				}
			}
		}
		return data;
	}

	/**
	 * 通过userAccount获得user。<br>
	 * 非静态方法。<br>
	 * 通过实例对象调用该方法。<br>
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
			System.out.println("查询完成...");
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
			System.out.println("查询失败！" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("数据库断开失败！" + e2.getMessage());
				}
			}
		}
		return data;
	}

	public void autoLogin(String autoAccount) {
		if (autoAccount == null || autoAccount.length() == 0) {
			System.out.println("传入账号不能为空！");
			return;
		}
		List<user_Info> data = getUserByAccount(autoAccount);
		if (data.size() == 0) {
			System.out.println("没有该账号！");
			return;
		}
		Connection con = DBCon.getConnection();
		try {
			String sql = "update bcz_user_table " + "set isautologin = 1 " + "where account = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index, autoAccount);
			statement.executeUpdate();
			System.out.println("修改数据库isAutoLogin -> 1 成功...");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("修改数据库isAutoLogin -> 1 失败！");
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("isAutoLogin断开数据库失败！" + e2.getMessage());
				}
			}
		}
	}

	public void cancel_autoLogin(String cancelAutoAccount) {
		if (cancelAutoAccount == null || cancelAutoAccount.length() == 0) {
			System.out.println("传入账号不能为空！");
			return;
		}
		List<user_Info> data = getUserByAccount(cancelAutoAccount);
		if (data.size() == 0) {
			System.out.println("没有该账号！");
			return;
		}
		Connection con = DBCon.getConnection();
		try {
			String sql = "update bcz_user_table " + "set isautologin = 0 " + "where account = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index, cancelAutoAccount);
			statement.executeUpdate();
			System.out.println("修改数据库isautoLogin -> 0 成功...");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("修改数据库isautoLogin -> 0 失败！");
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("cancel_autoLogin断开数据库失败！" + e2.getMessage());
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
			System.out.println("查询完成...");
			while (rSet.next()) {
				isAutoLogin = rSet.getInt("isautologin");
			}
		} catch (Exception e) {
			System.out.println("查询失败！" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getAutoLoginByAccount数据库断开失败！" + e2.getMessage());
				}
			}
		}
		return isAutoLogin;
	}

	/**
	 * 增加用户。<br>
	 * 非静态方法。<br>
	 * 通过实例对象调用该方法。<br>
	 * @param addAccount 账号
	 * @param password	密码
	 * @param Username	用户名
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
			System.out.println("addUser成功...");
		} catch (Exception e) {
			System.out.println("addUser失败！" + e.getMessage());
		}
	}

	/**
	 * 判断该Account是否存在。<br>
	 * 非静态方法。<br>
	 * 通过实例对象调用该方法。<br>
	 * @param Account 账号
	 * @return
	 */
	public int isAccountExist(String Account) {
		List<user_Info> data = getUserByAccount(Account);
		if (data.isEmpty() || data.size() == 0) {
			return 0; // 0表示不存在
		}
		return 1; // 1表示存在
	}

	/**
	 * 根据Account获得用户坚持天数。<br>
	 * 非静态方法。<br>
	 * 通过实例对象调用该方法。<br>
	 * @param Account 账户
	 * @return
	 */
	public int getInsistDay(String Account) {
		int day = 0;
		if (Account == null) {
			return 0;
		}
		// 用户不存在
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
			System.out.println("getInsistDay成功...");
			while (rSet.next()) {
				day = rSet.getInt("insist_day");
			}
		} catch (Exception e) {
			System.out.println("getInsistDay失败!" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getInsistDay数据库断开失败！" + e2.getMessage());
				}
			}
		}
		return day;
	}

	/**
	 * 增加用户名为Account的坚持天数。<br>
	 * 非静态方法。<br>
	 * 通过实例对象调用该方法。<br>
	 * @param Account 账户
	 * @return
	 */
	public int addInsistDay(String Account) {
		// 用户不存在
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
			System.out.println("addInsistDay 成功...");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("addInsistDay 失败！");
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("addInsistDay断开数据库失败！" + e2.getMessage());
				}
			}
		}
		return 1;
	}

	/**
	 * 获得账户为Account计划天数。<br>
	 * 非静态方法。<br>
	 * 通过实例对象调用该方法。<br>
	 * @param Account 账户
	 * @return
	 */
	public int getPlanNum(String Account) {
		int num = 0;
		if (Account == null) {
			return 0;
		}
		// 用户不存在
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
			System.out.println("getPlanNum成功...");
			while (rSet.next()) {
				num = rSet.getInt("plan_num");
			}
		} catch (Exception e) {
			System.out.println("getPlanNum失败!" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getPlanNum数据库断开失败！" + e2.getMessage());
				}
			}
		}
		return num;
	}

	/**
	 * 设置账号为Account的用户的坚持天数为num。<br>
	 * @param num 坚持天数
	 * @param Account 账号
	 * @return
	 */
	public int setPlanNum(String num, String Account) {
		// 用户不存在
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
			System.out.println("setPlanNum 成功...");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("setPlanNum 失败！");
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("setPlanNum断开数据库失败！" + e2.getMessage());
				}
			}
		}
		return 1;
	}

	public String getSelectKind(String Account) {
		if (Account == null) {
			return null;
		}
		// 用户不存在
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
			System.out.println("getSelectKind成功...");
			while (rSet.next()) {
				selectKind = rSet.getString("select_kind");
			}
		} catch (Exception e) {
			System.out.println("getSelectKind失败!" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getSelectKind数据库断开失败！" + e2.getMessage());
				}
			}
		}
		return selectKind;
	}

	public int setSelectKind(String selectKind, String Account) {
		// 用户不存在
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
			System.out.println("setSelectKind 成功...");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("setSelectKind 失败！");
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("setSelectKind断开数据库失败！" + e2.getMessage());
				}
			}
		}
		return 1;
	}
	
	/**
	 * 获得账号Account的收藏单词。<br>
	 * 非静态方法。<br>
	 * 通过实例对象调用该方法。<br>
	 * @param Account
	 * @return
	 */
	public String getCollection(String Account) {
		if (Account == null) {
			return null;
		}
		// 用户不存在
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
			System.out.println("getCollection成功...");
			while (rSet.next()) {
				collection = rSet.getString("collection");
			}
		} catch (Exception e) {
			System.out.println("getCollection失败!" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getCollection数据库断开失败！" + e2.getMessage());
				}
			}
		}
		return collection;
	}
	
	/**
	 * 获得账号Account的已学习单词。<br>
	 * 非静态方法。<br>
	 * 通过实例对象调用该方法。<br>
	 * @param Account
	 * @return
	 */
	public String getStudied(String Account) {
		if (Account == null) {
			return null;
		}
		// 用户不存在
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
			System.out.println("getStudied成功...");
			while (rSet.next()) {
				studied = rSet.getString("studied");
			}
		} catch (Exception e) {
			System.out.println("getStudied失败!" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getStudied数据库断开失败！" + e2.getMessage());
				}
			}
		}
		return studied;
	}
	
	public String getCutted(String Account) {
		if (Account == null) {
			return null;
		}
		// 用户不存在
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
			System.out.println("getCutted成功...");
			while (rSet.next()) {
				cutted = rSet.getString("cutted");
			}
		} catch (Exception e) {
			System.out.println("getCutted失败!" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getStudied数据库断开失败！" + e2.getMessage());
				}
			}
		}
		return cutted;
	}
	
	public String getUnstudied(String Account) {
		if (Account == null) {
			return null;
		}
		// 用户不存在
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
				_studied = rSet.getString("studied");	// 用户的已学单词
			}
			
			sql = "select English,Chinese from bcz_vocb_table";
			statement = con.prepareStatement(sql);
			rSet = statement.executeQuery();
			while (rSet.next()) {
				all = all+rSet.getString("English")+"#";	// 所有的单词
				all = all+rSet.getString("Chinese")+"#";
			}
			
			// System.out.println(_studied);
			// 如果一个单词都没学 全返回
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
			
			System.out.println("getUnstudied成功...");
		} catch (Exception e) {
			System.out.println("getUnstudied失败!" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getStudied数据库断开失败！" + e2.getMessage());
				}
			}
		}
		return builder.toString();
	}
	
	public void setCollection(String Account, String English) {
		if (Account == null) {
			return;
		}
		// 用户不存在
		List<user_Info> data = getUserByAccount(Account);
		if (data.isEmpty() || data.size() == 0) {
			System.out.println("用户不存在");
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
			System.out.println("setCollection成功...");
		} catch (Exception e) {
			System.out.println("setCollection失败!" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("setCollection数据库断开失败！" + e2.getMessage());
				}
			}
		}
	}
	
	public void cancelCollection(String Account, String English) {
		if (Account == null) {
			return;
		}
		// 用户不存在
		List<user_Info> data = getUserByAccount(Account);
		if (data.isEmpty() || data.size() == 0) {
			System.out.println("用户不存在");
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
			
			System.out.println("cancelCollection成功...");
		} catch (Exception e) {
			System.out.println("cancelCollection失败!" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("cancelCollection数据库断开失败！" + e2.getMessage());
				}
			}
		}
	}
	
	public void setStudied(String Account, String English) {
		if (Account == null) {
			return;
		}
		// 用户不存在
		List<user_Info> data = getUserByAccount(Account);
		if (data.isEmpty() || data.size() == 0) {
			System.out.println("用户不存在");
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
			System.out.println("setStudied成功...");
		} catch (Exception e) {
			System.out.println("setStudied失败!" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("setStudied数据库断开失败！" + e2.getMessage());
				}
			}
		}
	}
	
	public int setCut(String Account, String English) {
		if (Account == null) {
			return -1;
		}
		// 用户不存在
		List<user_Info> data = getUserByAccount(Account);
		if (data.isEmpty() || data.size() == 0) {
			System.out.println("用户不存在");
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
			
			// 如果myCut不为空 就判断以下
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
			System.out.println("setCut成功...");
		} catch (Exception e) {
			System.out.println("setCut失败!" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("setCut数据库断开失败！" + e2.getMessage());
				}
			}
		}
		return 0;
	}
}
