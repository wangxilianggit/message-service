package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ycg 2018/11/27 14:48
 */
@Data
public class EmailOrderParamBO implements Serializable {
    private static final long serialVersionUID = -4345660977618241722L;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 取款帐号
     * 模版二使用
     */
    private String accountCard;
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

}
