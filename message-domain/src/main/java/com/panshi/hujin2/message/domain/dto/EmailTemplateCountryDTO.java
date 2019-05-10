package com.panshi.hujin2.message.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * create by shenjiankang on 25/07/2018 17:25
 */
@Data
public class EmailTemplateCountryDTO {
    /**
     *  主键id
     */
    private Integer id;
    /**
     *  邮件模板编号
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
    /**
     *  创建时间
     */
    private Date createTime;
    /**
     *  修改人
     */
    private String modifier;
    /**
     *  修改时间
     */
    private Date modifyTime;

}
