package com.panshi.hujin2.message.domain.qo;

import lombok.Data;

/**
 * @author shenJianKang
 * @date 2019/8/12 20:18
 */
@Data
public class MsgSendStatisticsQO  extends BaseQO{

    /**
     *  商户id
     */
    private Integer consumerId;

    /**
     *  队列id
     */
    private Integer queueId;

    /**
     *  手机号
     */
    private String phoneNumber;

    /**
     *  发送失败次数
     */
    private Integer failCount;


}
