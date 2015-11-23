package com.qioq.android.lib.video.plugins.overlay.seek;

import android.widget.SeekBar;
import com.qioq.android.artemis.piece.core.PluginContext;
import com.qioq.android.artemis.piece.core.model.PluginEntry;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.core.ToolBar;
import com.qioq.android.lib.video.core.listener.OnGestureSeekListener;
import com.qioq.android.lib.video.VideoPlugin;

/**
 * Created by Amos on 2015/7/9.
 */
public class MiniCtrlBarPlugin extends VideoPlugin implements OnGestureSeekListener{

    private SeekBar mSbVideo;

    public MiniCtrlBarPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onCreated() {
        super.onCreated();
        mSbVideo = findViewById(R.id.sb_video);
        mSbVideo.setMax((int) (getLength() / 1000));
    }

    @Override
    public void onGestureSeekStart(long start) {
        ToolBar toolBar = getVideoPlayer().getToolBar();
        if( null == toolBar || !toolBar.isToolBarVisible()) {
            show();
        }
        if( null != mSbVideo ){
            mSbVideo.setProgress((int) (start / 1000));
        }
    }

    @Override
    public void onGestureSeek(long start, long seekTo) {
        if( null != mSbVideo ){
            mSbVideo.setProgress((int) (seekTo / 1000));
        }
    }

    @Override
    public void onGestureSeekEnd(long seekTo) {
        hide();
    }
}
