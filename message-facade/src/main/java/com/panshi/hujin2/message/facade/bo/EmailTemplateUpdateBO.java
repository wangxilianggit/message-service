package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * create by shenjiankang on 25/07/2018 19:38
 */
@Data
public class EmailTemplateUpdateBO implements Serializable {
    private static final long serialVersionUID = -4439501751003908242L;

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
     *  修改人
     */
    private String modifier;

    //国际化模板更新对象
    List<EmailTemplateCountryUpdateBO> emailTemplateCountryUpdateBOS;


}
