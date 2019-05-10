package com.panshi.hujin2.message.domain.enums;

/**
 * create by shenjiankang on 2018/7/3 16:48
 *
 * SUBMAIL 国际短信提交响应状态码
 *
 * 请求成功返回的数据结构
 * {"status":"success","send_id":"686f9da3c52cdd16e5cf9e1b357b1441","fee":"0.134","account_balance":"9.918"}
 *
 * 请求失败返回的数据结构
 * {"status":"error","code":255,"msg":"Unsupported Region Phone number"}
 */
public enum SubmailIntResponseEnum {

    SUCCESS("success","成功"),
    ERROR("error","失败");

    private String type;
    private String desc;

    SubmailIntResponseEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
