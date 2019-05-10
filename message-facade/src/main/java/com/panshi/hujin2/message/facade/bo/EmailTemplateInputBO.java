package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * create by shenjiankang on 25/07/2018 19:37
 */
@Data
public class EmailTemplateInputBO implements Serializable {
    private static final long serialVersionUID = -874999190259574644L;

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
     *  国际化语言模板
     */
    List<EmailTemplateCountryInputBO> emailTemplateCountryInputBOS;

}
