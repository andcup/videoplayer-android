package com.qioq.android.lib.video.tools;

import android.content.Context;
import android.os.PowerManager;

/**
 * Created by ND on 14-4-17.
 */
public class Locker {

    private static final String TAG = Locker.class.getName();
    private Context mContext;
    private PowerManager.WakeLock   mWakeLock;

    public Locker(Context context){
        mContext = context;
        mWakeLock= ((PowerManager)mContext.getSystemService(Context.POWER_SERVICE)).newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, TAG);
    }

    public void lock(){
        if(null != mWakeLock &&  !mWakeLock.isHeld()){
            mWakeLock.setReferenceCounted(false);
            mWakeLock.acquire();
        }
    }

    public void unlock(){
        if(null != mWakeLock && mWakeLock.isHeld()){
            mWakeLock.release();
        }
    }
}
