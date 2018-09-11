package com.qioq.android.lib.video.plugins;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.model.PluginEntry;
import com.qioq.android.lib.video.VideoPlugin;
import com.qioq.android.lib.video.core.NotificationService;
import com.qioq.android.lib.video.core.model.Video;
import com.qioq.android.lib.video.tools.VideoChecker;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Amos on 2015/7/10.
 */
public class VideoCheckerPlugin extends VideoPlugin {

    private CheckerTask mCheckerTask;
    private String      mCacheHost;

    public VideoCheckerPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onContentLoadingComplete(List<Video> videoList) {
        super.onContentLoadingComplete(videoList);
        if( null == videoList ){
            return;
        }
        Iterator<Video> videoIterator = videoList.iterator();
        while (videoIterator.hasNext()){
            Video video = videoIterator.next();
            if(video.getType() == Video.Type.Mp4){
                videoList.remove(video);
            }
        }
        if( null != mCheckerTask){
            mCheckerTask.cancel(true);
        }
        mCheckerTask = null;
        mCheckerTask = new CheckerTask(videoList);
        mCheckerTask.execute();
    }

    @Override
    public void onAppDestroy() {
        super.onAppDestroy();
        if(null != mCheckerTask){
            mCheckerTask.cancel(true);
        }
        mCheckerTask = null;
    }

    class CheckerTask extends AsyncTask<String, Integer, List<Video>>{

        private List<Video> mVideoList;
        public CheckerTask(List<Video> videos){
            mVideoList = videos;
            NotificationService.get().onCheckStart(mVideoList);
        }

        @Override
        protected List<Video> doInBackground(String... strings) {
            try{
                List<Video> videoList = new ArrayList<>();
                for(Video video: mVideoList){
                    String url = video.getVideoUrl();
                    if(TextUtils.isEmpty(url)){
                        continue;
                    }
                    Log.v(VideoCheckerPlugin.class.getSimpleName(), "video checking : " + url);
                    if(url.startsWith("http://")){
                        if( TextUtils.isEmpty(mCacheHost)){
                            try{
                                String host = VideoChecker.check(video);
                                if(!TextUtils.isEmpty(host)){
                                    int endIndex = host.lastIndexOf('/');
                                    mCacheHost = host.substring(0, endIndex);
                                    videoList.add(video);
                                }
                            }catch (Exception e){

                            }
                            continue;
                        }
                        NotificationService.get().onChecking(video);
                        //
                        if(hasSameQualityVideo(videoList, video)){
                            continue;
                        }
                        if(video.getVideoUrl().contains(mCacheHost)){
                            videoList.add(video);
                        }
                    }else{
                        File file = new File(url);
                        if(file.exists()){
                            videoList.add(video);
                        }
                    }
                }
                return videoList;
            }catch (Exception e){

            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Video> videos) {
            super.onPostExecute(videos);
            mCheckerTask = null;
            try{
                getVideoPlayer().getVideoList().clear();
                getVideoPlayer().getVideoList().addAll(videos);
                NotificationService.get().onCheckEnd(videos);
            }catch (Exception e){

            }
        }

        private boolean hasSameQualityVideo(List<Video> videos, Video video){
            for(Video video1 : videos){
                if(video1.getQuality() == video.getQuality()){
                    return true;
                }
            }
            return false;
        }
    }
}
