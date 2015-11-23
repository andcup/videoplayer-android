package com.qioq.android.lib.video.tools;

import android.util.Log;
import com.qioq.android.lib.video.core.model.Video;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Amos on 2015/7/10.
 */
public class VideoChecker {

    private static final String TAG = VideoChecker.class.getName();

    private static final int    CONNECT_TIME_OUT              = 30000;
    private static final int    READ_TIME_OUT                 = 30000;
    private static final String CORRECT_STREAM_CONNECTION_TYPE = "application/octet-stream";
    private static final String CORRECT_VIDEO_CONNECTION_TYPE = "video";
    private static final String CORRECT_AUDIO_CONNECTION_TYPE = "audio";

    public static String check(Video video) {
        if (null == video) {
            return null;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            String urlStr = video.getVideoUrl();
            URL url = new URL(urlStr);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(CONNECT_TIME_OUT);
            httpURLConnection.setReadTimeout(READ_TIME_OUT);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            inputStream = httpURLConnection.getInputStream();
            String contentType = httpURLConnection.getContentType();
            if (contentType.trim().equalsIgnoreCase(CORRECT_STREAM_CONNECTION_TYPE)
                    || contentType.trim().contains(CORRECT_VIDEO_CONNECTION_TYPE)
                    || contentType.trim().contains(CORRECT_AUDIO_CONNECTION_TYPE)){
                return urlStr;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(TAG, "get video timeout.");
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            httpURLConnection.disconnect();
        }
        return null;
    }
}
