package com.andcup.android.frame.plugin.core.navigator.impl;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/2/24.
 */
public class DialogFragmentNavigator extends Navigator<FragmentManager> {

    public DialogFragmentNavigator(FragmentManager carrier) {
        super(carrier);
    }

    protected String getKey(){
        return mTargetClass.getName();
    }

    public DialogFragment getDialogFragment(){
        return (DialogFragment) mCarrier.findFragmentByTag(getKey());
    }

    @Override
    public Navigator go() {
        DialogFragment fragment = getDialogFragment();
        if(null != fragment && (fragment.isVisible() || fragment.isAdded())){
            return this;
        }

        try {
            fragment = (DialogFragment) mTargetClass.newInstance();
            fragment.setArguments(mBundle);
            fragment.show(mCarrier, getKey());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public Navigator finish() {
        DialogFragment fragment = getDialogFragment();
        if(null != fragment && (fragment.isVisible() || fragment.isAdded())){
            fragment.dismissAllowingStateLoss();
        }
        return this;
    }

    @Override
    public Navigator toggle() {
        DialogFragment fragment = getDialogFragment();
        if(null != fragment && (fragment.isVisible() || fragment.isAdded())){
            finish();
        }else{
            go();
        }
        return this;
    }
}
