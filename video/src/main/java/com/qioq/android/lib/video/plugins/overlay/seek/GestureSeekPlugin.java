package com.qioq.android.lib.video.plugins.overlay.seek;

import android.text.SpannableStringBuilder;
import android.view.MotionEvent;
import android.widget.TextView;
import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.model.PluginEntry;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.core.NotificationService;
import com.qioq.android.lib.video.core.ToolBar;
import com.qioq.android.lib.video.core.listener.OnGestureSeekListener;
import com.qioq.android.lib.video.engine.model.VideoState;
import com.qioq.android.lib.video.engine.tools.TimeUtils;
import com.qioq.android.lib.video.plugins.overlay.base.BaseGesturePlugin;
import com.qioq.android.lib.video.tools.StringFormat;

/**
 * Created by Amos on 2015/7/9.
 */
public class GestureSeekPlugin extends BaseGesturePlugin implements OnGestureSeekListener {

    private long  mGestureSetTime = -1;
    private TextView mTvTime;

    public GestureSeekPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onCreated() {
        super.onCreated();
        mTvTime = findViewById(R.id.tv_time);
    }

    @Override
    public boolean onGestureProgress(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if(!mIsOnGesture){
            if(mIsGestureDetector){
                return false;
            }
            if(onSeekChangedStart(e1, e2)){
                mIsOnGesture = true;
                mIsGestureDetector = true;
                ToolBar toolBar = getVideoPlayer().getToolBar();
                if( null == toolBar || !toolBar.isToolBarVisible()) {
                    show();
                }
                NotificationService.get().onGestureSeekStart(getTime());
                return true;
            }
            return false;
        }else{
            int xMoved = (int) (e2.getX() - e1.getX());
            updateSeekBar(xMoved);
        }
        return true;
    }

    private boolean onSeekChangedStart(MotionEvent e1, MotionEvent e2){
        int x = (int) (e2.getX() - e1.getX());
        int y = (int) (e2.getY() - e1.getY());

        int absX = Math.abs(x);
        int absY = Math.abs(y);
        if( absX > absY && absX > GESTURE_MIN_STEP ){
            return true;
        }
        return false;
    }

    @Override
    public void onGestureEnd(MotionEvent event) {
        super.onGestureEnd(event);
        NotificationService.get().onGestureSeekEnd(mGestureSetTime);
        mGestureSetTime = -1;
    }

    private void updateSeekBar( int xMoved ){
        long moveTime  = Math.abs(xMoved) * getLength() / (getAppWidth() * 4) ;
        mGestureSetTime = (int) (getTime() + moveTime * (xMoved > 0 ? 1 : -1));
        if( mGestureSetTime > getLength() ){
            mGestureSetTime = getLength();
        }
        if( mGestureSetTime < 0){
            mGestureSetTime = 0;
        }
        NotificationService.get().onGestureSeek(getTime(), mGestureSetTime);
    }

    @Override
    public void onGestureSeekStart(long start) {
        setTime(start, start);
    }

    @Override
    public void onGestureSeek(long start, long seekTo) {
        setTime(start, seekTo);
    }

    @Override
    public void onGestureSeekEnd(long seekTo) {
        if(Math.abs(seekTo - getTime()) <= 2000 || seekTo == -1){
            return;
        }
        if(getVideoPlayer().getVideoState() != VideoState.Finish){
            if(seekTo(seekTo) > 0) {
                play();
            }
        }else{
            replay(seekTo);
        }
    }

    private void setTime(long start,long seekTo){
        if( null == mTvTime){
            return;
        }
        mTvTime.setEnabled(seekTo > start);
        String currentTime = TimeUtils.millisToString(seekTo, false);
        String totalLength = TimeUtils.millisToString(getLength(), false);
        SpannableStringBuilder value = StringFormat.formatTime(getContext(), currentTime, totalLength, " / ");
        mTvTime.setText(value);
    }
}
