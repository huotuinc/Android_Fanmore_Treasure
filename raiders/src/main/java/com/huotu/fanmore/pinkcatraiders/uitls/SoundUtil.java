package com.huotu.fanmore.pinkcatraiders.uitls;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * 声音播放工具类
 */
public class SoundUtil {
    private MediaPlayer mMediaPlayer;

    private Context mContext = null;

    public SoundUtil(Context context)
    {
        mContext = context;
    }

    public void shakeSound(int sid)
    {
        if (mMediaPlayer == null)
        {
            mMediaPlayer = MediaPlayer.create(mContext, sid);
        } else
        {
            mMediaPlayer.reset();
            mMediaPlayer = MediaPlayer.create(mContext, sid);
        }
        try
        {
            if (mMediaPlayer != null)
            {
                mMediaPlayer.stop();
            }
            mMediaPlayer.prepare();
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mMediaPlayer.start();
    }

    public void Release(){
        if( mMediaPlayer!=null){
            mMediaPlayer.release();
        }
    }
}
