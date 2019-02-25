package android.classwork.com.android_lesson.back;

/**
 * Created by 赵江江 on 2018/12/19.
 */

/*
*图片位置排布
*  p1 p2
*  p3 p4
*  rightpc：正确图片编号
*  wordtext: 单词内容
* */
public class Word {
    public String wordtext;
    public String meaning;
    public String example;
    public int[] pic = new int[4];
    public int rightpc;
    public boolean collect = false;

    public Word (String wordtext, int pic0, int pic1, int pic2, int pic3, int rightpc ){
        this.wordtext = wordtext;
        this.pic[0] = pic0;
        this.pic[1] = pic1;
        this.pic[2] = pic2;
        this.pic[3] = pic3;
        this.rightpc = rightpc;

    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getExample() {
        return example;
    }


    public void setRightpc(int rightpc) {
        this.rightpc = rightpc;
    }

    public void setWordtext(String wordtext) {
        this.wordtext = wordtext;
    }



    public int getRightpc() {
        return rightpc;
    }

    public String getWordtext() {
        return wordtext;
    }

    public int[] getPic() {
        return pic;
    }

    public void setPic(int[] pic) {
        for( int i = 0; i < 4; i++ )
            this.pic[i] = pic[i];
    }
}

