package com.ruoyi.system.Util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {
    // + MsgType 	消息类型，文本为 text
    public static final String MESSAGE_TEXT = "text";
    // + MsgType 	消息类型，图片为 image
    public static final String MESSAGE_IMAGE = "image";
    // + MsgType 	语音为 voice
    public static final String MESSAGE_VOICE = "voice";
    // + MsgType 	视频为 video
    public static final String MESSAGE_VIDEO = "video";
    // + MsgType 	小视频为 shortvideo
    public static final String MESSAGE_SHORTVIDEO = "shortvideo";
    // + MsgType 	消息类型，地理位置为 location
    public static final String MESSAGE_LOCATION = "location";
    // + MsgType 	消息类型，链接为 link
    public static final String MESSAGE_LINK = "link";
    // + MsgType 	消息类型，event
    public static final String MESSAGE_EVENT = "event";
    // > Event 	事件类型，subscribe(订阅)、unsubscribe(取消订阅)
    //Event 	事件类型，SCAN
    //Event 	事件类型，LOCATION
    //Event 	事件类型，CLICK
    //Event 	事件类型，VIEW
    public static final String MESSAGE_SUBSCRIBE_EVENT = "subscribe";
    public static final String MESSAGE_UNSUBSCRIBE_EVENT = "unsubscribe";
    public static final String MESSAGE_SCAN_EVENT = "SCAN";
    public static final String MESSAGE_LOCATION_EVENT = "LOCATION";
    public static final String MESSAGE_CLICK_EVENT = "CLICK";
    public static final String MESSAGE_VIEW_EVENT = "VIEW";

    /**
     * xml 转集合。
     *
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String, Object> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        Map<String, Object> map = new HashMap<>();
        SAXReader saxReader = new SAXReader();

        // 从 request 中获取输入流。
        ServletInputStream inputStream = request.getInputStream();
        Document document = saxReader.read(inputStream);

        // 获取 xml 根元素。
        Element rootElement = document.getRootElement();
        // 每一元素放入 list 中。
        List<Element> list = rootElement.elements();
        for (Element element : list) {
            map.put(element.getName(), element.getText());
        }

        inputStream.close();
        return map;
    }
}