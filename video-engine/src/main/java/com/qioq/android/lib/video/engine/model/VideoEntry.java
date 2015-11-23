package com.qioq.android.lib.video.engine.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/7/6.
 */
public class VideoEntry implements Serializable{
    /**
     * 视频唯一值
     * */
    private String mUniqueId;
    /**
     *视频地址
     * */
    private String mVideoUrl;
    /**
     * 上次播放位置
     * */
    private long   mLastPosition;
    /**
     * 视频总长度
     * */
    private long   mDuration;

    public void setUniqueId(String uniqueId) {
        this.mUniqueId = uniqueId;
    }

    public void setDuration(long duration) {
        this.mDuration = duration;
    }

    public void setLastPosition(long lastPosition) {
        this.mLastPosition = lastPosition;
    }

    public void setVideoUrl(String videoUrl) {
        this.mVideoUrl = videoUrl;
    }

    public long getDuration() {
        return mDuration;
    }

    public long getLastPosition() {
        return mLastPosition;
    }

    public String getUniqueId() {
        return mUniqueId;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }
}
