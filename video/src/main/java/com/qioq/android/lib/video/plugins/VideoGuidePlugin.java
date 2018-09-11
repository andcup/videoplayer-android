package com.qioq.android.lib.video.plugins;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.model.Mode;
import com.andcup.android.frame.plugin.core.model.PluginEntry;
import com.qioq.android.lib.video.VideoPlugin;

/**
 * Created by Amos on 2015/8/3.
 */
public class VideoGuidePlugin extends VideoPlugin{

    protected static final String VIDEO_GUIDE = "videoGuideOnFirstPlay";

    protected SharedPreferences mSharedPreferences;

    public VideoGuidePlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
        mSharedPreferences = getContext().getSharedPreferences(VideoGuidePlugin.class.getSimpleName(), Activity.MODE_PRIVATE);
    }

    @Override
    public void onCreated() {
        super.onCreated();
        mSharedPreferences.edit().putBoolean(VIDEO_GUIDE, true).commit();
        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });
    }

    @Override
    public void onModeChanged(Mode mode) {
        super.onModeChanged(mode);
        boolean visible = mSharedPreferences.getBoolean(VIDEO_GUIDE, false);
        if(!visible){
            show();
        }
    }
}
