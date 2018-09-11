package com.andcup.android.frame.plugin.core.navigator.impl;

import android.os.Bundle;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/2/23.
 */
public abstract class Navigator<T> {

    protected Class<?> mTargetClass;
    protected Bundle   mBundle;
    protected T        mCarrier;

    public Navigator(T carrier){
        mCarrier = carrier;
    }

    public Navigator to(Class<?> targetClass){
        mTargetClass = targetClass;
        return this;
    }

    public Navigator with(Bundle bundle){
        mBundle = bundle;
        return this;
    }

    public abstract Navigator go();

    public Navigator toggle(){
        return this;
    };

    public abstract Navigator finish();
}
