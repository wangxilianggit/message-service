package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ycg 2018/11/27 14:41
 */
@Data
public class BrEmailSendBO implements Serializable {
    private static final long serialVersionUID = -3851762113920671392L;
    /**
     *  app应用id
     */
    private String appName;
    /**
     * 用户名字
     */
    private String userName;
    /**
     * 邮件 收件人地址
     */
    private String emailAddress;
    /**
     * 模版id
     * EmailTemplateEnum
     */
    private int templateId;
    /**
     *  是否
     */
    private Boolean isParam = Boolean.FALSE;
    /**
     * 订单参数
     */
    private EmailOrderParamBO emailOrderParamBO;

    /**
     * 还款计划
     */
    private List<RepaymentPlanBO> repaymentPlanBOList;
    /**
     * 是否有图片
     */
    private Boolean isImages = Boolean.FALSE;
    /**
     * 图片地址
     */
    private String imagesUrl;
    /**
     * 是否有附件
     */
    private Boolean isDoc = Boolean.FALSE;
    /**
     * 附件地址
     */
    private String docUrl;
}
