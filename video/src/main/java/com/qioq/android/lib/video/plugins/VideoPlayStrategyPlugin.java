package com.qioq.android.lib.video.plugins;

import android.app.Activity;
import android.content.SharedPreferences;
import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.model.PluginEntry;
import com.qioq.android.lib.video.VideoPlayer;
import com.qioq.android.lib.video.VideoPlugin;
import com.qioq.android.lib.video.core.listener.OnCheckerListener;
import com.qioq.android.lib.video.core.model.Quality;
import com.qioq.android.lib.video.core.model.Video;
import com.qioq.android.lib.video.tools.Locker;

import java.util.List;

/**
 * Created by Amos on 2015/7/10.
 */
public class VideoPlayStrategyPlugin extends VideoPlugin implements OnCheckerListener{

    private SharedPreferences mSharedPreferences;
    private Locker mLocker;

    public VideoPlayStrategyPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
        mSharedPreferences = getContext().getSharedPreferences(VideoPlayStrategyPlugin.class.getSimpleName(), Activity.MODE_PRIVATE);
        mLocker = new Locker(getContext());
    }

    @Override
    public void onCheckStart(List<Video> videos) {

    }

    @Override
    public void onChecking(Video video) {

    }

    @Override
    public boolean onBeforeVideoPlay(Video video, long position) {
        mLocker.lock();
        return super.onBeforeVideoPlay(video, position);
    }

    @Override
    public void onVideoSeek(long seekTo) {
        super.onVideoSeek(seekTo);
        updateRecord(seekTo, false);
    }

    @Override
    public void onVideoPause() {
        super.onVideoPause();
        updateRecord(getTime(), true);
        mLocker.unlock();
    }

    private void updateRecord(long time, boolean save){
        VideoPlayer videoPlayer = getVideoPlayer();
        Video video  = videoPlayer.getActiveVideo();
        long getTime = time;
        video.setLastPosition(getTime);
        video.setLength(getLength());
        if(save){
            saveRecord(video);
        }
    }

    @Override
    public void onVideoPositionChanged() {
        super.onVideoPositionChanged();
        updateRecord(getTime(), false);
    }

    private void saveRecord(Video video){
        if(Math.abs(video.getLastPosition() - video.getLength()) <= 900){
            video.setLastPosition(0);
        }
        mSharedPreferences.edit().putLong(video.getVideoId(), video.getLastPosition()).commit();
    }

    @Override
    public void onVideoPlayStart() {
        super.onVideoPlayStart();
        Video video = getVideo();
        if(video.getLength() <= 0) {
            video.setLength(getLength());
        }
    }

    @Override
    public void onCheckEnd(List<Video> videoList) {
        if( null != videoList && videoList.size() > 0){
            for(Video video : videoList){
                long last = mSharedPreferences.getLong(video.getVideoId(), 0);
                video.setLastPosition(last);
            }
            getVideoPlayer().playVideo(getVideoByQualityIfHas(Quality.Standard, videoList));
        }
    }

    private int getVideoByQualityIfHas(Quality quality, List<Video> videoList){
        for(Video video : videoList){
            if(video.getQuality() == quality){
                return videoList.indexOf(video);
            }
        }
        return 0;
    }
}
