package com.andcup.android.frame.plugin.core;

import android.view.LayoutInflater;
import android.view.View;

import com.andcup.android.frame.plugin.core.base.AbsPlugin;
import com.andcup.android.frame.plugin.core.navigator.AbsNavigator;
import com.andcup.android.frame.plugin.core.indicator.AbsIndicator;
import com.andcup.android.frame.plugin.core.listener.OnBackPressedListener;
import com.andcup.android.frame.plugin.core.model.Mode;
import com.andcup.android.frame.plugin.core.model.PluginEntry;
import com.andcup.android.frame.plugin.core.model.Type;

/**
 * @author Amos
 * @version 2015/5/27
 */
public abstract class Plugin extends AbsPlugin implements OnBackPressedListener {

    private AbsNavigator mTransaction;
    private AbsIndicator mIndicator;

    public Plugin(PluginContext pluginContext, PluginEntry pluginEntry){
        super(pluginContext, pluginEntry);
        newIndicator(pluginEntry);
    }

    private Class getClassByName(final String clazz) throws ClassNotFoundException {
        Class c = null;
        if ((clazz != null) && !("".equals(clazz))) {
            c = Class.forName(clazz);
        }
        return c;
    }

    private void newIndicator(PluginEntry pluginEntry){
        try {
            Class c = getClassByName(pluginEntry.indicator);
            if(null != c && AbsIndicator.class.isAssignableFrom(c)){
                mIndicator = (AbsIndicator) c.newInstance();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        try{
            if(null != mIndicator && !mIndicator.onVisibleOnModeChanged(this)){
                return;
            }
            mTransaction.show(this, false);
        }catch (Exception e){

        }
    }

    @Override
    public void hide() {
        try{
            mTransaction.hide(this);
        }catch (Exception e){

        }
    }

    @Override
    public void show(boolean newView) {
        try{
            if(null != mIndicator && !mIndicator.onVisibleOnModeChanged(this)){
                return;
            }
            mTransaction.show(this, newView);
        }catch (Exception e){

        }
    }

    @Override
    public boolean isVisible() {
        try{
            return mTransaction.isVisible(this);
        }catch (Exception e){

        }
        return false;
    }

    public View onCreateView(LayoutInflater inflater){
        if( null != mIndicator ){
            int id = mIndicator.genLayoutId(this);
            if(id != AbsIndicator.INVALID_LAYOUT_ID){
                return inflater.inflate(id, null);
            }
        }
        return null;
    }

    public void setTransaction(AbsNavigator absNavigator){
        mTransaction = absNavigator;
    }

    public AbsNavigator getTransaction(){
        return mTransaction;
    }

    public void onBindView(View view){

    }

    public void onResume(){

    }

    public void onPause(){

    }

    public void onDestroy(){

    }

    public void onCreated(){
    }

    public void onAttach(){

    }

    @Override
    public void onBackPressed() {

    }

    public void onModeChanged(Mode mode){
        boolean visible = true;
        boolean visibleAlwaysIfNeed = false;
        if( null != mIndicator ){
            visible = mIndicator.onVisibleOnModeChanged(this);
            visibleAlwaysIfNeed = mIndicator.isVisibleAlwaysIfNeed();
        }
        if(!Mode.NORMAL.equals(getPluginEntry().mode)){
            if(visible && null != mTransaction){
                if(visibleAlwaysIfNeed){
                    show(true);
                }else{
                    mTransaction.newIfVisible(this);
                }
            }
        }else{
            if(!visible){
                hide();
            }else{
                if(visibleAlwaysIfNeed){
                    show();
                }
            }
        }
    }

    public boolean getVisibleOnCurrentMode(){
        boolean visible = true;
        if( null != mIndicator ){
            visible = mIndicator.getVisibleOnCurrentMode();
        }
        return visible;
    }

    public abstract int getAppWidth();

    public abstract int getAppHeight();

    public abstract int[] getLocationOnScreen();

    public void onAppStart(){
        if((getPluginEntry().type == Type.DIALOG_PLUGIN && getPluginEntry().autoStart) || getPluginEntry().entry){
            show();
        }
    }

    public boolean onBeforeAppDestroy(){
        return false;
    }

    public void onAppDestroy(){

    }
}
