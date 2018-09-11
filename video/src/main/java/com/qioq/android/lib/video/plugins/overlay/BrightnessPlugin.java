package com.qioq.android.lib.video.plugins.overlay;

import android.view.MotionEvent;
import android.widget.SeekBar;
import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.model.PluginEntry;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.core.NotificationService;
import com.qioq.android.lib.video.core.system.Brightness;
import com.qioq.android.lib.video.plugins.overlay.base.BaseGesturePlugin;

/**
 * Created by Amos on 2015/7/9.
 */
public class BrightnessPlugin extends BaseGesturePlugin {

    private int     mBrightness;
    private SeekBar mSbBrightness;

    public BrightnessPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onCreated() {
        super.onCreated();
        mSbBrightness = findViewById(R.id.sb_brightness);
        mSbBrightness.setMax(Brightness.INSTANCE.getMaxValue());
    }

    @Override
    public void onBrightnessChangeStart(int brightness) {
        super.onBrightnessChangeStart(brightness);
        if( null != mSbBrightness){
            mSbBrightness.setProgress(brightness);
        }
    }

    @Override
    public void onBrightnessChange(int brightness) {
        super.onBrightnessChange(brightness);
        if( null != mSbBrightness){
            mSbBrightness.setProgress(brightness);
        }
    }

    @Override
    public boolean onGestureProgress(MotionEvent e1, MotionEvent e2, float vX, float vY) {
        if(!mIsOnGesture ){
            if(mIsGestureDetector){
                return false;
            }
            if(onBrightnessChangedStart(e1, e2)){
                mIsOnGesture = true;
                mIsGestureDetector = true;
                mBrightness = Brightness.INSTANCE.getBrightness(getActivity());
                NotificationService.get().onBrightnessChangeStart(mBrightness);
                getVideoPlayer().setBrightness(mBrightness);
                show();
                return true;
            }
            return  false;
        }else{
            int yMoved = (int) (e2.getY() - e1.getY());
            setBrightness(yMoved);
        }
        return mIsOnGesture;
    }

    private boolean onBrightnessChangedStart(MotionEvent e1, MotionEvent e2){
        int x = (int) (e2.getX() - e1.getX());
        int y = (int) (e2.getY() - e1.getY());

        int absX = Math.abs(x);
        int absY = Math.abs(y);
        if( absX < absY && absY > GESTURE_MIN_STEP ){
            if( Math.abs(e1.getX()) < getAppWidth() / 3){
                return true;
            }
        }
        return false;
    }

    private void setBrightness( int yMoved ){
        float brightnessChanged =  -1 * yMoved * Brightness.INSTANCE.getMaxValue() / (float)getAppHeight();
        int brightness = (int) (mBrightness + brightnessChanged);
        brightness = Math.max(brightness, Brightness.INSTANCE.getMinValue());
        brightness = Math.min(brightness, Brightness.INSTANCE.getMaxValue());
        NotificationService.get().onBrightnessChange(brightness);
        getVideoPlayer().setBrightness(brightness);
    }

    @Override
    public void onGestureEnd(MotionEvent event) {
        super.onGestureEnd(event);
        NotificationService.get().onBrightnessChangeEnd();
    }
}
