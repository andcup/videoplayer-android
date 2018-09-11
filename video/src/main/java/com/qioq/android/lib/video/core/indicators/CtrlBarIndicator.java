package com.qioq.android.lib.video.core.indicators;

import com.andcup.android.frame.plugin.core.Plugin;
import com.andcup.android.frame.plugin.core.indicator.AbsIndicator;
import com.nd.hy.android.video.R;

/**
 * Created by Amos on 2015/7/24.
 */
public class CtrlBarIndicator extends AbsIndicator{

    @Override
    public int genLayoutId(Plugin plugin) {
        if(plugin.getPluginContext().isFullScreen()){
            return R.layout.video_ctrl_bar_large;
        }else{
            return R.layout.video_ctrl_bar;
        }
    }

    @Override
    protected boolean getVisibleOnModeChanged(Plugin plugin) {
        return true;
    }
}
