package com.panshi.hujin2.message.domain.qo;

import lombok.Data;

/**
 * create by shenjiankang on 25/07/2018 17:50
 */
@Data
public class EmailTemplateQO extends BaseQO{

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
     *  修改人
     */
    private String modifier;

    //country的查询参数
    /**
     *  国家key
     */
    private String countryKey;
    /**
     *  国家名
     */
    private String countryName;


}
