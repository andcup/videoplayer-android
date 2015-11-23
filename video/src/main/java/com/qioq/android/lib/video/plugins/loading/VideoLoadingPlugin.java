package com.qioq.android.lib.video.plugins.loading;

import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.widget.TextView;
import com.qioq.android.artemis.piece.core.PluginContext;
import com.qioq.android.artemis.piece.core.model.PluginEntry;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.tools.RateConvert;

/**
 * Created by Amos on 2015/7/10.
 */
public class VideoLoadingPlugin extends BaseLoadingPlugin {

    private UserGuideLooper mUserLooper = new UserGuideLooper();
    private ImageView   mIvLoading;
    private TextView    mTvLoading;
    private TextView    mTvUserGuide;

    public VideoLoadingPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onCreated() {
        super.onCreated();
        mIvLoading  = findViewById(R.id.iv_loading);
        mTvLoading  = findViewById(R.id.tv_loading);
        mTvUserGuide = findViewById(R.id.tv_guide);
        formatRate(0.0f);
        mIvLoading.setBackgroundResource(R.anim.video_loading);
        AnimationDrawable drawable = (AnimationDrawable) mIvLoading.getBackground();
        drawable.start();
    }

    @Override
    public void onVideoLoadingRate(float rate) {
        super.onVideoLoadingRate(rate);
        formatRate(rate);
    }

    @Override
    public void onResume() {
        super.onResume();
        if( null != mTvUserGuide){
            mTvUserGuide.setText(mUserLooper.loopNext(getContext()));
        }
    }

    private void formatRate(float rate){
        if( null != mTvLoading) {
            try{
                mTvLoading.setText(RateConvert.format(getContext(), rate));
            }catch (Exception e){

            }
        }
    }
}
