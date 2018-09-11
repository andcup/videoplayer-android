package com.qioq.android.lib.video.plugins;

import android.os.Handler;
import android.util.Log;
import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.model.PluginEntry;
import com.qioq.android.lib.video.VideoPlugin;
import com.qioq.android.lib.video.core.NotificationService;
import com.qioq.android.lib.video.core.listener.OnVideoTryListener;
import com.qioq.android.lib.video.core.model.Video;
import com.qioq.android.lib.video.engine.model.VideoState;

/**
 * Created by Amos on 2015/7/10.
 */
public class VideoErrorStrategyPlugin extends VideoPlugin implements OnVideoTryListener{

    private static final int TRY_MAX_COUNT = 3;
    private int   mErrorTryCount = 0;

    public VideoErrorStrategyPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onVideoPlayStart() {
        mErrorTryCount = 0;
        super.onVideoPlayStart();
    }

    @Override
    public void onVideoFinish(VideoState videoState) {
        super.onVideoFinish(videoState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Video video = getVideo();
                if( null == video){
                    return;
                }
                boolean tryPlay = NotificationService.get().onBeforeTryPlay(video, video.getLastPosition());
                NotificationService.get().onTryPlay(video, video.getLastPosition(), tryPlay);
                Log.v(VideoErrorStrategyPlugin.class.getSimpleName(), "mErrorTryCount = " + mErrorTryCount);
            }
        }, 5);
    }

    @Override
    public boolean onBeforeTryPlay(Video video, long position) {
        if(Math.abs(video.getLastPosition() - video.getLength()) <= 1500){
            return false;
        }
        return mErrorTryCount++ < TRY_MAX_COUNT;
    }

    @Override
    public void onTryPlay(Video video, long position, boolean isTry) {
        if(isTry){
            replay(position);
        }
    }
}
