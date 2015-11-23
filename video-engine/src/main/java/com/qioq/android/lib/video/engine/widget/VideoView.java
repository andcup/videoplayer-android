package com.qioq.android.lib.video.engine.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2015/7/6.
 */
public class VideoView extends SurfaceView{
    public VideoView(Context context) {
        super(context);
    }

    public VideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setFormat( int format ){
        this.getHolder().setFormat(format);
    }

    public void addCallback(SurfaceHolder.Callback callback){
        this.getHolder().addCallback(callback);
    }

    public void removeCallback(SurfaceHolder.Callback callback){
        this.getHolder().removeCallback(callback);
    }

    public void setSize(int width, int height){
        ViewGroup.LayoutParams lp =  getLayoutParams();
        lp.height = height;
        lp.width  = width;
        setLayoutParams(lp);
        invalidate();
        Log.v(VideoView.class.getSimpleName(), " width = " + width + " height = " + height);
    }
}
