package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * create by shenjiankang on 2018/6/22 17:24
 */
@Data
public class MessageSendRecordInputBO {

    /**
     *  应用id 如：（1.信用卡管家，）
     */
    private Integer appId;
    /**
     *  国家编号
     */
    private Integer countryId;
    /**
     *  渠道id（具体发送短信的渠道）
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

}
