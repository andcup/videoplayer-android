package com.qioq.android.lib.video.tools;

import android.content.Context;
import android.util.Log;
import com.nd.hy.android.video.R;

/**
 * Created by Amos on 2015/7/29.
 */
public class RateConvert {

    public static String format(Context context, float rate){
        int   m = (int) (rate / 1042);
        float k = rate % 1024;
        String mAndK = String.valueOf(m+k);
        String value = mAndK.substring(0, mAndK.length() > 4? 4 : mAndK.length());
        if(m > 0 && m < 10){
            mAndK = String.valueOf(m + k / 1000);
            int pointIndex = mAndK.indexOf(".");
            value = mAndK.substring(0, mAndK.length() > pointIndex + 3? pointIndex + 3 : mAndK.length());
            value += " M/s";
        }else{
            value += " KB/s";
        }
        Log.v(RateConvert.class.getSimpleName(), "rate = "+rate + " m = " + m + " k = " + k + " value = " + value);
        return String.format(context.getResources().getString(R.string.loading_rate), value);
    }
}
