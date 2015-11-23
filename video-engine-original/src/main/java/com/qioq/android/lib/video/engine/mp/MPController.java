package com.qioq.android.lib.video.engine.mp;

import com.qioq.android.lib.video.engine.AbsVideoController;
import com.qioq.android.lib.video.engine.model.VideoState;

/**
 * Created by Amos on 2015/8/12.
 */
public class MPController extends AbsVideoController {

    private MPEngine mMpEngine;

    public MPController(MPEngine mpEngine){
        mMpEngine = mpEngine;
    }

    @Override
    public void play() {
        if(!mMpEngine.getMediaPlayer().isPlaying()){
            mMpEngine.getMpEventHandler().publish(MPEventHandler.MP_PLAY_START);
            mMpEngine.getMediaPlayer().start();
        }
    }

    @Override
    public void pause() {
        if(mMpEngine.getMediaPlayer().isPlaying()){
            mMpEngine.getMpEventHandler().publish(MPEventHandler.MP_PAUSE);
            mMpEngine.getMediaPlayer().pause();
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isSeekAble() {
        return false;
    }

    @Override
    public long seekTo(long position) {
        mMpEngine.getMediaPlayer().seekTo((int) position);
        return position;
    }

    @Override
    public long getTime() {
        VideoState videoState = mMpEngine.getVideoState();
        if(videoState == VideoState.Playing || videoState == VideoState.Pause){
            return mMpEngine.getMediaPlayer().getCurrentPosition();
        }
        return 0;
    }

    @Override
    public long getLength() {
        VideoState videoState = mMpEngine.getVideoState();
        if(videoState == VideoState.Playing || videoState == VideoState.Pause){
            return mMpEngine.getMediaPlayer().getDuration();
        }
        return 0;
    }

    @Override
    public long getBuffering() {
        return 0;
    }

    @Override
    public void setRate(float rate) {

    }

    @Override
    public float getRate() {
        return 0;
    }
}
