package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * create by shenjiankang on 2018/7/3 21:18
 */
@Data
public class AppPushTemplateCountryUpdateBO implements Serializable {
    private static final long serialVersionUID = 2081178633644288122L;

    /**
     *  主键id
     */
    private Integer id;
    /**
     *  短信模板编号
     */
    private String templateCode;
    /**
     *  国家key
     */
    private String countryKey;
    /**
     *  国家名
     */
    private String countryName;
    /**
     *  通知栏标题
     */
    private String title;
    /**
     *  通知栏内容
     */
    private String text;
    /**
     *  通知栏内容(带样式标签)
     */
    private String textStyle;
    /**
     *  状态，0--失效，1--有效
     */
    private Boolean status;
    /**
     *  修改人
     */
    private String modifier;

}
