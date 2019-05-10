package com.panshi.hujin2.message.controller.utils;

/**
 * 马甲包枚举
 */
public enum VestEnum {

    VEST_0(0 ,"eLoan"),
    VEST_1(1 ,"MyLoan"),
    VEST_2(2,"FlashLoan"),
    VEST_3(3,"SimpleLoan"),
    VEST_4(4,"WOWLoan"),
    VEST_5(5,"FreeLoan");


    private Integer code;
    private String name;

    VestEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static VestEnum getDefaultByCode(Integer select) {
        for (VestEnum vestEnum : values()) {
            if (vestEnum.code.equals(select)) {
                return vestEnum;
            }
        }
        return VEST_0;
    }

    public static VestEnum getDefaultByName(String name) {
        for (VestEnum vestEnum : values()) {
            if (vestEnum.name.equals(name)) {
                return vestEnum;
            }
        }
        return VEST_0;
    }

}
