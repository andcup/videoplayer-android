package com.andcup.android.frame.plugin.core;

import java.io.Serializable;

/**
 * @author Amos
 * @version 2015/5/27
 */
public class PluginConfiguration implements Serializable {

    private String mPluginConfiguration;

    private PluginConfiguration(Builder builder){
        mPluginConfiguration = builder.mPluginConfiguration;
    }

    public String getPluginConfiguration(){
        return mPluginConfiguration;
    }

    public static class Builder{
        private String mPluginConfiguration;
        public Builder setPath(String pluginPath){
            mPluginConfiguration = pluginPath;
            return this;
        }

        public PluginConfiguration build(){
            return new PluginConfiguration(this);
        }
    }
}
