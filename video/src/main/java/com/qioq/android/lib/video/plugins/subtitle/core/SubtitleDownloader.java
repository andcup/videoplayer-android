package com.qioq.android.lib.video.plugins.subtitle.core;

import com.qioq.android.lib.video.core.model.Subtitle;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ND on 14-11-7.
 */
public class SubtitleDownloader {


    /**
     * 默认请求超时时间(秒)
     */
    private static final int REQUEST_TIMEOUT = 10;

    /**
     * 请求超时时间(毫秒)-默认值
     */
    private static final int REQUEST_TIMEOUT_MILLISECOND = REQUEST_TIMEOUT * 1000;

    /**
     * 默认响应超时时间(秒)
     */
    private static final int SO_TIMEOUT = 15;

    /**
     * 响应超时时间(毫秒)-默认值
     */
    private static final int SO_TIMEOUT_MILLISECOND = SO_TIMEOUT * 1000;

    /**
     * 响应超时时间(毫秒)
     */
    private static int SO_TIMEOUT_MILLISECOND_VALUE = SO_TIMEOUT_MILLISECOND;

    /**
     * 设定请求超时时间(毫秒)
     */
    private static int REQUEST_TIMEOUT_MILLISECOND_VALUE = REQUEST_TIMEOUT_MILLISECOND;


    public static boolean download(Subtitle subtitle, File file, OnSubtitleLoadingListener onSubtitleLoadingListener) {
        onSubtitleLoadingListener.onSubtitleLoadingStart(subtitle);
        boolean res = false;
        InputStream is = null;
        FileOutputStream fileOutputStream = null;
        try {
            BasicHttpParams httpParams = new BasicHttpParams();

            HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT_MILLISECOND_VALUE);
            HttpConnectionParams.setSoTimeout(httpParams,
                    SO_TIMEOUT_MILLISECOND_VALUE);
            HttpClient client = new DefaultHttpClient(httpParams);
            HttpGet get = new HttpGet(subtitle.getUrl());
            HttpResponse response;
            response = client.execute(get);
            HttpEntity entity = response.getEntity();
            long length = entity.getContentLength();
            is = entity.getContent();
            if (is != null) {
                fileOutputStream = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int ch = -1;
                int readed = 0;
                while ((ch = is.read(buf)) != -1) {
                    fileOutputStream.write(buf, 0, ch);
                    readed+= ch;
                    onSubtitleLoadingListener.onSubtitleLoading(subtitle, (int) (readed* 100 / length));
                }
            }
            res = true;
        } catch (Exception e) {
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                }
            if (fileOutputStream
                    != null)
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                }
        }
        return res;
    }

}
