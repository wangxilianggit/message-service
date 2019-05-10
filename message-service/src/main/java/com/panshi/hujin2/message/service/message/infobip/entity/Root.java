package com.panshi.hujin2.message.service.message.infobip.entity;

import lombok.Data;

import java.util.List;

/**
 * create by shenjiankang on 23/07/2018 16:05
 *
 * infobip 全文本功能發送的請求對象
 */
@Data
public class Root {

    private String bulkId;

    private List<Message> messages ;

    private Tracking tracking;

}
