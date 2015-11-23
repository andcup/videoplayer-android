package com.qioq.android.lib.video;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import java.lang.ref.SoftReference;

/**
 * Created by Amos on 2015/7/7.
 */
public abstract class VideoDelegate<T> {
    protected SoftReference<T> appDelegate;

    public VideoDelegate(T t){
        appDelegate = new SoftReference<T>(t);
    }

    /**
     * @brief 退出播放器
     *
     */
    public  void  finish(VideoPlayer videoPlayer){
        videoPlayer.stop();
    }

    /**
     *@brief 设置全屏
     */
    public abstract void  setFullScreen(boolean fullScreen);

    /**
     *@brief 获取播放器配置
     */
    public abstract FragmentManager getFragmentManager();

    /**
     *@brief 获取设备上下文.
     */
    public abstract Context getContext();
    /**
     *@brief 判断是否全屏
     */
    public abstract boolean  isFullScreen();
}
