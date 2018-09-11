package com.qioq.android.lib.video.plugins.overlay;

import android.view.View;
import android.widget.ImageButton;
import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.model.PluginEntry;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.core.listener.OnVideoTryListener;
import com.qioq.android.lib.video.core.model.Video;
import com.qioq.android.lib.video.engine.model.VideoState;
import com.qioq.android.lib.video.VideoPlugin;

/**
 * Created by Amos on 2015/7/10.
 */
public class PlayCtrlPlugin extends VideoPlugin implements View.OnClickListener, OnVideoTryListener{

    private ImageButton mBtnCtrl;

    public PlayCtrlPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onCreated() {
        super.onCreated();
        mBtnCtrl = findViewById(R.id.btn_video_ctrl);
        mBtnCtrl.setOnClickListener(this);
        setCtrl();
    }

    @Override
    public void onClick(View view) {
        VideoState videoState = getVideoPlayer().getVideoState();
        switch (videoState){
            case Preparing:
            case Playing:
                pause();
                break;
            case Pause:
                play();
                break;
            case Finish:
                Video video = getVideo();
                if((video.getLastPosition() - video.getLength()) <= 1000){
                    replay(0);
                }else{
                    replay(video.getLastPosition());
                }
                break;
        }
    }

    @Override
    public void onVideoPrepare(VideoState videoState) {
        super.onVideoPrepare(videoState);
        hide();
    }

    @Override
    public void onVideoPause() {
        super.onVideoPause();
        show();
        setCtrl();
    }

    @Override
    public void onVideoPlayStart() {
        super.onVideoPlayStart();
        hide();
    }

    @Override
    public void onVideoLoading(float progress) {
        super.onVideoLoading(progress);
        hide();
    }

    @Override
    public void onVideoSeek(long seekTo) {
        super.onVideoSeek(seekTo);
        hide();
    }

    @Override
    public void onVideoPositionChanged() {
        super.onVideoPositionChanged();
        hide();
    }

    private void setCtrl(){
        if( null == mBtnCtrl){
            return;
        }
        switch (getVideoPlayer().getVideoState()){
            case Finish:
                mBtnCtrl.setImageResource(R.drawable.video_ctrl_replay_selector);
                break;
            case Pause:
                mBtnCtrl.setImageResource(R.drawable.video_ctrl_play_selector);
                break;
        }

    }

    @Override
    public boolean onBeforeTryPlay(Video video, long position) {
        return false;
    }

    @Override
    public void onTryPlay(Video video, long position, boolean isTry) {
        if(!isTry){
            show();
            setCtrl();
        }
    }
}
