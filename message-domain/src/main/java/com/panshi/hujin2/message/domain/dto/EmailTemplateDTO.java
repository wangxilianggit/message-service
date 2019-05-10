package com.panshi.hujin2.message.domain.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * create by shenjiankang on 25/07/2018 17:24
 */
@Data
public class EmailTemplateDTO {
    /**
     *  id
     */
    private Integer id;
    /**
     *  app应用id
     */
    private Integer appId;
    /**
     *  邮件模板的編號
     */
    private String templateCode;
    /**
     *  邮件对应的业务类型，关联email_push_business_type主键
     */
    private Integer businessTypeId;
    /**
     *  主题
     */
    private String title;
    /**
     *  内容
     */
    private String text;
    /**
     *  备注
     */
    private String remark;
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

    List<EmailTemplateCountryDTO> templateCountryDTOS;
}
