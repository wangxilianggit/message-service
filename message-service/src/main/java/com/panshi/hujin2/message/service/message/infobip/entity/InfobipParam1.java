package com.panshi.hujin2.message.service.message.infobip.entity;

import lombok.Data;

/**
 * create by shenjiankang on 2018/8/3 20:11
 *
 * infobip 短信群發，適用於多個手機號碼，不同的短信模板,作爲Messages類的 list屬性
 */
@Data
public class InfobipParam1 {
    private String from;
    private String to;
    private String text;


}
