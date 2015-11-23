package com.qioq.android.lib.video.core.listener;

import com.qioq.android.lib.video.core.model.Video;
import com.qioq.android.lib.video.engine.model.VideoState;

/**
 * Created by Amos on 2015/7/7.
 */
public interface OnVideoListener {
    /**
     * @brief 开始播放视频
     * */
    public boolean onBeforeVideoPlay(Video video, long position);
    /**
     * @brief 视频播放中
     * */
    public void    onAfterVideoPlay(Video video, long position);
    /**
     * @brief 视频准备中
     * */
    public void    onVideoPrepare(VideoState videoState);
    /**
     * @brief 视频开始播放前。
     * */
    public boolean onBeforeVideoPlayStart();
    /**
     * @brief 视频播放后
     * */
    public void    onVideoPlayStart();
    /**
     * @brief 视频播放后
     * */
    public void    onVideoLoading(float progress);
    /**
     * @brief 视频播放后
     * */
    public void    onVideoLoadingRate(float rate);
    /**
     * @brief 视频开始播放前。
     * */
    public boolean onBeforeVideoSeek(long seekTo);
    /**
     * @brief 视频开始播放前。
     * */
    public void    onVideoSeek(long seekTo);
    /**
     * @brief 视频播放后
     * */
    public void    onVideoPositionChanged();
    /**
     * @brief 视频暂停前
     * */
    public boolean onBeforeVideoPause();
    /**
     * @brief 视频暂停
     * */
    public void    onVideoPause();
    /**
     * Attempting to stop the video.
     */
    public void    onVideoFinish(VideoState videoState);
}
