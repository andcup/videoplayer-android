package com.qioq.android.lib.video.engine.listener;

import com.qioq.android.lib.video.engine.model.VideoState;

/**
 * Created by Administrator on 2015/7/7.
 */
public interface OnEngineVideoListener {
    public void onStateChanged(VideoState videoState, int subState, Object value);
}
