package radio;

public class WordRadio {
	private String english;
	private String chinese;
	private String yinbiao;
	
	public WordRadio(String english, String chinese, String yinbiao) {
		super();
		this.english = english;
		this.chinese = chinese;
		this.yinbiao = yinbiao;
	}

	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public String getChinese() {
		return chinese;
	}

	public void setChinese(String chinese) {
		this.chinese = chinese;
	}

	public String getYinbiao() {
		return yinbiao;
	}

	public void setYinbiao(String yinbiao) {
		this.yinbiao = yinbiao;
	}

	@Override
	public String toString() {
		return "WordRadio [english=" + english + ", chinese=" + chinese + ", yinbiao=" + yinbiao + "]";
	}
}
