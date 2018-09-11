package com.andcup.android.frame.plugin;

import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;

import com.andcup.android.frame.plugin.host.HostFragment;
import com.andcup.android.frame.plugin.host.HostPluginContext;
import com.andcup.android.frame.plugin.host.SimpleLifeCycleListener;
import com.andcup.android.frame.plugin.core.PluginConfiguration;
import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.PluginManager;
import com.andcup.android.frame.plugin.core.PluginServices;
import com.andcup.android.frame.plugin.core.model.Mode;

import java.lang.ref.SoftReference;

/**
 * Created by Amos on 2015/7/3.
 */
public final class PluginApplication<T extends IPluginApplication>{

    public interface OnApplicationListener<T>{
        void onApplicationStart(T application);
        void onApplicationStop(T application);
    }
    private PluginManager mPluginManager;
    private SimpleLifeCycleListener mOnAppLifeCycleListener;
    private OnApplicationListener                    mOnApplicationListener;
    private SoftReference<IPluginApplication> mPluginApplication;
    private String mPluginConfiguration;
    private int     mWidth;
    private int     mHeight;
    private boolean mIsReady = false;
    private PluginContext mPluginContext;
    private static final int CREATE_DESTROY_DELAY = 20;

    public PluginApplication(T t, String pluginConfiguration){
        if(TextUtils.isEmpty(t.getAppId())){
            throw new RuntimeException("Plugin Application Id is not allowed null!");
        }
        PluginServices.getInstance().registerPluginApplication(t.getAppId(), this);
        mPluginApplication      = new SoftReference<IPluginApplication>(t);
        mPluginConfiguration    = pluginConfiguration;
        mPluginContext          = new HostPluginContext(mPluginApplication);
    }

    public void start(int containerId){
        try{
            HostFragment delegate   =  HostFragment.newInstance(mPluginApplication.get().getAppId());
            FragmentTransaction ft = mPluginApplication.get().getFragmentManager().beginTransaction();
            ft.replace(containerId, delegate, mPluginApplication.get().getAppId());
            ft.commitAllowingStateLoss();
        }catch (IllegalStateException e){

        }
    }

    public void stop(){
        try{
            if(null != mPluginManager && mPluginManager.onBeforeAppDestroy()){
                return;
            }
            FragmentTransaction ft     = mPluginApplication.get().getFragmentManager().beginTransaction();
            HostFragment readerFragment = getAppFragment();
            if(readerFragment != null){
                ft.remove(readerFragment);
                ft.commitAllowingStateLoss();
            }
        }catch (Exception e){

        }
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public int[] getLocationInWindow(){
        int[] position = new int[2];
        getAppFragment().getView().getLocationInWindow(position);
        Rect rect = getSystemToolBarRect();
        position[1] -= rect.top;
        return position;
    }

    public int[] getLocationOnScreen(){
        int[] position = new int[2];
        getAppFragment().getView().getLocationOnScreen(position);
        Rect rect = getSystemToolBarRect();
        position[1] -= rect.top;
        return position;
    }

    public Rect getSystemToolBarRect(){
        Rect rect = new Rect();
        getAppFragment().getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect;
    }

    public PluginManager getPluginManager() {
        return mPluginManager;
    }

    public void setOnApplicationListener(OnApplicationListener onApplicationListener) {
        this.mOnApplicationListener = onApplicationListener;
    }

    public void setOnAppLifeCycleListener(SimpleLifeCycleListener onAppLifeCycleListener) {
        this.mOnAppLifeCycleListener = onAppLifeCycleListener;
    }

    public boolean isIsReady() {
        return mIsReady;
    }

    public PluginContext getPluginContext() {
        return mPluginContext;
    }

    private HostFragment getAppFragment(){
        if( null != mPluginApplication.get()){
            return (HostFragment) mPluginApplication.get().getFragmentManager().findFragmentByTag(mPluginApplication.get().getAppId());
        }
        return null;
    }

    public void onCreate() {
        if( null != mOnAppLifeCycleListener){
            mOnAppLifeCycleListener.onCreate();
        }
    }

    public void onCreated(int containerId) {
        initPluginManager(containerId);
        if( null != mOnAppLifeCycleListener){
            mOnAppLifeCycleListener.onCreated();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if( null != mOnApplicationListener){
                    mOnApplicationListener.onApplicationStart(mPluginApplication.get());
                }
            }
        }, CREATE_DESTROY_DELAY);
    }

    public void onPause( ) {
        if( null != mOnAppLifeCycleListener){
            mOnAppLifeCycleListener.onPause();
        }
    }

    public void onResume() {
        if( null != mOnAppLifeCycleListener){
            mOnAppLifeCycleListener.onResume();
        }
    }

    public void onStop(){
        if( null != mOnAppLifeCycleListener){
            mOnAppLifeCycleListener.onStop();
        }
    }

    public void onDestroy() {
        if( null != mOnApplicationListener){
            mOnApplicationListener.onApplicationStop(mPluginApplication.get());
            mOnApplicationListener = null;
            Log.v("NotificationService", "onApplicationStop");
        }

        if( null != mPluginManager){
            mPluginManager.onAppDestroy();
            mPluginManager.clear();
            Log.v("NotificationService", "onAppDestroy");
        }
        mPluginManager = null;
        if( null != mOnAppLifeCycleListener){
            mOnAppLifeCycleListener.onDestroy();

        }
        mIsReady = false;
    }

    public void onConfigureChanged() {
        if( null != mOnAppLifeCycleListener){
            mOnAppLifeCycleListener.onConfigureChanged();
        }
    }

    public void onSizeChanged(int width, int height) {
        if( null != mOnAppLifeCycleListener){
            mOnAppLifeCycleListener.onSizeChanged();
        }
        mWidth = Integer.valueOf(width);
        mHeight= Integer.valueOf(height);
        if( null != mPluginManager){
            mPluginManager.setMode(Mode.SIZE);
        }
    }

    private void initPluginManager(int containerId){
        HostFragment hostFragment = getAppFragment();
        PluginConfiguration pluginConfiguration = new PluginConfiguration.Builder().setPath(mPluginConfiguration).build();
        mPluginManager  = new PluginManager(mPluginContext, pluginConfiguration);
        mPluginManager.loadPlugins(hostFragment.getChildFragmentManager(), containerId);

        mWidth = hostFragment.getView().getMeasuredWidth();
        mHeight= hostFragment.getView().getMeasuredHeight();
        mPluginManager.onAppStart();
        mIsReady = true;
    }
}
