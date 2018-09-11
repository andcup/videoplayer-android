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
import com.qioq.android.lib.video.core.model.Scale;
import com.qioq.android.lib.video.engine.model.ScaleType;
import com.qioq.android.lib.video.VideoPlugin;

import static com.qioq.android.lib.video.core.model.Scale.Scale_Original;

/**
 * Created by Amos on 2015/7/29.
 */
public class VideoScalePlugin extends VideoPlugin implements  RadioGroup.OnCheckedChangeListener{

    RadioGroup mRadioGroup;
    private Scale[] mScaleList;

    public VideoScalePlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onCreated() {
        super.onCreated();

        mScaleList = new Scale[]{ Scale.Scale_16_9, Scale.Scale_4_3, Scale.Scale_fill, Scale_Original};
        TextView mTvContext   = findViewById(R.id.tv_content);
        mRadioGroup   = findViewById(R.id.rg_child_item_container);
        final Scale currentScale = mapFromScaleType(getVideoPlayer().getScale());
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        for (Scale item : mScaleList) {
            View childItemView = layoutInflater.inflate(R.layout.video_setting_common_item, null);
            RadioButton radioButton = (RadioButton) childItemView.findViewById(R.id.rb_child_item);
            radioButton.setText(layoutInflater.getContext().getResources().getString(item.getResourceId()));
            childItemView.setId(item.getId());
            mRadioGroup.addView(radioButton,
                    layoutInflater.getContext().getResources().getDimensionPixelSize(R.dimen.setting_item_width),
                    ViewGroup.LayoutParams.MATCH_PARENT);
            if (item == currentScale) {
                radioButton.setChecked(true);
            }
        }
        mRadioGroup.setOnCheckedChangeListener(this);
        mTvContext.setText(layoutInflater.getContext().getResources().getString(R.string.settings_size));
    }

    private Scale mapFromScaleType(ScaleType scaleType) {
        switch (scaleType) {
            case FitOriginal:
                return Scale_Original;
            case Fit4_3:
                return Scale.Scale_4_3;
            case Fit16_9:
                return Scale.Scale_16_9;
            case FitFill:
                return Scale.Scale_fill;
            default:
                return Scale_Original;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
        for(Scale scale : mScaleList){
            if(scale.getId() == checkId){
                switch (scale){
                    case Scale_Original:
                        getVideoPlayer().setScale(ScaleType.FitOriginal);
                        break;
                    case Scale_4_3:
                        getVideoPlayer().setScale(ScaleType.Fit4_3);
                        break;
                    case Scale_16_9:
                        getVideoPlayer().setScale(ScaleType.Fit16_9);
                        break;
                    case Scale_fill:
                        getVideoPlayer().setScale(ScaleType.FitFill);
                        break;
                }
            }
        }
    }
}
