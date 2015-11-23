package com.qioq.android.lib.video.plugins.content;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.qioq.android.artemis.piece.core.PluginContext;
import com.qioq.android.artemis.piece.core.model.PluginEntry;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.core.listener.OnCheckerListener;
import com.qioq.android.lib.video.core.listener.OnVideoTryListener;
import com.qioq.android.lib.video.core.model.Video;
import com.qioq.android.lib.video.VideoPlugin;
import com.qioq.android.lib.video.tools.RateConvert;
import jp.wasabeef.blurry.Blurry;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Amos on 2015/7/8.
 */
public class OnContentLoadingPlugin extends VideoPlugin implements OnCheckerListener, OnVideoTryListener {

    private ImageView   mLoading;
    private TextView    mTvProgress;
    private List<Video> mVideoList;
    private Video       mVideo;
    private SimpleDraweeView mVideoCover;
    private ImageView   mIvBlur;

    public OnContentLoadingPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onCreated() {
        super.onCreated();
        mTvProgress =  findViewById(R.id.tv_progress);
        mLoading    =  findViewById(R.id.iv_loading);
        mVideoCover = findViewById(R.id.sdv_cover);
        mIvBlur     = findViewById(R.id.iv_cover);
        mLoading.setBackgroundResource(R.anim.video_loading);
        AnimationDrawable drawable = (AnimationDrawable) mLoading.getBackground();
        drawable.start();
        checkVideo(0);
        setCover();
    }

    private void setCover(){
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(ImageRequest.fromUri(getVideoPlayer().getCoverUrl()))
                .setOldController(mVideoCover.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        Blurry.with(getContext()).sampling(8).capture(mVideoCover).into(mIvBlur);
                    }
                })
                .build();
        mVideoCover.setController(controller);
    }

    @Override
    public boolean onBeforeVideoPlay(Video video, long position) {
        if( mVideo != video){
            show();
            mVideo = video;
        }
        return super.onBeforeVideoPlay(video, position);
    }

    @Override
    public void onContentLoadingStart() {
        super.onContentLoadingStart();
        show();
    }

    @Override
    public void onVideoPositionChanged() {
        super.onVideoPositionChanged();
        if(isVisible()){
            hide();
        }
    }

    @Override
    public void onContentLoadingFailed(Exception e) {
        super.onContentLoadingFailed(e);
        onError(R.string.no_video_for_play);
    }

    @Override
    public void onCheckStart(List<Video> videos) {
        mVideoList = new ArrayList<>(videos);
    }

    @Override
    public void onChecking(Video video) {
        try{
            checkVideo((int) (100 * (mVideoList.indexOf(video)) / (float)mVideoList.size()));
        }catch (Exception e){

        }
    }

    @Override
    public void onCheckEnd(List<Video> videoList) {
        if( null == videoList || videoList.size() <= 0){
            onError(R.string.no_video_for_play);
        }else{
            formatRate(0.0f);
        }
    }

    @Override
    public void onVideoLoadingRate(float rate) {
        super.onVideoLoadingRate(rate);
        formatRate(rate);
    }



    private void checkVideo(final int index){
        if( null != mTvProgress){
            mTvProgress.post(new Runnable() {
                @Override
                public void run() {
                    try{
                        String value = String.format(getContext().getResources().getString(R.string.get_video) );
                        mTvProgress.setText(value);
                    }catch (Exception e){

                    }
                }
            });
        }
    }

    private void formatRate(float rate){
        if( null != mTvProgress) {
            try{
                mTvProgress.setText(RateConvert.format(getContext(), rate));
            }catch (Exception e){

            }
        }
    }

    private void onError(final int resourceId){
        if( null != mLoading){
            mLoading.post(new Runnable() {
                @Override
                public void run() {
                    try{
                        mLoading.setVisibility(View.GONE);
                        mTvProgress.setText(getContext().getResources().getString(resourceId));
                        mExitHandler.sendEmptyMessageDelayed(0, 3000);
                    }catch (Exception e){

                    }
                }
            });
        }
    }

    private Handler   mExitHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                getVideoPlayer().finish();
            }catch (Exception e){

            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(isFullScreen()){
            setFullScreen(false);
            return;
        }
        getVideoPlayer().finish();
    }

    @Override
    public boolean onBeforeTryPlay(Video video, long position) {
        return false;
    }

    @Override
    public void onTryPlay(Video video, long position, boolean isTry) {
        if(!isTry && isVisible()){
            onError(R.string.video_error_play);
        }
    }
}
