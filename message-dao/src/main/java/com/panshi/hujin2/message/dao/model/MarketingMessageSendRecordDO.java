package com.panshi.hujin2.message.dao.model;

import lombok.Data;

import java.util.Date;

@Data
public class MarketingMessageSendRecordDO {
    /**
     *  id
     */
    private Long id;

    /**
     *  应用id
     */
    private Integer appId;

    /**
     *  国家编号
     */
    private Integer countryId;

    /**
     *  渠道id  10-kmi营销，13-牛信营销
     */
    private Integer channelId;

    /**
     *  手机号码
     */
    private String phoneNumber;

    /**
     *  短信内容
     */
    private String msgText;

    /**
     *  消息id
     */
    private String msgId;

    /**
     *  响应状态码
     */
    private Integer resCode;

    /**
     *  状态码说明
     */
    private String resExplain;

    /**
     *  请求短信发送接口的返回信息
     */
    private String returnValue;

    /**
     *  创建时间
     */
    private Date createTime;

    /**
     *  修改时间
     */
    private Date modifyTime;

    /**
     *  消息类型
     */
    private Integer msgType;

}