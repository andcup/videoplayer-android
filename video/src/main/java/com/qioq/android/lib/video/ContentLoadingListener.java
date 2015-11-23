package com.qioq.android.lib.video;

import com.qioq.android.lib.video.core.NotificationService;
import com.qioq.android.lib.video.core.listener.OnContentLoadingListener;
import com.qioq.android.lib.video.core.model.Video;
import java.util.List;

/**
 * Created by Amos on 2015/7/8.
 */
public class ContentLoadingListener implements OnContentLoadingListener<Video> {

    private String mAppId;

    public ContentLoadingListener(String appId){
        mAppId = appId;
    }

    @Override
    public void onContentLoadingStart() {
        NotificationService.get().onContentLoadingStart();
    }

    @Override
    public void onContentLoading(int progress) {
        NotificationService.get().onContentLoading(progress);
    }

    @Override
    public void onContentLoadingFailed(Exception e) {
        NotificationService.get().onContentLoadingFailed(e);
    }

    @Override
    public void onContentLoadingComplete(List<Video> videoList) {
        NotificationService.get().onContentLoadingComplete(videoList);
    }
}
