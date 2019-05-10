package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * create by shenjiankang on 2019/1/30 15:47
 */
@Data
public class UrgentRecallMsgLogOutputBO implements Serializable {
    private static final long serialVersionUID = -8583845793409413047L;

    /**
     *  主键id
     */
    private Integer id;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     *  发送人id
     */
    private Integer operatorId;

    /**
     *  发送人姓名
     */
    private String operatorName;

    /**
     *  发送目标手机号码
     */
    private String targetPhoneNumber;

    /**
     *  短信内容
     */
    private String content;

    /**
     *  短信发送结果
     */
    private String sendResult;

    /**
     *  创建时间
     */
    private Date createTime;

    /**
     *  更新时间
     */
    private Date modifyTime;
}
