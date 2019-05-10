package com.panshi.hujin2.message.domain.enums;

/**
 * create by shenjiankang on 2018/7/14 10:37
 *
 * 推送模板枚举
 *
 *
 */
public enum PushTemplateEnum {

    /**
     *  巴西 盘点借
     */
    CHU_ZHANG("201807141000142300002","提醒|出账提醒"),
    HUAN_KUAN("201807141005218640003","提醒|还款提醒"),
    YU_QI("201807141009176870004","提醒|逾期提醒"),
    YU_QI_ZHUAN_RU("201807141025227960006","提醒|逾期转入提醒"),
    HUAN_KUAN_CHENG_GONG("201807141027521310007","提醒|还款成功"),

    /**
     *  墨西哥 無憂貸
     */
//    WYD_audit_pass("",""),
//    WYD_audit_not_pass("",""),
//    WYD_enchashment_remind("",""),
//    WYD_repayment_remind("",""),
//    WYD_overdue_remind("",""),
//    WYD_repayment_success("",""),
//    WYD_repayment_portion("","");


    /**
     * 巴西 eLoan
     */
    BR_ELOAN_1("201808240843095510001", "尊敬的用户，您于%s通过借款审核，获得%s雷尔的提现额度。"),
    BR_ELOAN_2("201808240844513880002", "尊敬的用户，您未通过借款审核，请如实修改后重新提交审核。"),
    BR_ELOAN_3("201808240846398830003", "尊敬的用户，您于%s成功提现%s雷尔。"),
    BR_ELOAN_4("201808240847298360004", "尊敬的用户，您有一笔借款即将逾期，请尽快还款。"),
    BR_ELOAN_5("201808240848198760005", "尊敬的用户，您有一笔借款已逾期%s天，请尽快还款。"),
    BR_ELOAN_6("201808240849037520006", "尊敬的用户，您于%s成功还款%s雷尔。"),
    BR_ELOAN_7("201808240849537380007", "尊敬的用户，您于%s成功还款%s雷尔，剩余%s雷尔未还，请尽快还款。");

    private String code;
    private String desc;

    PushTemplateEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

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
}
