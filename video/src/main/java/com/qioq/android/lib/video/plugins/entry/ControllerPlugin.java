package com.qioq.android.lib.video.plugins.entry;

import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.model.PluginEntry;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.VideoPlugin;

/**
 * Created by Amos on 2015/7/7.
 */
public class ControllerPlugin extends VideoPlugin implements View.OnTouchListener{

    private RelativeLayout       mReControllerPanel;
    private VideoGestureDetector mVideoGestureDetector;

    public ControllerPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onCreated() {
        super.onCreated();
        mVideoGestureDetector = new VideoGestureDetector(getContext());
        mReControllerPanel    = findViewById(R.id.re_controller);
        mReControllerPanel.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return mVideoGestureDetector.onTouchEvent(motionEvent);
    }
}
