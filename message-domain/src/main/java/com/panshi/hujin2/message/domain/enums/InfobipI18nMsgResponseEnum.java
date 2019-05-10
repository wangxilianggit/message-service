package com.panshi.hujin2.message.domain.enums;

/**
 * create by shenjiankang on 2018/8/10 10:16
 *
 * infobip国际短信响应码
 * 具体状态码需要查看infobipAPI文档
 */
public enum InfobipI18nMsgResponseEnum {

    PENDING(1,"发送中"),
    UNDELIVERABLE(2,"未能发送"),
    DELIVERED(3,"已经成功传送到运营商或者收件人"),
    EXPIRED(4,"失效的"),
    REJECTED(5,"被拒绝");

    private Integer code;
    private String desc;

    InfobipI18nMsgResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
