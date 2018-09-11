package com.qioq.android.lib.video.plugins.subtitle;

import android.os.AsyncTask;
import android.widget.TextView;
import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.model.PluginEntry;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.VideoPlugin;
import com.qioq.android.lib.video.core.listener.OnSubtitleChangeListener;
import com.qioq.android.lib.video.core.model.Subtitle;
import com.qioq.android.lib.video.core.model.Video;
import com.qioq.android.lib.video.plugins.subtitle.core.SubtitleParser;
import com.qioq.android.lib.video.plugins.subtitle.model.SubtitleEntry;

import java.util.Iterator;
import java.util.TreeMap;

/**
 * Created by Amos on 2015/8/3.
 */
public class SubtitlePlugin extends VideoPlugin implements OnSubtitleChangeListener{

    protected TextView mTvSubTitle;
    private Subtitle   mSubtitle;
    private TreeMap<Integer, SubtitleEntry> mSubtitleMap;
    private SubtitleParseTask mSubtitleTask;

    public SubtitlePlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onCreated() {
        super.onCreated();
        mTvSubTitle = findViewById(R.id.tv_subtitle);
        if(isFullScreen()){
            mTvSubTitle.setTextSize(getContext().getResources().getDimension(R.dimen.video_common_8dp));
        }else{
            mTvSubTitle.setTextSize(getContext().getResources().getDimension(R.dimen.video_common_6dp));
        }
    }

    @Override
    public void onAfterVideoPlay(Video video, long position) {
        super.onAfterVideoPlay(video, position);
        mSubtitle = null;
    }

    @Override
    public void onSubtitleChanged(Subtitle subtitle) {
        if(mSubtitle != subtitle){
            SubtitlePlayer.get().setSubtitle(subtitle);
            mSubtitle = subtitle;
            if( null != mSubtitleTask){
                mSubtitleTask.cancel(true);
            }
            mSubtitleTask = new SubtitleParseTask();
            mSubtitleTask.execute(mSubtitle);
        }
    }

    @Override
    public void onVideoPositionChanged() {
        super.onVideoPositionChanged();
        long time = getTime();
        if( null != mSubtitleMap){
            Iterator<Integer> keys = mSubtitleMap.keySet().iterator();
            String subTitle = null;
            while (keys.hasNext()) {
                Integer key = keys.next();
                SubtitleEntry srtbean = mSubtitleMap.get(key);
                if (time > srtbean.getBeginTime() && time < srtbean.getEndTime()) {
                    subTitle = srtbean.getSrtBody();
                    break;
                }
            }
            mTvSubTitle.setText(subTitle);
        }
    }

    class SubtitleParseTask extends AsyncTask<Subtitle, Boolean, TreeMap<Integer, SubtitleEntry>>{

        @Override
        protected TreeMap<Integer, SubtitleEntry> doInBackground(Subtitle... subtitles) {
            TreeMap<Integer, SubtitleEntry> subtitleEntryTreeMap = null;
            try {
                subtitleEntryTreeMap = SubtitleParser.parseSrt(subtitles[0].getLocalUrl());
            }catch (Exception e){

            }
            return subtitleEntryTreeMap;
        }

        @Override
        protected void onPostExecute(TreeMap<Integer, SubtitleEntry> integerSubtitleEntryTreeMap) {
            super.onPostExecute(integerSubtitleEntryTreeMap);
            mSubtitleMap = integerSubtitleEntryTreeMap;
        }
    }

    @Override
    public void onAppDestroy() {
        super.onAppDestroy();
        SubtitlePlayer.get().close();
        if( null != mSubtitleTask){
            mSubtitleTask.cancel(true);
        }
        mSubtitleTask = null;
    }
}
