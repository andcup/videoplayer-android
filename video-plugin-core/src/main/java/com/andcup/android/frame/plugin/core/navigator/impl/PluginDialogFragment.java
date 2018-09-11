package com.andcup.android.frame.plugin.core.navigator.impl;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


import com.andcup.android.frame.plugin.PluginApplication;
import com.andcup.android.frame.plugin.core.Plugin;
import com.andcup.android.frame.plugin.core.PluginServices;
import com.andcup.android.frame.plugin.core.navigator.PluginNavigator;
import com.andcup.android.frame.plugin.core.base.AbsPluginDialogFragment;
import com.andcup.android.frame.plugin.core.model.ExpandElement;
import com.andcup.android.frame.plugin.core.model.PluginEntry;

import java.util.List;

/**
 * @author Amos
 * @version 2015/5/27
 */
public class PluginDialogFragment extends AbsPluginDialogFragment {

    public static final String TAG     = PluginDialogFragment.class.getSimpleName();
    private static final String APP_ID = "com.andcup.android.frame.plugin.core.delegate.impl.appId";

    private PluginEntry mPluginEntry;
    private Plugin mPlugin;
    private String mAppId;

    public static PluginDialogFragment newInstance(PluginEntry pluginEntry, String appId){
        PluginDialogFragment delegate = new PluginDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PluginEntry.class.getSimpleName(), pluginEntry);
        bundle.putSerializable(APP_ID, appId);
        delegate.setArguments(bundle);
        return delegate;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, mPluginEntry.style);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAppId = getArguments().getString(APP_ID);
        PluginApplication pluginApplication = getPluginApplication();
        mPluginEntry   = (PluginEntry) getArguments().getSerializable(PluginEntry.class.getSimpleName());
        if( null != pluginApplication && null != pluginApplication.getPluginManager()){
            mPlugin = pluginApplication.getPluginManager().getPlugin(mPluginEntry.getId());
            mPlugin.onAttach();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = null;
        if( null != mPlugin){
            view = mPlugin.onCreateView(inflater);
        }
        if( null == view){
            view = inflater.inflate(mPluginEntry.layout, null);
        }
        return view;
    }

    @Override
    public void onViewCreated() {
        if( null != mPlugin){
            mPlugin.onBindView(getView());
            onAttachChildPlugin();
        }
    }

    @Override
    public void onPluginCreated() {
        if( null != mPlugin){
            setAttribute();
            mPlugin.onCreated();
            getView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try{
                        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                                if (i== KeyEvent.KEYCODE_BACK && dialogInterface == getDialog()){
                                    if(keyEvent.getAction() == KeyEvent.ACTION_UP){
                                        mPlugin.onBackPressed();
                                    }
                                    return true;
                                }
                                return false;
                            }
                        });
                    }catch (Exception e){

                    }
                }
            }, 5);
        }
    }



    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if( null != mPlugin){
            mPlugin.onDestroy();
        }
    }

    private PluginApplication getPluginApplication(){
        PluginApplication app = PluginServices.getInstance().getPluginApplication(mAppId);
        return app;
    }

    private void setAttribute(){
        Window dialogWindow = getDialog().getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        if(mPluginEntry.modal){
            getDialog().setCanceledOnTouchOutside(true);
        }else{
            lp.flags = lp.flags | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        }

        if(mPluginEntry.relativeToContext){
            dialogWindow.setGravity(Gravity.LEFT| Gravity.TOP);
            correctionIfRelativeToContext(lp);
        }else{
            lp.width = mPluginEntry.width;
            lp.height= mPluginEntry.height;
            dialogWindow.setGravity(mPluginEntry.gravity);
        }
        if(mPluginEntry.dimEnable){
            lp.dimAmount = 0.5f;
            lp.flags = lp.flags | WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        }
        Log.v(TAG, " lp.x = " + lp.x + " lp.y = " + lp.y + " width = " + lp.width + " height = " + lp.height);
        dialogWindow.setAttributes(lp);
    }

    private void correctionIfRelativeToContext(WindowManager.LayoutParams lp){
        int contextWidth  = mPlugin.getAppWidth();
        int contextHeight = mPlugin.getAppHeight();
        Point point       = getMeasureSpec();
        int[] position    = mPlugin.getLocationOnScreen();
        //
        if(mPluginEntry.width == ViewGroup.LayoutParams.MATCH_PARENT){
            lp.width = contextWidth;
        }else if(mPluginEntry.width == ViewGroup.LayoutParams.WRAP_CONTENT){
            lp.width = point.x;
        }else{
            lp.width = mPluginEntry.width;
        }

        if(mPluginEntry.height == ViewGroup.LayoutParams.MATCH_PARENT){
            lp.height = contextHeight;
        }else if(mPluginEntry.height == ViewGroup.LayoutParams.WRAP_CONTENT){
            lp.height = point.y;
        }else{
            lp.height= mPluginEntry.height;
        }

        if(point.x > contextWidth){
            lp.width = contextWidth;
        }
        if(point.y > contextHeight){
            lp.height = contextHeight;
        }
        //
        boolean left  = ((mPluginEntry.gravity & Gravity.LEFT)   == Gravity.LEFT)  ? true : false;
        boolean right = ((mPluginEntry.gravity & Gravity.RIGHT)  == Gravity.RIGHT) ? true : false;
        boolean top   = ((mPluginEntry.gravity & Gravity.TOP)    == Gravity.TOP)   ? true : false;
        boolean bottom= ((mPluginEntry.gravity & Gravity.BOTTOM) == Gravity.BOTTOM)? true : false;
        if(left){
            lp.x = position[0] + mPluginEntry.left;
            if(top) {
                lp.y = position[1]  + mPluginEntry.top;
            }else if(bottom){
                lp.y = position[1] + contextHeight - lp.height;
            }else{
                lp.y = position[1] + (contextHeight - lp.height) / 2;
            }
        }
        if(top){
            lp.y = position[1] + mPluginEntry.top;
            if(left){
                lp.x = position[0] + mPluginEntry.left;
            }else if(right){
                lp.x = position[0] + contextWidth - lp.width - mPluginEntry.left;
            }else{
                lp.x = position[0] + (contextWidth - lp.width) / 2;
            }
        }
        if(right && !left){
            lp.x = position[0] + contextWidth - lp.width;
            if(top){
                lp.y = position[1];
            }else if(bottom){
                lp.y = position[1] + contextHeight - lp.height;
            }else{
                lp.y = position[1] + (contextHeight - lp.height) / 2;
            }
        }
        if(bottom && !top){
            lp.y = position[1] + contextHeight - lp.height;
            if(left){
                lp.x = position[0];
            }else if(right){
                lp.x = position[0] + contextWidth - lp.width;
            }else{
                lp.x = position[0] + (contextWidth - lp.width) / 2;
            }
        }

        if(!left && !top && !right && !bottom){
            lp.x = position[0] + (contextWidth  - lp.width) / 2;
            lp.y = position[1] + (contextHeight - lp.height) / 2;
        }
    }

    private Point getMeasureSpec(){
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        getView().measure(w, h);
        int height = getView().getMeasuredHeight();
        int width  = getView().getMeasuredWidth();

        Point point = new Point(width, height);
        return point;
    }

    private void onAttachChildPlugin(){
        List<ExpandElement> expandElements = mPluginEntry.expandElements;
        if( null == expandElements){
            return;
        }

        PluginApplication pluginApplication = getPluginApplication();
        if( null == pluginApplication){
            return;
        }

        for(ExpandElement element : expandElements){
            if(!element.enable){
                Log.e(TAG, "expand is not enabled : " + element.pluginId + " is this expand is disabled?");
                continue;
            }
            Plugin plugin = pluginApplication.getPluginManager().getPlugin(element.pluginId);
            if(null == plugin) {
                Log.e(TAG, "not found plugin : " + element.pluginId + " is this plugin disabled?");
                continue;
            }
            View containerView = getView().findViewById(element.expandId);
            PluginNavigator transaction = new PluginNavigator(getChildFragmentManager(), containerView.getId());
            plugin.setTransaction(transaction);
            if(plugin.getPluginEntry().visible && element.visible && plugin.getVisibleOnCurrentMode()){
                plugin.show();
            }
        }
    }
}
