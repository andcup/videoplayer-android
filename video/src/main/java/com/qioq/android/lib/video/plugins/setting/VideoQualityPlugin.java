package com.qioq.android.lib.video.plugins.setting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.model.PluginEntry;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.core.model.Quality;
import com.qioq.android.lib.video.core.model.Video;
import com.qioq.android.lib.video.VideoPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amos on 2015/7/29.
 */
public class VideoQualityPlugin extends VideoPlugin implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup mRadioGroup;

    public VideoQualityPlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onCreated() {
        super.onCreated();
        List<Video> videoList = getVideoPlayer().getVideoList();

        List<Quality> qualityArrayList = new ArrayList<Quality>();
        if (null == videoList || videoList.size() == 0) {
            qualityArrayList.add(Quality.Standard);
        } else {
            for(Video video : videoList) {
                Quality temp = video.getQuality();
                if (!qualityArrayList.contains(temp)) {
                    qualityArrayList.add(temp);
                }
            }
        }
        onBindView(qualityArrayList);
    }

    private void onBindView(List<Quality> qualityArrayList){

        Quality[]   qualities = new Quality[]{ Quality.Low, Quality.Smooth,Quality.Standard, Quality.HD, Quality.SD};
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        TextView mTvContext = findViewById(R.id.tv_content);
        mRadioGroup         = findViewById(R.id.rg_child_item_container);
        for (Quality item : qualities) {
            if (qualityArrayList.contains(item)) {
                View childItemView = layoutInflater.inflate(R.layout.video_setting_common_item, null);
                RadioButton radioButton = (RadioButton) childItemView.findViewById(R.id.rb_child_item);
                radioButton.setText(layoutInflater.getContext().getResources().getString(item.getResourceId()));
                radioButton.setId(item.getId());
                radioButton.setTag(item);
                mRadioGroup.addView(radioButton,
                        layoutInflater.getContext().getResources().getDimensionPixelSize(R.dimen.setting_item_width),
                        ViewGroup.LayoutParams.MATCH_PARENT);

                Video playVideo = getVideo();
                if( null != playVideo && playVideo.getQuality() == item){
                    radioButton.setChecked(true);
                }
            }
        }
        mRadioGroup.setOnCheckedChangeListener(this);
        mTvContext.setText(layoutInflater.getContext().getResources().getString(R.string.settings_quality));
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
        List<Video> videoList = getVideoPlayer().getVideoList();
        for(final Video video : videoList) {
            if (checkId == video.getQuality().getId()) {
                getVideoPlayer().playVideo(videoList.indexOf(video));
            }
        }
    }
}
