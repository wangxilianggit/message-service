package com.panshi.hujin2.message.domain.enums;

/**
 * create by shenjiankang on 2018/7/12 14:24
 *
 * submail 加密方式枚举
 */
public enum SubmailEncryptionTypeEnum {

    NORMAL("normal"),
    MD5("md5"),
    SHA1("sha1");

    private String type;

    SubmailEncryptionTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
