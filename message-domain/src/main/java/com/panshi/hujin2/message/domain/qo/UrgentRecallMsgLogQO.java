package com.panshi.hujin2.message.domain.qo;

import lombok.Data;

import java.util.Date;

@Data
public class UrgentRecallMsgLogQO extends BaseQO{


    // 发送对象
    private String receiver;

    // 开始时间
    private Date sendTimeStart;

    // 结束时间
    private Date sendTimeEnd;

}