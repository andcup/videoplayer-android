package com.qioq.android.lib.video.tools;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import com.nd.hy.android.video.R;

/**
 * Created by ND on 14-8-19.
 */
public class StringFormat {

    public static SpannableStringBuilder formatTime(Context context, String time, String length, String sep){
        String value = time + sep + length;
        int index = value.indexOf(sep);
        SpannableStringBuilder style = new SpannableStringBuilder(value);
        style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.video_duration_font)), 0, index, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.video_time_font)), index, value.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
    }

    public static SpannableStringBuilder formatLast(Context context, String format, String time){
        String value = String.format(format, time);
        int index = value.indexOf(time);
        SpannableStringBuilder style = new SpannableStringBuilder(value);
        style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.video_time_font)), 0, time.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.video_duration_font)), index, index + time.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
    }

    public static SpannableStringBuilder formatCacheRate(Context context, String format, String cacheRate){
        String value = String.format(format, cacheRate);
        int index = value.indexOf(cacheRate);
        SpannableStringBuilder style = new SpannableStringBuilder(value);
        //style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.media_video_time_font)), 0, cacheRate.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.video_duration_font)), index, index + cacheRate.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
    }

    public static SpannableStringBuilder formatExercise(Context context, String format, String position){
        String value = String.format(format, position);
        int index = value.indexOf(position);
        SpannableStringBuilder style = new SpannableStringBuilder(value);
        style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.video_duration_font)), 0, value.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
    }
}
