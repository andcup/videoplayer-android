package com.qioq.android.lib.video.engine;

import android.text.TextUtils;
import android.util.Log;
import com.qioq.android.lib.video.engine.listener.OnEngineVideoListener;
import com.qioq.android.lib.video.engine.model.VideoState;

/**
 * Created by Amos on 2015/7/7.
 */
public class VideoController extends AbsVideoController implements OnEngineVideoListener {

    private final String TAG = VideoController.class.getSimpleName();
    private final long   INVALID_SEEK_TO_POSITION = -1;

    private AbsVideoController mVideoController;
    private VideoState         mVideoState = VideoState.Preparing;
    private OnEngineVideoListener mOnEngineVideoListener;
    private long               mLatestSeekPosition = INVALID_SEEK_TO_POSITION;

    public VideoController(AbsVideoController targetController){
        mVideoController = targetController;
    }

    public void setOnVideoListener(OnEngineVideoListener onEngineVideoListener){
        mOnEngineVideoListener = onEngineVideoListener;
    }

    public VideoState getVideoState() {
        return mVideoState;
    }

    @Override
    public void play() {
        if (mVideoState == VideoState.Pause){
            mVideoState = VideoState.Playing;
            if( null != mOnEngineVideoListener){
                mOnEngineVideoListener.onStateChanged(VideoState.Playing, VideoState.STATE_PLAYING_START, null);
            }
            mVideoController.play();
        } else {
            Log.e(TAG, " video is already playing state");
        }
    }

    @Override
    public void pause() {
        if (mVideoState == VideoState.Playing){
            mVideoState = VideoState.Pause;
            if( null != mOnEngineVideoListener){
                mOnEngineVideoListener.onStateChanged(VideoState.Pause, VideoState.Pause.getVideoState(), null);
            }
            mVideoController.pause();
        } else {
            Log.e(TAG, " video is already pause state");
        }
    }

    @Override
    public void stop() {
        if(mVideoState != VideoState.Finish){
            mVideoController.stop();
        } else {
            Log.e(TAG, " video is already finish state");
        }
    }

    @Override
    public boolean isSeekAble() {
        return mVideoController.isSeekAble();
    }

    @Override
    public long seekTo(long position) {
        if(mVideoState == VideoState.Finish){
            return INVALID_SEEK_TO_POSITION;
        }
        if( mVideoState == VideoState.Playing ){
            if(mVideoState.getVideoState() == VideoState.STATE_PLAYING_POSITION_CHANGED){
                mLatestSeekPosition = INVALID_SEEK_TO_POSITION;
                return mVideoController.seekTo(position);
            }else{
                mLatestSeekPosition = position;
            }
        }else if(mVideoState == VideoState.Pause){
            if(mLatestSeekPosition != INVALID_SEEK_TO_POSITION){
                mLatestSeekPosition = position;
            }else{
                mVideoController.seekTo(position);
            }
            return position;
        }
        return mLatestSeekPosition;
    }

    @Override
    public long getTime() {
        if( INVALID_SEEK_TO_POSITION != mLatestSeekPosition){
            return mLatestSeekPosition;
        }
        return mVideoController.getTime();
    }

    @Override
    public long getLength() {
        return mVideoController.getLength();
    }

    @Override
    public long getBuffering() {
        return mVideoController.getBuffering();
    }

    @Override
    public void setRate(float rate) {
        mVideoController.setRate(rate);
    }

    @Override
    public float getRate() {
        return mVideoController.getRate();
    }

    @Override
    public void onStateChanged(VideoState videoState, int subState, Object value) {
        try{
            switch (videoState){
                case Preparing:
                    handlerPrepare(value);
                    break;
                case Playing:
                    handlerPlaying(value);
                    break;
                case Pause:
                    handlerPause(value);
                    break;
                case Finish:
                    handlerFinish(value);
                    break;
            }
        }catch (Exception e){
            if( null != e && !TextUtils.isEmpty(e.getMessage())){
                Log.e("VideoController", e.getMessage());
            }
        }
    }

    private void handlerPrepare( Object value){
        mVideoState = VideoState.Preparing;
        if( null != mOnEngineVideoListener){
            mOnEngineVideoListener.onStateChanged(VideoState.Preparing, VideoState.Preparing.getVideoState(), value);
        }
    }

    private void handlerPlaying(Object value){
        if(mVideoState == VideoState.Playing){
            if( null != mOnEngineVideoListener){
                mOnEngineVideoListener.onStateChanged(VideoState.Playing, VideoState.Playing.getVideoState(), value);
            }
        }else if(mVideoState == VideoState.Pause){
            mVideoController.pause();
        }else{
            mVideoState = VideoState.Playing;
        }
        if(VideoState.Playing.getVideoState() == VideoState.STATE_PLAYING_POSITION_CHANGED){
            if(mLatestSeekPosition != INVALID_SEEK_TO_POSITION){
                mVideoController.seekTo(mLatestSeekPosition);
                mLatestSeekPosition = INVALID_SEEK_TO_POSITION;
            }
        }
    }

    private void handlerPause( Object value){
        if(mVideoState == VideoState.Pause){
            if( null != mOnEngineVideoListener){
                mOnEngineVideoListener.onStateChanged(VideoState.Pause, VideoState.Pause.getVideoState(), value);
            }
        }
    }

    private void handlerFinish( Object value){
        mVideoState = VideoState.Finish;
        if( null != mOnEngineVideoListener){
            mOnEngineVideoListener.onStateChanged(VideoState.Finish, VideoState.Finish.getVideoState(), value);
        }
    }
}
