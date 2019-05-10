package com.panshi.hujin2.message.domain.enums.getui;

/**
 * Created by shenJianKang on 2017/12/11.
 */
public enum PushTypeEnum {
    SINGLE_PUSH(1,"单个推送"),
    ALL_PUSH(2,"按APP推送"),
    LIST_PUSH(3,"按列表推送");

    private int code;
    private String text;

    PushTypeEnum(int code, String text) {
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
