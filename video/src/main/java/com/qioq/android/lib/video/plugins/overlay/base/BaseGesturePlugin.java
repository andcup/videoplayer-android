package com.qioq.android.lib.video.plugins.overlay.base;

import android.view.MotionEvent;
import com.qioq.android.artemis.piece.core.PluginContext;
import com.qioq.android.artemis.piece.core.model.PluginEntry;
import com.qioq.android.lib.video.core.listener.OnGestureListener;
import com.qioq.android.lib.video.VideoPlugin;

/**
 * Created by Amos on 2015/7/9.
 */
public class BaseGesturePlugin extends VideoPlugin implements OnGestureListener {

    protected static final int GESTURE_MIN_STEP   = 50;
    protected boolean         mIsOnGesture        = false;
    protected static boolean  mIsGestureDetector  = false;

    public BaseGesturePlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onGestureStart(MotionEvent event) {

    }

    @Override
    public boolean onGestureProgress(MotionEvent e1, MotionEvent e2, float vX, float vY) {
        return false;
    }

    @Override
    public boolean onGestureSingleTab(MotionEvent event) {
        return false;
    }

    @Override
    public void onGestureEnd(MotionEvent event) {
        if(mIsOnGesture){
            mIsGestureDetector = false;
            mIsOnGesture = false;
        }
        hide();
    }
}
