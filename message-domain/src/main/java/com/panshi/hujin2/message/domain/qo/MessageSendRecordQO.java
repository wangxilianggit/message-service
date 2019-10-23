package com.panshi.hujin2.message.domain.qo;

import com.panshi.hujin2.base.domain.qo.BaseQO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author shenJianKang
 * @date 2019/8/21 16:10
 */
@Data
public class MessageSendRecordQO extends BaseQO {

    /**
     *  id
     */
    private Long id;

    /**
     *  商户id
     */
    private Integer consumerId;

    /**
     *  子商户id list
     */
    private List<Integer> currentConsumerIdList;

    /**
     *  队列id
     */
    private Integer queueId;

    /**
     *  消耗费用
     */
    private Double fee;

//    /**
//     *  应用id 如：（1.信用卡管家，）
//     */
//    private Integer appId;
//
//    /**
//     *  国家编号
//     */
//    private Integer countryId;

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
     *  响应状态码  发送结果:和前端约定 0-成功；1-失败；
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
     *  创建时间 - 起始時間
     */
    private Date createTimeStart;

    /**
     *  创建时间 - 結束時間
     */
    private Date createTimeEnd;

}
