package com.qioq.android.lib.video.engine.core;

import android.view.ViewGroup;
import com.qioq.android.lib.video.engine.widget.VideoView;

/**
 * Created by Amos on 2015/7/6.
 */
public class SimpleFitSizer extends AbsFitSizer {

    public SimpleFitSizer(VideoView videoView, ViewGroup videoGroup) {
        super(videoView, videoGroup);
    }

    @Override
    public void fitSize(int videoWidth, int videoHeight, int visibleWidth, int visibleHeight) {
        int dw = mVideoGroup.getWidth();
        int dh = mVideoGroup.getHeight();

        if (dw * dh == INVALID_VIDEO_VIEW_SIZE || videoWidth * videoHeight == INVALID_VIDEO_VIEW_SIZE) {
            return;
        }

        mVideoView.getHolder().setFixedSize(videoWidth, videoHeight);

        switch (getScaleType()){
            case FitFill:
                fitFull();
                break;
            case Fit16_9:
                fit16_9();
                break;
            case Fit4_3:
                fit4_3();
                break;
            case FitOriginal:
                fitOrigin(visibleWidth, visibleHeight);
                break;
            default:
                break;
        }
    }

    private void fit16_9(){
        float rate = 16.0f / 9.0f;
        fitRate(rate);
    }

    private void fit4_3(){
        float rate = 4.0f / 3.0f;
        fitRate(rate);
    }

    private void fitRate(float rate){
        int parentViewWidth = mVideoGroup.getWidth();
        int parentViewHeight = mVideoGroup.getHeight();

        int visibleWidth  = parentViewWidth;
        int visibleHeight = (int) (visibleWidth / rate);
        if(visibleHeight > parentViewHeight){
            visibleHeight = parentViewHeight;
            visibleWidth  = (int) (visibleHeight * rate);
        }

        mVideoView.setSize(visibleWidth, visibleHeight);
    }

    private void fitOrigin(int visibleWidth, int visibleHeight){
        int parentViewWidth  = mVideoGroup.getWidth();
        int parentViewHeight = mVideoGroup.getHeight();
        float vRate = parentViewWidth  /  (float)visibleWidth;
        float hRate = parentViewHeight /  (float)visibleHeight;

        visibleWidth = (int) (visibleWidth * Math.min(vRate, hRate));
        visibleHeight= (int) (visibleHeight * Math.min(vRate, hRate));

        mVideoView.setSize(visibleWidth, visibleHeight);
    }

    private void fitFull(){
        int parentViewWidth  = mVideoGroup.getWidth();
        int parentViewHeight = mVideoGroup.getHeight();
        mVideoView.setSize(parentViewWidth, parentViewHeight);
    }
}
