package radio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bcz.vocb.DBCon;

public class GetRadio {
	
	//������������
	public List<ExamRadio> getExamRadio(String examType){
		
		List<ExamRadio> list = new ArrayList<>();
		
		Connection conn = DBCon.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			//����Ҫִ�е�sql���
			String sql = "select * from bcz_exam_radio where exam_type = ?";
			//Ԥ���븳ֵ
			st = conn.prepareStatement(sql);
			st.setString(1, examType);
			//ִ��sql���
			rs = st.executeQuery();
			while(rs.next()) {
				String title = rs.getString("title");
				String fileName = rs.getString("file_name");
				ExamRadio examRadio = new ExamRadio(title, fileName, examType);
				list.add(examRadio);
			}
			return list;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, st, conn);
		}
		
		return null;
	}

	//��ȡ����
	public List<WordRadio> getWordRadio(){
		
		List<WordRadio> list = new ArrayList<>();
		
		Connection conn = DBCon.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			//����Ҫִ�е�sql���
			String sql = "select * from bcz_word_radio";
			//Ԥ���븳ֵ
			st = conn.prepareStatement(sql);
			//ִ��sql���
			rs = st.executeQuery();
			while(rs.next()) {
				String english = rs.getString("english");
				String chinese = rs.getString("chinese");
				String yinbiao = rs.getString("yinbiao");
				WordRadio wordRadio = new WordRadio(english, chinese, yinbiao);
				list.add(wordRadio);
			}
			return list;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, st, conn);
		}
		
		return null;
	}
}
