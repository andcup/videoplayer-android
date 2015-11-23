package com.qioq.android.lib.video.plugins.setting;

import android.view.View;
import android.widget.ImageButton;
import com.qioq.android.artemis.piece.core.Plugin;
import com.qioq.android.artemis.piece.core.PluginContext;
import com.qioq.android.artemis.piece.core.model.PluginEntry;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.VideoPlugin;

/**
 * Created by Amos on 2015/7/24.
 */
public class VideoSettingPlugin extends VideoPlugin implements View.OnClickListener{

    private ImageButton mBtnSetting;

    public VideoSettingPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onCreated() {
        super.onCreated();
        mBtnSetting = findViewById(R.id.btn_setting);
        mBtnSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Plugin plugin = getPluginContext().getPluginManager().getPlugin("@+id/video_setting_panel");
        if( null != plugin){
            plugin.show();
        }
    }
}
