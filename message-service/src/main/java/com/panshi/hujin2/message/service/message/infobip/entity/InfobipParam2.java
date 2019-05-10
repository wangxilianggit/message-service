package com.panshi.hujin2.message.service.message.infobip.entity;

import lombok.Data;

import java.util.List;

/**
 * create by shenjiankang on 2018/8/2 15:11
 *
 * infobip 短信群發，適用於多個手機號碼，相同的短信模板，相同的替換參數。（注意：如果是多個發送目標，每個目標參數不一樣，不能用這個類）
 */
@Data
public class InfobipParam2 {
    private String from;
    private List<String> to;
    private String text;

}
