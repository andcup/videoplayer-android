package com.qioq.android.lib.video.plugins.bar;

import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.core.NotificationService;
import com.qioq.android.lib.video.core.ToolBar;
import com.qioq.android.lib.video.core.listener.OnGestureListener;
import com.qioq.android.lib.video.core.listener.OnToolBarListener;
import com.qioq.android.lib.video.VideoPlugin;
import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.model.PluginEntry;

/**
 * Created by Amos on 2015/7/24.
 */
public class ToolBarPlugin extends VideoPlugin implements OnGestureListener, OnToolBarListener, ToolBar{

    private static final int AUTO_HIDE_DELAY = 4000;

    public ToolBarPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    private Handler mAutoHideHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try{
                if(null != getPluginContext().getPluginManager()){
                    NotificationService.get().onToolBarStateChanged(false);
                }
            }catch (Exception e){

            }
        }
    };

    public boolean isToolBarVisible(){
        VideoPlugin videoPlugin = (VideoPlugin) getPluginContext().getPluginManager().getPlugin("@+id/video_ctrl_bar");
        if( null != videoPlugin){
            return videoPlugin.isVisible();
        }
        return false;
    }

    @Override
    public int getHeight() {
        if(isFullScreen()){
            return getContext().getResources().getDimensionPixelSize(R.dimen.video_ctrl_bar_height);
        }else{
            return getContext().getResources().getDimensionPixelSize(R.dimen.video_mini_ctrl_bar_height);
        }
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
        boolean isVisible = isToolBarVisible();
        NotificationService.get().onToolBarStateChanged(!isVisible);
        if(!isVisible){
            mAutoHideHandler.removeMessages(0);
            mAutoHideHandler.sendEmptyMessageDelayed(0, AUTO_HIDE_DELAY);
        }
        return false;
    }

    @Override
    public void onGestureEnd(MotionEvent event) {

    }

    @Override
    public void onToolBarActionStart() {
        mAutoHideHandler.removeMessages(0);
    }

    @Override
    public void onToolBarStateChanged(boolean visible) {

    }

    @Override
    public void onToolBarActionEnd() {
        mAutoHideHandler.removeMessages(0);
        mAutoHideHandler.sendEmptyMessageDelayed(0, AUTO_HIDE_DELAY);
    }
}
