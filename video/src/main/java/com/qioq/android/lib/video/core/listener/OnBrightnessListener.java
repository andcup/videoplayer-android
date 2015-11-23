package com.qioq.android.lib.video.core.listener;

/**
 * Created by Amos on 2015/7/7.
 */
public interface OnBrightnessListener {
    public void onBrightnessChangeStart(int brightness);
    public void onBrightnessChange(int brightness);
    public void onBrightnessChangeEnd();
}
