package com.panshi.hujin2.message.service.notification.getui.utils;

import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.NotyPopLoadTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style1;

/**
 * Created by shenJianKang on 2017/12/1.
 */

public class GeTuiTemplate {


    //点击通知打开应用模板
    //描述:在通知栏显示一条含图标、标题等的通知，用户点击后激活您的应用。（激活后，打开应用的首页，如果只要求点击通知唤起应用，不要求到哪个指定页面就可以用此功能。
    //应用场景:场景1：针对沉默用户，发送推送消息，点击消息栏的通知可直接激活启动应用，提升应用的转化率。


    public static NotificationTemplate getNotificationTemplate(String appId,
                                                               String appKey,
                                                               String transmissionContent,
                                                               String title,
                                                               String text) {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        template.setTransmissionContent(transmissionContent);//"请输入您要透传的内容"
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");

        Style1 style1 = getStyle1( title, text);
        template.setStyle(style1);

        return template;
    }


    //点击通知打开网页模板
    //描述:在通知栏显示一条含图标、标题等的通知，用户点击可打开您指定的网页。
    //应用场景:场景1：推送广促销活动，用户点击通知栏信息，直接打开到指定的促销活动页面，推送直接到达指定页面，免去了中间过程的用户流失。
    public static LinkTemplate getLinkTemplate(String appId,String appKey,
                                                String title,
                                               String text,
                                               String openUrl) {
        LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);

        Style1 style1 = getStyle1( title, text);
        template.setStyle(style1);

        // 设置打开的网址地址
        template.setUrl(openUrl);
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
        return template;
    }


    //点击通知弹框下载模板
    //描述:消息以弹框的形式展现，点击弹框内容可启动下载任务。
    //应用场景:场景1：应用有更新，点击推送的更新通知，弹出下载弹窗，点击可启动应用更新下载。
    public static NotyPopLoadTemplate getNotyPopLoadTemplate(String appId,String appKey,String title,String text) {
        NotyPopLoadTemplate template = new NotyPopLoadTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);

        Style1 style1 = getStyle1( title, text);
        template.setStyle(style1);

        // 设置弹框标题与内容
        template.setPopTitle("弹框标题");
        template.setPopContent("弹框内容");
        // 设置弹框显示的图片
        template.setPopImage("");
        template.setPopButton1("下载");
        template.setPopButton2("取消");
        // 设置下载标题
        template.setLoadTitle("下载标题");
        template.setLoadIcon("file://icon.png");
        //设置下载地址
        template.setLoadUrl("http://gdown.baidu.com/data/wisegame/80bab73f82cc29bf/shoujibaidu_16788496.apk");
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
        return template;
    }


    //iOS推送说明
    //iOS推送需要在代码中通过TransmissionTemplate的setAPNInfo接口设置相应的APNs通知参数。 透传模板传输的数据最大为是2KB，APNs传输数据最大支持2KB。
    //透传模板  //String transmissionContent
    /**
     * @description                 透传模板推送
     * @param appId                 APPID
     * @param appKey                APPKEY
     * @param title                 标题
     * @param text                  内容
     * @param transmissionContent   透传参数
     * @Author shenjiankang
     * @Date 2018/2/9 11:49
     */
    public static TransmissionTemplate getTransmissionTemplate(String appId,
                                                               String appKey,
                                                               String title,
                                                               String text,
                                                               String transmissionContent) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionContent(transmissionContent);
        template.setTransmissionType(2);// 这个Type为int型，填写1则自动启动app

        APNPayload payload = new APNPayload();
        //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
        payload.setAutoBadge("+1");
        payload.setContentAvailable(1);//推送直接带有透传数据
        payload.setSound("default");//通知铃声文件名，无声设置为"com.gexin.ios.silence"
        payload.setCategory("$由客户端定义");//在客户端通知栏触发特定的action和button显示

        //简单模式APNPayload.SimpleMsg
        //payload.setAlertMsg(new APNPayload.SimpleAlertMsg(title));

        //todo  透传模板 消息标题 先尝试用字典模式
        //字典模式使用APNPayload.DictionaryAlertMsg
        payload.setAlertMsg(getDictionaryAlertMsg(title,text));

        // 添加多媒体资源
//        payload.addMultiMedia(new MultiMedia().setResType(MultiMedia.MediaType.video)
//                .setResUrl("http://ol5mrj259.bkt.clouddn.com/test2.mp4")
//                .setOnlyWifi(true));

        template.setAPNInfo(payload);
        return template;
    }

    /**
     * iOS 10 以上 “loc-key”和“body”这二个字段的优先级发生了变化，
     * 在iOS 10以上“body”的优先级大于“loc-key”，
     * 在iOS 10以下“loc-key”大于“body”，不推荐使用“loc-key”传递数据
     * 或者将body中的数据放到payload中，将loc-key的数据放到message（对应body）中，在iOS 10中就不会出现显示代码了
     * ***/
    private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(String title,String text){
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setBody(text);
//        alertMsg.setActionLocKey("ActionLockey");
//        alertMsg.setLocKey("LocKey");
//        alertMsg.addLocArg("loc-args");
//        alertMsg.setLaunchImage("launch-image");
        // iOS8.2以上版本支持
        alertMsg.setTitle(title);
//        alertMsg.setTitleLocKey("TitleLocKey");
//        alertMsg.addTitleLocArg("TitleLocArg");
        return alertMsg;
    }


    /**
     * @Description:  获取推送模板中的style对象
     * @param title  "请输入通知栏标题"
     * @param text   "请输入通知栏内容"
     * @Author: shenJianKang
     * @Date: 2017/12/2 13:01
     */
//    public static Style0 getStyle(String title,String text){
//
//        Style0 style = new Style0();
//        // 设置通知栏标题与内容
//        style.setTitle(title);
//        style.setText(text);
//        // 配置通知栏图标
////        style.setLogo("icon.png");
//        style.setLogo("push.png");
//        // 配置通知栏网络图标
//        style.setLogoUrl("");
//        // 设置通知是否响铃，震动，或者可清除
//        style.setRing(true);
//        style.setVibrate(true);
//        style.setClearable(true);
//
//        return style;
//    }


    public static Style1 getStyle1(String title, String text){

        Style1 style1 = new Style1();
        // 设置通知栏标题与内容
        style1.setTitle(title);
        style1.setText(text);
        // 配置通知栏图标
//        style1.setLogo("icon.png");
        style1.setLogo("push.png");
        // 配置通知栏网络图标
        style1.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        style1.setRing(true);
        style1.setVibrate(true);
        style1.setClearable(true);

        return style1;
    }
}
