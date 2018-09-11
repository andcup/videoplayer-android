package com.andcup.android.frame.plugin.core;

import android.content.Context;

import java.io.Serializable;

/**
 * @author Amos
 * @version 2015/5/27
 */
public abstract class PluginContext<T> implements Serializable {
    /**
     * @brief 退出播放器
     *
     */
    public   abstract void           finish();

    /**
     *@brief 设置全屏
     */
    public  abstract void            setFullScreen(boolean fullScreen);

    /**
     *@brief 获取插件管理器
     */
    public  abstract PluginManager   getPluginManager();
    /**
     *@brief
     */
    public abstract Context getContext();

    public abstract T                get();

    public abstract boolean           isFullScreen();
}
