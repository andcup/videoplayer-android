package com.andcup.android.frame.plugin.core.navigator;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.andcup.android.frame.plugin.core.base.AbsPlugin;

/**
 * @author Amos
 * @version 2015/5/27
 */
public abstract class AbsNavigator {

    protected static final String TAG = AbsNavigator.class.getSimpleName();
    private int             mContainerId;
    private FragmentManager mFragmentManager;

    public AbsNavigator(FragmentManager fragmentManager, int containerId){
        mFragmentManager = fragmentManager;
        mContainerId     = containerId;
    }

    /**隐藏*/
    public  abstract void   hide(AbsPlugin absPlugin);
    /**显示.*/
    public  abstract void   show(AbsPlugin absPlugin, boolean forceNew);
    /**如果可见.则重建.否则保持不变.*/
    public abstract  void   newIfVisible(AbsPlugin absPlugin);
    /**是否可见.*/
    public abstract boolean isVisible(AbsPlugin absPlugin);

    protected int getContainerId(){
        return mContainerId;
    }

    protected <T>  T  getFragment(String tag){
        return (T) mFragmentManager.findFragmentByTag(tag);
    }

    protected FragmentTransaction getTransaction(){
        return mFragmentManager.beginTransaction();
    }

    protected FragmentManager getFragmentManager(){
        return mFragmentManager;
    }

}
