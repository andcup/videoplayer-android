package com.andcup.android.frame.plugin.core.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Amos
 * @version 2015/5/27
 */
public class PluginEntry implements Serializable {

    /**
     *@brief 默认启动插件.唯一
     */
    public boolean entry;
    /**
     *@brief 插件类名
     */
    public String plugin;
    /**
     *@brief 插件id, 唯一值
     */
    public String id;
    /**
     *@brief 插件布局
     */
    public int    layout;
    /**
     *@brief 插件style.
     */
    public int    style;
    /**
     *@brief 类型, 默认全屏小屏都启用.
     */
    public String mode = Mode.NORMAL.mValue;

    /**
     *@brief 首次加载是否显示
     */
    public boolean visible;

    /**
     *@brief 是否模态. 争对对话框插件
     */
    public boolean modal;

    /**
     *@brief 插件类型
     */
    public Type type = Type.NORMAL_PLUGIN;

    /**
     * @brief dialog position.
     * */
    public boolean relativeToContext = true;

    public int      width ;

    public int      height;

    public int      gravity;

    public boolean  enable;

    public String indicator;

    public boolean  autoStart;

    public int      left;

    public int      top;

    public boolean  dimEnable = false;

    public List<ExpandElement> expandElements;

    public String getId() {
        return id;
    }

}
