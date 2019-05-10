package com.panshi.hujin2.message.service.message.infobip.entity;

import lombok.Data;

import java.util.List;

/**
 * create by shenjiankang on 2018/8/3 20:19
 *
 * infobip 短信群發，適用於多個手機號碼，不同的短信模板。
 */
@Data
public class Messages {

    List<InfobipParam1> messages;
}
