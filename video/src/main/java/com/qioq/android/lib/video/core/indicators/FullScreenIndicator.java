package com.qioq.android.lib.video.core.indicators;

import android.graphics.Point;
import com.andcup.android.frame.plugin.core.Plugin;
import com.andcup.android.frame.plugin.core.indicator.AbsIndicator;

/**
 * Created by Amos on 2015/7/10.
 */
public class FullScreenIndicator extends AbsIndicator{
    @Override
    protected boolean getVisibleOnModeChanged(Plugin plugin) {
        Point point = getWindowSize(plugin);
        int width  = plugin.getAppWidth();
        int height = plugin.getAppHeight();
        if(Math.abs(width - point.x) > 300 || Math.abs(height - point.y) > 300){
            return true;
        }
        return false;
    }
}
