package com.qioq.android.lib.video.core.indicators;

import com.qioq.android.artemis.piece.core.Plugin;
import com.qioq.android.artemis.piece.core.indicator.AbsIndicator;

/**
 * Created by Amos on 2015/7/23.
 */
public class LargeIndicator extends AbsIndicator{

    @Override
    protected boolean getVisibleOnModeChanged(Plugin plugin) {
        return plugin.getPluginContext().isFullScreen();
    }
}
