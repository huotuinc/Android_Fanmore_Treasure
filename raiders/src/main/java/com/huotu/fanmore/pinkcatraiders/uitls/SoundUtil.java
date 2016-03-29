package com.huotu.fanmore.pinkcatraiders.uitls;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * 声音播放工具类
 */
public class SoundUtil {
    private SoundPool soundPool;
    private int raw;
    private Context mContext;
    HashMap<Integer, Integer> soundMap = new HashMap<Integer, Integer>();

    public SoundUtil(Context context, int raw)
    {
        mContext = context;
        this.raw = raw;
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundMap.put(1, soundPool.load(context, raw, 1));

    }

    public void shakeSound()
    {
        soundPool.play(soundMap.get(1), 1, 1, 0, 0, 1);
    }

    public void release(){
        if( soundPool!=null){
            soundPool.release();
        }
    }
}
