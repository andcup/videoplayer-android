package com.qioq.android.lib.video.engine;

import android.content.Context;

import java.lang.reflect.Constructor;

/**
 * Created by Amos on 2015/8/13.
 */
public class VideoEngineManager {

    private static final String[] ENGINE_LIST = new String[]{
        "com.nd.hy.android.video.engine.vlc.VLCEngine",
        "com.nd.hy.android.video.engine.mp.MPEngine",
    };

    public static AbsVideoEngine newEngine(Context context){
        for(String engineClass : ENGINE_LIST){
            if(forName(engineClass)){
                AbsVideoEngine absVideoEngine = newEngine(context, engineClass);
                if( null != absVideoEngine){
                    return absVideoEngine;
                }
            }
        }
        return null;
    }

    private static AbsVideoEngine newEngine(Context context, String className){
        AbsVideoEngine absVideoEngine = null;
        try {
            @SuppressWarnings("rawtypes")
            Class c = getClassByName(className);
            if (isEngine(c)) {
                Class cls[] = new Class[]{Context.class};
                Constructor constructor = c.getConstructor(cls);
                absVideoEngine = (AbsVideoEngine) constructor.newInstance(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return absVideoEngine;
    }

    private static Class getClassByName(final String clazz) throws ClassNotFoundException {
        Class c = null;
        if ((clazz != null) && !("".equals(clazz))) {
            c = Class.forName(clazz);
        }
        return c;
    }

    private static boolean forName(String engineClass){
        try {
            Class.forName(engineClass);
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean isEngine(Class c) {
        if (c != null) {
            return AbsVideoEngine.class.isAssignableFrom(c);
        }
        return false;
    }
}
