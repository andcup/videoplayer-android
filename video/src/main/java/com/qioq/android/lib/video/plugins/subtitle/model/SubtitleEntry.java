package com.qioq.android.lib.video.plugins.subtitle.model;

/**
 * Created by ND on 14-11-14.
 */
public class SubtitleEntry {

    private int mBeginTime;
    private int mEndTime;
    private String mSrtBody;

    public int getBeginTime() {
        return mBeginTime;
    }

    public void setBeginTime(int beginTime) {
        this.mBeginTime = beginTime;
    }

    public int getEndTime() {
        return mEndTime;
    }

    public void setEndTime(int endTime) {
        this.mEndTime = endTime;
    }

    public String getSrtBody() {
        return mSrtBody;
    }

    public void setSrtBody(String srtBody) {
        this.mSrtBody = srtBody;
    }

    @Override
    public String toString() {
        return "" + mBeginTime + ":" + mEndTime + ":" + mSrtBody;
    }
}
