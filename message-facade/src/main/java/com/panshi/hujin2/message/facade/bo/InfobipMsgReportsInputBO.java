package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * create by shenjiankang on 2018/7/19 20:43
 */

@Data
public class InfobipMsgReportsInputBO implements Serializable {

    private static final long serialVersionUID = 5690071793876127938L;
    /**
     *  id
     */
    private Integer id;
    /**
     *  唯一标识已发送请
     */
    private String bulkId;
    /**
     *  手机号码
     */
    private String phoneNumber;
    /**
     *  消息ID
     */
    private String messageId;
    /**
     *
     */
    private String sentAt;
    /**
     *
     */
    private String doneAt;
    /**
     *  消息数量
     */
    private Integer smsCount;
    /**
     *  短信每条单价
     */
    private String pricePerMessage;
    /**
     *  货币
     */
    private String currency;
    /**
     *  自定义的数据
     */
    private String callbackData;
    /**
     *  发送状态id，详细见infobip文档
     */
    private Integer statusGroupId;
    /**
     *
     */
    private String statusGroupName;
    /**
     *
     */
    private Integer statusId;
    /**
     *
     */
    private String statusName;
    /**
     *
     */
    private String statusDescription;
    /**
     *  错误id，详细见infobip文档
     */
    private Integer errorGroupId;
    /**
     *
     */
    private String errorGroupName;
    /**
     *
     */
    private Integer errorId;
    /**
     *
     */
    private String errorName;
    /**
     *
     */
    private String errorDescription;
    /**
     *
     */
    private Boolean errorPermanent;
    /**
     *  创建时间
     */
    private Date createTime;
}
