package com.qioq.android.lib.video;

import com.qioq.android.lib.video.core.listener.OnContentLoadingListener;

/**
 * Created by Amos on 2015/7/8.
 */
public abstract class ContentProvider {
    public abstract void load(OnContentLoadingListener onDocLoadingListener);
}
