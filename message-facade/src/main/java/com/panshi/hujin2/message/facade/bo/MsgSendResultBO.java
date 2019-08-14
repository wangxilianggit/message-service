package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

/**
 * @author shenJianKang
 * @date 2019/8/13 18:37
 */
@Data
public class MsgSendResultBO {

    /**
     *  发送条数
     */
    private Integer totalSendCount;

    /**
     *  成功条数
     */
    private Integer successSendCount;

    /**
     *  失败条数
     */
    private Integer failSendCount;

    /**
     *  消费费用
     */
    private Double fee;
}
