package com.andcup.android.frame.plugin.core;

import com.andcup.android.frame.plugin.PluginApplication;

import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * Created by Amos on 2015/7/8.
 */
public class PluginServices {

    private static final String TAG = "PluginServices";

    private static PluginServices sPluginServices;
    private HashMap<String, SoftReference<PluginApplication>> mPluginApplicationList = new HashMap<>();

    private PluginServices(){

    }

    public static PluginServices getInstance(){
        if( null == sPluginServices){
            sPluginServices = new PluginServices();
        }
        return sPluginServices;
    }

    public PluginApplication getPluginApplication(String appId){
        SoftReference<PluginApplication> app = mPluginApplicationList.get(appId);
        if( null != app){
            return app.get();
        }
        return null;
    }

    public void registerPluginApplication(String appId, PluginApplication pluginApplication){
        if( null == pluginApplication){
            return;
        }
        unRegisterPluginApplication(appId);
        SoftReference<PluginApplication> app = new SoftReference<PluginApplication>(pluginApplication);
        mPluginApplicationList.put(appId, app);
    }

    public void unRegisterPluginApplication(String appId){
        mPluginApplicationList.remove(appId);
    }
}
