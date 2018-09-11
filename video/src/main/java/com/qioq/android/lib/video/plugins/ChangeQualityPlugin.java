package com.qioq.android.lib.video.plugins;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.model.PluginEntry;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.VideoPlugin;
import com.qioq.android.lib.video.core.model.Quality;
import com.qioq.android.lib.video.core.model.Video;
import com.qioq.android.lib.video.engine.model.VideoState;

import java.util.List;

/**
 * Created by Amos on 2015/7/31.
 */
public class ChangeQualityPlugin extends VideoPlugin implements View.OnClickListener{

    private static final int INTERVAL        = 20000;
    private static final int POSITION_CHANGE = 5000;
    private static final int INVALID_START   = -1;

    private long   mLoadingStartTime = INVALID_START;

    private boolean mIsActive  = false;
    private boolean mHasNotify = false;

    private ImageButton mBtnClose;
    private TextView    mTvTitle;

    public ChangeQualityPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onCreated() {
        super.onCreated();
        mBtnClose = findViewById(R.id.btn_close);
        mTvTitle  = findViewById(R.id.tv_message);
        mBtnClose.setOnClickListener(this);
        mTvTitle.setOnClickListener(this);
    }

    @Override
    public boolean onBeforeVideoPlay(Video video, long position) {
        mIsActive = false;
        return super.onBeforeVideoPlay(video, position);
    }

    @Override
    public void onAfterVideoPlay(Video video, long position) {
        super.onAfterVideoPlay(video, position);
        start(position);
    }

    @Override
    public void onVideoLoading(float progress) {
        super.onVideoLoading(progress);
        start(getTime());
    }

    @Override
    public void onVideoSeek(long seekTo) {
        super.onVideoSeek(seekTo);
        mIsActive = true;
        start(seekTo);
    }

    @Override
    public void onVideoPlayStart() {
        super.onVideoPlayStart();
        start(getTime());
    }

    @Override
    public void onVideoFinish(VideoState videoState) {
        super.onVideoFinish(videoState);
        mPositionChangeHandler.removeMessages(0);
    }

    private void start(long time){
        if(!mIsActive){
            return;
        }
        if(mLoadingStartTime == INVALID_START){
            mPositionChangeHandler.sendEmptyMessageDelayed(0, INTERVAL);
        }
        mLoadingStartTime = time;
    }


    private Handler mPositionChangeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mLoadingStartTime == INVALID_START){
                return;
            }
            VideoState videoState = getVideoPlayer().getVideoState();
            if(videoState == VideoState.Playing && Math.abs(getTime() - mLoadingStartTime) <= POSITION_CHANGE){
                Video video = getLowerQualityVideo();
                if( null != video){
                    show();
                }
            }
            mLoadingStartTime = INVALID_START;
        }
    };

    private Video getLowerQualityVideo(){
        Video video = getVideo();
        if( null != video && video.getQuality() != Quality.Low){
            List<Video> videoList = getVideoPlayer().getVideoList();
            int index = videoList.indexOf(video);
            if(index != 0){
                return videoList.get(0);
            }
        }
        return null;
    }

    @Override
    public void show() {
        if(mHasNotify){
            return;
        }
        super.show();
    }

    @Override
    public void onClick(View view) {
        hide();
        if(view == mTvTitle){
            Video video = getLowerQualityVideo();
            int index = getVideoPlayer().getVideoList().indexOf(video);
            getVideoPlayer().playVideo(index);
        }
        mHasNotify = true;
    }
}
