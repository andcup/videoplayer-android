package com.qioq.android.lib.video.plugins.network;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.qioq.android.artemis.piece.core.PluginContext;
import com.qioq.android.artemis.piece.core.model.PluginEntry;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.VideoPlugin;
import com.qioq.android.lib.video.core.model.Video;
import com.qioq.android.lib.video.engine.model.VideoState;
import com.qioq.android.lib.video.plugins.network.core.NetworkChangeMonitor;
import com.qioq.android.lib.video.plugins.network.core.NetworkType;
import com.qioq.android.lib.video.plugins.network.core.NetworkUtil;
import com.qioq.android.lib.video.plugins.network.core.OnNetworkChangeListener;

/**
 * Created by Amos on 2015/7/31.
 */
public class VideoNetworkNotConnectPlugin extends VideoPlugin implements View.OnClickListener, OnNetworkChangeListener{

    private Button  mBtnOk;
    private Button  mBtnRetry;

    public VideoNetworkNotConnectPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
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

        mBtnOk    = findViewById(R.id.btn_ok);
        mBtnRetry = findViewById(R.id.btn_retry);


        mBtnOk.setOnClickListener(this);
        mBtnRetry.setOnClickListener(this);
    }

    @Override
    public boolean onBeforeVideoSeek(long seekTo) {
        return onPauseIfNetworkNotAlive();
    }

    @Override
    public boolean onBeforeVideoPlayStart() {
        return onPauseIfNetworkNotAlive();
    }

    @Override
    public void onClick(View view) {
        hide();
        if(view == mBtnRetry){
            if(!onPauseIfNetworkNotAlive()){
                replay(getVideo().getLastPosition());
            }
        }
    }

    private boolean onPauseIfNetworkNotAlive(){
        if(getVideo().getVideoUrl().startsWith("http://")){
            NetworkType networkType = NetworkUtil.getConnectivityStatus(getContext());
            if(networkType == NetworkType.TypeNotConnect){
                if(isFullScreen()){
                    show();
                }else{
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.network_network_err_msg), Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void onChange(NetworkType type) {
        if(type == NetworkType.TypeNotConnect){
            VideoState videoState = getVideoPlayer().getVideoState();
            if(videoState != VideoState.Finish && videoState != VideoState.Pause){
                pause();
                if(isFullScreen()){
                    show();
                }else{
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.network_network_err_msg), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
