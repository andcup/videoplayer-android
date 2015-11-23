package com.qioq.android.lib.video.core.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Amos on 2015/6/30.
 */
public class BaseFragmentActivity extends FragmentActivity {

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("android:support:fragments", null);
    }
}
