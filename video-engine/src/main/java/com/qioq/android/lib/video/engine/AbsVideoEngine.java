package com.qioq.android.lib.video.engine;

import android.content.Context;
import android.view.ViewGroup;
import com.qioq.android.lib.video.engine.core.AbsFitSizer;
import com.qioq.android.lib.video.engine.core.IVideoEngine;
import com.qioq.android.lib.video.engine.listener.OnEngineVideoListener;
import com.qioq.android.lib.video.engine.model.ScaleType;
import com.qioq.android.lib.video.engine.model.VideoState;
import com.qioq.android.lib.video.engine.widget.VideoView;

/**
 * Created by Amos on 2015/7/6.
 */
public abstract  class AbsVideoEngine implements IVideoEngine, OnEngineVideoListener {

    protected static final int DEFAULT_NETWORK_CACHING = 20000;

    private Context         mContext;
    private AbsFitSizer     mFitSizer;
    private VideoController mVideoController;
    private String          mVideoUrl;
    private VideoState      mLastState  = VideoState.Preparing;
    private long            mNetworkCaching = DEFAULT_NETWORK_CACHING;
    private ScaleType       mScaleType = ScaleType.FitOriginal;
    private OnEngineVideoListener mOnEngineVideoListener;

    public AbsVideoEngine(Context context){
        mContext = context;
    }

    public void setOnEngineVideoListener(OnEngineVideoListener onEngineVideoListener){
        mOnEngineVideoListener = onEngineVideoListener;
        if( null != mVideoController){
            mVideoController.setOnVideoListener(onEngineVideoListener);
        }
    }

    public AbsVideoController getVideoController() {
        return mVideoController;
    }

    public Context getContext() {
        return mContext;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public VideoState getVideoState() {
        return mVideoController.getVideoState();
    }

    @Override
    public final void onCreate() {
        onCreateVideoSdk();
        mVideoController = new VideoController(createVideoController());
        mVideoController.setOnVideoListener(mOnEngineVideoListener);
    }

    @Override
    public void playVideo(String url, long position) {
        mVideoUrl = url;
        onVideoPrepare(position);
    }

    @Override
    public final void onBindView(VideoView videoView, ViewGroup parentView) {
        mFitSizer = createFitSizer(videoView, parentView);
        mFitSizer.setScaleType(mScaleType);
    }

    public AbsFitSizer getFitSizer(){
        return mFitSizer;
    }

    @Override
    public void onPause() {
        mLastState = mVideoController.getVideoState();
        mVideoController.pause();
    }

    @Override
    public void onResume() {
        switch (mLastState){
            case Playing:
                mVideoController.play();
                break;
        }
        mLastState = mVideoController.getVideoState();
    }

    @Override
    public void onStop() {
        mVideoController.stop();
    }

    @Override
    public void onDestroy() {
        onDestroyVideoSdk();
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
    public void setScale(ScaleType scaleType) {
        mScaleType = scaleType;
        if(null != mFitSizer){
            mFitSizer.setScaleType(scaleType);
        }
    }

    @Override
    public ScaleType getScale() {
        return mFitSizer.getScaleType();
    }

    @Override
    public void setNetworkCaching(int caching) {
        mNetworkCaching = caching;
    }

    @Override
    public void setFormat(int format) {
        mFitSizer.setFormat(format);
    }

    @Override
    public float getCacheRate() {
        return 0;
    }

    protected abstract AbsFitSizer        createFitSizer(VideoView videoView, ViewGroup viewGroup);
    protected abstract AbsVideoController createVideoController();
    protected abstract void               onCreateVideoSdk();
    protected abstract void               onDestroyVideoSdk();
    protected abstract void               onVideoPrepare(long position);

    @Override
    public void onStateChanged(VideoState videoState, int subState, Object value) {
        mVideoController.onStateChanged(videoState, subState, value);
    }
}
