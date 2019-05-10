package com.panshi.hujin2.message.service.email.emailhtml;

import lombok.Data;

import java.util.List;

/**
 * @author ycg 2018/11/29 22:21
 */
@Data
public class EmailTemplateEntity {
    /**
     * app 名字
     */
    private String appName;
    /**
     * 用户名字
     */
    private String userName;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 借款日期
     */
    private String payDate;
    /**
     * 应还日期
     */
    private String repaymentDate;
    /**
     * 借款金额
     */
    private Double loanMoney;
    /**
     * 借款利息
     */
    private Double loanInterest;
    /**
     * 其他费用
     */
    private Double otherExpenses;
    /**
     * 预估逾期费用
     */
    private Double overDueFine;
    /**
     * 实际应还金额
     */
    private Double currentRepay;
    /**
     * 借款天数(借款期限)
     */
    private Integer dueDays;
    /**
     * 借款月数(借款期限)
     */
    private Integer loanMonths;
    /**
     * 借款日利息
     */
    private Double dayInterest;
    /**
     * 取款帐号
     * 模版二使用
     */
    private String accountCard;

    private String boleteCode;

    /**
     * 每笔审核服务费率
     */
    private Double auditChargeRate;

    /**
     * 每笔技术服务费率
     */
    private Double technologyChargeRate;
    /**
     * 逾期天数
     */
    private Integer overDueDays;

    /**
     * 还款计划
     */
    private List<RepaymentPlan> repaymentPlanList;

}
