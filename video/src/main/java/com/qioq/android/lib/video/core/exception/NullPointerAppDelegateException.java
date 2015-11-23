package com.qioq.android.lib.video.core.exception;

/**
 * @author Amos
 * @version 2015/5/27
 */
public class NullPointerAppDelegateException extends NullPointerException {
    public NullPointerAppDelegateException(){
        super("null AppDelegate found exception! Please call setAppDelegate() when build reader player!");
    }
}
