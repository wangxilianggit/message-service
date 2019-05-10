package com.panshi.hujin2.message.domain.enums;

/**
 * @author ycg 2018/11/30 11:43
 */
public enum EmailTemplateEnum {
    Email_Template_1(1, "风控审核通过，实时发送"),
    Email_Template_2(2, "系统判定放款成功，实时发送（现金贷）"),
    Email_Template_3(3, "账单即将逾期,还款日当天10：00发送（现金贷）"),
    Email_Template_4(4, "账单逾期,还款日第二天10：00发送（现金贷）"),
    Email_Template_5(5, "发送Boleto到邮箱,用户实时操作发送"),
    Email_Template_6(6, "系统判定放款成功，实时发送（分期）"),
    Email_Template_7(7, "账单即将逾期,还款日当天10：00发送（分期）"),
    Email_Template_8(8, "账单逾期,还款日第二天10：00发送（分期）");

    private int code;
    private String explain;

    EmailTemplateEnum(int code, String explain) {
        this.code = code;
        this.explain = explain;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
