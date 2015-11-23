package com.qioq.android.lib.video.core.model;

import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by Amos on 2015/7/7.
 */
public class Video implements Serializable{

    public enum Type implements Serializable{
        Mp4, flv, f4v, mp3;
    }

    private String    mVideoId;
    private String    mVideoUrl;
    private long      mLastPosition;
    private long      mLength;
    private Quality   mQuality = Quality.Standard;
    private Type      mType    = Type.f4v;
    private String    mTitle;
    private String    mCoverUrl;

    private Bundle    mExtraData = new Bundle();

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public long getLastPosition() {
        return mLastPosition;
    }

    public long getLength() {
        return mLength;
    }

    public void setLastPosition(long lastPosition) {
        this.mLastPosition = lastPosition;
    }

    public void setLength(long length) {
        this.mLength = length;
    }

    public void setVideoUrl(String videoUrl) {
        this.mVideoUrl = videoUrl;
    }

    public void setQuality(Quality quality) {
        this.mQuality = quality;
    }

    public Quality getQuality() {
        return mQuality;
    }

    public void setVideoId(String mVideoId) {
        this.mVideoId = mVideoId;
    }

    public String getVideoId() {
        return mVideoId;
    }

    public void setType(Type type) {
        this.mType = type;
    }

    public Type getType() {
        return mType;
    }

    public void putExtra(String key, Serializable serializable){
        mExtraData.putSerializable(key, serializable);
    }

    public Serializable getExtra(String key){
        return mExtraData.getSerializable(key);
    }

    public Boolean getBoolean(String key){
        return mExtraData.getBoolean(key, false);
    }

    public void putBoolean(String key, boolean value){
        mExtraData.putBoolean(key, value);
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getCoverUrl() {
        return mCoverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.mCoverUrl = coverUrl;
    }
}
