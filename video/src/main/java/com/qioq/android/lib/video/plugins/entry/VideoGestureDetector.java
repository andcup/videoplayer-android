package com.qioq.android.lib.video.plugins.entry;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import com.qioq.android.lib.video.core.NotificationService;

/**
 * Created by Amos on 2015/7/9.
 */
public class VideoGestureDetector implements GestureDetector.OnGestureListener{
    private Context         mContext;
    private GestureDetector mGestureDetector;

    public VideoGestureDetector(Context context) {
        mContext = context;
        mGestureDetector = new GestureDetector(mContext, this);
    }

    public boolean onTouchEvent(MotionEvent event){
        if( event.getAction() == MotionEvent.ACTION_UP ){
            NotificationService.get().onGestureEnd(event);
        }
        if( event.getAction() == MotionEvent.ACTION_DOWN ){
            NotificationService.get().onGestureStart(event);
        }
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return NotificationService.get().onGestureSingleTab(motionEvent);
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return NotificationService.get().onGestureProgress(motionEvent, motionEvent1, v, v1);
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}
