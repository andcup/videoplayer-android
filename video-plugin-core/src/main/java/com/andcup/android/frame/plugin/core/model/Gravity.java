package com.andcup.android.frame.plugin.core.model;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * @author Amos
 * @version 2015/6/4
 */
public enum  Gravity implements Serializable {
    LEFT("left"),
    TOP("top"),
    RIGHT("right"),
    BOTTOM("bottom"),
    CENTER("center"),
    CENTER_HORIZONTAL("center_horizontal"),
    CENTER_VERTICAL("center_vertical");

    String gravity;
    Gravity(String value){
        gravity = value;
    }

    public static int get(String value){
        if(TextUtils.isEmpty(value)){
            return android.view.Gravity.CENTER;
        }
        int gravity = android.view.Gravity.NO_GRAVITY;
        if(value.contains(LEFT.gravity)){
            gravity |= android.view.Gravity.LEFT;
        }
        if(value.contains(TOP.gravity)){
            gravity |= android.view.Gravity.TOP;
        }
        if(value.contains(RIGHT.gravity)){
            gravity |= android.view.Gravity.RIGHT;
        }
        if(value.contains(BOTTOM.gravity)){
            gravity |= android.view.Gravity.BOTTOM;
        }
        if(value.contains(CENTER_HORIZONTAL.gravity)){
            gravity |= android.view.Gravity.CENTER_HORIZONTAL;
        }
        if(value.contains(CENTER_VERTICAL.gravity)){
            gravity |= android.view.Gravity.CENTER_VERTICAL;
        }
        return gravity;
    }
}
