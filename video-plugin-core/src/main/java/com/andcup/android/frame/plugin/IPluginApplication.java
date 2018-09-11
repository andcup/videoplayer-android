package com.andcup.android.frame.plugin;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.andcup.android.frame.plugin.core.PluginManager;

/**
 * Created by Amos on 2015/7/3.
 */
public abstract class IPluginApplication {
    public abstract String getAppId();

    public abstract Context getContext();

    public abstract PluginManager getPluginManager();

    public abstract FragmentManager getFragmentManager();

    public abstract void setFullScreen(boolean fullScreen);

    public abstract boolean isFullScreen();

    public abstract void finish();
}
