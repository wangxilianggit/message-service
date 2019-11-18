package com.panshi.hujin2.message.service.email.facadeimpl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
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
//    @Autowired
//    EmailSendBrService emailSendBrService;


    public static void main(String[] args) {
        String destAddr = "shenjiankang0292@dingtalk.com";
        new EmailSendFacadeImpl().sendEmail(destAddr, "test邮件","测试邮件发送");
    }

    @Override
    public BasicResult<Void> sendEmail(String address,String subject,String htmlContent) {
        // 如果是除杭州region外的其它region（如新加坡、澳洲Region），需要将下面的"cn-hangzhou"替换为"ap-southeast-1"、或"ap-southeast-2"。
//        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIpBIvcbLzIg00", "wXX8AARHxujKZLHMpefrfgJwG8pvCr");
        IClientProfile profile = DefaultProfile.getProfile("ap-southeast-1", "LTAIpBIvcbLzIg00", "wXX8AARHxujKZLHMpefrfgJwG8pvCr");

//        aliyun.oss.accessKeyID=LTAIpBIvcbLzIg00
//        aliyun.oss.accessKeySecret=wXX8AARHxujKZLHMpefrfgJwG8pvCr

        // 如果是除杭州region外的其它region（如新加坡region）， 需要做如下处理------印尼也不需要打开
//        try {
//            DefaultProfile.addEndpoint("dm.ap-southeast-1.aliyuncs.com", "ap-southeast-1", "Dm", "dm.ap-southeast-1.aliyuncs.com");
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }

        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            //request.setVersion("2017-06-22");// 如果是除杭州region外的其它region（如新加坡region）,必须指定为2017-06-22
//            request.setVersion("2017-06-22");
            request.setAccountName("admin@sendmessage.dolphinft.com");
            request.setFromAlias("轩邈科技");
            request.setAddressType(1);
//            request.setTagName("控制台创建的标签");
            request.setReplyToAddress(true);
            request.setToAddress(address);
            //可以给多个收件人发送邮件，收件人之间用逗号分开，批量发信建议使用BatchSendMailRequest方式
            //request.setToAddress("邮箱1,邮箱2");
            request.setSubject(subject);
            request.setHtmlBody(htmlContent);
            //开启需要备案，0关闭，1开启
            //request.setClickTrace("0");
            //如果调用成功，正常返回httpResponse；如果调用失败则抛出异常，需要在异常中捕获错误异常码；错误异常码请参考对应的API文档;
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
            LOGGER.info(httpResponse.toString());

        } catch (ServerException e) {
            //捕获错误异常码
            LOGGER.error("ErrCode : " + e.getErrCode());
            e.printStackTrace();
        } catch (ClientException e) {
            //捕获错误异常码
            LOGGER.error("ErrCode : " + e.getErrCode());
            e.printStackTrace();
        }

        return null;
    }

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
