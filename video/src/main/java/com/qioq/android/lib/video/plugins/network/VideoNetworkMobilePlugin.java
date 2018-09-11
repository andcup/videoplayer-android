package com.qioq.android.lib.video.plugins.network;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.model.PluginEntry;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.VideoPlugin;
import com.qioq.android.lib.video.core.model.Video;
import com.qioq.android.lib.video.engine.model.VideoState;
import com.qioq.android.lib.video.plugins.network.core.NetworkChangeMonitor;
import com.qioq.android.lib.video.plugins.network.core.NetworkType;
import com.qioq.android.lib.video.plugins.network.core.OnNetworkChangeListener;

/**
 * Created by Amos on 2015/7/31.
 */
public class VideoNetworkMobilePlugin extends VideoPlugin implements View.OnClickListener, OnNetworkChangeListener{

    private Button  mBtnContinuePlay;
    private Button  mBtnCancelPlay;
    private boolean mHasNotify = false;

    public VideoNetworkMobilePlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
        NetworkChangeMonitor.get().addNetWorkChangeListener(this);
    }

    @Override
    public boolean onBeforeVideoPlay(Video video, long position) {
        if(video.getVideoUrl().startsWith("http://")){
            NetworkChangeMonitor.get().start(getContext());
        }
        return super.onBeforeVideoPlay(video, position);
    }

    @Override
    public boolean onBeforeAppDestroy() {
        NetworkChangeMonitor.get().stop(getContext());
        return super.onBeforeAppDestroy();
    }

    @Override
    public void onCreated() {
        super.onCreated();
        mBtnContinuePlay = findViewById(R.id.btn_continue_play);
        mBtnCancelPlay   = findViewById(R.id.btn_cancel_play);

        if( null != mBtnCancelPlay){
            mBtnCancelPlay.setOnClickListener(this);
            mBtnContinuePlay.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        if(view == mBtnContinuePlay){
            play();
        }else{
            getVideoPlayer().finish();
        }
        hide();
        mHasNotify = true;
    }

    @Override
    public void onChange(NetworkType type) {
        if(type == NetworkType.TypeMobile ){
            if(mHasNotify){
                return;
            }
            VideoState videoState = getVideoPlayer().getVideoState();
            if(videoState != VideoState.Finish && videoState != VideoState.Pause){
                pause();
                if(isFullScreen()){
                    show();
                }else{
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.dlg_not_wifi), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
