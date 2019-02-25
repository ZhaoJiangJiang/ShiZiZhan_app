package radio;

public class ExamRadio {
	private String title;
	private String fileName;
	private String examType;
	
	public ExamRadio(String title, String fileName, String examType) {
		super();
		this.title = title;
		this.fileName = fileName;
		this.examType = examType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String exam_type) {
		this.examType = exam_type;
	}

	@Override
	public String toString() {
		return "ExamRadio [title=" + title + ", fileName=" + fileName + ", examType=" + examType + "]";
	}
}
