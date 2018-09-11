package com.andcup.android.frame.plugin.core;

import android.support.v4.app.FragmentManager;

import com.andcup.android.frame.plugin.core.model.Mode;

/**
 * @author Amos
 * @version 2015/5/28
 */
public interface IPluginManager {

    public void   loadPlugins(FragmentManager fragmentManager, int containerId);

    public Plugin getPlugin(String pluginId);

    public Plugin getEntryPlugin();

    public void   setMode(Mode mode);

    public void   onAppStart();

    public void   onAppDestroy();
}
