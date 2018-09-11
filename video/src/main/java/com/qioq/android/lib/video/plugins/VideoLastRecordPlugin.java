package com.qioq.android.lib.video.plugins;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.model.PluginEntry;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.VideoPlugin;
import com.qioq.android.lib.video.core.model.Video;
import com.qioq.android.lib.video.engine.tools.TimeUtils;

/**
 * Created by Amos on 2015/7/29.
 */
public class VideoLastRecordPlugin extends VideoPlugin implements View.OnClickListener{

    private TextView    mTvLastRecord;
    private ImageButton mBtnClose;
    private long        mLastPosition;
    private boolean     mFirstStart = true;

    public VideoLastRecordPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onCreated() {
        super.onCreated();
        mTvLastRecord = findViewById(R.id.tv_record);
        mBtnClose     = findViewById(R.id.btn_close);
        mBtnClose.setOnClickListener(this);
        String format = getContext().getResources().getString(R.string.seek_last);
        String value = String.format(format , TimeUtils.millisToString(mLastPosition, false));
        mTvLastRecord.setText(value);
    }

    @Override
    public boolean onBeforeVideoPlay(Video video, long position) {
        if(position >= 2000 && mFirstStart){
            mFirstStart = true;
            mLastPosition = position;
            show();
        }
        return super.onBeforeVideoPlay(video, position);
    }

    @Override
    public void onVideoPositionChanged() {
        super.onVideoPositionChanged();
        hide();
    }

    @Override
    public void onClick(View view) {
        hide();
    }
}
