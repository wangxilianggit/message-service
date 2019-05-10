package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

/**
 * create by shenjiankang on 2018/6/23 16:11
 */
@Data
public class SubmailMsgSubhookInputBO {

    /**
     *  事件类型
     */
    private String events;
    /**
     *  此联系人的手机号码
     */
    private String phoneNumber;
    /**
     *  应用ID
     */
    private String app;
    /**
     *  该条短信的唯一发送标识，可在 API 请求时获取
     */
    private String sendId;
    /**
     *  事件触发时间（此时间戳为此事件本身的触发时间，不参与计算签名）
     */
    private Integer eventTimestamp;
    /**
     *  网关失败回执
     */
    private String report;
    /**
     *  短信内容(只有在事件是''短信上行接口推送mo''时才会有该字段)
     */
    private String content;
    /**
     *  模板ID（只有在事件是''审核模板''时才有该字段）
     */
    private String templateId;
    /**
     *  审核未通过原因(只有在事件是''审核模板不通过''时才有该字段)
     */
    private String reason;

}
