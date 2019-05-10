package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * create by shenjiankang on 2018/6/29 9:55
 */
@Data
public class MsgTemplateUpdateBO implements Serializable {
    private static final long serialVersionUID = 3872935078758176761L;

    /**
     *  主键id
     */
    private Integer id;
    /**
     *  app应用id
     */
    private Integer appId;
    /**
     *  短信所属的业务id  关联msg_business_type主键id
     */
    private Integer msgBusinessTypeId;
    /**
     *  短信模板编号
     */
    private String templateCode;
    /**
     *  短信文本
     */
    private String msgText;
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
     *  修改人
     */
    private String modifier;

    /**
     *  国际化模板修改对象
     */
    List<MsgTemplateCountryUpdateBO> msgTemplateCountryUpdateBOS;


}
