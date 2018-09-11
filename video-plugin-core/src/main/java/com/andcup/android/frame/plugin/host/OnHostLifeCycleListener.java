package com.andcup.android.frame.plugin.host;
/**
 * @author Amos
 * @version 2015/5/28
 */
public interface OnHostLifeCycleListener {

    public  void onCreate();

    public  void onCreated();

    public  void onPause();

    public  void onStop();

    public  void onResume();

    public  void onDestroy();

    public  void onConfigureChanged();

    public  void onSizeChanged();
}
