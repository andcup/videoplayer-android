package com.qioq.android.lib.video.plugins.entry;

import android.graphics.Color;
import android.view.ViewGroup;
import com.qioq.android.artemis.piece.core.PluginContext;
import com.qioq.android.artemis.piece.core.model.Mode;
import com.qioq.android.artemis.piece.core.model.PluginEntry;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.core.listener.OnFullScreenListener;
import com.qioq.android.lib.video.engine.widget.VideoView;
import com.qioq.android.lib.video.VideoPlugin;

/**
 * Created by Amos on 2015/7/7.
 */
public class ContentPlugin extends VideoPlugin implements OnFullScreenListener{

    private ViewGroup   mParentView;
    private VideoView   mVideoView;

    public ContentPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onCreated() {
        super.onCreated();
        mParentView = findViewById(R.id.fr_parent_view);
        mVideoView  = findViewById(R.id.vv_video);
        if( null != getVideoPlayer() && null != getVideoPlayer().getVideoEngine()){
            getVideoPlayer().getVideoEngine().onBindView(mVideoView, mParentView);
        }
    }


    @Override
    public void onBeforeFullScreen(boolean fullScreen) {
        mVideoView.setBackgroundColor(Color.BLACK);
    }

    @Override
    public void onModeChanged(Mode mode) {
        super.onModeChanged(mode);
        mVideoView.setBackgroundColor(Color.TRANSPARENT);
    }
}
