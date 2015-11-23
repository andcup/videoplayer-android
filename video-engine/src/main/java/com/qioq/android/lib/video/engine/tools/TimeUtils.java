package com.qioq.android.lib.video.engine.tools;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Amos on 2015/7/9.
 */
public class TimeUtils {
    public static String millisToString(long millis, boolean text) {
        boolean negative = millis < 0;
        millis = Math.abs(millis);

        millis /= 1000;
        int sec = (int) (millis % 60);
        millis /= 60;
        int min = (int) (millis % 60);
        millis /= 60;
        int hours = (int) millis;

        String time;
        DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        format.applyPattern("00");
        if (text) {
            if (millis > 0)
                time = (negative ? "0" : "") + hours + "h" + format.format(min) + "min";
            else if (min > 0)
                time = (negative ? "0" : "") + min + "min";
            else
                time = (negative ? "0" : "") + sec + "s";
        }
        else {
            if (millis > 0)
                time = (negative ? "0" : "") + hours + ":" + format.format(min) + ":" + format.format(sec);
            else
                time = (negative ? "0" : "") + min + ":" + format.format(sec);
        }
        return time;
    }
}
