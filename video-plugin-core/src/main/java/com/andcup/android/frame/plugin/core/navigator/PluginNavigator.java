package com.andcup.android.frame.plugin.core.navigator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.andcup.android.frame.plugin.core.base.AbsPlugin;
import com.andcup.android.frame.plugin.core.navigator.impl.FragmentNavigator;

/**
 * @author Amos
 * @version 2015/5/27
 */
public class PluginNavigator extends AbsNavigator {

    public PluginNavigator(FragmentManager fragmentManager, int containerId) {
        super(fragmentManager, containerId);
    }

    @Override
    public void hide(AbsPlugin absPlugin) {
        FragmentNavigator navigator = getNavigation(absPlugin);
        navigator.finish();
    }

    @Override
    public void show(AbsPlugin absPlugin, boolean forceNew) {
        FragmentNavigator navigator = getNavigation(absPlugin);
        if(forceNew){
            navigator.go(true);
        }else{
            navigator.go();
        }
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
        FragmentNavigator navigator = getNavigation(absPlugin);
        Fragment fragment = navigator.getFragment();
        if( null != fragment){
            return fragment.isVisible();
        }
        return false;
    }

    private FragmentNavigator getNavigation(AbsPlugin plugin){
        FragmentNavigator navigator = new FragmentNavigator(getFragmentManager());
        navigator.at(getContainerId()).to(plugin.getClass());
        return navigator;
    }
}
