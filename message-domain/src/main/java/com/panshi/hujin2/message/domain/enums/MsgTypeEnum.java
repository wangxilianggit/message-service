package com.panshi.hujin2.message.domain.enums;

/**
 * create by shenjiankang on 2018/6/20 16:47
 *
 * 具体发送短信的渠道
 */
public enum MsgTypeEnum {

    REGISTER(1,"注册"),
    CHANGE_PASSWORD(2,"修改密码"),

    ;

    private int code;
    private String text;

    MsgTypeEnum(int code, String text) {
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
