package com.qioq.android.lib.video.plugins.loading;

import android.content.Context;
import com.nd.hy.android.video.R;

/**
 * Created by Amos on 2015/7/10.
 */
public class UserGuideLooper {

    int mLastIndex = -1;

    public static final int USER_GUIDE[] = new int[]{
            R.string.user_guide_light,
            R.string.user_guide_volume,
            R.string.user_guide_play};

    public String loopNext(Context context){
        double random = Math.random() * 3;
        int index = (int) random;
        if(index != mLastIndex){
            mLastIndex = index;
            return context.getResources().getString(USER_GUIDE[index]);
        }
        return loopNext(context);
    }
}
