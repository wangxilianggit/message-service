package com.panshi.hujin2.message.domain.enums;

/**
 * create by shenjiankang on 2018/6/20 14:54
 *
 * 创蓝253国际短信提交响应状态码
 *
 *       0	提交成功
 *     101	账号不存在
 *     102	密码错误
 *     106	短信内容长度错误(>536)
 *     108	手机号码格式错误(>20或<5)
 *     110	余额不足
 *     112	产品配置错误
 *     114	请求ip和绑定ip不一致
 *     115	没有开通国内短信权限
 *     123	短信内容不能为空
 *     128	账号长度错误(>50或<=0)
 *     129	产品价格配置错误
 *
 */
public enum ChuangLanIntResponseEnum {



    SUCCESS(0,"提交成功"),
    ACCOUNT_NOT_EXIST(101,"账号不存在");

    private Integer code;
    private String text;

    ChuangLanIntResponseEnum(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
