package com.andcup.android.frame.plugin.core.base;

import android.content.Context;

import com.andcup.android.frame.plugin.IPluginApplication;
import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.model.PluginEntry;

import java.lang.ref.SoftReference;

/**
 * @author Amos
 * @version 2015/5/27
 */
public abstract class AbsPlugin implements IPluginLoad{

    private PluginEntry mPluginEntry;
    private SoftReference<PluginContext> mPluginContext;

    public AbsPlugin(PluginContext pluginContext, PluginEntry pluginEntry){
        mPluginContext = new SoftReference<PluginContext>(pluginContext);
        mPluginEntry   = pluginEntry;
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onUnLoad() {

    }

    public abstract void show();

    public abstract void hide();

    public abstract void show(boolean newView);

    public abstract boolean isVisible();

    protected Context getContext(){
        return mPluginContext.get().getContext();
    }

    public PluginEntry getPluginEntry() {
        return mPluginEntry;
    }

    public String getId(){
        return mPluginEntry.getId();
    }

    public PluginContext getPluginContext(){
        return mPluginContext.get();
    }

    public String getAppId(){
        return ((IPluginApplication)getPluginContext().get()).getAppId();
    }
}
