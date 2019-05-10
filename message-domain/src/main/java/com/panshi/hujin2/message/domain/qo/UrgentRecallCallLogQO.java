package com.panshi.hujin2.message.domain.qo;

import lombok.Data;

import java.util.Date;

@Data
public class UrgentRecallCallLogQO extends BaseQO{

    // 主叫号码
    private String callFrom;

    // 被叫号码
    private String callTo;

    // 开始呼叫时间
    private Date callTimeStartBegin;

    // 结束呼叫时间
    private Date callTimeStartEnd;

}