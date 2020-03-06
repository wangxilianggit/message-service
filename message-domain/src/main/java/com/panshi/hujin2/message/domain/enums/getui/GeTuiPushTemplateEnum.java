package com.panshi.hujin2.message.domain.enums.getui;

/**
 * Created by shenJianKang on 2017/12/1.
 *
 * 个推 单个推送模板 类型
 */
public enum GeTuiPushTemplateEnum {
    NOTIFICATION_TEMPLATE(1,"点击通知打开应用模板"),
    LINK_TEMPLATE(2,"点击通知打开网页模板"),
    NOTYPOPLOAD_TEMPLATE(3,"点击通知弹框下载模板"),
    TRANSMISSION_TEMPLATE(4,"透传消息模版"),


    FCM(11,"FIREBASE FCM"),
    ONE_SIGNAL(12,"ONE SIGNAL"),

    ;

    private int code;
    private String text;

    GeTuiPushTemplateEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
