package com.panshi.hujin2.message.domain.enums;

/**
 * create by shenjiankang on 2018/9/1 3:47
 *
 * paasoo 发送短信响应枚举
 */
public enum PaasooResponseEnum {

    paasoo_sms_response_0("0","成功：成功"),
    paasoo_sms_response_2("2","缺少参数：缺少必需参数"),
    paasoo_sms_response_4("4","凭据无效：API密钥或密码错误"),
    paasoo_sms_response_5("5","未授权IP：禁止IP"),
    paasoo_sms_response_6("6","无效电话号码：目的地号码格式不正确"),
    paasoo_sms_response_7("7","发件人ID无效：发件人ID格式无效"),
    paasoo_sms_response_9("9","超出配额：余额或信用额度不足");



    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    PaasooResponseEnum(String code, String desc) {

        this.code = code;
        this.desc = desc;
    }
}
