package com.andcup.android.frame.plugin.core.handler;

import android.text.TextUtils;


import com.andcup.android.frame.plugin.core.PluginContext;
import com.andcup.android.frame.plugin.core.model.ExpandElement;
import com.andcup.android.frame.plugin.core.model.Gravity;
import com.andcup.android.frame.plugin.core.model.Mode;
import com.andcup.android.frame.plugin.core.model.PluginEntry;
import com.andcup.android.frame.plugin.core.model.Type;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Amos
 * @version 2015/5/28
 */
public class XmlParser extends BaseParser{

    private List<String> mPluginIdList = new ArrayList<String>();
    private boolean       mEntryFound   = false;

    public XmlParser(PluginContext pluginContext){
        super(pluginContext);
    }

    @Override
    public List<PluginEntry> parse(FileHandler fileHandler){
        return parse(fileHandler.openConfiguration());
    }

    @Override
    public List<PluginEntry> parse(InputStream inputStream){
        List<PluginEntry> pluginEntryList = new ArrayList<PluginEntry>();
        Document document = build(inputStream);
        if( null != document){
            //解析父节点
            Element rootElement = document.getDocumentElement();
            List<PluginEntry> parentPluginEntry = parseInclude(rootElement);
            if( null != parentPluginEntry){
                pluginEntryList.addAll(parentPluginEntry);
            }
            //解析插件
            parsePlugin(rootElement, pluginEntryList, Type.NORMAL_PLUGIN);
            //解析对话框插件
            parsePlugin(rootElement, pluginEntryList, Type.DIALOG_PLUGIN);
        }
        return pluginEntryList;
    }

    public List<PluginEntry> parseInclude(Element root){
        NodeList pluginList = root.getElementsByTagName(NODE_TYPE_INCLUDE);
        List<PluginEntry> pluginEntryList = new ArrayList<PluginEntry>();
        for(int i = 0; i< pluginList.getLength(); i++){
            Element element   = (Element) pluginList.item(i);
            boolean isEnabled = parseBoolean(getAttr(element, ATTR_ENABLE), false);
            if(isEnabled){
                String parent = getAttr(element, ATTR_PATH);
                FileHandler fileHandler = new FileHandler(mPluginContext, parent);
                List<PluginEntry> pluginEntries = parse(fileHandler.openConfiguration());
                if( null != pluginEntries){
                    pluginEntryList.addAll(pluginEntries);
                }
            }
        }
        return pluginEntryList;
    }

    public void parsePlugin(Element root, List<PluginEntry> pluginEntryList, Type type){
        NodeList pluginList;
        if(type == Type.DIALOG_PLUGIN){
            pluginList = root.getElementsByTagName(NODE_TYPE_DIALOG);
        }else{
            pluginList = root.getElementsByTagName(NODE_TYPE_PLUGIN);
        }
        for(int i = 0; i< pluginList.getLength(); i++){
            Element element = (Element) pluginList.item(i);
            PluginEntry pluginEntry = parseAttribute(element);
            pluginEntry.expandElements = parseExpandElement(element);
            pluginEntry.type = type;
            pluginEntryList.add(pluginEntry);
        }
    }

    private PluginEntry parseAttribute(Element element){
        PluginEntry pluginEntry = new PluginEntry();
        pluginEntry.id = getAttr(element, ATTR_ID);
        if(mPluginIdList.contains(pluginEntry.id)){
            throw new RuntimeException(" plugin id repeat : " + pluginEntry.id);
        }
        mPluginIdList.add(pluginEntry.id);
        pluginEntry.entry = getAttr(element, ATTR_ENTRY).equals("true");
        if(mEntryFound && pluginEntry.entry){
            throw new RuntimeException(" multi entry plugin : " + pluginEntry.id);
        }
        if(pluginEntry.entry){
            mEntryFound = true;
        }
        pluginEntry.layout = parseLayout(getAttr(element, ATTR_LAYOUT));
        pluginEntry.style  = parseStyle(getAttr(element,  ATTR_STYLE));
        pluginEntry.plugin = getAttr(element, ATTR_CLASS);
        if( null == pluginEntry.plugin){
            throw new RuntimeException(" plugin class not found : " + pluginEntry.id);
        }
        String mode = getAttr(element, ATTR_MODE);
        pluginEntry.mode    = TextUtils.isEmpty(mode) ? Mode.NORMAL.mValue : mode;
        pluginEntry.visible = parseBoolean(getAttr(element, ATTR_VISIBLE), true);
        pluginEntry.indicator = getAttr(element, ATTR_INDICATOR);
        // used for dialog?
        pluginEntry.modal   = parseBoolean(getAttr(element, ATTR_MODAL), true);
        pluginEntry.gravity = Gravity.get(getAttr(element, ATTR_GRAVITY));
        pluginEntry.width   = parseSize(getAttr(element, ATTR_WIDTH));
        pluginEntry.height  = parseSize(getAttr(element, ATTR_HEIGHT));
        pluginEntry.enable  = parseBoolean(getAttr(element, ATTR_ENABLE), true);
        pluginEntry.left    = parseSize(getAttr(element, ATTR_LEFT));
        pluginEntry.top     = parseSize(getAttr(element, ATTR_TOP));
        pluginEntry.autoStart  = parseBoolean(getAttr(element, ATTR_AUTO_START), false);
        pluginEntry.dimEnable  = parseBoolean(getAttr(element, ATTR_DIM_ENABLE), false);
        String relativeTo = getAttr(element, ATTR_RELATIVE_TO);
        if(!TextUtils.isEmpty(relativeTo) && relativeTo.contains("window")){
            pluginEntry.relativeToContext = false;
        }else{
            pluginEntry.relativeToContext = true;
        }
        return pluginEntry;
    }

    private List<ExpandElement> parseExpandElement(Element root){
        List<ExpandElement> expandElements = new ArrayList<ExpandElement>();
        NodeList expandList = root.getElementsByTagName(NODE_TYPE_EXPAND);
        for(int i = 0; i< expandList.getLength(); i++){
            Element element = (Element) expandList.item(i);
            ExpandElement expandElement = new ExpandElement();
            expandElement.expandId = parseId(getAttr(element, ATTR_EXPAND_ID));
            expandElement.pluginId = getAttr(element, ATTR_EXPAND_PLUGIN);
            expandElement.enable   = parseBoolean(getAttr(element, ATTR_ENABLE), true);
            expandElement.visible  = parseBoolean(getAttr(element, ATTR_VISIBLE), true);
            expandElements.add(expandElement);
        }
        return expandElements;
    }

    private String getAttr(Element element, String value){
        return element.getAttribute(value);
    }

    private Document build(InputStream inputStream){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder= factory.newDocumentBuilder();
            return documentBuilder.parse(inputStream);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
