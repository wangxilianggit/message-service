package com.panshi.hujin2.message.service.email.facadeimpl;

import com.panshi.hujin2.base.common.factory.MessageFactory;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.domain.result.BasicResultCode;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.facade.IEmailSendFacade;
import com.panshi.hujin2.message.facade.bo.BrEmailSendBO;
import com.panshi.hujin2.message.service.email.service.EmailSendBrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by shenjiankang on 2018/7/21 15:30
 */
@Service
public class EmailSendFacadeImpl implements IEmailSendFacade {
    private static Logger LOGGER = LoggerFactory.getLogger(EmailSendFacadeImpl.class);
    @Autowired
    EmailSendBrService emailSendBrService;

//    @Override
//    public BasicResult<Void> sendEmailBr(BrEmailSendBO brEmailSendBO, Context context) {
//        try {
//            emailSendBrService.sendMailPictureEnclosure(brEmailSendBO);
//            return BasicResult.ok();
//        } catch (Exception e) {
//            return BasicResult.error(BasicResultCode.ERROR.getCode(), MessageFactory.getMsg("G19770108", context.getLocale()));
//        }
//    }
//
//    @Override
//    public BasicResult<Void> batchSendEmailBr(List<BrEmailSendBO> brEmailSendBOS, Context context) {
//        try {
//            if (brEmailSendBOS.size() > 0) {
//                for (BrEmailSendBO brEmailSendBO : brEmailSendBOS) {
//                   emailSendBrService.sendMailPictureEnclosure(brEmailSendBO);
//                }
//            }
//            return BasicResult.ok();
//        } catch (Exception e) {
//            return BasicResult.error(BasicResultCode.ERROR.getCode(), MessageFactory.getMsg("G19770108", context.getLocale()));
//        }
//    }
}
