package com.qioq.android.lib.video.engine.vlc;

import com.qioq.android.lib.video.engine.AbsVideoController;
import com.nd.hy.android.video.sdk.vlc.libvlc.LibVLC;

/**
 * Created by Amos on 2015/7/6.
 */
public class VLCController extends AbsVideoController{

    private LibVLC mLibVLC;

    public VLCController(LibVLC libVLC){
        mLibVLC = libVLC;
    }

    @Override
    public void play() {
        if( null != mLibVLC && !mLibVLC.isPlaying() ){
            mLibVLC.play();
        }
    }

    @Override
    public void pause() {
        if( null != mLibVLC && mLibVLC.isPlaying() ){
            mLibVLC.pause();
        }
    }

    @Override
    public void stop() {
        if( null != mLibVLC){
            mLibVLC.stop();
        }
    }

    @Override
    public boolean isSeekAble() {
        if( null != mLibVLC){
            return mLibVLC.isSeekable();
        }
        return false;
    }

    @Override
    public long seekTo(long position) {
        if( null != mLibVLC){
            return mLibVLC.setTime(position);
        }
        return 0;
    }

    @Override
    public long getTime() {
        if( null != mLibVLC){
            return mLibVLC.getTime();
        }
        return 0;
    }

    @Override
    public long getLength() {
        if( null != mLibVLC){
            return mLibVLC.getLength();
        }
        return 0;
    }

    @Override
    public long getBuffering() {
        /*if( null != mLibVLC){
            //return mLibVLC.getCache();
        }*/
        return 0;
    }

    @Override
    public void setRate(float rate) {
        if( null != mLibVLC){
            mLibVLC.setRate(rate);
        }
    }

    @Override
    public float getRate() {
        if( null != mLibVLC){
            return mLibVLC.getRate();
        }
        return 1.0f;
    }
}
