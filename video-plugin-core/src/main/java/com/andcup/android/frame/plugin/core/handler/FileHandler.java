package com.andcup.android.frame.plugin.core.handler;

import android.util.Log;

import com.andcup.android.frame.plugin.core.PluginContext;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Amos
 * @version 2015/5/28
 */
public class FileHandler {

    private PluginContext mPluginContext;
    private String mConfiguration;

    public FileHandler(PluginContext pluginContext, String configuration){
        mPluginContext = pluginContext;
        mConfiguration = configuration;
    }

    public InputStream openConfiguration(){
        try {
            Log.v(FileHandler.class.getSimpleName(), " open = " + mConfiguration);
            return mPluginContext.getContext().getAssets().open(mConfiguration);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
