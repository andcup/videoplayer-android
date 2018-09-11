package com.qioq.android.lib.video.plugins.loading;

import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.model.PluginEntry;
import com.qioq.android.lib.video.core.listener.OnVideoTryListener;
import com.qioq.android.lib.video.core.model.Video;
import com.qioq.android.lib.video.engine.model.VideoState;
import com.qioq.android.lib.video.VideoPlugin;

import java.util.Date;

/**
 * Created by Amos on 2015/7/31.
 */
public class BaseLoadingPlugin extends VideoPlugin implements OnVideoTryListener{

    private static final int LIMIT_SHOW_TIME = 1200;
    private long mLastShowTime;

    public BaseLoadingPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onVideoPrepare(VideoState videoState) {
        super.onVideoPrepare(videoState);
        show();
    }

    @Override
    public void onVideoLoading(float progress) {
        super.onVideoLoading(progress);
        show();
    }

    @Override
    public void onVideoSeek(long seekTo) {
        super.onVideoSeek(seekTo);
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
    public void show() {
        Video video = getVideo();
        if( null != video && !video.getVideoUrl().startsWith("http://")){
            return;
        }
        if(!isVisible()){
            mLastShowTime = new Date().getTime();
        }
        super.show();
    }

    @Override
    public void hide() {
        if(getVideoPlayer().getVideoState() == VideoState.Finish ||
           getVideoPlayer().getVideoState() == VideoState.Pause){
            super.hide();
        }else{
            long es = new Date().getTime();
            if(es - mLastShowTime >= LIMIT_SHOW_TIME){
                super.hide();
            }
        }
    }

    protected  void hide(boolean force){
        super.hide();
    }

    @Override
    public boolean onBeforeTryPlay(Video video, long position) {
        return false;
    }

    @Override
    public void onTryPlay(Video video, long position, boolean isTry) {
        if(!isTry){
            hide();
        }
    }
}
