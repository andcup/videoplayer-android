package com.andcup.android.frame.plugin.host.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Amos
 * @version 2015/5/26
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return onCreateView (inflater);
    }

    @Override
    public final void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onBindView();
    }

    @Override
    public final void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onCreated();
    }

    protected View findViewById(int viewId){
        if( null != getView()){
            return getView().findViewById(viewId);
        }
        return null;
    }

    protected abstract View onCreateView(LayoutInflater inflater);
    protected void onBindView(){}
    protected void onCreated(){}

}
