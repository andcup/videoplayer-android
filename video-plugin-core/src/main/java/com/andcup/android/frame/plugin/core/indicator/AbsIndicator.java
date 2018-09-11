package com.andcup.android.frame.plugin.core.indicator;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.andcup.android.frame.plugin.core.Plugin;

/**
 * Created by Administrator on 2015/6/26.
 */
public abstract class AbsIndicator {
    public static final int INVALID_LAYOUT_ID = 0XFFFFFFFF;

    private boolean mVisible = true;

    public boolean          getVisibleOnCurrentMode(){
        return mVisible;
    }

    public int              genLayoutId(Plugin plugin){
        return INVALID_LAYOUT_ID;
    }
    public final boolean    onVisibleOnModeChanged(Plugin plugin){
         mVisible = getVisibleOnModeChanged(plugin);
        return mVisible;
    }

    public  boolean      isVisibleAlwaysIfNeed(){
        return false;
    }

    protected abstract boolean getVisibleOnModeChanged(Plugin plugin);

    protected Point getWindowSize(Plugin plugin){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) plugin.getPluginContext().getContext().getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        return new Point(dm.widthPixels, dm.heightPixels);
    }
}
