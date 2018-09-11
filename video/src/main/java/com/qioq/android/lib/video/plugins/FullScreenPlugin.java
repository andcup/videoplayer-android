package com.qioq.android.lib.video.plugins;

import android.view.View;
import android.widget.ImageButton;
import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.model.PluginEntry;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.VideoPlugin;

/**
 * Created by Amos on 2015/7/10.
 */
public class FullScreenPlugin extends VideoPlugin implements View.OnClickListener{

    private ImageButton mBtnFullScreen;

    public FullScreenPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onCreated() {
        super.onCreated();
        mBtnFullScreen = findViewById(R.id.btn_full_screen);
        mBtnFullScreen.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        setFullScreen(!isFullScreen());
    }
}
