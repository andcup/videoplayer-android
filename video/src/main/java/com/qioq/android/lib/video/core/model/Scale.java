package com.qioq.android.lib.video.core.model;

import com.nd.hy.android.video.R;

/**
 * Created by Amos on 2015/7/30.
 */
public enum  Scale {
    Scale_Original(1, R.string.view_original),
    Scale_4_3(2, R.string.view_4_3),
    Scale_16_9(3, R.string.view_16_9),
    Scale_fill(4, R.string.view_fill);


    int mId;
    int mResourceId;

    Scale(int id, int resourceId) {
        mId         = id;
        mResourceId = resourceId;
    }

    public int getId(){
        return mId;
    }

    public int getResourceId() {
        return mResourceId;
    }
}
