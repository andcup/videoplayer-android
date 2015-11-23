package com.qioq.android.lib.video.core.listener;

/**
 * Created by Administrator on 2015/7/7.
 */
public interface OnVolumeListener {
    public void onVolumeChangeStart(int volume);

    public void onVolumeChange(int volume);

    public void onVolumeChangeEnd();
}
