package com.andcup.android.frame.plugin.core.navigator.impl;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.andcup.android.frame.plugin.PluginApplication;
import com.andcup.android.frame.plugin.core.Plugin;
import com.andcup.android.frame.plugin.core.PluginServices;
import com.andcup.android.frame.plugin.core.navigator.PluginNavigator;
import com.andcup.android.frame.plugin.core.base.AbsPluginFragment;
import com.andcup.android.frame.plugin.core.model.ExpandElement;
import com.andcup.android.frame.plugin.core.model.PluginEntry;

import java.util.List;


/**
 * @author Amos
 * @version 2015/5/27
 */
public class PluginFragment extends AbsPluginFragment {

    private static final String TAG    = PluginFragment.class.getSimpleName();
    private static final String APP_ID = "com.nd.hy.android.plugin.frame.core.delegate.impl.appId";

    private Plugin mPlugin;
    private PluginEntry mPluginEntry;
    private String mAppId;

    public static PluginFragment newInstance(PluginEntry pluginEntry, String appId){
        PluginFragment delegate = new PluginFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PluginEntry.class.getSimpleName(),  pluginEntry);
        bundle.putSerializable(APP_ID, appId);
        delegate.setArguments(bundle);
        return delegate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        if( null == mPlugin ){
            return null;
        }
        Log.v(TAG, "plugin before on create view.");
        View view =  mPlugin.onCreateView(inflater);
        Log.v(TAG, "plugin after on create view. view == " + view);
        if( null == view){
            try{
                return inflater.inflate(mPlugin.getPluginEntry().layout, null);
            }catch (Exception e){

            }
        }
        return view;
    }

    @Override
    public void onViewCreated() {
        if( null == mPlugin ){
            return ;
        }
        mPlugin.onBindView(getView());
        onAttachChildPlugin();
    }

    @Override
    public void onPluginCreated() {
        if( null == mPlugin ){
            return ;
        }
        mPlugin.onCreated();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAppId         = getArguments().getString(APP_ID);
        PluginApplication pluginApplication = getPluginApplication();
        mPluginEntry         = (PluginEntry) getArguments().getSerializable(PluginEntry.class.getSimpleName());
        if( null != pluginApplication && null != pluginApplication.getPluginManager()){
            mPlugin = pluginApplication.getPluginManager().getPlugin(mPluginEntry.getId());
            mPlugin.onAttach();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if( null == mPlugin ){
            return ;
        }
        mPlugin.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if( null == mPlugin ){
            return ;
        }
        mPlugin.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    private PluginApplication getPluginApplication(){
        PluginApplication app = PluginServices.getInstance().getPluginApplication(mAppId);
        return app;
    }

    private void onAttachChildPlugin(){
        List<ExpandElement> expandElements = mPluginEntry.expandElements;
        if( null == expandElements){
            return;
        }

        PluginApplication pluginApplication = getPluginApplication();
        if( null == pluginApplication || pluginApplication.getPluginManager() == null){
            return;
        }

        for(ExpandElement element : expandElements){
            if(!element.enable){
                Log.e(TAG, "expand is not enabled : " + element.pluginId + " is this expand is disabled?");
                continue;
            }

            Plugin plugin = pluginApplication.getPluginManager().getPlugin(element.pluginId);
            if(null == plugin) {
                Log.e(TAG, "not found plugin : " + element.pluginId + " is this plugin disabled?");
                continue;
            }
            View containerView = getView().findViewById(element.expandId);
            if( null == containerView){
                Log.e(TAG, "not found plugin container : " + element.pluginId + " containerId = " + element.expandId);
                plugin.setTransaction(null);
                continue;
            }
            PluginNavigator transaction = new PluginNavigator(getChildFragmentManager(), containerView.getId());
            plugin.setTransaction(transaction);
            if(plugin.getPluginEntry().visible && element.visible && plugin.getVisibleOnCurrentMode()){
                plugin.show();
            }
        }
    }
}
