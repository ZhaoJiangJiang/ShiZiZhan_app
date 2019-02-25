package android.classwork.com.android_lesson.util.vocb_list;

/**
 * Created by 赵江江 on 2018/12/2.
 */

public class vocbInfo {
    private String chinese;
    private String english;

    public vocbInfo(String chinese, String english) {
        this.chinese = chinese;
        this.english = english;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

}
