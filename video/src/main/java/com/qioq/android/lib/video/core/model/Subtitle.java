package com.qioq.android.lib.video.core.model;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Amos on 2015/7/7.
 */
public class Subtitle implements Serializable{

    private String mUrl;
    private String mTitle;
    private String mLocalUrl;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public boolean isExists(){
        if( null == mLocalUrl){
            return false;
        }
        File file = new File(mLocalUrl);
        return file.exists();
    }

    public String getLocalUrl() {
        return mLocalUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.mLocalUrl = localUrl;
    }
}
