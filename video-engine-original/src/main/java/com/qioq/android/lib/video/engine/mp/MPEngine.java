package com.qioq.android.lib.video.engine.mp;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.SurfaceHolder;
import android.view.ViewGroup;
import com.qioq.android.lib.video.engine.AbsVideoController;
import com.qioq.android.lib.video.engine.AbsVideoEngine;
import com.qioq.android.lib.video.engine.core.AbsFitSizer;
import com.qioq.android.lib.video.engine.core.SimpleFitSizer;
import com.qioq.android.lib.video.engine.widget.VideoView;

import java.io.IOException;

/**
 * Created by Amos on 2015/8/12.
 */
public class MPEngine extends AbsVideoEngine {

    private MediaPlayer     mMediaPlayer;
    private MPController    mController;
    private VideoView       mVideoView;
    private MPEventHandler  mMpEventHandler;

    public MPEngine(Context context) {
        super(context);
    }

    @Override
    protected AbsFitSizer createFitSizer(VideoView videoView, ViewGroup viewGroup) {
        AbsFitSizer absFitSizer = new SimpleFitSizer(videoView, viewGroup);
        mVideoView = videoView;
        mVideoView.addCallback(mSurfaceCallback);

        return absFitSizer;
    }

    @Override
    protected AbsVideoController createVideoController() {
        mController = new MPController(this);
        return mController;
    }

    @Override
    public void playVideo(String url, long position) {
        if( null != mMediaPlayer){
            try {
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(getContext(), Uri.parse(url));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.playVideo(url, position);
    }

    public MediaPlayer getMediaPlayer(){
        return mMediaPlayer;
    }

    public MPEventHandler getMpEventHandler() {
        return mMpEventHandler;
    }

    @Override
    protected void onCreateVideoSdk() {
        mMediaPlayer    = new MediaPlayer();
        mMpEventHandler = new MPEventHandler(this);
    }

    @Override
    protected void onDestroyVideoSdk() {
        mMediaPlayer.release();
    }

    @Override
    protected void onVideoPrepare(final long position) {
        mMediaPlayer.prepareAsync();
        mMediaPlayer.setScreenOnWhilePlaying(true);
        mMpEventHandler.publish(MPEventHandler.MP_PREPARE);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMpEventHandler.publish(MPEventHandler.MP_PREPARED);
                mController.seekTo(position);
                mController.play();
            }
        });
    }

    private  SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            mMediaPlayer.setDisplay(mVideoView.getHolder());
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
        }
    };
}
