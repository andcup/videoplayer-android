package com.qioq.android.lib.video.core.listener;

import java.util.List;

/**
 * Created by Amos on 2015/7/8.
 */
public interface OnContentLoadingListener<T> {
    /**
     *@brief 开始加载
     */
    public void onContentLoadingStart();

    /**
     *@brief 下载中
     */
    public void onContentLoading(int progress);
    /**
     *@brief 下载失败
     */
    public void onContentLoadingFailed(Exception e);

    /**
     *@brief 下载成功
     */
    public void onContentLoadingComplete(List<T> contentList);
}
