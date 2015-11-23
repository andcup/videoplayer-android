package com.qioq.android.lib.video.core.system;

import android.app.Activity;
import android.provider.Settings;
import android.view.WindowManager;

/**
 * Created by Amos on 2015/7/9.
 */
public enum  Brightness {
    INSTANCE;

    private static final int BRIGHTNESS_MAX_VALUE = 255;
    private static final int BRIGHTNESS_MIN_VALUE = 5;

    public void setBrightness( Activity activity, int brightness ){
        if (brightness < BRIGHTNESS_MIN_VALUE) {
            brightness = BRIGHTNESS_MIN_VALUE;
        }
        if (brightness > BRIGHTNESS_MAX_VALUE) {
            brightness = BRIGHTNESS_MAX_VALUE;
        }
        Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.screenBrightness = brightness/(float)BRIGHTNESS_MAX_VALUE;
        activity.getWindow().setAttributes(lp);
    }

    public int getBrightness(Activity activity){
        return Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, -1);
    }

    public int getMaxValue() {
        return BRIGHTNESS_MAX_VALUE;
    }

    public int getMinValue() {
        return BRIGHTNESS_MIN_VALUE;
    }
}
