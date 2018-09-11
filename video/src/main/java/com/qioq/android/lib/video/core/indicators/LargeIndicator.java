package com.qioq.android.lib.video.core.indicators;

import com.andcup.android.frame.plugin.core.Plugin;
import com.andcup.android.frame.plugin.core.indicator.AbsIndicator;

/**
 * Created by Amos on 2015/7/23.
 */
public class LargeIndicator extends AbsIndicator{

    @Override
    protected boolean getVisibleOnModeChanged(Plugin plugin) {
        return plugin.getPluginContext().isFullScreen();
    }
}
