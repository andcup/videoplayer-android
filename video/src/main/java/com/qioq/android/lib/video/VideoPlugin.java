package com.qioq.android.lib.video;

import android.app.Activity;
import android.view.View;
import com.qioq.android.lib.video.core.listener.OnBrightnessListener;
import com.qioq.android.lib.video.core.listener.OnContentLoadingListener;
import com.qioq.android.lib.video.core.listener.OnVideoListener;
import com.qioq.android.lib.video.core.listener.OnVolumeListener;
import com.qioq.android.lib.video.core.model.Video;
import com.qioq.android.lib.video.engine.core.IVideoController;
import com.qioq.android.lib.video.engine.model.VideoState;
import com.qioq.android.artemis.piece.core.Plugin;
import com.qioq.android.artemis.piece.core.PluginContext;
import com.qioq.android.artemis.piece.core.model.PluginEntry;

import java.lang.ref.SoftReference;
import java.util.List;

/**
 * Created by Amos on 2015/7/7.
 */
public class VideoPlugin extends Plugin implements IVideoController, OnContentLoadingListener<Video>, OnVideoListener, OnVolumeListener, OnBrightnessListener{

    private SoftReference<View> mView;

    public VideoPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    public final VideoPlayer getVideoPlayer(){
        return (VideoPlayer) getPluginContext().get();
    }

    public void onBindView(View view){
        mView = new SoftReference<View>(view);
    }

    public View getView(){
        if( null != mView){
            return mView.get();
        }
        return null;
    }

    public Activity getActivity(){
        return (Activity) getContext();
    }

    public <T> T findViewById(int id){
        return (T)getView().findViewById(id);
    }

    @Override
    public final int getAppWidth() {
        try{
            return getVideoPlayer().getWidth();
        }catch (Exception e){

        }
        return 0;
    }

    @Override
    public final int getAppHeight() {
        try{
            return getVideoPlayer().getHeight();
        }catch (Exception e){

        }
        return 0;
    }

    @Override
    public int[] getLocationOnScreen() {
        return getVideoPlayer().getLocationOnScreen();
    }

    @Override
    public final void play() {
        getVideoPlayer().play();
    }

    @Override
    public final void pause() {
        getVideoPlayer().pause();
    }

    @Override
    public final void stop() {
    }

    @Override
    public final boolean isSeekAble() {
        return true;
    }

    @Override
    public final long seekTo(long position) {
        return  getVideoPlayer().seekTo(position);
    }

    @Override
    public final long getTime() {
        return getVideoPlayer().getTime();
    }

    @Override
    public final long getLength() {
        return getVideoPlayer().getLength();
    }

    @Override
    public final long getBuffering() {
        return 0;
    }

    @Override
    public final void setRate(float rate) {
        getVideoPlayer().setRate(rate);
    }

    @Override
    public final float getRate() {
        return getVideoPlayer().getRate();
    }

    public final void  replay(long seekTo){
        getVideoPlayer().replayVideo(seekTo);
    }

    public final Video getVideo(){
        return getVideoPlayer().getActiveVideo();
    }

    public void setFullScreen(boolean fullScreen){
        getPluginContext().setFullScreen(fullScreen);
    }

    public boolean isFullScreen( ){
        return getPluginContext().isFullScreen();
    }

    @Override
    public void onContentLoadingStart() {

    }

    @Override
    public void onContentLoading(int progress) {

    }

    @Override
    public void onContentLoadingFailed(Exception e) {

    }

    @Override
    public void onContentLoadingComplete(List<Video> videoList) {

    }

    @Override
    public void onVolumeChangeStart(int volume) {

    }

    @Override
    public void onVolumeChange(int volume) {

    }

    @Override
    public void onVolumeChangeEnd() {

    }

    @Override
    public void onBrightnessChangeStart(int volume) {

    }

    @Override
    public void onBrightnessChange(int volume) {

    }

    @Override
    public void onBrightnessChangeEnd() {

    }

    @Override
    public boolean onBeforeVideoPlay(Video video, long position) {
        return false;
    }

    @Override
    public void onAfterVideoPlay(Video video, long position) {

    }

    @Override
    public void onVideoPrepare(VideoState videoState) {
    }

    @Override
    public boolean onBeforeVideoPlayStart() {
        return false;
    }

    @Override
    public void onVideoPlayStart() {

    }


    @Override
    public void onVideoLoading(float progress) {

    }

    @Override
    public void onVideoLoadingRate(float rate) {

    }

    @Override
    public boolean onBeforeVideoSeek(long seekTo) {
        return false;
    }

    @Override
    public void onVideoSeek(long seekTo) {
    }

    @Override
    public void onVideoPositionChanged() {

    }

    @Override
    public boolean onBeforeVideoPause() {
        return false;
    }

    @Override
    public void onVideoPause() {

    }

    @Override
    public void onVideoFinish(VideoState videoState) {
    }
}
