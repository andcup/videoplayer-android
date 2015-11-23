package com.qioq.android.lib.video.core.system;

import android.content.Context;
import android.media.AudioManager;

/**
 * Created by Amos on 2015/7/9.
 */
public class Volume {

    private AudioManager mAudioManager;
    private int          mAudioMax;

    public Volume(Context context){
        mAudioManager = (AudioManager)context.getSystemService(context.AUDIO_SERVICE);
        mAudioMax     = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    public void setVolume( float volume ){
        //获取音量最大值.(耳机和扬声器的音量范围不同)
        mAudioMax = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        if (volume < 0) {
            volume = 0;
        }
        if (volume > mAudioMax) {
            volume = mAudioMax;
        }
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) volume, 0);
    }

    public int getVolume(){
        return mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    public int getMaxVolume(){
        return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }
}
