package com.qioq.android.lib.video.core.exception;

/**
 * @author Amos
 * @version 2015/6/14
 */
public class VideoPlayerIsNotReadyException extends RuntimeException {
    public VideoPlayerIsNotReadyException(){
        super("Reader is not start or is creating. Please open document after reader created. you can setOnReaderListener and get reader status !");
    }
}
