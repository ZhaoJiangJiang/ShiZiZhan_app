package android.classwork.com.android_lesson.util.vocb_dialog;

import android.graphics.Color;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 赵江江 on 2018/12/15.
 */

public class wordClick {
    public static void getEachWord(TextView textView){
        //1、将TextView内容转换为Spannable对象
        Spannable spans = (Spannable)textView.getText();
        //2、使用getIndices方法将文本内容根据空格划分成各个单词
        //获取所有空格位置
        Integer[] indices = getIndices(textView.getText().toString().trim(), ' ');
        int start = 0;
        int end = 0;
        // to cater last/only word loop will run equal to the length of indices.length
        for (int i = 0; i <= indices.length; i++) {     //单词数为空格数+1
            //3、为每个单词添加ClickableSpan
            ClickableSpan clickSpan = getClickableSpan();
            // 看是否为最后一个单词
            end = (i < indices.length ? indices[i] : spans.length());
            spans.setSpan(clickSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = end + 1;
        }
        //改变选中文本的高亮颜色
        //textView.setHighlightColor(Color.BLUE);
    }

    //定义ClickableSpan的点击事件
    private static ClickableSpan getClickableSpan(){
        return new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                TextView tv = (TextView) widget;    //转为TextView获取内容
                String s = tv.getText().subSequence(tv.getSelectionStart(), //获取按住的字符串
                        tv.getSelectionEnd()).toString().replaceAll(",|\\.", "");   //将,和.换成空
                //Dialog引入布局，添加到项目中时必须自定义WordDialog类，传入String word，
                // 初始化好布局和内容，在这边只做显示
                /*Dialog dialog = new Dialog(widget.getContext());
                Toast.makeText(widget.getContext(), s, Toast.LENGTH_SHORT).show();
                dialog.setTitle(s);
                dialog.setContentView(R.layout.click_word);
                dialog.show();*/
                WordDialog wordDialog = new WordDialog(widget.getContext(), s);
                wordDialog.show();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLACK);
                ds.setUnderlineText(false);
            }
        };
    }

    //获取所有指定字符在字符串中的位置
    private static Integer[] getIndices(String s, char c) {
        int pos = s.indexOf(c, 0);  //从指定位置开始，返回第一次出现的指定子字符在此字符串中的位置
        List<Integer> indices = new ArrayList<Integer>();
        while (pos != -1) {
            indices.add(pos);
            pos = s.indexOf(c, pos + 1);
        }
        return (Integer[]) indices.toArray(new Integer[0]);
    }
}
