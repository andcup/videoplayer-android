package com.qioq.android.lib.video.engine.vlc;

import android.os.Handler;
import android.os.Message;
import com.qioq.android.lib.video.engine.model.VideoState;
import com.nd.hy.android.video.sdk.vlc.libvlc.EventHandler;
import com.nd.hy.android.video.sdk.vlc.libvlc.LibVLC;

import java.lang.ref.SoftReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Amos on 2015/7/6.
 */
public class VLCEventHandler extends Handler{

    private SoftReference<VLCEngine> mVLCEngine;
    private VideoLoadingDispatcher   mVideoLoadingDispatcher = new VideoLoadingDispatcher();

    public VLCEventHandler(VLCEngine vlcEngine){
        mVLCEngine = new SoftReference<VLCEngine>(vlcEngine);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        VLCEngine vlcEngine = mVLCEngine.get();
        if( null == vlcEngine){
            return;
        }

        switch (msg.what){
            /**
             * @VideoState : VideoStatePreparing
             * */
            case EventHandler.MediaPlayerPlayPrepare:
                VideoState.Preparing.setVideoState(VideoState.STATE_PREPARING_START);
                vlcEngine.onStateChanged(VideoState.Preparing, VideoState.STATE_PREPARING_START, vlcEngine.getVideoUrl());
                mVideoLoadingDispatcher.start();
                break;
            /**
             * @VideoState : VideoStatePlaying
             * */
            case EventHandler.MediaPlayerPlaying:
                VideoState.Playing.setVideoState(VideoState.STATE_PLAYING_START);
                vlcEngine.onStateChanged(VideoState.Playing, VideoState.STATE_PLAYING_START, null);
                mVideoLoadingDispatcher.start();
                break;
            case EventHandler.MediaPlayerBuffering:
                VideoState.Playing.setVideoState(VideoState.STATE_PLAYING_LOADING);
                float loading = msg.getData().getFloat("data");
                vlcEngine.onStateChanged(VideoState.Playing, VideoState.STATE_PLAYING_LOADING, loading);
                break;
            case EventHandler.MediaPlayerSecondBuffering:
                VideoState.Playing.setVideoState(VideoState.STATE_PLAYING_BUFFERING);
                int buffering = msg.getData().getInt("data");
                vlcEngine.onStateChanged(VideoState.Playing, VideoState.STATE_PLAYING_BUFFERING, buffering);
                break;
            case EventHandler.MediaPlayerCacheRate:
                VideoState.Playing.setVideoState(VideoState.STATE_PLAYING_LOADING_RATE);
                float loadingRate = msg.getData().getFloat("data");
                vlcEngine.onStateChanged(VideoState.Playing, VideoState.STATE_PLAYING_LOADING_RATE, loadingRate);
                break;
            case EventHandler.MediaPlayerPositionChanged:
                VideoState.Playing.setVideoState(VideoState.STATE_PLAYING_POSITION_CHANGED);
                vlcEngine.onStateChanged(VideoState.Playing, VideoState.STATE_PLAYING_POSITION_CHANGED, vlcEngine.getVideoController().getTime());
                break;
            /**
             * @VideoState : VideoStateFinish
             * */
            case EventHandler.MediaPlayerEndReached:
                VideoState.Finish.setVideoState(VideoState.STATE_FINISH_FINISH);
                vlcEngine.onStateChanged(VideoState.Finish, VideoState.STATE_FINISH_FINISH, null);
                mVideoLoadingDispatcher.stop();
                break;
            case EventHandler.MediaPlayerStopped:
//                VideoState.Finish.setVideoState(VideoState.STATE_FINISH_STOP);
//                vlcEngine.onStateChanged(VideoState.Finish, VideoState.STATE_FINISH_STOP, null);
//                mVideoLoadingDispatcher.stop();
                break;
            case EventHandler.MediaPlayerEncounteredError:
                VideoState.Finish.setVideoState(VideoState.STATE_FINISH_ERROR);
                vlcEngine.onStateChanged(VideoState.Finish, VideoState.STATE_FINISH_ERROR, null);
                mVideoLoadingDispatcher.stop();
                break;
            /**
             * @VideoState : VideoStatePause
             * */
            case EventHandler.MediaPlayerPaused:
                VideoState.Finish.setVideoState(VideoState.STATE_PAUSE);
                vlcEngine.onStateChanged(VideoState.Pause, VideoState.STATE_PAUSE, null);
                break;
            default:
                break;
        }
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
                    VLCEngine vlcEngine = mVLCEngine.get();
                    if(vlcEngine == null){
                        return;
                    }

                    LibVLC libVLC = vlcEngine.getLibVLC();
                    if(null != libVLC && libVLC.isPlaying()){
                        long currentTime = vlcEngine.getVideoController().getTime();
                        if( preLoadingTime != currentTime ) {
                            preLoadingTime = currentTime;
                            mEqualCount = 0;
                        }else{
                            if(++ mEqualCount >= LoopCount){
                                sendEmptyMessage(EventHandler.MediaPlayerBuffering);
                            }
                        }
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
