package com.andcup.android.frame.plugin.host.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Amos on 2015/6/25.
 */
public class SizeRelativeLayout extends RelativeLayout {

    private OnSizeChangeListener mOnSizeChangedListener;

    public SizeRelativeLayout(Context context) {
        super(context);
    }

    public SizeRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SizeRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if( null != mOnSizeChangedListener){
            mOnSizeChangedListener.onSizeChanged(w, h, oldw, oldh);
        }
    }

    public void setOnSizeChangedListener(OnSizeChangeListener onSizeChangedListener) {
        this.mOnSizeChangedListener = onSizeChangedListener;
    }

    public interface OnSizeChangeListener{
        public void onSizeChanged(int w, int h, int oldw, int oldh);
    }
}
