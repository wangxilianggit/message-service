package com.panshi.hujin2.message.domain.enums;

/**
 * create by shenjiankang on 2018/6/20 16:47
 *
 * 具体发送短信的渠道
 */
public enum ChannelEnum {

    CHUANGLAN(1,"chuanglan"),
    SUBMAIL(2,"submail"),
    INFOBIP(3,"infobip"),
    TIANYIHONG(4,"tianyihong"),
    PAASOO(5,"paasoo"),
    YIMEIRUANTONG(6,"yimeiruantong"),
    KMI(7,"KMI"),
    INA_HORIZON_SMS(8,"INA_HORIZON_SMS"),
    NIU_XIN(9,"NIU_XIN"),
    ;

    private int code;
    private String text;

    ChannelEnum(int code, String text) {
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
