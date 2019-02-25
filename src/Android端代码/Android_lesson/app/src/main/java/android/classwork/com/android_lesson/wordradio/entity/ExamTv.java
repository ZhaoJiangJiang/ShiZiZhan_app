package android.classwork.com.android_lesson.wordradio.entity;

/**
 * Created by 赵江江 on 2018/12/18.
 * 考试音频实体类
 */

public class ExamTv {
    /** 试卷标题 */
    private String title;
    /** 服务器上的文件名 */
    private String fileName;

    public ExamTv(String title, String fileName) {
        this.title = title;
        this.fileName = fileName;
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
}
