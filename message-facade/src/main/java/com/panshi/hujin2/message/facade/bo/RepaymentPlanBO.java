package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: wangxl
 * @Date: 2018/12/17 17:14
 */
@Data
public class RepaymentPlanBO implements Serializable {
    // 当前期数
    private Integer currentPeriod;

    //总期数
    private Integer totalPeriod;

    // 放款金额（融资金额）
    private Double payMoney;

    //计划应还日期
    private Date planRepaymentDate;

    //计划应还日期
    private String planRepaymentDateStr;

    //应还金额
    private Double currentRepay;

    // 逾期金额（滞纳金）
    private Double overDueFine;

    // 利息
    private Double interestMoney;
}
