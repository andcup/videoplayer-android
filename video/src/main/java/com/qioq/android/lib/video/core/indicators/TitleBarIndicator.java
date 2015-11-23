package com.qioq.android.lib.video.core.indicators;

import com.qioq.android.artemis.piece.core.Plugin;
import com.qioq.android.artemis.piece.core.indicator.AbsIndicator;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.VideoPlugin;

/**
 * Created by Amos on 2015/7/7.
 */
public class TitleBarIndicator extends AbsIndicator{

    @Override
    public int genLayoutId(Plugin plugin) {
        VideoPlugin videoPlugin = (VideoPlugin) plugin;
        if(videoPlugin.isFullScreen()){
            return R.layout.video_title_bar_full_screen;
        }else{
            return R.layout.video_title_bar;
        }
    }

    @Override
    protected boolean getVisibleOnModeChanged(Plugin plugin) {
        return true;
    }
}
