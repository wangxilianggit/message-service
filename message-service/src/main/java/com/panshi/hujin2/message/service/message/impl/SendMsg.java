package com.panshi.hujin2.message.service.message.impl;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.redis.RedisUtils;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.domain.enums.ApplicationDefaultLanguageEnum;
import com.panshi.hujin2.message.facade.bo.BatchSendDiffTemplateParamBO;
import com.panshi.hujin2.message.service.message.utils.ExceptionMessageUtils;
import com.panshi.hujin2.message.service.message.IMsgDBService;
import com.panshi.hujin2.message.service.message.ISendMsgService;
import com.panshi.hujin2.message.service.message.utils.MsgUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

/**
 * create by shenjiankang on 2018/7/19 14:30
 */
public abstract class SendMsg implements ISendMsgService {
    private static Logger LOGGER = LoggerFactory.getLogger(SendMsg.class);

    @Value("${msg.symbol}")
    protected String symbol;

    @Value("${everyday.send.num}")
    protected Integer everydaySendNum;

    @Value("${param.size}")
    protected Integer paramSize;

    @Autowired
    protected IMsgDBService msgDBService;




    @Override
    public boolean sendInternationalMsg(ApplicationEnmu applicationEnmu,
                                        String phoneNumber,
                                        String templateCode,
                                        List<String> paramList,
                                        Context context,
                                        Integer msgType) {
        ExceptionMessageUtils.verifyObjectIsNull(context, applicationEnmu);
        ExceptionMessageUtils.verifyStringIsBlank(context, phoneNumber,templateCode);

        if(!MsgUtils.checkSendNum(phoneNumber,everydaySendNum)){
            ExceptionMessageUtils.throwExceptionMobileSendNumOverLimit(context);
        }

        //获取短信模板
        String template = msgDBService.getMsgTemplate(applicationEnmu,templateCode ,context);
//        if(StringUtils.isBlank(template)){
//            //如果模板不存在，选择app默认的语言模板
//            Context defaultContext = getDefaultContext(applicationEnmu);
//            template = msgDBService.getMsgTemplate(templateCode ,defaultContext);
//        }
        if(StringUtils.isBlank(template)){
            ExceptionMessageUtils.throwExceptionTemplateNotExist(context);
        }
        //替换可变参数
        String sendContent = MsgUtils.getSendContent(template,symbol,paramList,context);

        LOGGER.info("发送内容：[{}]",sendContent);

        return sendInternationalMsg(applicationEnmu,phoneNumber,sendContent,context,msgType);
    }


    //同模不同参(循环调用单推实现)
    @Override
    public void batchSendSameTemplateDiffParam(ApplicationEnmu applicationEnmu,
                                          Map<String, List> paramMap,
                                          String templateCode,
                                          Context context) {
        // 群发短信 实现,同一模板，不同参数
        ExceptionMessageUtils.verifyObjectIsNull(context,applicationEnmu,paramMap);
        ExceptionMessageUtils.verifyStringIsBlank(context,templateCode);

        //  paramMap长度默认2000
        if(paramMap.size() > paramSize){
            ExceptionMessageUtils.throwExceptionParamTooLong(context);
        }
        String msgTemplate = msgDBService.getMsgTemplate(applicationEnmu,templateCode,context);
        if(StringUtils.isBlank(msgTemplate)){
            ExceptionMessageUtils.throwExceptionTemplateNotExist(context);
        }
        //存放未发送的手机号
        //List<String> unsendList = new ArrayList<>();
        LOGGER.info("--------开始批量单推短信");
        for (Iterator<String> iterator = paramMap.keySet().iterator();iterator.hasNext();){
            String phoneNumber = iterator.next();
            List paramList = paramMap.get(phoneNumber);

            //替换可变参数
            String sendContent = MsgUtils.getSendContent(msgTemplate,symbol,paramList);
            if (StringUtils.isNotBlank(sendContent)){
                //发送
                LOGGER.info("--------发送手机号[{}]: [{}]",phoneNumber,sendContent);
                sendInternationalMsg(applicationEnmu,phoneNumber,sendContent,context,null);
            } else {
                LOGGER.info("--------因为参数异常没有发送的手机号[{}]",phoneNumber);
                //unsendList.add(phoneNumber);
            }
        }
        //return unsendList;
    }

    @Override
    public void batchSendSameTemplateSameParam(ApplicationEnmu applicationEnmu,
                                               Integer queueId,
                                               Integer consumerId,
                                               Double fee,
                                               List phoneNumberList,
                                               String sendText,
                                               Context context) {
        //由底层具体短信通道实现
    }

    @Override
    public void batchSendDiffTemplate(ApplicationEnmu applicationEnmu,
                                      List<BatchSendDiffTemplateParamBO> paramList,
                                      Context context) {
        //批量發送，不同模板
        //todo 只能循环调用单个发送的接口
        ExceptionMessageUtils.verifyObjectIsNull(context,applicationEnmu,paramList);
        if(paramList.size() > paramSize){
            ExceptionMessageUtils.throwExceptionParamTooLong(context);
        }

        for (BatchSendDiffTemplateParamBO paramBO : paramList){
            if (paramBO!=null){
                String phoneNumber = paramBO.getPhoneNumber();
                String templateCode = paramBO.getTemplateCode();
                List templateParamList = paramBO.getParamList();
                if (StringUtils.isNotBlank(phoneNumber) && StringUtils.isNotBlank(templateCode)){
                    //獲取發送模板
                    try {
                        String msgTemplate = msgDBService.getMsgTemplate(applicationEnmu,templateCode,context);
                        if(StringUtils.isNotBlank(msgTemplate)){
                            //替換模板中的參數
                            String sendContent = MsgUtils.getSendContent(msgTemplate,symbol,templateParamList,context);
                            if (StringUtils.isNotBlank(sendContent)){

                                //暂时只能用循环单推实现，没用不同模板群发对应的api接口
                                sendInternationalMsg(applicationEnmu,phoneNumber,sendContent,context,null);
                                LOGGER.info("--------发送手机号[{}]: [{}]",phoneNumber,sendContent);
                            }
                        }
                    }catch (Exception e){
                        LOGGER.info("========異常！替換參數失敗的手機號[{}]",phoneNumber);
                        LOGGER.error(e.getMessage(), e);
                        continue;
                    }
                }
            }
        }

    }




    /**
     * @description:        根据app获取对应默认的国际化上下文
     * @param applicationEnmu
     * @Author shenjiankang
     * @Date 2018/7/31 16:35
     */
    private Context getDefaultContext(ApplicationEnmu applicationEnmu){
        ApplicationDefaultLanguageEnum defaultLanguageEnum =
                ApplicationDefaultLanguageEnum.getByCode(applicationEnmu.getCode());
        Context defaultContext = new Context();
        defaultContext.setLocale(new Locale(defaultLanguageEnum.getLanguage(),defaultLanguageEnum.getCountry()));
        return defaultContext;
    }

}
