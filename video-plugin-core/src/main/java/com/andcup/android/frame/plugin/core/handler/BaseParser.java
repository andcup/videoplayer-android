package com.andcup.android.frame.plugin.core.handler;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.model.PluginEntry;

import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Amos
 * @version 2015/5/28
 */
public abstract class BaseParser {

    private final static String PATTEN_ID = "^@\\+id/[a-zA-Z_0-9]+$";
    private final static String PATTEN_STYLE = "^@style/[a-zA-Z_]+$";
    private final static String PATTEN_LAYOUT = "^@layout/[a-zA-Z_]+$";

    private Pattern mPattenId = Pattern.compile(PATTEN_ID);
    private Pattern mPattenStyle = Pattern.compile(PATTEN_STYLE);
    private Pattern mPattenLayout = Pattern.compile(PATTEN_LAYOUT);

    protected final static String NODE_TYPE_INCLUDE = "include";
    protected final static String NODE_TYPE_PLUGIN = "plugin";
    protected final static String NODE_TYPE_DIALOG = "dialog";
    protected final static String NODE_TYPE_EXPAND = "expand";

    protected final static String ATTR_PATH = "plugin.path";
    protected final static String ATTR_ID = "plugin.id";
    protected final static String ATTR_ENTRY = "plugin.entry";
    protected final static String ATTR_MODE = "plugin.new";
    protected final static String ATTR_ENABLE = "plugin.enable";
    protected final static String ATTR_LAYOUT = "plugin.layout";
    protected final static String ATTR_STYLE = "plugin.style";
    protected final static String ATTR_CLASS = "plugin.class";
    protected final static String ATTR_MODAL = "plugin.modal";
    protected final static String ATTR_VISIBLE = "plugin.visible";
    protected final static String ATTR_GRAVITY = "plugin.gravity";
    protected final static String ATTR_WIDTH = "plugin.width";
    protected final static String ATTR_HEIGHT = "plugin.height";
    protected final static String ATTR_LEFT = "plugin.left";
    protected final static String ATTR_TOP = "plugin.top";
    protected final static String ATTR_INDICATOR = "plugin.displayIndicator";
    protected final static String ATTR_RELATIVE_TO = "plugin.relativeTo";
    protected final static String ATTR_AUTO_START = "plugin.autoStart";
    protected final static String ATTR_DIM_ENABLE = "plugin.dimEnable";

    protected final static String ATTR_EXPAND_ID = "id.expand";
    protected final static String ATTR_EXPAND_PLUGIN = "id.plugin";

    public static final int INVALID_RESOURCE = 0;

    protected PluginContext mPluginContext;

    protected BaseParser(PluginContext pluginContext) {
        mPluginContext = pluginContext;
    }

    public abstract List<PluginEntry> parse(FileHandler fileHandler);

    public abstract List<PluginEntry> parse(InputStream inputStream);

    protected int parseId(String value) {
        Matcher matcher = mPattenId.matcher(value);
        return genResourceId("id", find(matcher));
    }

    protected int parseStyle(String value) {
        Matcher matcher = mPattenStyle.matcher(value);
        return genResourceId("style", find(matcher));
    }

    protected int parseLayout(String value) {
        Matcher matcher = mPattenLayout.matcher(value);
        return genResourceId("layout", find(matcher));
    }

    protected boolean parseBoolean(String value, boolean defaultValue) {
        if (TextUtils.isEmpty(value)) {
            return defaultValue;
        }
        return value.equalsIgnoreCase("true");
    }

    private int genResourceId(String flag, String value) {
        if (TextUtils.isEmpty(value)) {
            return INVALID_RESOURCE;
        }
        int resourceId = mPluginContext.getContext().getResources().getIdentifier(value, flag, mPluginContext.getContext().getPackageName());
        return resourceId;
    }

    protected String find(Matcher matcher) {
        if (matcher.find()) {
            String temp = matcher.group().substring(1);
            return temp.split("/")[1];
        }
        return null;
    }

    protected int parseSize(String value) {
        if (TextUtils.isEmpty(value)) {
            return ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        if (value.equalsIgnoreCase("match_parent")) {
            return ViewGroup.LayoutParams.MATCH_PARENT;
        }
        if (value.equalsIgnoreCase("wrap_content")) {
            return ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        if (value.startsWith("@dimen")) {
            int dimenLength = "@dimen".length();
            if (value.length() == dimenLength) {
                return ViewGroup.LayoutParams.MATCH_PARENT;
            }
            int resourceId = genResourceId("dimen", value.substring(dimenLength + 1, value.length()));
            return mPluginContext.getContext().getResources().getDimensionPixelSize(resourceId);
        }
        String size = value.substring(0, value.length() - "dp".length());
        return dip2px(mPluginContext.getContext(), Float.parseFloat(size));
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
