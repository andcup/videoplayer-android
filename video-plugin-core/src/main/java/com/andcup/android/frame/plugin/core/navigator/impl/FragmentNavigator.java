package com.andcup.android.frame.plugin.core.navigator.impl;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/2/23.
 */
public class FragmentNavigator extends Navigator<FragmentManager> {

    private int mContainerId;

    public FragmentNavigator(FragmentManager carrier) {
        super(carrier);
    }

    protected String getKey(){
        return mTargetClass.getName();
    }

    public Fragment getFragment(){
        return mCarrier.findFragmentByTag(getKey());
    }

    public Navigator at(int containerId){
        mContainerId = containerId;
        return this;
    }

    public Navigator go(boolean replace){
        Fragment fragment = getFragment();
        if(null != fragment){
            if( !replace){
                if(!fragment.isVisible()){
                    mCarrier.beginTransaction().show(fragment).commitAllowingStateLoss();
                }
                return this;
            }
        }

        try {
            fragment = (Fragment) mTargetClass.newInstance();
            fragment.setArguments(mBundle);
            FragmentTransaction ft = mCarrier.beginTransaction();
            if(replace){
                ft.replace(mContainerId, fragment, getKey());
            }else{
                ft.replace(mContainerId, fragment, getKey());
                //ft.add(mContainerId, fragment, getKey());
            }
            ft.commitAllowingStateLoss();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public Navigator go() {
        go(false);
        return this;
    }

    public Navigator finish(boolean removed){
        Fragment fragment = getFragment();
        if(null != fragment){
            FragmentTransaction fragmentTransaction = mCarrier.beginTransaction();
            if(removed){
                fragmentTransaction.remove(fragment).commitAllowingStateLoss();
            }else{
                fragmentTransaction.remove(fragment).commitAllowingStateLoss();
//                mCarrier.beginTransaction().hide(fragment).commitAllowingStateLoss();
            }
        }
        return this;
    }

    @Override
    public Navigator finish() {
        finish(false);
        return this;
    }
}
