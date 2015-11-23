package com.qioq.android.lib.video.plugins.network.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackoder on 14-8-25.
 */
public class NetworkChangeMonitor extends BroadcastReceiver {

    private boolean mHasRegister = false;
    private List<SoftReference<OnNetworkChangeListener>> mOnNetworkChangeListenerList = new ArrayList<SoftReference<OnNetworkChangeListener>>();
    private static NetworkChangeMonitor sNetworkChangeMonitor;

    public static NetworkChangeMonitor get(){
        if( null == sNetworkChangeMonitor){
            sNetworkChangeMonitor = new NetworkChangeMonitor();
        }
        return sNetworkChangeMonitor;
    }

    private NetworkChangeMonitor() {
    }

    public void addNetWorkChangeListener(OnNetworkChangeListener networkChangeListener){
        mOnNetworkChangeListenerList.add(new SoftReference<OnNetworkChangeListener>(networkChangeListener));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            NetworkType networkType = NetworkUtil.getConnectivityStatus(context);
            if(null != networkType){
                for(SoftReference<OnNetworkChangeListener> onNetworkChangeListener : mOnNetworkChangeListenerList){
                    if(onNetworkChangeListener.get() != null){
                        onNetworkChangeListener.get().onChange(networkType);
                    }
                }
            }
        }
    }

    public void start(Context context){
        if( mHasRegister ){
            return;
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(this, filter);
        mHasRegister = true;

    }
    public void stop(Context context){
        if(mHasRegister){
            mHasRegister = false;
            context.unregisterReceiver(this);
        }
        sNetworkChangeMonitor = null;
    }

}
