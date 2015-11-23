package com.qioq.android.lib.video.core.exception;

/**
 * @author Amos
 * @version 2015/5/27
 */
public class NullContainerFoundException extends RuntimeException {
    public NullContainerFoundException(){
        super("null container found exception! Please call setContainerId() when build reader player!");
    }
}
