package com.panshi.hujin2.message.facade.bo;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * create by shenjiankang on 2019/1/30 15:24
 */
@Data
public class UrgentRecallMsgLogInputBO implements Serializable {
    private static final long serialVersionUID = -516643899574804637L;

    /**
     *  app
     */
    private ApplicationEnmu applicationEnmu;

    /**
     *  订单id
     */
    private Integer orderId;

    /**
     *  操作人id
     */
    private Integer operatorId;

    /**
     *  操作人姓名
     */
    private String operatorName;

    /**
     *  手机号码集合
     */
    private List<String> phoneNumbers;

    /**
     *  发送内容
     */
    private String content;

    /**
     *  发送结果
     */
    private String sendResult;

}
