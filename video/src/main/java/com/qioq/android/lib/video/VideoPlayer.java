package com.qioq.android.lib.video;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.qioq.android.artemis.piece.IPluginApplication;
import com.qioq.android.artemis.piece.PluginApplication;
import com.qioq.android.artemis.piece.app.SimpleLifeCycleListener;
import com.qioq.android.artemis.piece.core.PluginManager;
import com.qioq.android.lib.video.core.IVideoPlayer;
import com.qioq.android.lib.video.core.NotificationService;
import com.qioq.android.lib.video.core.SimpleEngineVideoListener;
import com.qioq.android.lib.video.core.ToolBar;
import com.qioq.android.lib.video.core.exception.EnvironmentException;
import com.qioq.android.lib.video.core.exception.NullContainerFoundException;
import com.qioq.android.lib.video.core.exception.NullPointerAppDelegateException;
import com.qioq.android.lib.video.core.model.Video;
import com.qioq.android.lib.video.core.system.Brightness;
import com.qioq.android.lib.video.core.system.Volume;
import com.qioq.android.lib.video.engine.AbsVideoEngine;
import com.qioq.android.lib.video.engine.VideoEngineManager;
import com.qioq.android.lib.video.engine.model.ScaleType;
import com.qioq.android.lib.video.engine.model.VideoState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amos on 2015/7/7.
 */
public class VideoPlayer extends IPluginApplication implements IVideoPlayer {

    private static final int   INVALID_CONTAINER = 0;
    private VideoDelegate      mAppDelegate;
    private int                mContainerId = INVALID_CONTAINER;
    private VideoConfiguration mConfiguration;
    private PluginApplication<VideoPlayer> mPluginApplication;
    private AbsVideoEngine     mVideoEngine;
    private Bundle             mArguments;
    private List<Video>        mVideos = new ArrayList<>();
    private Video              mActiveVideo;
    private ContentProvider    mContentProvider;
    private ContentLoadingListener mContentLoadingListener;
    private ScaleType          mScaleType = ScaleType.FitFill;
    private Volume             mVolume;
    private String             mCoverUrl;

    public static class Builder{
        private VideoConfiguration mConfiguration;
        private VideoDelegate      mAppDelegate;
        private int   mContainerId = INVALID_CONTAINER;

        public Builder setConfiguration(VideoConfiguration configuration){
            mConfiguration = configuration;
            return this;
        }

        public Builder setAppDelegate(VideoDelegate appDelegate){
            mAppDelegate = appDelegate;
            return this;
        }

        public Builder setContainerId(int containerId){
            mContainerId = containerId;
            return this;
        }

        public VideoPlayer build(){
            VideoPlayer readerPlayer = new VideoPlayer(this);
            return readerPlayer;
        }
    }

    public VideoPlayer(Builder builder){
        mAppDelegate    = builder.mAppDelegate;
        mContainerId    = builder.mContainerId;
        mConfiguration  = builder.mConfiguration;

        if( INVALID_CONTAINER == mContainerId ){
            throw new NullContainerFoundException();
        }
        if( null == mAppDelegate ){
            throw new NullPointerAppDelegateException();
        }
        if(!(mAppDelegate.getFragmentManager() instanceof FragmentManager)){
            throw new EnvironmentException();
        }

        Fresco.initialize(getContext());
        NotificationService.newInstance(getAppId());
        mPluginApplication      = new PluginApplication<>(this, mConfiguration.getPluginPath());
        mPluginApplication.setOnAppLifeCycleListener(mSimpleLifeCycleListener);
        mContentLoadingListener = new ContentLoadingListener(getAppId()){
            @Override
            public void onContentLoadingComplete(List<Video> videoList) {
                mVideos.clear();
                if( null != videoList){
                    mVideos.addAll(videoList);
                }
                super.onContentLoadingComplete(videoList);
            }
        };
        mVolume = new Volume(getContext());
    }

    public void setOnVideoPlayerListener(PluginApplication.OnApplicationListener<VideoPlayer> onVideoPlayerListener){
        mPluginApplication.setOnApplicationListener(onVideoPlayerListener);
    }

    public AbsVideoEngine getVideoEngine() {
        return mVideoEngine;
    }

    public String getCoverUrl() {
        return mCoverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.mCoverUrl = coverUrl;
    }

    @Override
    public String getAppId() {
        return VideoPlayer.class.getName();
    }

    @Override
    public void start() {
        mPluginApplication.start(mContainerId);
        mVideoEngine = VideoEngineManager.newEngine(mAppDelegate.getContext());
        if( null == mVideoEngine){
            Log.e(VideoPlayer.class.getSimpleName(), "========> not found video engine");
            return;
        }
        mVideoEngine.setOnEngineVideoListener(new SimpleEngineVideoListener(getAppId()));
        mVideoEngine.setScale(mScaleType);
        mVideoEngine.onCreate();
    }

    @Override
    public void stop() {
        if( null != mVideoEngine){
            mVideoEngine.onDestroy();
        }
        mVideoEngine = null;
        if( null != mPluginApplication){
            mPluginApplication.stop();
        }
        mPluginApplication = null;
    }

    @Override
    public Context getContext() {
        return mAppDelegate.getContext();
    }

    @Override
    public int getWidth() {
        return mPluginApplication.getWidth();
    }

