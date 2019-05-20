package bcz.vocb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * vocb_Bean()。
 * vocb类的model。<br>
 * @author 赵江江
 *
 */

public class vocb_Bean {
	
	/**
	 * 得到所有数据。<br>
	 * 非静态方法。<br>
	 * 通过实例对象调用该方法。<br>
	 * @return
	 */
	public List<vocb_Info> getAll(){
		List<vocb_Info> data = new ArrayList<>();
		
		Connection con = DBCon.getConnection();
		try {
			String sql = "select English, Chinese"
					+ " from bcz_vocb_table";
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			System.out.println("查询完成...");
			
			while (result.next()) {
				String English = result.getString("English");	// 英文
				String Chinese = result.getString("Chinese");	// 翻译
				vocb_Info info = new vocb_Info(English, Chinese);
				data.add(info);
			}
			
		} catch (Exception e) {
			System.out.println("查询失败！"+e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getAll断开数据库失败！"+e2.getMessage());
				}
			}
		}
		return data;
	}
	
	/**
	 * 获得英文单词为queryData的vocb。<br>
	 * 非静态方法。<br>
	 * 通过实例对象调用该方法。<br>
	 * @param queryData 英文单词
	 * @return
	 */
	public List<vocb_Info> getWordByEnglish(String queryData) {
		List<vocb_Info> data = new ArrayList<>();
		Connection con = DBCon.getConnection();
		try {
			String sql = "select English, Chinese, vocbroot, sentence, sentenceChinese, need "
					+ "from bcz_vocb_table "
					+ "where English = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, queryData);
			ResultSet rSet = statement.executeQuery();
			System.out.println("查询完成...");
			while (rSet.next()) {
				String English = rSet.getString("English");
				String Chinese = rSet.getString("Chinese");
				String vocbRoot = rSet.getString("vocbroot");
				String sentence = rSet.getString("sentence");
				String sentenceChinese = rSet.getString("sentenceChinese");
				String need = rSet.getString("need");
				vocb_Info info = new vocb_Info(English, Chinese, vocbRoot, sentence, sentenceChinese, need);
				data.add(info);
			}
		} catch (Exception e) {
			System.out.println("查询失败！"+e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getWordByEnglish数据库断开失败"+e2.getMessage());
				}
			}
		}
		return data;
	}
	
	/**
	 * 增加新单词。<br>
	 * 非静态方法。<br>
	 * 通过实例对象调用该方法。<br>
	 * @param addInfo 增加的vocb
	 * @return
	 */
	public int addWord(vocb_Info addInfo) {
		String EngStr = addInfo.getEnglish();
		List<vocb_Info> data = getWordByEnglish(EngStr);
		if (data.size() != 0 || !data.isEmpty()) {
			return 1;
		}
		Connection con = DBCon.getConnection();
		try {
			String sql = "insert bcz_vocb_table "
					+ "(English, Chinese) "
					+ "values(?, ?)";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, addInfo.getEnglish());
			statement.setString(index++, addInfo.getChinese());
			statement.executeUpdate();
			System.out.println("插入成功..."+addInfo.toString());
		} catch (Exception e) {
			System.out.println("插入失败!"+e.getMessage());
			return 2;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("addWord数据库断开失败!"+e2.getMessage());
				}
			}
		}
		return 0;
	}
	
	/**
	 * 删除单词。<br>
	 * 非静态方法。<br>
	 * 通过实例对象调用该方法。<br>
	 * @param delInfo 删除的单词
	 * @return
	 */
	public int delWordByEnglish(String delInfo) {
		if (delInfo == null || delInfo.length() == 0) {
			return 1;
		}
		List<vocb_Info> data = getWordByEnglish(delInfo);
		if (data.size() == 0 || data.isEmpty()) {
			return 2;
		}
		Connection con = DBCon.getConnection();
		try {
			String sql = "delete from bcz_vocb_table "
					+ "where English = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, delInfo);
			statement.executeUpdate();
			System.out.println("删除成功...");
		} catch (Exception e) {
			System.out.println("删除失败！"+e.getMessage());
			return 3;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("delWordByEnglish数据库断开失败"+e2.getMessage());
				}
			}
		}
		return 0;
	}
}











