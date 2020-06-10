package com.panshi.hujin2.message.facade.bo;

import com.panshi.hujin2.message.domain.enums.MsgTypeEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author shenJianKang
 * @date 2019/8/21 16:23
 */
@Data
public class MessageSendRecordBO {
    /**
     *  id
     */
    private Long id;

    /**
     *  商户id
     */
    private Integer consumerId;

    /**
     *  队列id
     */
    private Integer queueId;

    /**
     *  消耗费用
     */
    private Double fee;

    /**
     *  应用id 如：（1.信用卡管家，）
     */
    private Integer appId;

    /**
     *  国家编号
     */
    private Integer countryId;

    /**
     *  渠道id：7-kmi;8-ina_sms;
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
     * 短信类型
     * @see MsgTypeEnum
     */
    private Integer msgType;
}
