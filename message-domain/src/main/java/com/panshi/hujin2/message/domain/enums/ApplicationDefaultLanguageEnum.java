package com.panshi.hujin2.message.domain.enums;

import java.util.Objects;

/**
 * create by shenjiankang on 2018/7/31 16:04
 *
 * 注意：这里的code和 basic project基础服务中的com.panshi.hujin2.base.common.enmu.ApplicationEnmu code对应
 */
public enum ApplicationDefaultLanguageEnum {

    PAN_GUAN_JIA_LOCALE(1, "盘管家","pt","BR"),//葡萄牙文(巴西)
    WU_YOU_DAI_LOCALE(2,"无忧贷", "es","MX");//西班牙文(墨西哥)


    private Integer code;
    private String appName;
    private String language;
    private String country;

    ApplicationDefaultLanguageEnum(Integer code, String appName, String language, String country) {
        this.code = code;
        this.appName = appName;
        this.language = language;
        this.country = country;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static ApplicationDefaultLanguageEnum getByCode(Integer code) {
        for (ApplicationDefaultLanguageEnum t : values()) {
            if (Objects.equals(t.getCode(), code))
                return t;
        }
        return null;
    }
}
