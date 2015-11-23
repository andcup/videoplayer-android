package com.qioq.android.lib.video.engine.core;

import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.ViewGroup;
import com.qioq.android.lib.video.engine.model.ScaleType;
import com.qioq.android.lib.video.engine.widget.VideoView;

/**
 * Created by Amos on 2015/7/6.
 */
public abstract class AbsFitSizer {

    protected static final int INVALID_VIDEO_VIEW_SIZE = 0;

    protected VideoView mVideoView;
    protected ViewGroup mVideoGroup;
    private   ScaleType mScaleType  = ScaleType.FitFill;
    private int         mFormat     = PixelFormat.RGBX_8888;

    private int mVideoWidth;
    private int mVideoHeight;
    private int mVisibleWidth;
    private int mVisibleHeight;

    public AbsFitSizer(VideoView videoView, ViewGroup videoGroup){
        mVideoView = videoView;
        mVideoGroup= videoGroup;
        setFormat(mFormat);
    }

    public void setFormat(int format) {
        mFormat = format;
        mVideoView.setFormat(mFormat);
    }

    public void setScaleType(ScaleType scaleType){
        mScaleType = scaleType;
        mUpdateHandler.sendEmptyMessage(0);
    }

    public ScaleType getScaleType(){
        return mScaleType;
    }

    public void setSize(int videoWidth, int videoHeight, int visibleWidth, int visibleHeight){
        mVideoWidth    = videoWidth;
        mVideoHeight   = videoHeight;
        mVisibleWidth  = visibleWidth;
        mVisibleHeight = visibleHeight;
        mUpdateHandler.sendEmptyMessage(0);
    }

    protected abstract void fitSize(int videoWidth, int videoHeight, int visibleWidth, int visibleHeight);

    private Handler mUpdateHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            fitSize(mVideoWidth, mVideoHeight, mVisibleWidth, mVisibleHeight);
        }
    };
}
