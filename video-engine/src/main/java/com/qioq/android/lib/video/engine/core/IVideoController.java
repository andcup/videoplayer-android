package com.qioq.android.lib.video.engine.core;

/**
 * Created by Amos on 2015/7/6.
 */
public interface IVideoController {

    public void     play();

    public void     pause();

    public void     stop();

    public boolean  isSeekAble();

    public long     seekTo(long position);

    public long     getTime();

    public long     getLength();

    public long     getBuffering();

    public void     setRate(float rate);

    public float    getRate();
}
