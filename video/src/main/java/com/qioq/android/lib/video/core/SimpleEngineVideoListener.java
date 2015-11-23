package com.qioq.android.lib.video.core;

import com.qioq.android.lib.video.engine.listener.OnEngineVideoListener;
import com.qioq.android.lib.video.engine.model.VideoState;

/**
 * Created by Amos on 2015/7/8.
 */
public class SimpleEngineVideoListener implements OnEngineVideoListener {

    private String mAppId;

    public SimpleEngineVideoListener(String appId){
        mAppId = appId;
    }

    @Override
    public void onStateChanged(VideoState videoState, int subState, Object value) {
        switch (videoState){
            case Preparing:
                onPrepareState(videoState);
                break;
            case Playing:
                onPlayingState(subState, value);
                break;
            case Pause:
                onPauseState(subState);
                break;
            case Finish:
                onFinishState(videoState);
                break;
        }
    }

    private void onPrepareState(VideoState videoState){
        NotificationService.get().onVideoPrepare(videoState);
    }

    private void onPlayingState(int subState, Object value){
        switch (subState){
            case VideoState.STATE_PLAYING_START:
                NotificationService.get().onVideoPlayStart();
                break;
            case VideoState.STATE_PLAYING_LOADING:
                NotificationService.get().onVideoLoading((float) value);
                break;
            case VideoState.STATE_PLAYING_LOADING_RATE:
                NotificationService.get().onVideoLoadingRate((float)value);
                break;
            case VideoState.STATE_PLAYING_POSITION_CHANGED:
                NotificationService.get().onVideoPositionChanged();
                break;
            case VideoState.STATE_PLAYING_BUFFERING:
                break;
        }
    }

    private void onPauseState( int subState){
        switch (subState){
            case VideoState.STATE_PAUSE:
                NotificationService.get().onVideoPause();
                break;
        }
    }

    private void onFinishState(VideoState videoState){
        NotificationService.get().onVideoFinish(videoState);
    }
}
