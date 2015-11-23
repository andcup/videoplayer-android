package com.qioq.android.lib.video.engine.vlc;

import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.ViewGroup;
import android.widget.Toast;
import com.qioq.android.lib.video.engine.core.SimpleFitSizer;
import com.qioq.android.lib.video.engine.core.AbsFitSizer;
import com.qioq.android.lib.video.engine.AbsVideoController;
import com.qioq.android.lib.video.engine.AbsVideoEngine;
import com.qioq.android.lib.video.engine.widget.VideoView;
import com.nd.hy.android.video.sdk.vlc.VLCApplication;
import com.nd.hy.android.video.sdk.vlc.libvlc.*;
import com.nd.hy.android.video.sdk.vlc.util.VLCInstance;

/**
 * Created by Amos on 2015/7/6.
 */
public class VLCEngine extends AbsVideoEngine implements  LibVLC.OnNativeCrashListener, IVideoPlayer{


    private LibVLC          mLibVLC;
    private VLCController   mVLCController;
    private VLCEventHandler mHandler;
    private int             mNetworkCaching = DEFAULT_NETWORK_CACHING;
    private VideoView       mVideoView;

    public VLCEngine(Context context) {
        super(context);
    }

    @Override
    protected AbsFitSizer createFitSizer(VideoView videoView, ViewGroup viewGroup) {
        mVideoView = videoView;
        videoView.addCallback(mSurfaceCallback);
        return new SimpleFitSizer(videoView, viewGroup);
    }

    @Override
    protected AbsVideoController createVideoController() {
        mVLCController = new VLCController(mLibVLC);
        return mVLCController;
    }

    @Override
    protected void onCreateVideoSdk() {
        initVLCSdk();
    }

    @Override
    protected void onDestroyVideoSdk() {
        if( null != mLibVLC ){
            mLibVLC.eventVideoPlayerActivityCreated(false);
//            mLibVLC.detachSurface();
            mLibVLC.stop();
            EventHandler.getInstance().removeHandler(mHandler);
        }
        mLibVLC = null;
    }

    @Override
    protected void onVideoPrepare(long position) {
        if( null != mHandler ) {
            Message message = new Message();
            message.what = EventHandler.MediaPlayerPlayPrepare;
            message.arg1 = (int) position;
            mHandler.sendMessage(message);
        }
    }

    @Override
    public void playVideo(String url, long position) {
        super.playVideo(url, position);
        if( null != mLibVLC){
            mLibVLC.getMediaList().clear();
            mLibVLC.getMediaList().add(new Media(mLibVLC, getUri(url)), false);
            mLibVLC.playIndex(0);
            mLibVLC.setTime(position);
        }
    }

    private void initVLCSdk(){
        try {
            VLCApplication.initVLCApp(getContext());
            mLibVLC = VLCInstance.getLibVlcInstance();
            mLibVLC.setNetworkCaching(mNetworkCaching);
            mLibVLC.setOnNativeCrashListener(this);
            //取消硬件解码。部分设备硬件解码容易死机
            mLibVLC.setHardwareAcceleration(LibVLC.HW_ACCELERATION_DISABLED);

            mHandler = new VLCEventHandler(this);
            EventHandler.getInstance().addHandler(mHandler);
            mLibVLC.eventVideoPlayerActivityCreated(true);
        } catch (LibVlcException e) {
            Toast toast = Toast.makeText(getContext(), "Init VLC Engine Failed.", Toast.LENGTH_SHORT);
            toast.getView().setBackgroundColor(0xcc1D3566);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
            mLibVLC = null;
        }
    }

    private  SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if(null != mLibVLC){
                mLibVLC.attachSurface(holder.getSurface(), VLCEngine.this);
            }
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if(null != mLibVLC){
                mLibVLC.detachSurface();
            }
            Log.v(VLCEngine.class.getSimpleName(), "surfaceDestroyed");
        }
    };

    private String getUri(String file){
        if( null == file ){
            return null;
        }
        if(file.startsWith("/")){
            file = "file://" + file;
        }
        return file;
    }

    @Override
    public void onNativeCrash() {

    }

    @Override
    public void setSurfaceSize(int videoWidth, int videoHeight, int videoVisibleWidth, int videoVisibleHeight, int sar_num, int sar_den) {
        getFitSizer().setSize(videoWidth, videoHeight, videoVisibleWidth, videoVisibleHeight);
    }

    public LibVLC getLibVLC() {
        return mLibVLC;
    }
}
