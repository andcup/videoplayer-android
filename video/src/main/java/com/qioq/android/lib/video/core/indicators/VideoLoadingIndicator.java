package com.qioq.android.lib.video.core.indicators;

import android.graphics.Point;
import com.andcup.android.frame.plugin.core.Plugin;
import com.andcup.android.frame.plugin.core.indicator.AbsIndicator;
import com.nd.hy.android.video.R;

/**
 * Created by Amos on 2015/7/10.
 */
public class VideoLoadingIndicator extends AbsIndicator{

    @Override
    public int genLayoutId(Plugin plugin) {
        Point point = getWindowSize(plugin);
        if(point.x < point.y){
            return R.layout.video_loading_mini;
        }else{
            return R.layout.video_loading;
        }
    }

    @Override
    protected boolean getVisibleOnModeChanged(Plugin plugin) {
        return true;
    }
}
