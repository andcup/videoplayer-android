package com.qioq.android.lib.video.core.listener;

import com.qioq.android.lib.video.core.model.Video;

import java.util.List;

/**
 * Created by Amos on 2015/7/10.
 */
public interface OnCheckerListener {

    public void onCheckStart(List<Video> videoList);

    public void onChecking(Video video);

    public void onCheckEnd(List<Video> videoList);
}
