package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * create by shenjiankang on 2018/6/29 9:56
 */
@Data
public class MsgTemplateOutputBO implements Serializable {
    private static final long serialVersionUID = 2539418630288053555L;

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
//    /**
//     *  短信文本
//     */
//    private String msgText;
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


    /**
     * 国际化模板列表
     */
    private List<MsgTemplateCountryOutputBO> templateCountryOutputBOS;

}