    @Override
    public int getHeight() {
        return mPluginApplication.getHeight();
    }

    @Override
    public int[] getLocationOnScreen() {
        return mPluginApplication.getLocationOnScreen();
    }

    public int getAndroidToolBarHeight() {
        return mPluginApplication.getSystemToolBarRect().top;
    }

    @Override
    public PluginManager getPluginManager() {
        return mPluginApplication.getPluginManager();
    }

    @Override
    public FragmentManager getFragmentManager() {
        return mAppDelegate.getFragmentManager();
    }

    @Override
    public void setFullScreen(boolean fullScreen) {
        NotificationService.get().onBeforeFullScreen(fullScreen);
        mAppDelegate.setFullScreen(fullScreen);
    }

    @Override
    public boolean isFullScreen() {
        return mAppDelegate.isFullScreen();
    }

    @Override
    public void finish() {
        mAppDelegate.finish(this);
    }

    @Override
    public void setArguments(Bundle bundle) {
        mArguments = bundle;
    }

    @Override
    public Bundle getArguments() {
        return mArguments;
    }

    @Override
    public void open(ContentProvider contentProvider) {
        mContentProvider = contentProvider;
        if(null != mPluginApplication && mPluginApplication.getPluginManager() != null){
            mContentProvider.load(mContentLoadingListener);
        }
    }

    @Override
    public Video getVideo(int index) {
        return mVideos.get(index);
    }

    @Override
    public Video getActiveVideo() {
        return mActiveVideo;
    }

    @Override
    public List<Video> getVideoList() {
        return mVideos;
    }

    @Override
    public void playVideo(int index) {
        mActiveVideo = getVideo(index);
        if(null == mVideoEngine || NotificationService.get().onBeforeVideoPlay(mActiveVideo, mActiveVideo.getLastPosition())){
            return;
        }
        mVideoEngine.playVideo(mActiveVideo.getVideoUrl(), mActiveVideo.getLastPosition());
        NotificationService.get().onAfterVideoPlay(mActiveVideo, mActiveVideo.getLastPosition());
    }

    @Override
    public void replayVideo(long position) {
        if( null != mActiveVideo){
            if(null == mVideoEngine || NotificationService.get().onBeforeVideoPlay(mActiveVideo, mActiveVideo.getLastPosition())){
                return;
            }
            mVideoEngine.playVideo(mActiveVideo.getVideoUrl(), position);
            NotificationService.get().onAfterVideoPlay(mActiveVideo, mActiveVideo.getLastPosition());
        }
    }

    @Override
    public void play() {
        if (null != mVideoEngine && !NotificationService.get().onBeforeVideoPlayStart()) {
            mVideoEngine.getVideoController().play();
        }
    }

    @Override
    public void pause() {
        if(null != mVideoEngine && !NotificationService.get().onBeforeVideoPause()){
            mVideoEngine.getVideoController().pause();
        }
    }

    @Override
    public long seekTo(long position) {
        if(null == mVideoEngine || NotificationService.get().onBeforeVideoSeek(position)){
            return -1;
        }
        long seekTo = mVideoEngine.getVideoController().seekTo(position);
        NotificationService.get().onVideoSeek(position);
        return seekTo;
    }

    @Override
    public long getTime() {
        if( null == mVideoEngine){
            return 0;
        }
        return mVideoEngine.getVideoController().getTime();
    }

    @Override
    public long getLength() {
        if( null == mVideoEngine){
            return 0;
        }
        return mVideoEngine.getVideoController().getLength();
    }

    @Override
    public void setScale(ScaleType scaleType) {
        if( null == mVideoEngine){
            return ;
        }
        mVideoEngine.setScale(scaleType);
    }

    @Override
    public ScaleType getScale() {
        if( null == mVideoEngine){
            return ScaleType.FitFill;
        }
        return mVideoEngine.getScale();
    }

    @Override
    public void setRate(float rate) {
        if( null == mVideoEngine){
            return ;
        }
        mVideoEngine.getVideoController().setRate(rate);
    }

    @Override
    public float getRate() {
        if( null == mVideoEngine){
            return 1.0f;
        }
        return mVideoEngine.getVideoController().getRate();
    }

    @Override
    public void setVolume(float volume) {
        mVolume.setVolume((int) volume);
    }

    @Override
    public int getVolume() {
        return mVolume.getVolume();
    }

    @Override
    public void setBrightness(int brightness) {
        Brightness.INSTANCE.setBrightness((Activity) getContext(), brightness);
    }

    @Override
    public int getBrightness() {
        return 0;
    }

    @Override
    public VideoState getVideoState() {
        if( null != mVideoEngine){
            return mVideoEngine.getVideoState();
        }
        return VideoState.Finish;
    }

    @Override
    public ToolBar getToolBar() {
        return (ToolBar) getPluginManager().getPlugin("@+id/video_tool_bar");
    }

    private SimpleLifeCycleListener mSimpleLifeCycleListener = new SimpleLifeCycleListener() {

        @Override
        public void onPause() {
            super.onPause();
            if( null != mVideoEngine){
                mVideoEngine.onPause();
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            if( null != mVideoEngine){
                mVideoEngine.onResume();
            }
        }

        @Override
        public void onCreated() {
            if(null != mContentProvider){
                mContentProvider.load(mContentLoadingListener);
            }
            super.onCreated();
        }
    };
}
