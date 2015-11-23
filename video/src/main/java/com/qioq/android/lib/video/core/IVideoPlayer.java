package com.qioq.android.lib.video.core;

import android.content.Context;
import android.os.Bundle;
import com.qioq.android.lib.video.ContentProvider;
import com.qioq.android.lib.video.core.model.Video;
import com.qioq.android.lib.video.engine.model.ScaleType;
import com.qioq.android.lib.video.engine.model.VideoState;

import java.util.List;

/**
 * Created by Amos on 2015/7/7.
 */
public interface IVideoPlayer {

    public void     start();

    public void     stop();

    public Context  getContext();

    public int      getWidth();

    public int      getHeight();

    public int[]    getLocationOnScreen();

    public void     setArguments(Bundle bundle);

    public Bundle   getArguments( );

    public void     open(ContentProvider contentProvider);

    public Video    getVideo(int index);

    public Video    getActiveVideo();

    public List<Video>  getVideoList();

    public void     playVideo(int index);

    public void     replayVideo(long position);

    public void     play();

    public void     pause();

    public long     seekTo(long position);

    public long     getTime();

    public long     getLength();

    public void      setScale(ScaleType scaleType);

    public ScaleType getScale();

    public void      setRate(float rate);

    public float     getRate();

    public void      setVolume(float volume);

    public int       getVolume();

    public void      setBrightness( int brightness);

    public int       getBrightness();

    public VideoState getVideoState();

    public ToolBar    getToolBar();

}
