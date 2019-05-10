package com.panshi.hujin2.message.domain.enums;

/**
 * create by shenjiankang on 2018/9/29 16:33
 *
 * 亿美软通
 */
public enum YiMeiRuanTongResponseEnum {
    SUCCESS("SUCCESS","提交成功"),
    ERROR_APPID("ERROR_APPID","AppId错误"),
    ERROR_PARAMS("ERROR_PARAMS","请求参数错误"),
    ERROR_CLIENT_IP("ERROR_CLIENT_IP","不识别的Ip"),
    ERROR_MOBILE_EMPTY("ERROR_MOBILE_EMPTY","手机号为空"),
    ERROR_MOBILE_NUMBER("ERROR_MOBILE_NUMBER","号码数量过多"),
    ERROR_MOBILE_ERROR("ERROR_MOBILE_ERROR","手机号码错误"),
    ERROR_CONTENT_EMPTY("ERROR_CONTENT_EMPTY","短信内容为空"),
    ERROR_CUSTOM_IMSID("ERROR_CUSTOM_IMSID","自定义消息ID过长"),
    ERROR_LONG_CONTENT("ERROR_LONG_CONTENT","短信内容过长"),
    ERROR_TIMESTAMP("ERROR_TIMESTAMP","时间戳错误"),
    ERROR_SIGN("ERROR_SIGN","签名错误");

    private String type;
    private String desc;

    YiMeiRuanTongResponseEnum(String type, String desc) {
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


    public static YiMeiRuanTongResponseEnum getStatusDesc(String type){
        YiMeiRuanTongResponseEnum[] enums = YiMeiRuanTongResponseEnum.values();
        for(YiMeiRuanTongResponseEnum ps:enums){
            if(ps.type.equals(type)){
                return ps;
            }
        }
        return null;
    }
}
