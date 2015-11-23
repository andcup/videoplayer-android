package com.qioq.android.lib.video.core.listener;

import com.qioq.android.lib.video.core.model.Video;

/**
 * Created by Amos on 2015/7/31.
 */
public interface OnVideoTryListener {

    public boolean onBeforeTryPlay(Video video, long position);

    public void    onTryPlay(Video video, long position, boolean isTry);
}
