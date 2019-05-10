package com.panshi.hujin2.message.service.message.chuanglan.entity;

import lombok.Data;

import java.util.List;

/**
 * create by shenjiankang on 2018/8/6 17:46
 *
 * 创蓝短信api 请求参数
 */
@Data
public class RequestParamBean1 {

    /**
     *  账户
     */
    private String account;

    /**
     *  密码
     */
    private String password;

    /**
     *  短信内容
     */
    private String msg;

    /**
     *  手机号
     */
    private String mobile;
}
