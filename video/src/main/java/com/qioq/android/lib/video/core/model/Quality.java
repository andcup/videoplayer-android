package com.qioq.android.lib.video.core.model;

import com.nd.hy.android.video.R;

import java.io.Serializable;

/**
 * Created by Amos on 2015/7/8.
 */
public enum Quality implements Serializable{
    Low(0, R.string.quality_lower),
    Smooth(1, R.string.quality_smooth),
    Standard(2, R.string.quality_standard),
    HD(3, R.string.quality_high),
    SD(4, R.string.quality_super_high);

    int mId;
    int mResourceId;

    Quality(int id, int resourceId) {
        mId         = id;
        mResourceId = resourceId;
    }

    public int getResourceId() {
        return mResourceId;
    }

    public int getId() {
        return mId;
    }

    public static Quality map(int quality){
        switch (quality){
            case 0:
                return Low;
            case 1:
                return Smooth;
            case 2:
                return Standard;
            case 3:
                return HD;
            case 4:
                return SD;
            default:
                return Standard;
        }
    }
}
