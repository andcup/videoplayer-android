package com.qioq.android.lib.video.plugins.subtitle.core;

import com.qioq.android.lib.video.core.model.Subtitle;

/**
 * Created by Amos on 2015/8/3.
 */
public interface OnSubtitleLoadingListener {

    public void onSubtitleLoadingStart(Subtitle subtitle);
    public void onSubtitleLoading(Subtitle subtitle, int progress);
    public void onSubtitleLoadingComplete(Subtitle subtitle);
    public void onSubtitleLoadingFailed(Subtitle subtitle);
}
