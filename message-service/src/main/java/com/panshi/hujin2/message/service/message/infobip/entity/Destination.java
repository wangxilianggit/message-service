package com.panshi.hujin2.message.service.message.infobip.entity;

import lombok.Data;

/**
 * create by shenjiankang on 23/07/2018 16:14
 *
 * infobip 全文本功能發送  Root  >  Message > Destination
 */
@Data
public class Destination {

    private String to;

    private String messageId;
}
