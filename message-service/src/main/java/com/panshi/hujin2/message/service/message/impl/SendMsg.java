package com.panshi.hujin2.message.service.message.impl;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.redis.RedisUtils;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.domain.enums.ApplicationDefaultLanguageEnum;
import com.panshi.hujin2.message.facade.bo.BatchSendDiffTemplateParamBO;
import com.panshi.hujin2.message.facade.bo.BatchSendSelfDefinedMsgBO;
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

        //??????????????????
        String template = msgDBService.getMsgTemplate(applicationEnmu,templateCode ,context);
//        if(StringUtils.isBlank(template)){
//            //??????????????????????????????app?????????????????????
//            Context defaultContext = getDefaultContext(applicationEnmu);
//            template = msgDBService.getMsgTemplate(templateCode ,defaultContext);
//        }
        if(StringUtils.isBlank(template)){
            ExceptionMessageUtils.throwExceptionTemplateNotExist(context);
        }
        //??????????????????
        String sendContent = MsgUtils.getSendContent(template,symbol,paramList,context);

        LOGGER.info("???????????????[{}]",sendContent);

        return sendInternationalMsg(applicationEnmu,phoneNumber,sendContent,context,msgType);
    }


    //???????????????(????????????????????????)
    @Override
    public void batchSendSameTemplateDiffParam(ApplicationEnmu applicationEnmu,
                                          Map<String, List> paramMap,
                                          String templateCode,
                                          Context context) {
        // ???????????? ??????,???????????????????????????
        ExceptionMessageUtils.verifyObjectIsNull(context,applicationEnmu,paramMap);
        ExceptionMessageUtils.verifyStringIsBlank(context,templateCode);

        //  paramMap????????????2000
        if(paramMap.size() > paramSize){
            ExceptionMessageUtils.throwExceptionParamTooLong(context);
        }
        String msgTemplate = msgDBService.getMsgTemplate(applicationEnmu,templateCode,context);
        if(StringUtils.isBlank(msgTemplate)){
            ExceptionMessageUtils.throwExceptionTemplateNotExist(context);
        }
        //???????????????????????????
        //List<String> unsendList = new ArrayList<>();
        LOGGER.info("--------????????????????????????");
        for (Iterator<String> iterator = paramMap.keySet().iterator();iterator.hasNext();){
            String phoneNumber = iterator.next();
            List paramList = paramMap.get(phoneNumber);

            //??????????????????
            String sendContent = MsgUtils.getSendContent(msgTemplate,symbol,paramList);
            if (StringUtils.isNotBlank(sendContent)){
                //??????
                LOGGER.info("--------???????????????[{}]: [{}]",phoneNumber,sendContent);
                sendInternationalMsg(applicationEnmu,phoneNumber,sendContent,context,null);
            } else {
                LOGGER.info("--------??????????????????????????????????????????[{}]",phoneNumber);
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
        //?????????????????????????????????
    }

    @Override
    public void batchSendDiffTemplate(ApplicationEnmu applicationEnmu,
                                      List<BatchSendDiffTemplateParamBO> paramList,
                                      Context context) {
        //???????????????????????????
        //todo ???????????????????????????????????????
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
                    //??????????????????
                    try {
                        String msgTemplate = msgDBService.getMsgTemplate(applicationEnmu,templateCode,context);
                        if(StringUtils.isNotBlank(msgTemplate)){
                            //????????????????????????
                            String sendContent = MsgUtils.getSendContent(msgTemplate,symbol,templateParamList,context);
                            if (StringUtils.isNotBlank(sendContent)){

                                //?????????????????????????????????????????????????????????????????????api??????
                                sendInternationalMsg(applicationEnmu,phoneNumber,sendContent,context,null);
                                LOGGER.info("--------???????????????[{}]: [{}]",phoneNumber,sendContent);
                            }
                        }
                    }catch (Exception e){
                        LOGGER.info("========???????????????????????????????????????[{}]",phoneNumber);
                        LOGGER.error(e.getMessage(), e);
                        continue;
                    }
                }
            }
        }

    }

    @Override
    public void batchSendSelfDefinedMsg(BatchSendSelfDefinedMsgBO bo,
                                        Context context)throws Exception{
        //TODO: 2020/6/23 19:29 by ShenJianKang  ????????????kmi ????????????????????? ??????
    }



    /**
     * @description:        ??????app???????????????????????????????????????
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
