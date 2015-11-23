package com.nd.hy.android.video.app;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.qioq.android.artemis.piece.PluginApplication;
import com.qioq.android.lib.video.ContentProvider;
import com.qioq.android.lib.video.VideoConfiguration;
import com.qioq.android.lib.video.VideoPlayer;
import com.qioq.android.lib.video.core.listener.OnContentLoadingListener;
import com.qioq.android.lib.video.core.model.Quality;
import com.qioq.android.lib.video.core.model.Subtitle;
import com.qioq.android.lib.video.core.model.Video;
import com.qioq.android.lib.video.delegate.ActivityDelegate;
import com.qioq.android.lib.video.plugins.subtitle.SubtitlePlayer;
import com.qioq.android.lib.video.plugins.subtitle.SubtitleProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amos on 2015/7/15.
 */
public class StartActivity extends FragmentActivity implements View.OnClickListener{
    VideoPlayer mVideoPlayer;
    FrameLayout mFrVideo;
    private boolean mIsFullScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFrVideo = (FrameLayout) findViewById(R.id.fr_video);
        setFullScreen(false);
        startPlay();
    }

    private void restartPlay(){
        if( null != mVideoPlayer){
            mVideoPlayer.finish();
            mVideoPlayer = null;
        }
    }

    private void startPlay(){
        VideoConfiguration videoConfiguration = new VideoConfiguration.Builder()
                .setPluginPath("video_configuration.xml")
                .build();

        mVideoPlayer = new VideoPlayer.Builder()
                .setAppDelegate(new ActivityDelegate(this) {
                    @Override
                    public void setFullScreen(boolean fullScreen) {
                        super.setFullScreen(fullScreen);
                        StartActivity.this.setFullScreen(fullScreen);
                    }

                    @Override
                    public boolean isFullScreen() {
                        return mIsFullScreen;
                    }

                    @Override
                    public void finish(VideoPlayer videoPlayer) {
                        if( null != mVideoPlayer){
                            mVideoPlayer.stop();
                            mVideoPlayer = null;
                        }
                    }
                })
                .setContainerId(R.id.fr_video)
                .setConfiguration(videoConfiguration)
                .build();

        mVideoPlayer.setOnVideoPlayerListener(new PluginApplication.OnApplicationListener<VideoPlayer>() {
            @Override
            public void onApplicationStart(VideoPlayer videoPlayer) {
                mVideoPlayer.open(new ContentProvider() {
                    @Override
                    public void load(OnContentLoadingListener onDocLoadingListener) {
                        List<Video> videos = new ArrayList<Video>();

                        Video video = new Video();
                        video.setVideoUrl("http://192.168.10.4/test.mp4");
                        video.setVideoId("test video");
                        video.setTitle("http://192.168.10.4/test.mp4");
                        video.setQuality(Quality.Smooth);
                        videos.add(video);
                        onDocLoadingListener.onContentLoadingComplete(videos);
                    }
                });
            }

            @Override
            public void onApplicationStop(VideoPlayer videoPlayer) {
            }
        });
        mVideoPlayer.start();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setFullScreen(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE);
    }

    @Override
    public void onBackPressed() {
        if(null != mVideoPlayer && mVideoPlayer.isFullScreen()){
            mVideoPlayer.setFullScreen(false);
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected Point getWindowSize() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        return new Point(dm.widthPixels, dm.heightPixels);
    }


    private void setFullScreen(boolean fullScreen) {
        ViewGroup.LayoutParams params = mFrVideo.getLayoutParams();
        if (fullScreen) {
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            params.width = getWindowSize().x;
            params.height = params.width * 8 / 15;
        }
        mFrVideo.setLayoutParams(params);
        mIsFullScreen = fullScreen;
    }

    @Override
    public void onClick(View view) {
        restartPlay();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPlay();
            }
        }, 500);
    }
}
