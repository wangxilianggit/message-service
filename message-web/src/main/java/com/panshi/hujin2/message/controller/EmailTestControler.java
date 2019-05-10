package com.panshi.hujin2.message.controller;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.service.utils.ContextUtils;
import com.panshi.hujin2.message.controller.utils.VestUtils;
import com.panshi.hujin2.message.facade.IEmailSendFacade;
import com.panshi.hujin2.message.facade.bo.BrEmailSendBO;
import com.panshi.hujin2.message.facade.bo.EmailOrderParamBO;
import com.panshi.hujin2.message.service.email.emailhtml.BrMoneyFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ycg 2018/11/28 21:03
 */
@RestController
@RequestMapping("/email")
public class EmailTestControler {
    @Autowired
    IEmailSendFacade emailSendFacade;

    @GetMapping("/test1")
    public BasicResult<Void> getEmailTest1() {
        BrEmailSendBO brEmailSendBO = new BrEmailSendBO();
        brEmailSendBO.setUserName("审核通过");
        ApplicationEnmu appEnum = VestUtils.getAppEnumByVest(1);
        brEmailSendBO.setAppName(appEnum.getDesc());
        EmailOrderParamBO emailOrderParamBO = new EmailOrderParamBO();
        emailOrderParamBO.setOrderNo("br964363387708954");
        brEmailSendBO.setEmailOrderParamBO(emailOrderParamBO);
        brEmailSendBO.setTemplateId(1);
        brEmailSendBO.setEmailAddress("yechanggui@adpanshi.com");
//        emailSendFacade.sendEmailBr(brEmailSendBO, ContextUtils.getDefaultContext());
        return BasicResult.ok();
    }

    @GetMapping("/test2")
    public BasicResult<Void> getEmailTest2() {
        BrEmailSendBO brEmailSendBO = new BrEmailSendBO();
        brEmailSendBO.setUserName("放款成功");
        ApplicationEnmu appEnum = VestUtils.getAppEnumByVest(1);
        brEmailSendBO.setAppName(appEnum.getDesc());
        brEmailSendBO.setIsParam(Boolean.TRUE);
        EmailOrderParamBO emailOrderParamBO = new EmailOrderParamBO();
        emailOrderParamBO.setPayDate("30/11/2018");
        emailOrderParamBO.setLoanMoney(3000.0D);
        emailOrderParamBO.setDueDays(7);
        emailOrderParamBO.setDayInterest(1D);
        emailOrderParamBO.setLoanInterest(100D);
        emailOrderParamBO.setOtherExpenses(0D);
        emailOrderParamBO.setCurrentRepay(3100D);
        emailOrderParamBO.setRepaymentDate("7/12/2018");
        emailOrderParamBO.setAccountCard("56494313167416");

        emailOrderParamBO.setOrderNo("br964363387708954");
        brEmailSendBO.setEmailOrderParamBO(emailOrderParamBO);
        brEmailSendBO.setTemplateId(2);
        brEmailSendBO.setEmailAddress("yechanggui@adpanshi.com");
//        brEmailSendBO.setEmailAddress("chuminghua@adpanshi.com");
//        emailSendFacade.sendEmailBr(brEmailSendBO, ContextUtils.getDefaultContext());
        return BasicResult.ok();
    }

    @GetMapping("/test3")
    public BasicResult<Void> getEmailTest3() {
        BrEmailSendBO brEmailSendBO = new BrEmailSendBO();
        brEmailSendBO.setUserName("账单即将逾期");
        ApplicationEnmu appEnum = VestUtils.getAppEnumByVest(1);
        brEmailSendBO.setAppName(appEnum.getDesc());
        brEmailSendBO.setIsParam(Boolean.TRUE);
        EmailOrderParamBO emailOrderParamBO = new EmailOrderParamBO();
        emailOrderParamBO.setPayDate("30/11/2018");
        emailOrderParamBO.setRepaymentDate("7/12/2018");
        emailOrderParamBO.setLoanMoney(3000.0D);
        emailOrderParamBO.setDueDays(7);
        emailOrderParamBO.setDayInterest(1D);
        emailOrderParamBO.setLoanInterest(100D);
        emailOrderParamBO.setOtherExpenses(0D);
        emailOrderParamBO.setOverDueFine(150D);
        emailOrderParamBO.setCurrentRepay(3250D);

        emailOrderParamBO.setOrderNo("br964364277208239");
        brEmailSendBO.setEmailOrderParamBO(emailOrderParamBO);
        brEmailSendBO.setTemplateId(3);
        brEmailSendBO.setEmailAddress("yechanggui@adpanshi.com");
//        emailSendFacade.sendEmailBr(brEmailSendBO, ContextUtils.getDefaultContext());
        return BasicResult.ok();
    }

    @GetMapping("/test4")
    public BasicResult<Void> getEmailTest4() {
        BrEmailSendBO brEmailSendBO = new BrEmailSendBO();
        brEmailSendBO.setUserName("账单逾期");
        ApplicationEnmu appEnum = VestUtils.getAppEnumByVest(1);
        brEmailSendBO.setAppName(appEnum.getDesc());
        brEmailSendBO.setIsParam(Boolean.TRUE);
        EmailOrderParamBO emailOrderParamBO = new EmailOrderParamBO();
        emailOrderParamBO.setPayDate("30/11/2018");
        emailOrderParamBO.setRepaymentDate("7/12/2018");
        emailOrderParamBO.setLoanMoney(3000.0D);
        emailOrderParamBO.setLoanInterest(100D);
        emailOrderParamBO.setOtherExpenses(0D);
        emailOrderParamBO.setOverDueFine(150D);
        emailOrderParamBO.setCurrentRepay(3250D);

        emailOrderParamBO.setOrderNo("br964364277208239");
        brEmailSendBO.setEmailOrderParamBO(emailOrderParamBO);
        brEmailSendBO.setTemplateId(4);
        brEmailSendBO.setEmailAddress("yechanggui@adpanshi.com");
//        emailSendFacade.sendEmailBr(brEmailSendBO, ContextUtils.getDefaultContext());
        return BasicResult.ok();
    }

    @GetMapping("/test5")
    public BasicResult<Void> getEmailTest5() {
        BrEmailSendBO brEmailSendBO = new BrEmailSendBO();
        brEmailSendBO.setUserName("发送Boleto到邮箱");
        ApplicationEnmu appEnum = VestUtils.getAppEnumByVest(1);
        brEmailSendBO.setAppName(appEnum.getDesc());
        brEmailSendBO.setIsParam(Boolean.TRUE);
        EmailOrderParamBO emailOrderParamBO = new EmailOrderParamBO();
        emailOrderParamBO.setOrderNo("br964364277208239");
        brEmailSendBO.setEmailOrderParamBO(emailOrderParamBO);
        brEmailSendBO.setTemplateId(5);
        brEmailSendBO.setEmailAddress("yechanggui@adpanshi.com");
//        emailSendFacade.sendEmailBr(brEmailSendBO, ContextUtils.getDefaultContext());
        return BasicResult.ok();
    }

    public static void main(String[] args) {
        double d = 0;
        String string = BrMoneyFormatUtil.formatMoney(d);
        System.out.println(string);
    }

}
