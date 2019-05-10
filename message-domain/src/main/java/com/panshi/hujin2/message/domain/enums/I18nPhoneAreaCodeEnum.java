package com.panshi.hujin2.message.domain.enums;

/**
 * create by shenjiankang on 2018/7/14 16:25
 *
 *
 * key:国际化i18n
 * value:国家区号
 */
public enum I18nPhoneAreaCodeEnum {
    CHINA("CN","86"),//中国
    BRAZIL("BR","55");//巴西

    private String key;
    private String value;

    I18nPhoneAreaCodeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static I18nPhoneAreaCodeEnum getCountryAreaCode(String key){
        I18nPhoneAreaCodeEnum[] enums = I18nPhoneAreaCodeEnum.values();
        for(I18nPhoneAreaCodeEnum ps:enums){
            if(ps.key.equals(key)){
                return ps;
            }
        }
        return null;
    }
}
