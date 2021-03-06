package com.panshi.hujin2.message.domain.enums;

import java.util.Objects;

/**
 * create by shenjiankang on 2018/6/20 16:47
 *
 * 具体发送短信的渠道
 */
public enum ChannelEnum {

    CHUANGLAN(1,"chuanglan"),
    SUBMAIL(2,"submail"),
    INFOBIP(3,"infobip"),
    TIANYIHONG_OTP(4,"天一泓-otp"),
    PAASOO(5,"paasoo"),
    YIMEIRUANTONG(6,"yimeiruantong"),
    KMI(7,"KMI-opt"),
    INA_HORIZON_SMS(8,"INA_HORIZON_SMS"),
    NIU_XIN(9,"NIU_XIN_OTP"),
    KMI_LONGNUMBER(10,"KMI-营销短信"),
    KMI_VOICE_VERIFY(11,"KMI-语音短信"),
    ALIYUN(12,"aliyun"),
    NIU_XIN_MARKETING(13,"NIU_XIN_MARKETING"),
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

    public static ChannelEnum getByCode(Integer code) {
        for (ChannelEnum t : values()) {
            if (Objects.equals(t.getCode(), code))
                return t;
        }
        return null;
    }

    public static ChannelEnum getByText(String text) {
        for (ChannelEnum t : values()) {
            if (Objects.equals(t.getText(), text))
                return t;
        }
        return null;
    }


}
