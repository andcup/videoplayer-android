package com.qioq.android.lib.video.plugins.network.core;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Jackoder on 14-8-25.
 */
public class NetworkUtil {

    public static NetworkType getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo.State wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

        NetworkInfo mobileNetworkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo.State mobileState = NetworkInfo.State.DISCONNECTED;
        if( null != mobileNetworkInfo ) {
            mobileState = mobileNetworkInfo.getState();
        }

        if (wifiState != null && NetworkInfo.State.CONNECTED == wifiState) {
            return NetworkType.TypeWifi;
        } else if (mobileState != null && NetworkInfo.State.CONNECTED == mobileState ) {
            return NetworkType.TypeMobile;
        }
        return NetworkType.TypeNotConnect;
    }
}
