package com.andcup.android.frame.plugin.core.navigator;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.andcup.android.frame.plugin.core.base.AbsPlugin;
import com.andcup.android.frame.plugin.core.navigator.impl.DialogFragmentNavigator;

/**
 * @author Amos
 * @version 2015/6/4
 */
public class DialogPluginNavigator extends AbsNavigator {

    public DialogPluginNavigator(FragmentManager fragmentManager) {
        super(fragmentManager, 0);
    }

    @Override
    public void hide(AbsPlugin absPlugin) {
        getNavigator(absPlugin).finish();
    }

    @Override
    public void show(AbsPlugin absPlugin, boolean forceNew) {
        DialogFragmentNavigator nav = getNavigator(absPlugin);
        if(forceNew){
            nav.finish();
        }else{
            DialogFragment fragment = nav.getDialogFragment();
            if(null != fragment && fragment.isVisible() || fragment.isAdded()){
                return;
            }
        }
        nav.go();
    }

    @Override
    public void newIfVisible(AbsPlugin absPlugin) {
        boolean visible = isVisible(absPlugin);
        if(visible){
            show(absPlugin, true);
        }
    }

    @Override
    public boolean isVisible(AbsPlugin absPlugin) {
        DialogFragmentNavigator nav = getNavigator(absPlugin);
        DialogFragment fragment = nav.getDialogFragment();
        if(null != fragment && (fragment.isVisible() || fragment.isAdded())){
            return true;
        }
        return false;
    }

    private DialogFragmentNavigator getNavigator(AbsPlugin plugin){
        DialogFragmentNavigator nav = new DialogFragmentNavigator(getFragmentManager());
        nav.to(plugin.getClass());
        return nav;
    }
}
