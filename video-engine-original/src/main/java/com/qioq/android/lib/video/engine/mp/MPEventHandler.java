package com.qioq.android.lib.video.engine.mp;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.qioq.android.lib.video.engine.model.VideoState;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Amos on 2015/8/13.
 */
public class MPEventHandler extends Handler implements
        MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnVideoSizeChangedListener,
        MediaPlayer.OnSeekCompleteListener {

    public static final int MP_PREPARE    = 0X01;
    public static final int MP_PREPARED   = 0X011;
    public static final int MP_PLAY_START = 0X02;
    public static final int MP_PLAYING    = 0X021;
    public static final int MP_LOADING    = 0X022;
    public static final int MP_PAUSE      = 0X04;
    public static final int MP_FINISH     = 0X05;
    public static final int MP_ERROR      = 0X06;

    private MPEngine mMPEngine;
    private long     mLastPosition = 0;
    private VideoLoadingDispatcher mVideoLoadingDispatcher = new VideoLoadingDispatcher();

    public MPEventHandler(MPEngine mpEngine){
        mMPEngine = mpEngine;

        mMPEngine.getMediaPlayer().setOnBufferingUpdateListener(this);
        mMPEngine.getMediaPlayer().setOnCompletionListener(this);
        mMPEngine.getMediaPlayer().setOnVideoSizeChangedListener(this);
        mMPEngine.getMediaPlayer().setOnSeekCompleteListener(this);
        mMPEngine.getMediaPlayer().setOnErrorListener(this);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case MP_PREPARE:
                VideoState.Preparing.setVideoState(VideoState.STATE_PREPARING_START);
                mMPEngine.onStateChanged(VideoState.Preparing, VideoState.STATE_PREPARING_START, null);
                mVideoLoadingDispatcher.start();
                break;
            case MP_PREPARED:
                VideoState.Preparing.setVideoState(VideoState.STATE_PREPARED);
                mMPEngine.onStateChanged(VideoState.Preparing, VideoState.STATE_PREPARED, null);
                break;
            case MP_PLAY_START:
                mPlayingHandler.sendEmptyMessageDelayed(0, 1000);
                VideoState.Playing.setVideoState(VideoState.STATE_PLAYING_START);
                mMPEngine.onStateChanged(VideoState.Playing, VideoState.STATE_PLAYING_START, null);
                mVideoLoadingDispatcher.start();
                break;
            case MP_PLAYING:
                VideoState.Playing.setVideoState(VideoState.STATE_PLAYING_POSITION_CHANGED);
                mMPEngine.onStateChanged(VideoState.Playing, VideoState.STATE_PLAYING_POSITION_CHANGED, null);
                break;
            case MP_PAUSE:
                VideoState.Pause.setVideoState(VideoState.STATE_PAUSE);
                mMPEngine.onStateChanged(VideoState.Pause, VideoState.STATE_PAUSE, null);
                break;
            case MP_FINISH:
                VideoState.Finish.setVideoState(VideoState.STATE_FINISH_FINISH);
                mMPEngine.onStateChanged(VideoState.Finish, VideoState.STATE_FINISH_FINISH, null);
                mVideoLoadingDispatcher.stop();
                break;
            case MP_ERROR:
                VideoState.Finish.setVideoState(VideoState.STATE_FINISH_ERROR);
                mMPEngine.onStateChanged(VideoState.Finish, VideoState.STATE_FINISH_ERROR, null);
                mVideoLoadingDispatcher.stop();
                break;
        }
    }

    public void publish(int event){
        sendEmptyMessage(event);
    }

    private Handler mPlayingHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try{
                long position = mMPEngine.getMediaPlayer().getCurrentPosition();
                mPlayingHandler.sendEmptyMessageDelayed(0, 600);
                mLastPosition = position;
                if(mMPEngine.getMediaPlayer().isPlaying()){
                    publish(MP_PLAYING);
                }
            }catch (IllegalStateException e){

            }
        }
    };

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.v(MPEngine.class.getSimpleName(), " onCompletion = ");
        publish(MP_FINISH);
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
        Log.v(MPEngine.class.getSimpleName(), " onVideoSizeChanged i = " + i + " i1 = " + i1);
        mMPEngine.getFitSizer().setSize(i, i1, i, i1);
    }

    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {
        publish(MP_PLAYING);
    }

    class VideoLoadingDispatcher {
        Timer mTimer;
        long  preLoadingTime;
        int   mEqualCount   = 0;
        final int LoopCount = 12;

        public void start(){
            stop();

            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try{
                        MediaPlayer mediaPlayer = mMPEngine.getMediaPlayer();
                        if(null != mediaPlayer && mediaPlayer.isPlaying()){
                            long currentTime = mediaPlayer.getCurrentPosition();
                            if( preLoadingTime != currentTime ) {
                                preLoadingTime = currentTime;
                                mEqualCount = 0;
                            }else{
                                if(++ mEqualCount >= LoopCount){
                                    sendEmptyMessage(MP_LOADING);
                                }
                            }
                        }
                    }catch (IllegalStateException e){

                    }
                }
            }, 0, 100);
        }

        public void stop(){
            if( null != mTimer ) {
                mTimer.cancel();
            }
            mTimer = null;
        }
    }
}
