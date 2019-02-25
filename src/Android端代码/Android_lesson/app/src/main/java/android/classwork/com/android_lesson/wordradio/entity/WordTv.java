package android.classwork.com.android_lesson.wordradio.entity;

/**
 * Created by 赵江江 on 2018/12/18.
 * 单词电台实体类
 */

public class WordTv {
    /** 英文 */
    private String english;
    /** 中文 */
    private String chinese;
    /** 音标 */
    private String yinbiao;

    public WordTv(String english, String chinese, String yinbiao) {
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
}
