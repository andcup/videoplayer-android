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
import com.qioq.android.lib.video.core.model.Rate;
import com.qioq.android.lib.video.VideoPlugin;

import static com.qioq.android.lib.video.core.model.Rate.Rate100X;
import static com.qioq.android.lib.video.core.model.Rate.Rate125X;
import static com.qioq.android.lib.video.core.model.Rate.Rate150X;

/**
 * Created by Amos on 2015/7/29.
 */
public class VideoRatePlugin extends VideoPlugin implements RadioGroup.OnCheckedChangeListener {

    RadioGroup mRadioGroup;
    Rate[]     mRates;

    public VideoRatePlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onCreated() {
        super.onCreated();
        mRates = new Rate[]{Rate100X, Rate.Rate125X, Rate.Rate150X};
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        TextView mTvContext   = findViewById(R.id.tv_content);
        mRadioGroup = findViewById(R.id.rg_child_item_container);
        for (Rate item : mRates) {
            View childItemView = layoutInflater.inflate(R.layout.video_setting_common_item, null);
            RadioButton radioButton = (RadioButton) childItemView.findViewById(R.id.rb_child_item);
            radioButton.setText(layoutInflater.getContext().getResources().getString(item.getResourceId()));
            childItemView.setId(item.getId());
            childItemView.setTag(item);
            mRadioGroup.addView(radioButton,
                    layoutInflater.getContext().getResources().getDimensionPixelSize(R.dimen.setting_item_width),
                    ViewGroup.LayoutParams.MATCH_PARENT);
            if(mapRate(getRate()) == item){
                radioButton.setChecked(true);
            }

        }
        mRadioGroup.setOnCheckedChangeListener(this);
        mTvContext.setText(layoutInflater.getContext().getResources().getString(R.string.settings_rate));
    }

    private Rate mapRate(float rate){
        if(rate == 1.0f){
            return Rate100X;
        }else if(rate == 1.25f){
            return Rate125X;
        }else {
            return Rate150X;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
        if(checkId == Rate100X.getId()){
            setRate(1.0f);
        }else if(checkId == Rate125X.getId()){
            setRate(1.25f);
        }else{
            setRate(1.50f);
        }
    }
}
