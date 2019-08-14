package com.panshi.hujin2.message.common.utils;

import com.thoughtworks.xstream.XStream;

/**
 * create by shenjiankang on 2018/10/17 20:35
 *
 * xml javaBean 互转
 */
public class XmlUtils {


    /**
     * @description:    javaBean 转 xml字符串
     * @param object    java对象
     * @param c         class对象
     * @Author shenjiankang
     * @Date 2018/10/19 15:09
     */
    public static String toXmlStr(Object object,Class c){
        XStream xstream = new XStream();
        xstream.autodetectAnnotations(true);
        xstream.processAnnotations(c);
        String xmlStr = xstream.toXML(object);
        return xmlStr;
    }

    /**
     * @description:    javaBean 转 xml字符串
     * @param object    java对象
     * @param alias     java对象转成xml标签的别名
     * @param c         class对象
     * @Author shenjiankang
     * @Date 2018/10/19 15:10
     */
    public static String toXmlStr(Object object,String alias,Class c){
        XStream xstream = new XStream();
        xstream.alias(alias, c);
        xstream.autodetectAnnotations(true);
        xstream.processAnnotations(c);
        String xmlStr = xstream.toXML(object);
        return xmlStr;
    }

    /**
     * @description:    xml字符串转 java对象
     * @param xmlStr    xml字符串
     * @param c         javaBean class对象
     * @Author shenjiankang
     * @Date 2018/10/19 15:11
     */
    public static Object toJavaBean(String xmlStr,Class c){
        XStream xstream = new XStream();
        xstream.autodetectAnnotations(true);
        xstream.processAnnotations(c);
        return xstream.fromXML(xmlStr);
    }
}
