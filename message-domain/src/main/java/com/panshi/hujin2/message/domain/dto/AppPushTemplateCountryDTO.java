package com.panshi.hujin2.message.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * create by shenjiankang on 2018/7/5 14:33
 */
@Data
public class AppPushTemplateCountryDTO {

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
