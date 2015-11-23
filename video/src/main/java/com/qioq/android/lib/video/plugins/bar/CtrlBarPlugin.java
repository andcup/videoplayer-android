package com.qioq.android.lib.video.plugins.bar;

import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.core.NotificationService;
import com.qioq.android.lib.video.core.listener.OnGestureSeekListener;
import com.qioq.android.lib.video.core.listener.OnToolBarListener;
import com.qioq.android.lib.video.core.listener.OnVideoTryListener;
import com.qioq.android.lib.video.core.model.Video;
import com.qioq.android.lib.video.engine.model.VideoState;
import com.qioq.android.lib.video.engine.tools.TimeUtils;
import com.qioq.android.lib.video.VideoPlugin;
import com.qioq.android.lib.video.tools.StringFormat;
import com.qioq.android.artemis.piece.core.PluginContext;
import com.qioq.android.artemis.piece.core.model.PluginEntry;

/**
 * Created by Amos on 2015/7/7.
 */
public class CtrlBarPlugin extends VideoPlugin implements
        OnGestureSeekListener,
        View.OnClickListener,
        SeekBar.OnSeekBarChangeListener ,
        OnToolBarListener,
        OnVideoTryListener{

    private boolean     mOnSeekListener = false;
    private ImageButton mBtnCtrl;
    private SeekBar     mSbVideo;
    private TextView    mTvTime;

    public CtrlBarPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onCreated() {
        super.onCreated();
        mBtnCtrl = findViewById(R.id.btn_ctrl);
        mSbVideo = findViewById(R.id.sb_video);
        mTvTime  = findViewById(R.id.tv_time);

        setTime(getTime());
        setProgress(getTime());
        setCtrl();
        mBtnCtrl.setOnClickListener(this);
        mSbVideo.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onVideoPrepare(VideoState videoState) {
        super.onVideoPrepare(videoState);
        setCtrl();
    }

    @Override
    public void onVideoPlayStart() {
        super.onVideoPlayStart();
        setCtrl();
    }

    @Override
    public void onVideoPositionChanged() {
        if(!mOnSeekListener){
            setTime(getTime());
            setProgress(getTime());
        }
    }

    @Override
    public void onVideoPause() {
        super.onVideoPause();
        setCtrl();
    }

    @Override
    public void onGestureSeekStart(long start) {
        mOnSeekListener = true;
    }

    @Override
    public void onGestureSeek(long start, long seekTo) {
        setTime(seekTo);
        setProgress(seekTo);
    }

    @Override
    public void onGestureSeekEnd(long seekTo) {
        mOnSeekListener = false;
    }

    private void setCtrl(){
        if( null == mBtnCtrl || getVideoPlayer() == null){
            return;
        }
        switch (getVideoPlayer().getVideoState()){
            case Preparing:
            case Playing:
                mBtnCtrl.setImageResource(R.drawable.video_pause_selector);
                break;
            case Pause:
                mBtnCtrl.setImageResource(R.drawable.video_play_selector);
                break;
            case Finish:
                mBtnCtrl.setImageResource(R.drawable.video_replay_selector);
                break;
        }

    }

    private void setProgress(long progress){
        if( null == mSbVideo){
            return;
        }
        if(progress > getLength()){
            progress = getLength();
        }
        mSbVideo.setProgress((int) (progress/1000));
        mSbVideo.setMax((int) (getLength() / 1000));
    }

    private void setTime(long time){
        if( null == mTvTime){
            return;
        }
        if(time > getLength()){
            time = getLength();
        }
        String currentTime = TimeUtils.millisToString(time, false);
        String totalLength = TimeUtils.millisToString(getLength(), false);
        SpannableStringBuilder value = StringFormat.formatTime(getContext(), currentTime, totalLength, " / ");
        mTvTime.setText(value);
    }

    @Override
    public void onVideoSeek(long seekTo) {
        super.onVideoSeek(seekTo);
        setProgress(seekTo);
    }

    @Override
    public void onClick(View view) {
        VideoState videoState = getVideoPlayer().getVideoState();
        switch (videoState){
            case Preparing:
            case Playing:
                pause();
                break;
            case Pause:
                play();
                break;
            case Finish:
                Video video = getVideo();
                if((video.getLastPosition() - video.getLength()) <= 1000){
                    replay(0);
                }else{
                    replay(video.getLastPosition());
                }
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if(b){
            setTime(i * 1000);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mOnSeekListener = true;
        NotificationService.get().onToolBarActionStart();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mOnSeekListener = false;
        if(getVideoPlayer().getVideoState() != VideoState.Finish){
            if(seekTo(seekBar.getProgress() * 1000) >= 0) {
                play();
            }
        }else{
            replay(seekBar.getProgress() * 1000);
        }
        NotificationService.get().onToolBarActionEnd();
    }

    @Override
    public void onToolBarActionStart() {

    }

    @Override
    public void onToolBarStateChanged(boolean visible) {
        if(visible){
            show();
        }else{
            hide();
        }
    }

    @Override
    public void onToolBarActionEnd() {

    }

    @Override
    public boolean onBeforeTryPlay(Video video, long position) {
        return false;
    }

    @Override
    public void onTryPlay(Video video, long position, boolean isTry) {
        if(!isTry){
            setCtrl();
        }
    }
}
