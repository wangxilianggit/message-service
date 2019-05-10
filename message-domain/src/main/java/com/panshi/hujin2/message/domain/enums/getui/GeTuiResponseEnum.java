package com.panshi.hujin2.message.domain.enums.getui;

/**
 * Created by shenJianKang on 2017/12/12.
 *
 * 个推推送消息响应信息枚举
 */
public enum GeTuiResponseEnum {
    OK("ok","发送成功"),
    CLIENT_ID_INVALID("TokenMD5NoUsers","无效的clientId"),
    RESULT("result","返回结果");

    private String state;
    private String text;

    GeTuiResponseEnum(String state, String text) {
        this.state = state;
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
