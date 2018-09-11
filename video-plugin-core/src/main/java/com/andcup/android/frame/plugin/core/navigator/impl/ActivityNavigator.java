package com.andcup.android.frame.plugin.core.navigator.impl;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/2/23.
 */
public class ActivityNavigator extends Navigator<Activity> {

    public ActivityNavigator(Activity carrier) {
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
        mCarrier.startActivity(intent);
        return this;
    }

    @Override
    public Navigator finish() {
        mCarrier.finish();
        return this;
    }
}
