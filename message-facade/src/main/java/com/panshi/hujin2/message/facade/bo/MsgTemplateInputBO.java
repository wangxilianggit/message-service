package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * create by shenjiankang on 2018/6/29 9:54
 */
@Data
public class MsgTemplateInputBO implements Serializable {
    private static final long serialVersionUID = -5159155503414118031L;


    /**
     *  app应用id
     */
    private Integer appId;
    /**
     *  短信所属的业务id  关联msg_business_type主键id
     */
    private Integer msgBusinessTypeId;
    /**
     *  备注
     */
    private String remark;
    /**
     *  是否为验证码短信。0--否，1--是。
     */
    private Boolean isVerifyCode;
    /**
     *  状态，0--失效，1--有效
     */
    private Boolean status;
    /**
     *  创建人
     */
    private String creator;



    /**
     * 国际化模板表参数
     */
    List<MsgTemplateCountryInputBO> templateCountryInputBOS;





}
