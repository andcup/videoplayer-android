package com.qioq.android.lib.video.core.listener;

/**
 * Created by Amos on 2015/7/9.
 */
public interface OnGestureSeekListener {
    public void onGestureSeekStart(long start);
    public void onGestureSeek(long start, long seekTo);
    public void onGestureSeekEnd(long seekTo);
}
