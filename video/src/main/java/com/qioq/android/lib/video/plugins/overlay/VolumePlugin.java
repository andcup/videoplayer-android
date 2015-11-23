package com.qioq.android.lib.video.plugins.overlay;

import android.view.MotionEvent;
import android.widget.SeekBar;
import com.qioq.android.artemis.piece.core.PluginContext;
import com.qioq.android.artemis.piece.core.model.PluginEntry;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.core.NotificationService;
import com.qioq.android.lib.video.core.system.Volume;
import com.qioq.android.lib.video.plugins.overlay.base.BaseGesturePlugin;

/**
 * Created by Amos on 2015/7/9.
 */
public class VolumePlugin extends BaseGesturePlugin{

    private Volume mVolume;
    private int mCurrentVolume;
    private int mMaxVolume;

    private SeekBar mSbVolume;

    public VolumePlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
        mVolume   = new Volume(getContext());
    }

    @Override
    public void onCreated() {
        super.onCreated();
        mSbVolume = findViewById(R.id.sb_volume);
        mSbVolume.setMax(mMaxVolume);
    }

    @Override
    public void onVolumeChangeStart(int volume) {
        super.onVolumeChangeStart(volume);
        if( null != mSbVolume){
            mSbVolume.setProgress(volume);
        }
    }

    @Override
    public void onVolumeChange(int volume) {
        super.onVolumeChange(volume);
        if( null != mSbVolume){
            mSbVolume.setProgress(volume);
        }
    }

    @Override
    public boolean onGestureProgress(MotionEvent e1, MotionEvent e2, float vX, float vY) {
        if(!mIsOnGesture){
            if(mIsGestureDetector){
                return false;
            }
            if(onVolumeChangedStart(e1, e2)){
                mIsOnGesture = true;
                mIsGestureDetector = true;
                mCurrentVolume = mVolume.getVolume();
                mMaxVolume     = mVolume.getMaxVolume();
                show();
                NotificationService.get().onVolumeChangeStart(mCurrentVolume);
            }
        }else{
            int yMoved = (int) (e2.getY() - e1.getY());
            setVolume(yMoved);
        }
        return mIsOnGesture;
    }

    private void setVolume( int yMoved ){
        float volumeChanged =  -1 * yMoved * mMaxVolume / (float) getAppHeight();
        float volume = (mCurrentVolume + volumeChanged);
        //获取音量最大值.(耳机和扬声器的音量范围不同)
        int maxVolume = mVolume.getMaxVolume();
        if (volume < 0) {
            volume = 0;
        }
        if (volume > maxVolume) {
            volume = maxVolume;
        }
        getVideoPlayer().setVolume(volume);
        NotificationService.get().onVolumeChange((int) volume);
    }

    @Override
    public void onGestureEnd(MotionEvent event) {
        NotificationService.get().onVolumeChangeEnd();
        super.onGestureEnd(event);
    }

    private boolean onVolumeChangedStart(MotionEvent e1, MotionEvent e2){
        int x = (int) (e2.getX() - e1.getX());
        int y = (int) (e2.getY() - e1.getY());

        int absX = Math.abs(x);
        int absY = Math.abs(y);
        if( absX < absY && absY > GESTURE_MIN_STEP ){
            if( Math.abs(e1.getX()) > getAppWidth() * 2 / 3){
                return true;
            }
        }
        return false;
    }
}
