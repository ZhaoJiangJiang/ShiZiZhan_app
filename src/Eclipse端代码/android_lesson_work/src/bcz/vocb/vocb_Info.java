package bcz.vocb;

public class vocb_Info {
	private String English;
	private String Chinese;
	private String vocbRoot;
	private String sentence;
	private String sentenceChinese;
	private String need;
	
	public vocb_Info() {
		
	}
	
	public vocb_Info(String english, String chinese) {
		this.English = english;
		this.Chinese = chinese;
	}
	
	public vocb_Info(String english, String chinese, String vocbRoot, String sentence, String sentenceChinese) {
		this.English = english;
		this.Chinese = chinese;
		this.vocbRoot = vocbRoot;
		this.sentence = sentence;
		this.sentenceChinese = sentenceChinese;
	}

	public vocb_Info(String english, String chinese, String vocbRoot, String sentence, String sentenceChinese,
			String need) {
		English = english;
		Chinese = chinese;
		this.vocbRoot = vocbRoot;
		this.sentence = sentence;
		this.sentenceChinese = sentenceChinese;
		this.need = need;
	}

	public String getEnglish() {
		return English;
	}
	public void setEnglish(String english) {
		English = english;
	}
	public String getChinese() {
		return Chinese;
	}
	public void setChinese(String chinese) {
		Chinese = chinese;
	}

	public String getVocbRoot() {
		return vocbRoot;
	}

	public void setVocbRoot(String vocbRoot) {
		this.vocbRoot = vocbRoot;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public String getSentenceChinese() {
		return sentenceChinese;
	}

	public void setSentenceChinese(String sentenceChinese) {
		this.sentenceChinese = sentenceChinese;
	}

	public String getNeed() {
		return need;
	}

	public void setNeed(String need) {
		this.need = need;
	}

	@Override
	public String toString() {
		return "bcz_Info [English=" + English + ", Chinese=" + Chinese + "]";
	}

	
}
