package com.qioq.android.lib.video.core.exception;

import android.support.v4.app.FragmentManager;

/**
 * @author Amos
 * @version 2015/5/27
 */
public class EnvironmentException extends RuntimeException {
    public EnvironmentException(){
        super("Environment exception. FragmentManager must be " + FragmentManager.class.getName() + " type!");
    }
}
