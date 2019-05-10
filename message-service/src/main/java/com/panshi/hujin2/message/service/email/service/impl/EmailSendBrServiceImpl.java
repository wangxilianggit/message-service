package com.panshi.hujin2.message.service.email.service.impl;

import com.panshi.hujin2.base.common.factory.MessageFactory;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.domain.result.BasicResultCode;
import com.panshi.hujin2.message.facade.bo.BrEmailSendBO;
import com.panshi.hujin2.message.service.email.model.HtmlTemplateEntity;
import com.panshi.hujin2.message.service.email.service.EmailObtainBrService;
import com.panshi.hujin2.message.service.email.service.EmailSendBrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * @author ycg 2018/11/27 11:39
 */
@Service
public class EmailSendBrServiceImpl implements EmailSendBrService {
    private static Logger LOGGER = LoggerFactory.getLogger(EmailSendBrServiceImpl.class);
    @Value("${amazon.sender.email}")
    private String senderEmail;

    @Value("${amazon.port}")
    private String amazonPort;

    @Value("${amazon.smtp.username}")
    private String smtpUserName;

    @Value("${amazon.smtp.password}")
    private String smtpPassword;

    @Value("${amazon.host}")
    private String amazonHost;
    @Autowired
    private EmailObtainBrService emailObtainBrService;

//    @Override
//    public BasicResult<Void> sendMailPictureEnclosure(BrEmailSendBO brEmailSendBO) {
//        try {
//            //1.连接邮件服务器的参数配置
//            Properties props = new Properties();
//            // 设置用户的认证方式
//            props.setProperty("mail.smtp.auth", "true");
//            // 设置传输协议
//            props.setProperty("mail.transport.protocol", "smtp");
//            // 设置端口编号
//            props.setProperty("mail.smtp.port", amazonPort);
//            // 设置发件人的SMTP服务器地址
//            props.setProperty("mail.smtp.host", amazonHost);
//            // 设置安全邮件
//            props.setProperty("mail.smtp.starttls.enable", "true");
//            // 生成完整的模版
//            BasicResult<HtmlTemplateEntity> htmlTemplateEntityBasicResult = emailObtainBrService.emailObtainBrHtmlTemplate(brEmailSendBO);
//            if (!htmlTemplateEntityBasicResult.getSuccess()) {
//                return BasicResult.error(htmlTemplateEntityBasicResult);
//            }
//            //2、创建定义整个应用程序所需的环境信息的 Session 对象 session有过期时间
//            Session session = Session.getInstance(props);
//            //设置调试信息在控制台打印出来 true 会打印发送的邮件
//            session.setDebug(false);
//            //3、创建邮件的实例对象
//            HtmlTemplateEntity htmlTemplateEntity = htmlTemplateEntityBasicResult.getData();
//            Message msg = getMimeMessage(session, brEmailSendBO, htmlTemplateEntity);
//            //4、根据session对象获取邮件传输对象Transport
//            Transport transport = session.getTransport();
//            //设置发件人的账户名和密码
//            transport.connect(amazonHost, smtpUserName, smtpPassword);
//            //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
//            transport.sendMessage(msg, msg.getAllRecipients());
//            //5、关闭邮件连接
//            transport.close();
//            LOGGER.info("发送邮件 --> 邮箱 " + brEmailSendBO.getEmailAddress()+" 模版为: "+ brEmailSendBO.getTemplateId());
//            return BasicResult.ok();
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage(), e);
//            return BasicResult.error(BasicResultCode.ERROR.getCode(), MessageFactory.getMsg("G19770111"));
//        }
//    }

    private MimeMessage getMimeMessage(Session session, BrEmailSendBO brEmailSendBO, HtmlTemplateEntity htmlTemplateEntity) throws Exception {
        //1.创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //2.设置发件人地址
        msg.setFrom(new InternetAddress(senderEmail));
        /**
         * 3.设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(brEmailSendBO.getEmailAddress()));

        //4.设置邮件主题
        msg.setSubject(htmlTemplateEntity.getSubject(), "UTF-8");

        // 创建文本"节点"
        MimeBodyPart text = new MimeBodyPart();
        // 这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片

        text.setContent(htmlTemplateEntity.getTemplateHtml(), "text/html;charset=UTF-8");
        // 7. （文本+图片）设置 文本 和 图片"节点"的关系（将 文本 和 图片"节点"合成一个混合"节点"）
        MimeMultipart mm_text_image = new MimeMultipart();
        mm_text_image.addBodyPart(text);
        if (brEmailSendBO.getIsImages()) {
            // 创建图片 节点
            MimeBodyPart image = new MimeBodyPart();
            // 读取本地文件
            DataHandler dh = new DataHandler(new FileDataSource(brEmailSendBO.getImagesUrl()));
            // 将图片数据添加到"节点"
            image.setDataHandler(dh);
            // 为"节点"设置一个唯一编号（在文本"节点"将引用该ID）
            image.setContentID("mailImages");
            mm_text_image.addBodyPart(image);
        }

        // 关联关系
        mm_text_image.setSubType("related");

        // 8. 将 文本+图片 的混合"节点"封装成一个普通"节点"
        // 最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
        // 上面的 mailTestPic 并非 BodyPart, 所有要把 mm_text_image 封装成一个 BodyPart
        MimeBodyPart text_image = new MimeBodyPart();
        text_image.setContent(mm_text_image);

        // 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合"节点" / Multipart ）
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text_image);
        // 如果有多个附件，可以创建多个多次添加
        if (brEmailSendBO.getIsDoc()) {
            // 9. 创建附件"节点"
            MimeBodyPart attachment = new MimeBodyPart();
            // 读取本地文件
            DataHandler dh2 = new DataHandler(new FileDataSource(brEmailSendBO.getDocUrl()));
            // 将附件数据添加到"节点"
            attachment.setDataHandler(dh2);
            // 设置附件的文件名（需要编码）
            attachment.setFileName(MimeUtility.encodeText(dh2.getName()));
            mm.addBodyPart(attachment);
        }

        // 混合关系
        mm.setSubType("mixed");
        // 11. 设置整个邮件的关系（将最终的混合"节点"作为邮件的内容添加到邮件对象）
        msg.setContent(mm);
        //设置邮件的发送时间,默认立即发送 msg.setSentDate(new Date());
        return msg;
    }
}
