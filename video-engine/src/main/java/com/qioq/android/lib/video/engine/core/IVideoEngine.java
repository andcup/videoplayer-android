package com.qioq.android.lib.video.engine.core;

import android.view.ViewGroup;
import com.qioq.android.lib.video.engine.model.ScaleType;
import com.qioq.android.lib.video.engine.widget.VideoView;

/**
 * Created by Amos on 2015/7/6.
 */
public interface IVideoEngine {
    /**
     * @brief Init player.(so.)
     * */
    public  void onCreate();

    /**
     * @brief Start play media.
     * */
    public  void playVideo(String videoUrl, long position);

    /**
     * @brief Bind view.
     * */
    public  void onBindView(VideoView videoView, ViewGroup parentView);

    /**
     * @brief pause media
     * */
    public  void onPause();

    /**
     * @brief resume media.
     * */
    public  void onResume();

    /**
     * @brief stop media.
     * */
    public  void onStop();

    /**
     * @brief destroy media play.( release all resource. )
     * */
    public  void onDestroy();

    /**
     * @brief set media play rate.
     * */
    public  void setRate(float rate);

    /**
     * @brief set media play rate.
     * */
    public  float getRate();

    /**
     * @brief set media play scale.
     * */
    public  void        setScale(ScaleType scaleType);

    /**
     * @brief fit mode
     * */
    public  ScaleType   getScale();

    /**
     * @brief set network caching (ms)
     * */
    public  void        setNetworkCaching(int caching);

    /**
     * @brief set format
     */
    public  void        setFormat(int format);

    /**
     * @brief get caching rate.
     * */
    public  float       getCacheRate();
}
