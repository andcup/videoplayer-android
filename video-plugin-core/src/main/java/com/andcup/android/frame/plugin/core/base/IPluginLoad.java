package com.andcup.android.frame.plugin.core.base;

/**
 * @author Amos
 * @version 2015/5/27
 */
public interface IPluginLoad {
    /**
     * @brief Called when plugin first created.
     * */
    public void onLoad();

    /**
     * @brief Called when plugin on destroyed.
     * */
    public void onUnLoad();
}
