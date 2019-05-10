package com.panshi.hujin2.message.domain.enums;

/**
 * create by shenjiankang on 2018/7/13 14:55
 *
 * 短信模板编号枚举
 *
 * todo 廢棄，刪除該枚舉
 */
public enum MsgTemplateCodeEnum {

    /**
     *  盘点借
     */
    LOGIN_VERIFY("201807130238138540004","登录验证码：您正在登录MoneyManager，验证码为%s。【MoneyManager】"),
    REGISTER_SUCCESS("201807130249110290005","注册成功（第一次登录之后）：您已注册成功，祝您生活愉快！【MoneyManager】"),
    MODIFY_PASSWORD("201807130251139090006","修改密码：您正在修改账户密码，验证码为%s。【MoneyManager】"),
    MODIFY_SUCCESS("201807130251514350007","密码修改成功：您已成功修改盘点借账户密码，如非本人操作，请尽快找回密码。【盘点借】"),

    /**
     *  無憂貸
     */
    WYD_LOGIN_VERIFY("201807240342551900001","登录验证码: 您好，您的验证码为666666。【moneyrush】"),
    WYD_MODIFY_PWD("201807240353333890001","（修改登錄密碼和支付密碼都用此模板）修改密码: 您好，本次操作验证码为666666。【moneyrush】"),
    WYD_BORROW_MONEY_AUDIT_PASS("201807240357267080002","借款审核通过: 尊敬的用户，您好！您已通过moneyrush借款审核，请尽快提现。【moneyrush】"),
    WYD_LOAN_SUCCESS("201807240359082610002","放款成功: 尊敬的用户，您好！moneyrush已成功放款到您的借款账户****222222，请注意查收。【moneyrush】"),
    WYD_REPAYMENT_REMIND("201807240410487930003","（日期西班牙語要倒著來，替換的格式：20-03-2018；3要改成03）还款提醒: 尊敬的用户，您好！您有一笔借款即将逾期，请于2018年3月20日前还款。【moneyrush】"),
    WYD_OVERDUE_REMIND("201807240414518250003","逾期提醒: 尊敬的用户，您好！您有一笔借款逾期，请尽快还款。【MoneyRush】");

    private String code;
    private String desc;

    MsgTemplateCodeEnum(String code, String desc) {
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
