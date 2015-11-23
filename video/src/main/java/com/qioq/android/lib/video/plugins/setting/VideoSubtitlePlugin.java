package com.qioq.android.lib.video.plugins.setting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.qioq.android.artemis.piece.core.PluginContext;
import com.qioq.android.artemis.piece.core.model.PluginEntry;
import com.nd.hy.android.video.R;
import com.qioq.android.lib.video.core.NotificationService;
import com.qioq.android.lib.video.core.model.Subtitle;
import com.qioq.android.lib.video.VideoPlugin;
import com.qioq.android.lib.video.plugins.subtitle.SubtitlePlayer;

import java.util.List;

/**
 * Created by Amos on 2015/7/29.
 */
public class VideoSubtitlePlugin extends VideoPlugin  implements RadioGroup.OnCheckedChangeListener{

    public VideoSubtitlePlugin(PluginContext pluginContext, PluginEntry pluginEntry) {
        super(pluginContext, pluginEntry);
    }

    @Override
    public void onCreated() {
        super.onCreated();

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        List<Subtitle> subtitleList = SubtitlePlayer.get().getSubtitles();
        if(null != subtitleList && subtitleList.size() > 0){
            //
            TextView mTvContext   = findViewById(R.id.tv_content);
            RadioGroup radioGroup = findViewById(R.id.rg_child_item_container);
            for(int i = 0; i< subtitleList.size(); i++){
                Subtitle subTitle = subtitleList.get(i);
                View childItemView = layoutInflater.inflate(R.layout.video_setting_common_item, null);
                RadioButton radioButton = (RadioButton) childItemView.findViewById(R.id.rb_child_item);
                radioButton.setText(subTitle.getTitle());
                childItemView.setId(subTitle.hashCode());
                childItemView.setTag(subTitle);
                radioGroup.addView(radioButton,layoutInflater.getContext().getResources().getDimensionPixelSize(R.dimen.setting_item_width), ViewGroup.LayoutParams.MATCH_PARENT);
                //TODO
                radioButton.setEnabled(subTitle.isExists());
                if(subTitle == SubtitlePlayer.get().getSubtitle()){
                    radioButton.setChecked(true);
                }
            }
            mTvContext.setText(layoutInflater.getContext().getResources().getString(R.string.settings_language));
            radioGroup.setOnCheckedChangeListener(this);
        }else{
            //
            String[] languageStr = getContext().getResources().getStringArray(R.array.subtitles);
            TextView mTvContext   = findViewById(R.id.tv_content);
            RadioGroup radioGroup = findViewById(R.id.rg_child_item_container);
            for (String item : languageStr) {
                View childItemView = layoutInflater.inflate(R.layout.video_setting_common_item, null);
                RadioButton radioButton = (RadioButton) childItemView.findViewById(R.id.rb_child_item);
                radioButton.setText(item);
                childItemView.setId(item.hashCode());
                childItemView.setTag(item);
                radioGroup.addView(radioButton,
                        layoutInflater.getContext().getResources().getDimensionPixelSize(R.dimen.setting_item_width),
                        ViewGroup.LayoutParams.MATCH_PARENT);
                //TODO
                radioButton.setEnabled(false);
            }
            mTvContext.setText(layoutInflater.getContext().getResources().getString(R.string.settings_language));
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
        List<Subtitle> subtitleList = SubtitlePlayer.get().getSubtitles();
        for(Subtitle subtitle : subtitleList){
            if(checkId == subtitle.hashCode()){
                NotificationService.get().onSubtitleChanged(subtitle);
                break;
            }
        }
    }
}
