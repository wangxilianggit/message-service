package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * create by shenjiankang on 25/07/2018 15:48
 */
@Data
public class EmailTemplateCountryInputBO implements Serializable {
    private static final long serialVersionUID = -1648746259709155550L;

    /**
     *  国家key
     */
    private String countryKey;
    /**
     *  国家名
     */
    private String countryName;
    /**
     *  邮件标题
     */
    private String title;
    /**
     *  邮件内容
     */
    private String text;
    /**
     *  邮件内容(带样式标签)
     */
    private String textStyle;
    /**
     *  状态，false--失效，true--有效
     */
    private Boolean status;
    /**
     *  创建人
     */
    private String creator;



}
