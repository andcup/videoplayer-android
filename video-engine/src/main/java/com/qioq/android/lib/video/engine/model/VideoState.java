package com.qioq.android.lib.video.engine.model;

import android.util.Log;

/**
 * Created by Amos on 2015/7/6.
 */
public enum  VideoState {
    /**
     * @brief 视频准备状态。底层引擎准备好，并在请求视频资源信息
     * */
    Preparing(VideoState.STATE_PREPARING_START, VideoState.STATE_PREPARING_MAX, VideoState.STATE_PREPARING_START),
    /**
     * @brief 视频正在播放。包括开始播放、缓冲、 二级缓冲、以及播放中.
     * */
    Playing  (VideoState.STATE_PLAYING_START, VideoState.STATE_PLAYING_MAX, VideoState.STATE_PLAYING_START),
    /**
     * @brief 视频正在暂停。
     * */
    Pause    (VideoState.STATE_PAUSE, VideoState.STATE_PAUSE_MAX, VideoState.STATE_PAUSE),
    /**
     * @brief 视频播放完成。包括播放停止、播放错误、以及正常播放完成事件
     * */
    Finish   (VideoState.STATE_FINISH_FINISH, VideoState.STATE_FINISH_MAX, VideoState.STATE_FINISH_FINISH);

    private final int mMin;
    private final int mMax;
    private int mState;

    VideoState(int min, int max, int initValue){
        mMin = min;
        mMax = max;
        mState = initValue;
    }

    public void setVideoState(int state){
        if(state < mMin || state > mMax){
            Log.e(VideoState.class.getSimpleName(), " invalid state = " + state);
            return;
        }
        mState = state;
    }

    public int getVideoState(){
        return mState;
    }

    /**
     * VideoStatePreparing
     * */
    public static final int STATE_PREPARING_START           = 0x00;
    public static final int STATE_PREPARED                  = 0x01;

    public static final int STATE_PREPARING_MAX             = 0x09;

    /**
     * VideoStatePlaying
     * */
    public static final int STATE_PLAYING_START             = 0x10;
    public static final int STATE_PLAYING_LOADING           = 0x11;
    public static final int STATE_PLAYING_LOADING_RATE      = 0x12;
    public static final int STATE_PLAYING_BUFFERING         = 0x13;
    public static final int STATE_PLAYING_POSITION_CHANGED  = 0x14;

    public static final int STATE_PLAYING_MAX             = 0x19;

    /**
     * VideoStatePause
     * */
    public static final int STATE_PAUSE     = 0x21;
    public static final int STATE_PAUSE_MAX = 0x29;

    /**
     * VideoStateFinish
     * */
    public static final int STATE_FINISH_FINISH = 0x30;
    public static final int STATE_FINISH_STOP   = 0x31;
    public static final int STATE_FINISH_ERROR  = 0x32;
    public static final int STATE_FINISH_MAX    = 0x39;
}
