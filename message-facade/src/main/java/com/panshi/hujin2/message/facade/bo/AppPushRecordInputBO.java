package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * create by shenjiankang on 2018/6/26 10:15
 */
@Data
public class AppPushRecordInputBO implements Serializable {


    private static final long serialVersionUID = -6324338405554810811L;
    /**
     *  app应用id
     */
    private Integer appId;
    /**
     *  推送类型 1--单个推送；2--按app推送；3--按列表推送
     */
    private Integer pushType;
    /**
     *  推送模板类型--1，点击通知打开应用模板；--2，点击通知打开网页模板；--3，点击通知弹框下载模板；--4，透传消息模版
     */
    private Integer templateType;
    /**
     *  用户id
     */
    private Integer userId;
    /**
     *  个推业务层中的对外用户标识，用于标识客户端身份，由第三方客户端获取并保存到第三方服务端，是个推SDK的唯一识别号。
     */
    private String clientId;
    /**
     *  消息模板的編號
     */
    private String templateCode;
    /**
     *  所属的业务类型id
     */
    private Integer businessTypeId;
    /**
     *  通知栏标题
     */
    private String title;
    /**
     *  通知栏内容
     */
    private String text;
    /**
     *  透传内容
     */
    private String transmissionContent;
    /**
     *  推送响应信息
     */
    private String pushResponse;


}
