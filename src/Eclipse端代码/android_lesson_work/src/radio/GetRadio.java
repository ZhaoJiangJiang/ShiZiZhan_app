package radio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bcz.vocb.DBCon;

public class GetRadio {
	
	//按考试类别查找
	public List<ExamRadio> getExamRadio(String examType){
		
		List<ExamRadio> list = new ArrayList<>();
		
		Connection conn = DBCon.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			//创建要执行的sql语句
			String sql = "select * from bcz_exam_radio where exam_type = ?";
			//预编译赋值
			st = conn.prepareStatement(sql);
			st.setString(1, examType);
			//执行sql语句
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

	//获取所有
	public List<WordRadio> getWordRadio(){
		
		List<WordRadio> list = new ArrayList<>();
		
		Connection conn = DBCon.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			//创建要执行的sql语句
			String sql = "select * from bcz_word_radio";
			//预编译赋值
			st = conn.prepareStatement(sql);
			//执行sql语句
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
