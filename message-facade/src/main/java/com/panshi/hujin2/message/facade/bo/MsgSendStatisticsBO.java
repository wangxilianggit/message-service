package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

/**
 * @author shenJianKang
 * @date 2019/8/12 20:10
 */
@Data
public class MsgSendStatisticsBO {

    /**
     *  接收人号码
     */
    private String phoneNumber;

    /**
     *  累计发送次数
     */
    private Integer totalSendCount;

    /**
     *  累计发送成功次数
     */
    private Integer successSendCount;

    /**
     *  累计发送失败次数
     */
    private Integer failSendCount;
}
