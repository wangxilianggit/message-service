package com.panshi.hujin2.message.service.message.infobip.entity;

import lombok.Data;

import java.util.List;

/**
 * create by shenjiankang on 23/07/2018 16:14
 *
 * infobip 全文本功能發送  Root >  Message
 */
@Data
public class Message {

    private String from;

    private List<Destination> destinations ;

    private String text;

    private boolean flash;

    private Language language;

    private String transliteration;

    private boolean intermediateReport;

    private String notifyUrl;

    private String notifyContentType;

    private String callbackData;

    private int validityPeriod;
}
