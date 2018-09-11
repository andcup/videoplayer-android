package com.andcup.android.frame.plugin.host;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;

import com.andcup.android.frame.plugin.PluginApplication;
import com.andcup.android.frame.plugin.R;
import com.andcup.android.frame.plugin.host.base.BaseFragment;
import com.andcup.android.frame.plugin.host.widget.SizeRelativeLayout;
import com.andcup.android.frame.plugin.core.PluginServices;

/**
 * @author Amos
 * @version 2015/5/27
 */
public class HostFragment extends BaseFragment implements SizeRelativeLayout.OnSizeChangeListener{

    private SizeRelativeLayout mSizeRelativeLayout;
    private String mAppId;

    public static HostFragment newInstance(String appId){
        Bundle bundle = new Bundle();
        HostFragment delegate = new HostFragment();
        bundle.putSerializable(HostFragment.class.getSimpleName(), appId);
        delegate.setArguments(bundle);
        return delegate;
    }

    @Override
    protected View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.plugin_context, null);
        mSizeRelativeLayout = (SizeRelativeLayout) view.findViewById(R.id.srl_size);
        mSizeRelativeLayout.setOnSizeChangedListener(this);
        return view;
    }

    @Override
    protected void onCreated() {
        ViewTreeObserver viewTreeObserver = getView().getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getView().getViewTreeObserver().removeOnPreDrawListener(this);
                PluginApplication pluginApplication = getPluginApplication();
                if( null != pluginApplication){
                    getPluginApplication().onCreated(R.id.fr_entry);
                }
                return false;
            }
        });
    }

    @Override
    public void onStop() {
        PluginApplication pluginApplication = getPluginApplication();
        if( null != pluginApplication){
            getPluginApplication().onStop();
        }
        super.onStop();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PluginApplication pluginApplication = getPluginApplication();
        if( null != pluginApplication){
            getPluginApplication().onCreate();
        }
    }


    @Override
    public void onDestroy() {
        PluginApplication pluginApplication = getPluginApplication();
        if( null != pluginApplication){
            getPluginApplication().onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onPause() {
        PluginApplication pluginApplication = getPluginApplication();
        if( null != pluginApplication){
            getPluginApplication().onPause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        PluginApplication pluginApplication = getPluginApplication();
        if( null != pluginApplication){
            getPluginApplication().onResume();
        }
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        PluginApplication pluginApplication = getPluginApplication();
        if( null != pluginApplication){
            getPluginApplication().onConfigureChanged();
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        PluginApplication pluginApplication = getPluginApplication();
        if( null != pluginApplication){
            getPluginApplication().onSizeChanged(w, h);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAppId = getArguments().getString(HostFragment.class.getSimpleName());
    }

    private PluginApplication getPluginApplication(){
        PluginApplication app = PluginServices.getInstance().getPluginApplication(mAppId);
        return app;
    }
}
