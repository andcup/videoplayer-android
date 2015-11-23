package com.qioq.android.lib.video;

/**
 * Created by Amos on 2015/7/7.
 */
public class VideoConfiguration {
    private String mPluginPath;

    protected VideoConfiguration(Builder builder){
        mPluginPath = builder.mPluginPath;
    }

    public String getPluginPath(){
        return mPluginPath;
    }

    public static class Builder{
        private String mPluginPath;
        public VideoConfiguration build() {
            return new VideoConfiguration(this);
        }

        public Builder setLandscapeEnabled(boolean enabled){
            return this;
        }

        public Builder setPortraitEnabled(boolean enabled){
            return this;
        }

        public Builder setVolumeEnabled(boolean enabled){
            return this;
        }

        public Builder setBrightnessEnabled(boolean enabled){
            return this;
        }

        public Builder setPluginPath(String pluginPath){
            mPluginPath = pluginPath;
            return this;
        }
    }
}
