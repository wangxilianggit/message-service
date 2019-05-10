package com.panshi.hujin2.message.service.email.service.impl;

import com.panshi.hujin2.aws.utils.AWSS3FileFactory;
import com.panshi.hujin2.base.common.factory.MessageFactory;
import com.panshi.hujin2.base.common.util.DozerUtils;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.domain.result.BasicResultCode;
import com.panshi.hujin2.base.service.utils.ContextUtils;
import com.panshi.hujin2.message.common.utils.QrBarCodeUtil;
import com.panshi.hujin2.message.dao.mapper.email.EmailBoletoRecordMapper;
import com.panshi.hujin2.message.dao.model.EmailBoletoRecordDO;
import com.panshi.hujin2.message.facade.bo.BrEmailSendBO;
import com.panshi.hujin2.message.facade.bo.EmailOrderParamBO;
import com.panshi.hujin2.message.facade.bo.RepaymentPlanBO;
import com.panshi.hujin2.message.service.email.emailhtml.*;
import com.panshi.hujin2.message.service.email.model.HtmlTemplateEntity;
import com.panshi.hujin2.message.service.email.service.EmailObtainBrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

/**
 * @author ycg 2018/11/30 15:29
 */
@Service
public class EmailObtainBrServiceImpl implements EmailObtainBrService {
    private static Logger LOGGER = LoggerFactory.getLogger(EmailObtainBrServiceImpl.class);
    @Autowired
    private AWSS3FileFactory awss3FileFactory;
//    @Autowired
//    private IBMPChannelFacade ibmpChannelFacade;
    @Autowired
    private EmailBoletoRecordMapper emailBoletoRecordMapper;

//    @Override
//    public BasicResult<HtmlTemplateEntity> emailObtainBrHtmlTemplate(BrEmailSendBO brEmailSendBO) {
//        try {
//            String boletoUrl = null;
//            String boletoString = null;
//            if (brEmailSendBO.getIsParam()) {
//                String orderNo = brEmailSendBO.getEmailOrderParamBO().getOrderNo();
//                EmailBoletoRecordDO emailBoletoRecordDO = emailBoletoRecordMapper.getOrderNo(orderNo);
//                if (emailBoletoRecordDO != null) {
//                    boletoString = emailBoletoRecordDO.getBoleteCode();
//                    boletoUrl = awss3FileFactory.getUrl(emailBoletoRecordDO.getBoleteUrl());
//                } else {
//                    BasicResult<String> ccbCodeBasicResult = ibmpChannelFacade.getBoleto(orderNo, ContextUtils.getDefaultContext());
//                    if (ccbCodeBasicResult.getSuccess()) {
//                        // 转成一维码图片,转换成输入流上传到亚马逊
//                        boletoString = ccbCodeBasicResult.getData().replace("\n", " ");
//                        if (boletoString.length() > 80){
//                            return BasicResult.error(BasicResultCode.ERROR.getCode(), MessageFactory.getMsg("G19770112"));
//                        }
//                        InputStream barcodeWriteFile = QrBarCodeUtil.getBarcodeWriteFile(boletoString, 200, 200);
//                        String uploadUrl = awss3FileFactory.upload(barcodeWriteFile);
//                        EmailBoletoRecordDO emailBoletoRecord = new EmailBoletoRecordDO();
//                        emailBoletoRecord.setOrderNo(orderNo);
//                        emailBoletoRecord.setBoleteUrl(uploadUrl);
//                        emailBoletoRecord.setBoleteCode(boletoString);
//                        emailBoletoRecordMapper.save(emailBoletoRecord);
//                        boletoUrl = awss3FileFactory.getUrl(uploadUrl);
//                    }
//                }
//            }
//            EmailTemplateEntity emailTemplateEntity = new EmailTemplateEntity();
//            emailTemplateEntity.setBoleteCode(boletoString);
//            emailTemplateEntity.setAppName(brEmailSendBO.getAppName());
//            emailTemplateEntity.setUserName(brEmailSendBO.getUserName());
//            EmailOrderParamBO emailOrderParamBO = brEmailSendBO.getEmailOrderParamBO();
//            if (emailOrderParamBO != null) {
//                emailTemplateEntity.setOrderNo(emailOrderParamBO.getOrderNo());
//                emailTemplateEntity.setPayDate(emailOrderParamBO.getPayDate());
//                emailTemplateEntity.setRepaymentDate(emailOrderParamBO.getRepaymentDate());
//                emailTemplateEntity.setLoanMoney(emailOrderParamBO.getLoanMoney());
//                emailTemplateEntity.setLoanInterest(emailOrderParamBO.getLoanInterest());
//                emailTemplateEntity.setOtherExpenses(emailOrderParamBO.getOtherExpenses());
//                emailTemplateEntity.setOverDueFine(emailOrderParamBO.getOverDueFine());
//                emailTemplateEntity.setCurrentRepay(emailOrderParamBO.getCurrentRepay());
//                emailTemplateEntity.setDueDays(emailOrderParamBO.getDueDays());
//                emailTemplateEntity.setDayInterest(emailOrderParamBO.getDayInterest());
//                emailTemplateEntity.setAccountCard(emailOrderParamBO.getAccountCard());
//                emailTemplateEntity.setTechnologyChargeRate(emailOrderParamBO.getTechnologyChargeRate());
//                emailTemplateEntity.setAuditChargeRate(emailOrderParamBO.getAuditChargeRate());
//                emailTemplateEntity.setOverDueDays(emailOrderParamBO.getOverDueDays());
//            }
//            // 还款计划
//            List<RepaymentPlanBO> repaymentPlanBOList = brEmailSendBO.getRepaymentPlanBOList();
//            if(repaymentPlanBOList!=null && repaymentPlanBOList.size()>0){
//                List<RepaymentPlan> repaymentPlanList = DozerUtils.convertList(repaymentPlanBOList, RepaymentPlan.class);
//                emailTemplateEntity.setRepaymentPlanList(repaymentPlanList);
//            }
//
//            String subject = "";
//            String templateHtml = "";
//            switch (brEmailSendBO.getTemplateId()) {
//                case 1:
//                    subject = EmailTemplate1.getSubject1(brEmailSendBO.getAppName());
//                    templateHtml = EmailTemplate1.getHtml1(emailTemplateEntity);
//                    break;
//                case 2:
//                    subject = EmailTemplate2.getSubject2(brEmailSendBO.getAppName());
//                    templateHtml = EmailTemplate2.getHtml2(emailTemplateEntity, boletoUrl);
//                    break;
//                case 3:
//                    subject = EmailTemplate3.getSubject3(brEmailSendBO.getAppName());
//                    templateHtml = EmailTemplate3.getHtml3(emailTemplateEntity, boletoUrl);
//                    break;
//                case 4:
//                    subject = EmailTemplate4.getSubject4(brEmailSendBO.getAppName());
//                    templateHtml = EmailTemplate4.getHtml4(emailTemplateEntity, boletoUrl);
//                    break;
//                case 5:
//                    subject = EmailTemplate5.getSubject5(brEmailSendBO.getAppName());
//                    templateHtml = EmailTemplate5.getHtml5(emailTemplateEntity, boletoUrl);
//                    break;
//                case 6:
//                    subject = EmailTemplate6.getSubject6(brEmailSendBO.getAppName());
//                    templateHtml = EmailTemplate6.getHtml6(emailTemplateEntity, boletoUrl);
//                    break;
//                case 7:
//                    subject = EmailTemplate7.getSubject7(brEmailSendBO.getAppName());
//                    templateHtml = EmailTemplate7.getHtml7(emailTemplateEntity, boletoUrl);
//                    break;
//                case 8:
//                    subject = EmailTemplate8.getSubject8(brEmailSendBO.getAppName());
//                    templateHtml = EmailTemplate8.getHtml8(emailTemplateEntity, boletoUrl);
//                    break;
//                default:
//                    new NullPointerException();
//            }
//
//            return BasicResult.ok(new HtmlTemplateEntity(subject, templateHtml));
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage(), e);
//            return BasicResult.error(BasicResultCode.ERROR.getCode(), MessageFactory.getMsg("G19770110"));
//        }
//    }
}
