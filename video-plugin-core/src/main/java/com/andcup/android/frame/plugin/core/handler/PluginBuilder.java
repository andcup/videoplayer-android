package com.andcup.android.frame.plugin.core.handler;


import android.support.v4.app.FragmentManager;


import com.andcup.android.frame.plugin.core.Plugin;
import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.navigator.DialogPluginNavigator;
import com.andcup.android.frame.plugin.core.model.PluginEntry;
import com.andcup.android.frame.plugin.core.model.Type;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Amos
 * @version 2015/5/28
 */
public class PluginBuilder {

    private PluginContext mPluginContext;
    private FragmentManager mFragmentManager;

    public PluginBuilder(PluginContext pluginContext, FragmentManager fragmentManager){
        mPluginContext  = pluginContext;
        mFragmentManager = fragmentManager;
    }

    public List<Plugin> build(List<PluginEntry> pluginEntryList){
        List<Plugin> pluginList = new ArrayList<Plugin>();
        for(PluginEntry pluginEntry : pluginEntryList){
            if(pluginEntry.enable){
                Plugin plugin = newPlugin(pluginEntry);
                if( null != plugin){
                    pluginList.add(plugin);
                }
            }
        }
        return pluginList;
    }

    private  Plugin newPlugin(PluginEntry pluginEntry) {
        Plugin plugin;
        try {
            @SuppressWarnings("rawtypes")
            Class c = getClassByName(pluginEntry.plugin);
            if (isPlugin(c)) {
                Class cls[] = new Class[]{PluginContext.class, PluginEntry.class};
                Constructor constructor = c.getConstructor(cls);
                plugin = (Plugin) constructor.newInstance(mPluginContext, pluginEntry);
                if(null != plugin){
                    if(plugin.getPluginEntry().type == Type.DIALOG_PLUGIN){
                        DialogPluginNavigator transaction = new DialogPluginNavigator(mFragmentManager);
                        plugin.setTransaction(transaction);
                    }
                }
                return plugin;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Class getClassByName(final String clazz) throws ClassNotFoundException {
        Class c = null;
        if ((clazz != null) && !("".equals(clazz))) {
            c = Class.forName(clazz);
        }
        return c;
    }

    private boolean isPlugin(Class c) {
        if (c != null) {
            return Plugin.class.isAssignableFrom(c);
        }
        return false;
    }

}
