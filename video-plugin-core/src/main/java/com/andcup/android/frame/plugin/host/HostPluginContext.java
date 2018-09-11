package com.andcup.android.frame.plugin.host;

import android.content.Context;

import com.andcup.android.frame.plugin.IPluginApplication;
import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.PluginManager;

import java.lang.ref.SoftReference;

/**
 * Created by Administrator on 2015/7/8.
 */
public class HostPluginContext<T> extends PluginContext<T> {

    SoftReference<IPluginApplication> mPluginApplication;

    public HostPluginContext(SoftReference<IPluginApplication> pluginApplication){
        mPluginApplication = pluginApplication;
    }

    @Override
    public void finish() {
        mPluginApplication.get().finish();
    }

    @Override
    public void setFullScreen(boolean fullScreen) {
        mPluginApplication.get().setFullScreen(fullScreen);
    }

    @Override
    public PluginManager getPluginManager() {
        return mPluginApplication.get().getPluginManager();
    }

    @Override
    public Context getContext() {
        return mPluginApplication.get().getContext();
    }

    @Override
    public T get() {
        return (T) mPluginApplication.get();
    }

    @Override
    public boolean isFullScreen() {
        return mPluginApplication.get().isFullScreen();
    }
}
