package com.qioq.android.lib.video.core;

import android.util.Log;
import android.view.MotionEvent;
import com.qioq.android.artemis.piece.PluginApplication;
import com.qioq.android.artemis.piece.core.Plugin;
import com.qioq.android.artemis.piece.core.PluginManager;
import com.qioq.android.artemis.piece.core.PluginServices;
import com.qioq.android.lib.video.core.listener.*;
import com.qioq.android.lib.video.core.model.Subtitle;
import com.qioq.android.lib.video.core.model.Video;
import com.qioq.android.lib.video.engine.model.VideoState;
import com.qioq.android.lib.video.VideoPlugin;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Amos on 2015/7/8.
 */
public class NotificationService implements OnVideoListener,
        OnVolumeListener,
        OnContentLoadingListener<Video>,
        OnBrightnessListener,
        OnGestureListener,
        OnGestureSeekListener,
        OnCheckerListener,
        OnToolBarListener,
        OnFullScreenListener,
        OnVideoTryListener,
        OnSubtitleChangeListener{

    private static final String TAG = NotificationService.class.getSimpleName();
    private static NotificationService sNotificationService;
    private String mAppId;

    public static void newInstance(String appId){
        if( null == sNotificationService){
            sNotificationService = new NotificationService(appId);
        }
    }

    public static NotificationService get(){
        return sNotificationService;
    }

    private NotificationService(String appId){
        mAppId = appId;
    }

    private List<Plugin> getPluginList(){
        PluginApplication pluginApplication = PluginServices.getInstance().getPluginApplication(mAppId);
        if( null == pluginApplication){
            return null;
        }
        PluginManager pluginManager = pluginApplication.getPluginManager();
        if( null == pluginManager){
            return null;
        }
        return pluginManager.getPluginList();
    }

    @Override
    public void onVolumeChangeStart(final int volume) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onVolumeChangeStart");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin.getView()!= null){
                videoPlugin.getView().post(new Runnable() {
                    @Override
                    public void run() {
                        (videoPlugin).onVolumeChangeStart(volume);
                    }
                });
            }else{
                (videoPlugin).onVolumeChangeStart(volume);
            }
        }
    }

    @Override
    public void onVolumeChange(final int volume) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onVolumeChange");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin.getView()!= null){
                videoPlugin.getView().post(new Runnable() {
                    @Override
                    public void run() {
                        (videoPlugin).onVolumeChange(volume);
                    }
                });
            }else{
                (videoPlugin).onVolumeChange(volume);
            }
        }
    }

    @Override
    public void onVolumeChangeEnd() {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onVolumeChangeEnd");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin.getView()!= null){
                videoPlugin.getView().post(new Runnable() {
                    @Override
                    public void run() {
                        (videoPlugin).onVolumeChangeEnd();
                    }
                });
            }else{
                (videoPlugin).onVolumeChangeEnd();
            }
        }
    }

    @Override
    public void onContentLoadingStart() {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onContentLoadingStart");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin.getView()!= null){
                videoPlugin.getView().post(new Runnable() {
                    @Override
                    public void run() {
                        (videoPlugin).onContentLoadingStart();
                    }
                });
            }else{
                (videoPlugin).onContentLoadingStart();
            }
        }
    }

    @Override
    public void onContentLoading(final int progress) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onContentLoading");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin.getView()!= null){
                videoPlugin.getView().post(new Runnable() {
                    @Override
                    public void run() {
                        (videoPlugin).onContentLoading(progress);
                    }
                });
            }else{
                (videoPlugin).onContentLoading(progress);
            }
        }
    }

    @Override
    public void onContentLoadingFailed(final Exception e) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onContentLoadingFailed");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            (videoPlugin).onContentLoadingFailed(e);
        }
    }

    @Override
    public void onContentLoadingComplete(final List<Video> videoList) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin.getView()!= null){
                videoPlugin.getView().post(new Runnable() {
                    @Override
                    public void run() {
                        (videoPlugin).onContentLoadingComplete(videoList);
                    }
                });
            }else{
                (videoPlugin).onContentLoadingComplete(videoList);
            }
        }
    }

    @Override
    public void onBrightnessChangeStart(final int brightness) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onBrightnessChangeStart");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin.getView()!= null){
                videoPlugin.getView().post(new Runnable() {
                    @Override
                    public void run() {
                        (videoPlugin).onBrightnessChangeStart(brightness);
                    }
                });
            }else{
            }
        }
    }

    @Override
    public void onBrightnessChange(final int brightness) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onBrightnessChange");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin.getView()!= null){
                videoPlugin.getView().post(new Runnable() {
                    @Override
                    public void run() {
                        (videoPlugin).onBrightnessChange(brightness);
                    }
                });
            }else{
                (videoPlugin).onBrightnessChange(brightness);
            }
        }
    }

    @Override
    public void onBrightnessChangeEnd() {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onBrightnessChangeEnd");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin.getView()!= null){
                videoPlugin.getView().post(new Runnable() {
                    @Override
                    public void run() {
                        (videoPlugin).onBrightnessChangeEnd();
                    }
                });
            }else{
                (videoPlugin).onBrightnessChangeEnd();
            }
        }
    }

    @Override
    public boolean onBeforeVideoPlay(Video video, long position) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return false;
        }
        Log.v(TAG, "onBeforeVideoPlay");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin.onBeforeVideoPlay(video, position)){
                Log.e(TAG, " onBeforeVideoPlay interrupt by plugin : " + videoPlugin.getId());
                return true;
            }
        }
        return false;
    }

    @Override
    public void onAfterVideoPlay(final Video video, final long position) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return ;
        }
        Log.v(TAG, "onAfterVideoPlay");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin.getView()!= null){
                videoPlugin.getView().post(new Runnable() {
                    @Override
                    public void run() {
                        (videoPlugin).onAfterVideoPlay(video, position);
                    }
                });
            }else{
                (videoPlugin).onAfterVideoPlay(video, position);
            }
        }
    }

    @Override
    public void onVideoPrepare(final VideoState videoState) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return ;
        }
        Log.v(TAG, "onVideoPrepare");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin.getView()!= null){
                videoPlugin.getView().post(new Runnable() {
                    @Override
                    public void run() {
                        (videoPlugin).onVideoPrepare(videoState);
                    }
                });
            }else{
                (videoPlugin).onVideoPrepare(videoState);
            }
        }
    }

    @Override
    public boolean onBeforeVideoPlayStart() {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return false;
        }
        Log.v(TAG, "onBeforeVideoPlayStart");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin.onBeforeVideoPlayStart()){
                Log.e(TAG, " onBeforeVideoPlayStart interrupt by plugin : " + videoPlugin.getId());
                return true;
            }
        }
        return false;
    }

    @Override
    public void onVideoPlayStart() {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onVideoPlayStart");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin.getView()!= null){
                videoPlugin.getView().post(new Runnable() {
                    @Override
                    public void run() {
                        (videoPlugin).onVideoPlayStart();
                    }
                });
            }else{
                (videoPlugin).onVideoPlayStart();
            }
        }
    }


    @Override
    public void onVideoLoading(final float progress) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onVideoLoading");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin.getView()!= null){
                videoPlugin.getView().post(new Runnable() {
                    @Override
                    public void run() {
                        (videoPlugin).onVideoLoading(progress);
                    }
                });
            }else{
                (videoPlugin).onVideoLoading(progress);
            }
        }
    }

    @Override
    public void onVideoLoadingRate(final float rate) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin.getView()!= null){
                videoPlugin.getView().post(new Runnable() {
                    @Override
                    public void run() {
                        (videoPlugin).onVideoLoadingRate(rate);
                    }
                });
            }else{
                (videoPlugin).onVideoLoadingRate(rate);
            }
        }
    }

    @Override
    public boolean onBeforeVideoSeek(long seekTo) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return false;
        }
        Log.v(TAG, "onBeforeVideoSeek");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin.onBeforeVideoSeek(seekTo)){
                Log.e(TAG, " onBeforeVideoSeek interrupt by plugin : " + videoPlugin.getId());
                return true;
            }
        }
        return false;
    }

    @Override
    public void onVideoSeek(final long seekTo) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onVideoSeek");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin.getView()!= null){
                videoPlugin.getView().post(new Runnable() {
                    @Override
                    public void run() {
                        (videoPlugin).onVideoSeek(seekTo);
                    }
                });
            }else{
                (videoPlugin).onVideoSeek(seekTo);
            }
        }
    }


    @Override
    public void onVideoPositionChanged() {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            (videoPlugin).onVideoPositionChanged();
        }
    }

    @Override
    public boolean onBeforeVideoPause() {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return false;
        }
        Log.v(TAG, "onBeforeVideoPause");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin.onBeforeVideoPause()){
                Log.e(TAG, " onBeforeVideoPause interrupt by plugin : " + videoPlugin.getId());
                return true;
            }
        }
        return false;
    }

    @Override
    public void onVideoPause() {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return ;
        }
        Log.v(TAG, "onVideoPause");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin.getView()!= null){
                videoPlugin.getView().post(new Runnable() {
                    @Override
                    public void run() {
                        (videoPlugin).onVideoPause();
                    }
                });
            }else{
                (videoPlugin).onVideoPause();
            }
        }
    }

    @Override
    public void onVideoFinish(final VideoState videoState) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return ;
        }
        Log.v(TAG, "onVideoFinish");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin.getView()!= null){
                videoPlugin.getView().post(new Runnable() {
                    @Override
                    public void run() {
                        (videoPlugin).onVideoFinish(videoState);
                    }
                });
            }else{
                (videoPlugin).onVideoFinish(videoState);
            }
        }
    }

    @Override
    public void onGestureStart(final MotionEvent event) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onGestureStart");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin instanceof OnGestureListener){
                if(videoPlugin.getView()!= null){
                    videoPlugin.getView().post(new Runnable() {
                        @Override
                        public void run() {
                            ((OnGestureListener)videoPlugin).onGestureStart(event);
                        }
                    });
                }else{
                    ((OnGestureListener)videoPlugin).onGestureStart(event);
                }
            }
        }
    }

    @Override
    public boolean onGestureProgress(MotionEvent e1, MotionEvent e2, float vX, float vY) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return false;
        }
        Log.v(TAG, "onGestureProgress");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin instanceof OnGestureListener){
                if(((OnGestureListener)videoPlugin).onGestureProgress(e1, e2, vX, vY)){
                    Log.e(TAG, " onGestureProgress interrupt by plugin : " + videoPlugin.getId());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onGestureSingleTab(MotionEvent event) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return false;
        }
        Log.v(TAG, "onGestureSingleTab");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin instanceof OnGestureListener){
                if(((OnGestureListener)videoPlugin).onGestureSingleTab(event)){
                    Log.e(TAG, " onGestureSingleTab interrupt by plugin : " + videoPlugin.getId());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onGestureEnd(final MotionEvent event) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return ;
        }
        Log.v(TAG, "onGestureEnd");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin instanceof OnGestureListener){
                if(videoPlugin.getView()!= null){
                    videoPlugin.getView().post(new Runnable() {
                        @Override
                        public void run() {
                            ((OnGestureListener)videoPlugin).onGestureEnd(event);
                        }
                    });
                }else{
                    ((OnGestureListener)videoPlugin).onGestureEnd(event);
                }
            }
        }
    }

    @Override
    public void onGestureSeekStart(final long start) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onGestureSeekStart");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin instanceof OnGestureSeekListener){
                if(videoPlugin.getView()!= null){
                    videoPlugin.getView().post(new Runnable() {
                        @Override
                        public void run() {
                            ((OnGestureSeekListener)videoPlugin).onGestureSeekStart(start);
                        }
                    });
                }else{
                    ((OnGestureSeekListener)videoPlugin).onGestureSeekStart(start);
                }
            }
        }
    }

    @Override
    public void onGestureSeek(final long start, final long seekTo) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onGestureSeek");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin instanceof OnGestureSeekListener){
                if(videoPlugin.getView()!= null){
                    videoPlugin.getView().post(new Runnable() {
                        @Override
                        public void run() {
                            ((OnGestureSeekListener)videoPlugin).onGestureSeek(start, seekTo);
                        }
                    });
                }else{
                    ((OnGestureSeekListener)videoPlugin).onGestureSeek(start, seekTo);
                }
            }
        }
    }

    @Override
    public void onGestureSeekEnd(final long seekTo) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onGestureSeekEnd");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin instanceof OnGestureSeekListener){
                if(videoPlugin.getView()!= null){
                    videoPlugin.getView().post(new Runnable() {
                        @Override
                        public void run() {
                            ((OnGestureSeekListener)videoPlugin).onGestureSeekEnd(seekTo);
                        }
                    });
                }else{
                    ((OnGestureSeekListener)videoPlugin).onGestureSeekEnd(seekTo);
                }
            }
        }
    }

    @Override
    public void onCheckStart(final List<Video> videoList) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onCheckStart");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin instanceof OnCheckerListener) {
                if(videoPlugin.getView()!= null){
                    videoPlugin.getView().post(new Runnable() {
                        @Override
                        public void run() {
                            ((OnCheckerListener)videoPlugin).onCheckStart(videoList);
                        }
                    });
                }else{
                    ((OnCheckerListener)videoPlugin).onCheckStart(videoList);
                }
            }
        }
    }

    @Override
    public void onChecking(final Video video) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onChecking");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin instanceof OnCheckerListener) {
                if(videoPlugin.getView()!= null){
                    videoPlugin.getView().post(new Runnable() {
                        @Override
                        public void run() {
                            ((OnCheckerListener)videoPlugin).onChecking(video);
                        }
                    });
                }else{
                    ((OnCheckerListener)videoPlugin).onChecking(video);
                }
            }
        }
    }

    @Override
    public void onCheckEnd(final List<Video> videoList) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onCheckEnd");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin instanceof OnCheckerListener) {
                if(videoPlugin.getView()!= null){
                    videoPlugin.getView().post(new Runnable() {
                        @Override
                        public void run() {
                            ((OnCheckerListener) videoPlugin).onCheckEnd(videoList);
                        }
                    });
                }else{
                    ((OnCheckerListener) videoPlugin).onCheckEnd(videoList);
                }
            }
        }
    }

    @Override
    public void onToolBarActionStart() {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onToolBarActionStart");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin instanceof OnToolBarListener) {
                if(videoPlugin.getView()!= null){
                    videoPlugin.getView().post(new Runnable() {
                        @Override
                        public void run() {
                            ((OnToolBarListener) videoPlugin).onToolBarActionStart();
                        }
                    });
                }else{
                    ((OnToolBarListener) videoPlugin).onToolBarActionStart();
                }
            }
        }
    }

    @Override
    public void onToolBarStateChanged(final boolean visible) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onToolBarStateChanged");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin instanceof OnToolBarListener) {
                if(videoPlugin.getView()!= null){
                    videoPlugin.getView().post(new Runnable() {
                        @Override
                        public void run() {
                            ((OnToolBarListener) videoPlugin).onToolBarStateChanged(visible);
                        }
                    });
                }else{
                    ((OnToolBarListener) videoPlugin).onToolBarStateChanged(visible);
                }
            }
        }
    }

    @Override
    public void onToolBarActionEnd() {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onToolBarActionEnd");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin instanceof OnToolBarListener) {
                if(videoPlugin.getView()!= null){
                    videoPlugin.getView().post(new Runnable() {
                        @Override
                        public void run() {
                            ((OnToolBarListener) videoPlugin).onToolBarActionEnd();
                        }
                    });
                }else{
                    ((OnToolBarListener) videoPlugin).onToolBarActionEnd();
                }
            }
        }
    }

    @Override
    public void onBeforeFullScreen(final boolean fullScreen) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return;
        }
        Log.v(TAG, "onBeforeFullScreen");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin instanceof OnFullScreenListener) {
                if(videoPlugin.getView()!= null){
                    videoPlugin.getView().post(new Runnable() {
                        @Override
                        public void run() {
                            ((OnFullScreenListener) videoPlugin).onBeforeFullScreen(fullScreen);
                        }
                    });
                }else{
                    ((OnFullScreenListener) videoPlugin).onBeforeFullScreen(fullScreen);
                }
            }
        }
    }

    @Override
    public boolean onBeforeTryPlay(final Video video, final long position) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return false;
        }
        Log.v(TAG, "onBeforeTryPlay");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin instanceof OnVideoTryListener) {
                if(((OnVideoTryListener) videoPlugin).onBeforeTryPlay(video, position)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onTryPlay(Video video, long position, boolean isTry) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return ;
        }
        Log.v(TAG, "onTryPlay");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin instanceof OnVideoTryListener) {
                ((OnVideoTryListener) videoPlugin).onTryPlay(video, position, isTry);
            }
        }
    }

    @Override
    public void onSubtitleChanged(Subtitle subtitle) {
        List<Plugin> plugins = getPluginList();
        if(null == plugins){
            return ;
        }
        Log.v(TAG, "onSubtitleChanged");
        Iterator<Plugin> pluginIterator =  plugins.iterator();
        while (pluginIterator.hasNext()){
            final VideoPlugin videoPlugin =  ((VideoPlugin) pluginIterator.next());
            if(videoPlugin instanceof OnSubtitleChangeListener) {
                ((OnSubtitleChangeListener) videoPlugin).onSubtitleChanged(subtitle);
            }
        }
    }
}
