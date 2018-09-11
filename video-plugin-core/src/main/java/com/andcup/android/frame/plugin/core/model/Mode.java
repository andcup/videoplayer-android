package com.andcup.android.frame.plugin.core.model;

import java.io.Serializable;

/**
 * @author Amos
 * @version 2015/5/28
 */
public enum  Mode implements Serializable {
    ORIENTATION("orientation"),
    SIZE("size"),
    NORMAL("normal");

    public String mValue;
    Mode(String value){
        mValue = value;
    }

    public boolean equals(String value){
        return mValue.equals(value);
    }
}
