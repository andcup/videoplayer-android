package com.qioq.android.lib.video.core.listener;

import android.view.MotionEvent;

/**
 * Created by Amos on 2015/7/9.
 */
public interface OnGestureListener {

    public void    onGestureStart(MotionEvent event);

    public boolean onGestureProgress(MotionEvent e1, MotionEvent e2, float vX, float vY);

    public boolean onGestureSingleTab(MotionEvent event);

    public void    onGestureEnd(MotionEvent event);
}
