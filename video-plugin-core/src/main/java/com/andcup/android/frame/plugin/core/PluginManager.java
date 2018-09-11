package com.andcup.android.frame.plugin.core;

import android.support.v4.app.FragmentManager;

import com.andcup.android.frame.plugin.core.navigator.PluginNavigator;
import com.andcup.android.frame.plugin.core.handler.FileHandler;
import com.andcup.android.frame.plugin.core.handler.PluginBuilder;
import com.andcup.android.frame.plugin.core.handler.XmlParser;
import com.andcup.android.frame.plugin.core.model.Mode;
import com.andcup.android.frame.plugin.core.model.PluginEntry;

import java.util.Iterator;
import java.util.List;

/**
 * @author Amos
 * @version 2015/5/27
 */
public class PluginManager implements IPluginManager{

    private PluginConfiguration mPluginConfiguration;
    private PluginContext       mPluginContext;
    private List<Plugin> mPluginList;

    public PluginManager(PluginContext pluginContext, PluginConfiguration pluginConfiguration){
        mPluginConfiguration = pluginConfiguration;
        mPluginContext = pluginContext;
    }

    public PluginContext getPluginContext() {
        return mPluginContext;
    }

    @Override
    public void loadPlugins(FragmentManager fragmentManager, int containerId) {
        List<PluginEntry> pluginEntryList = new XmlParser(mPluginContext).parse(new FileHandler(mPluginContext, mPluginConfiguration.getPluginConfiguration()));
        mPluginList = new PluginBuilder(mPluginContext, fragmentManager).build(pluginEntryList);
        Iterator<Plugin> pluginIterator =  mPluginList.iterator();
        while (pluginIterator.hasNext()){
            Plugin plugin = pluginIterator.next();
            if(plugin.getPluginEntry().entry){
                PluginNavigator transaction = new PluginNavigator(fragmentManager, containerId);
                plugin.setTransaction(transaction);
            }
            plugin.onLoad();
        }
    }

    @Override
    public Plugin getPlugin(String pluginId) {
        Iterator<Plugin> pluginIterator =  mPluginList.iterator();
        while (pluginIterator.hasNext()){
            Plugin plugin = pluginIterator.next();
            if(plugin.getId().equals(pluginId)){
                return plugin;
            }
        }
        return null;
    }

    public void removePlugin(String pluginId) {
        Iterator<Plugin> pluginIterator =  mPluginList.iterator();
        while (pluginIterator.hasNext()){
            Plugin plugin = pluginIterator.next();
            if(plugin.getId().equals(pluginId)){
                plugin.onUnLoad();
                mPluginList.remove(plugin);
                break;
            }
        }
    }

    @Override
    public Plugin getEntryPlugin() {
        Iterator<Plugin> pluginIterator =  mPluginList.iterator();
        while (pluginIterator.hasNext()){
            Plugin plugin = pluginIterator.next();
            if(plugin.getPluginEntry().entry){
                return plugin;
            }
        }
        throw new RuntimeException("entry plugin is not found ! please add plugin.entry = true at plugin");
    }

    @Override
    public void setMode(Mode mode) {
        Iterator<Plugin> pluginIterator =  mPluginList.iterator();
        while (pluginIterator.hasNext()){
            Plugin plugin = pluginIterator.next();
            plugin.onModeChanged(mode);
        }
    }

    @Override
    public void onAppStart() {
        Iterator<Plugin> pluginIterator =  mPluginList.iterator();
        while (pluginIterator.hasNext()){
            Plugin plugin = pluginIterator.next();
            plugin.onAppStart();
        }
    }

    public boolean onBeforeAppDestroy() {
        Iterator<Plugin> pluginIterator =  mPluginList.iterator();
        while (pluginIterator.hasNext()){
            Plugin plugin = pluginIterator.next();
            if(plugin.onBeforeAppDestroy()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onAppDestroy() {
        Iterator<Plugin> pluginIterator =  mPluginList.iterator();
        while (pluginIterator.hasNext()){
            Plugin plugin = pluginIterator.next();
            plugin.onAppDestroy();
        }
    }

    public List<Plugin> getPluginList(){
        return mPluginList;
    }

    public void clear(){
        Iterator<Plugin> pluginIterator =  mPluginList.iterator();
        while (pluginIterator.hasNext()){
            Plugin plugin = pluginIterator.next();
            plugin.onUnLoad();
        }
        mPluginList.clear();
    }
}
