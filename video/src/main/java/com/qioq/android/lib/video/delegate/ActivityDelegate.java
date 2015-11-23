package com.qioq.android.lib.video.delegate;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.qioq.android.lib.video.VideoDelegate;
import com.qioq.android.lib.video.VideoPlayer;

/**
 * Created by Amos on 2015/6/24.
 */
public class ActivityDelegate extends VideoDelegate<FragmentActivity> {

    public ActivityDelegate(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public void setFullScreen(boolean fullScreen) {
        try{
            if(fullScreen){
                appDelegate.get().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }else{
                appDelegate.get().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }catch (Exception e){

        }
    }

    @Override
    public FragmentManager getFragmentManager() {
        try{
            return appDelegate.get().getSupportFragmentManager();
        }catch (Exception e){

        }
        return null;
    }

    @Override
    public Context getContext() {
        try{
            return appDelegate.get();
        }catch (Exception e){

        }
        return  null;
    }

    @Override
    public boolean isFullScreen() {
        try{
            return appDelegate.get().getResources().getConfiguration().orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE;
        }catch (Exception e){

        }
        return false;
    }

    @Override
    public void finish(VideoPlayer videoPlayer) {
        super.finish(videoPlayer);
        try{
            appDelegate.get().finish();
        }catch (Exception e){

        }
    }
}
