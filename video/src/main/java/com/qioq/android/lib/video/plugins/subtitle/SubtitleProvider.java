package com.qioq.android.lib.video.plugins.subtitle;


import com.qioq.android.lib.video.core.model.Subtitle;

import java.util.List;

/**
 * Created by Amos on 2015/8/3.
 */
public abstract class SubtitleProvider {

    public interface OnSubtitleGetterListener{
        public void onSubtitleGetFinish(List<Subtitle> subtitleList);
    }

    public abstract void load(OnSubtitleGetterListener onSubtitleLoadingListener);
}
