package com.panshi.hujin2.message.dao.model;

import lombok.Data;

import java.util.Date;

@Data
public class SmsChannelConfigDO {
    /**
     *  id
     */
    private Integer id;

    /**
     *  优先级,数字越小，优先级越高
     */
    private Integer priority;

    /**
     *  短信渠道, 同枚举 @SEE ChannelEnum text
     */
    private String msgChannel;

    /**
     *  状态：0-失效；1-有效；
     */
    private Integer status;

    /**
     *  创建时间
     */
    private Date createTime;

    /**
     *  更新时间
     */
    private Date modifyTime;







}