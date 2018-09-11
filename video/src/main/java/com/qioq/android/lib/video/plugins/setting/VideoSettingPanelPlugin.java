package com.qioq.android.lib.video.plugins.setting;

import android.view.View;
import android.widget.ImageButton;
import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.model.PluginEntry;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.engine.model.VideoState;
import com.qioq.android.lib.video.VideoPlugin;

/**
 * Created by Amos on 2015/7/29.
 */
public class VideoSettingPanelPlugin extends VideoPlugin implements View.OnClickListener{

    private ImageButton mBtnClose;

    public VideoSettingPanelPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onCreated() {
        super.onCreated();
        mBtnClose = findViewById(R.id.btn_close);
        mBtnClose.setOnClickListener(this);
    }

    @Override
    public void onVideoPrepare(VideoState videoState) {
        super.onVideoPrepare(videoState);
        hide();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hide();
    }

    @Override
    public void onClick(View view) {
        hide();
    }
}
