package com.qioq.android.lib.video.plugins.bar;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.core.listener.OnToolBarListener;
import com.qioq.android.lib.video.engine.model.VideoState;
import com.qioq.android.lib.video.VideoPlugin;
import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.model.PluginEntry;

/**
 * Created by Amos on 2015/7/7.
 */
public class TitleBarPlugin extends VideoPlugin implements View.OnClickListener, OnToolBarListener{

    private ImageButton mBtnClose;
    private TextView    mTvTitle;

    public TitleBarPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onCreated() {
        super.onCreated();
        mBtnClose = findViewById(R.id.btn_close);
        mTvTitle  = findViewById(R.id.tv_title);
        mBtnClose.setOnClickListener(this);

        if( null != mTvTitle && null != getVideo()){
            mTvTitle.setText(getVideo().getTitle());
        }
    }

    @Override
    public void onVideoPrepare(VideoState videoState) {
        super.onVideoPrepare(videoState);
        if( null != mTvTitle){
            mTvTitle.post(new Runnable() {
                @Override
                public void run() {
                    mTvTitle.setText(getVideo().getTitle());
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        if(isFullScreen()){
            setFullScreen(false);
        }else{
            getVideoPlayer().finish();
        }
    }

    @Override
    public void onToolBarActionStart() {

    }

    @Override
    public void onToolBarStateChanged(boolean visible) {
        if(visible){
            show();
        }else{
            hide();
        }
    }

    @Override
    public void onToolBarActionEnd() {

    }
}
