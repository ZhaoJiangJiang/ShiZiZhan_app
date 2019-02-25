package android.classwork.com.android_lesson.wordradio.until;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by 赵江江 on 2018/12/18.
 * 封装了MediaPlayer，用于音频的播放
 */

public class DoMediaPlayer {
    private MediaPlayer mediaPlayer;

    public DoMediaPlayer(){
        mediaPlayer = new MediaPlayer();
    }

    /**
     * 开始播放
     * @param path
     */
    public void startMediaPlayer(String path){
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放资源
     */
    public void release(){
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
