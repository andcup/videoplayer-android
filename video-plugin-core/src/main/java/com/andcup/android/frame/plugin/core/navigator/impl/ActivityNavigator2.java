package com.andcup.android.frame.plugin.core.navigator.impl;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/2/23.
 */
public class ActivityNavigator2 extends Navigator<Context> {

    private int mFlag = Intent.FLAG_ACTIVITY_NEW_TASK;

    public ActivityNavigator2(Context carrier) {
        super(carrier);
    }

    @Override
    public Navigator go() {
        if (mCarrier == null || mTargetClass == null) {
            Log.e("Navigator Fail", "Activity or Target is NULL!");
            return this;
        }
        Intent intent = new Intent(mCarrier, mTargetClass);
        if (mBundle != null) {
            intent.putExtras(mBundle);
        }
        intent.addFlags(mFlag);
        mCarrier.startActivity(intent);
        return this;
    }

    public void addFlag(int flag){
        mFlag |= flag;
    }

    @Override
    public Navigator finish() {
        return this;
    }
}
