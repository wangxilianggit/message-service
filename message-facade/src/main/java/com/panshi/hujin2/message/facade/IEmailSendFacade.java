package com.panshi.hujin2.message.facade;

import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.facade.bo.BrEmailSendBO;

import java.util.List;

/**
 * create by shenjiankang on 2018/7/21 15:28
 *
 * 发送邮件
 */
public interface IEmailSendFacade {

    /**
     *@Description:
     *@Param:  * @param address
     * @param subject
     * @param htmlContent
     *@Author: shenJianKang
     *@date: 2019/11/18 14:43
     */
    BasicResult<Void> sendEmail(String address,String subject,String htmlContent);



//    /**
//     * 发送巴西的邮件
//     * @param brEmailSendBO
//     * @param context
//     * @return
//     */
//    BasicResult<Void> sendEmailBr(BrEmailSendBO brEmailSendBO, Context context);
//
//    /**
//     * 发送巴西的邮件
//     * 内部循环调用发送
//     * @param brEmailSendBOS
//     * @param context
//     * @return
//     */
//    BasicResult<Void> batchSendEmailBr(List<BrEmailSendBO> brEmailSendBOS, Context context);


}
