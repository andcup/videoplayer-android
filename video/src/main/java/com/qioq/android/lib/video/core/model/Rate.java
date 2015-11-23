package com.qioq.android.lib.video.core.model;

import com.nd.hy.android.video.R;

/**
 * Created by Amos on 2015/7/30.
 */
public enum  Rate {

    Rate100X(1, R.string.play_rate_100X),
    Rate125X(2, R.string.play_rate_125X),
    Rate150X(3, R.string.play_rate_150X);

    int mId;
    int mResourceId;

    Rate(int id, int resourceId) {
        mId         = id;
        mResourceId = resourceId;
    }

    public int getResourceId() {
        return mResourceId;
    }

    public int getId(){
        return mId;
    }
}
