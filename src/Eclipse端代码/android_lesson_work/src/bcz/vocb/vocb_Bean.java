package bcz.vocb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * vocb_Bean()��
 * vocb���model��<br>
 * @author �Խ���
 *
 */

public class vocb_Bean {
	
	/**
	 * �õ��������ݡ�<br>
	 * �Ǿ�̬������<br>
	 * ͨ��ʵ��������ø÷�����<br>
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
			System.out.println("��ѯ���...");
			
			while (result.next()) {
				String English = result.getString("English");	// Ӣ��
				String Chinese = result.getString("Chinese");	// ����
				vocb_Info info = new vocb_Info(English, Chinese);
				data.add(info);
			}
			
		} catch (Exception e) {
			System.out.println("��ѯʧ�ܣ�"+e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getAll�Ͽ����ݿ�ʧ�ܣ�"+e2.getMessage());
				}
			}
		}
		return data;
	}
	
	/**
	 * ���Ӣ�ĵ���ΪqueryData��vocb��<br>
	 * �Ǿ�̬������<br>
	 * ͨ��ʵ��������ø÷�����<br>
	 * @param queryData Ӣ�ĵ���
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
			System.out.println("��ѯ���...");
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
			System.out.println("��ѯʧ�ܣ�"+e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("getWordByEnglish���ݿ�Ͽ�ʧ��"+e2.getMessage());
				}
			}
		}
		return data;
	}
	
	/**
	 * �����µ��ʡ�<br>
	 * �Ǿ�̬������<br>
	 * ͨ��ʵ��������ø÷�����<br>
	 * @param addInfo ���ӵ�vocb
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
			System.out.println("����ɹ�..."+addInfo.toString());
		} catch (Exception e) {
			System.out.println("����ʧ��!"+e.getMessage());
			return 2;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("addWord���ݿ�Ͽ�ʧ��!"+e2.getMessage());
				}
			}
		}
		return 0;
	}
	
	/**
	 * ɾ�����ʡ�<br>
	 * �Ǿ�̬������<br>
	 * ͨ��ʵ��������ø÷�����<br>
	 * @param delInfo ɾ���ĵ���
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
			System.out.println("ɾ���ɹ�...");
		} catch (Exception e) {
			System.out.println("ɾ��ʧ�ܣ�"+e.getMessage());
			return 3;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					System.out.println("delWordByEnglish���ݿ�Ͽ�ʧ��"+e2.getMessage());
				}
			}
		}
		return 0;
	}
}











