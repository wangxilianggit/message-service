package com.panshi.hujin2.message.service.message.facade;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.common.factory.MessageFactory;
import com.panshi.hujin2.base.common.util.DozerUtils;
import com.panshi.hujin2.base.domain.page.Page;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.domain.result.BasicResultCode;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.base.service.utils.ContextUtils;
import com.panshi.hujin2.message.dao.mapper.message.MessageSendRecordMapper;
import com.panshi.hujin2.message.dao.mapper.message.UrgentRecallCallLogMapper;
import com.panshi.hujin2.message.dao.mapper.message.UrgentRecallMsgLogMapper;
import com.panshi.hujin2.message.dao.model.*;
import com.panshi.hujin2.message.domain.enums.ChannelEnum;
import com.panshi.hujin2.message.domain.qo.MessageSendRecordQO;
import com.panshi.hujin2.message.domain.qo.MsgSendStatisticsQO;
import com.panshi.hujin2.message.domain.qo.UrgentRecallCallLogQO;
import com.panshi.hujin2.message.domain.qo.UrgentRecallMsgLogQO;
import com.panshi.hujin2.message.facade.IMessageFacade;
import com.panshi.hujin2.message.facade.bo.*;
import com.panshi.hujin2.message.service.message.ISendMsgService;
import com.panshi.hujin2.message.service.message.utils.ExceptionMessageUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * create by shenjiankang on 2018/6/20 11:44
 */
@Service
public class MessageFacadeImpl implements IMessageFacade {

    private static Logger LOGGER = LoggerFactory.getLogger(MessageFacadeImpl.class);

//    @Value("${send.msg.channel}")
//    private String sendMsgChannel;

    @Autowired
    private List<ISendMsgService> messageService;

    @Autowired
    @Qualifier("chuanglanService")
    private ISendMsgService chuanglanMessageService;

    @Autowired
    @Qualifier("submailSerivce")
    private ISendMsgService submailMessageService;

    @Autowired
    @Qualifier("infobipService")
    private ISendMsgService infobipService;

    @Autowired
    @Qualifier("tianyihongService")
    private ISendMsgService tianyihongService;

    @Autowired
    @Qualifier("paasooService")
    private ISendMsgService paasooService;

    @Autowired
    @Qualifier("KMIService")
    private ISendMsgService KMIService;

    @Autowired
    @Qualifier("inaSMSService")
    private ISendMsgService inaSMSService;

    @Autowired
    @Qualifier("nxService")
    private ISendMsgService nxService;

    @Autowired
    private UrgentRecallMsgLogMapper urgentRecallMsgLogMapper;
    @Autowired
    private UrgentRecallCallLogMapper callLogMapper;

    @Autowired
    private MessageSendRecordMapper messageSendRecordMapper;


    public static void main(String[] args) {
        Context context = ContextUtils.getDefaultContext();
        System.out.println("context.getLocale() = " + context.getLocale());
    }
    
    @Override
    public BasicResult<Void> sendInternationalMsg(ApplicationEnmu applicationEnmu,
                                                  String phoneNumber,
                                                  String templateCode,
                                                  List<String> paramList,
                                                  Context context,Integer msgType) {
        try {
            //todo 应该在开头就限制发送次数(因为现在的计算次数是再方法执行完放入换成执行的,如果几个请求同时执行,就会发生多个都算第一次的情况)
            ExceptionMessageUtils.verifyObjectIsNull(context,applicationEnmu);
            ExceptionMessageUtils.verifyStringIsBlank(context,phoneNumber,templateCode);

            ISendMsgService sendMsgService = null;

            //String locale = String.valueOf(context.getLocale());
            String i18n = applicationEnmu.getI18n();
            LOGGER.info("==========> 发送短信，获取到的 i18n ：[{}]",i18n);
            if("vi".equals(i18n)){
                //越南
                phoneNumber = 84 +phoneNumber;
                sendMsgService = tianyihongService;
            }else {
                //印尼
                phoneNumber = 62 +phoneNumber;
                sendMsgService = KMIService;
            }
//            if(applicationEnmu == VI_CASH_DOG
//                    || applicationEnmu == VI_CASH_DOG_NEVERSOLDOUT
//                    || applicationEnmu == VI_CASH_DOG_GOODDAY
//                    || applicationEnmu == VI_CASH_CAT
//                    || applicationEnmu == VI_IN_CASHCAT){
//                //越南
//                phoneNumber = 84 +phoneNumber;
//                sendMsgService = tianyihongService;
//
//            }else if(applicationEnmu == INA_WEB_GAME
//                    || applicationEnmu == INA_CASH_KANGAROO
//                    || applicationEnmu == INA_CASH_KANGAROO_2
//                    || applicationEnmu == INA_CASH_KANGAROO_DEXTER){
//                //印尼
//                phoneNumber = 62 +phoneNumber;
//                sendMsgService = KMIService;
//            }

            boolean res = sendMsgService.sendInternationalMsg(applicationEnmu,
                        phoneNumber,
                        templateCode,
                        paramList,
                        context,msgType);
            if(res){
                return BasicResult.ok();
            }
            return BasicResult.error(BasicResultCode.ERROR.getCode(),"tianYiHong msg send error");

//            ISendMsgService sendMsgService = null;
//            Map<String,Integer> suMap = SuccessivelySendMap.successivelySendMap;

//            if (ApplicationEnmu.PAN_GUAN_JIA.equals(applicationEnmu)
//                    || ApplicationEnmu.BR_Eloan.equals(applicationEnmu)
//                    || ApplicationEnmu.BR_MY_LOAN.equals(applicationEnmu)
//                    || ApplicationEnmu.BR_FLASH_LOAN.equals(applicationEnmu)
//                    || ApplicationEnmu.BR_SIMPLE_LOAN.equals(applicationEnmu)
//                    || ApplicationEnmu.BR_WOWLOAN.equals(applicationEnmu)
//                    || ApplicationEnmu.BR_FREELOAN.equals(applicationEnmu)){
//                //巴西短信平台,创蓝,submail,infobip
//                if(suMap.size()==0){
//                    sendMsgService = getMsgSendInstance(sendMsgChannel);
//                }else{
//                    Integer resType = suMap.get(phoneNumber);
//
//                    if(resType!=null){
//                        if(ChannelEnum.CHUANGLAN.getCode() == resType){
//                            sendMsgService = chuanglanMessageService;
//                        }else if(ChannelEnum.SUBMAIL.getCode() == resType){
//                            sendMsgService = submailMessageService;
//                        }else if(ChannelEnum.INFOBIP.getCode() == resType){
//                            sendMsgService = infobipService;
//                        } else {
//                            sendMsgService = infobipService;
//                        }
//                    }else{
//                        sendMsgService = infobipService;
//                    }
//                }
//
//                LOGGER.info("发送类型 类型：[{}]", sendMsgService);
//                boolean res = sendMsgService.sendInternationalMsg(applicationEnmu,
//                        phoneNumber,
//                        templateCode,
//                        paramList,
//                        context
//                );
//
//                //发送优先级,1.infobip  2.submail  3.chuanglan
//                if(sendMsgService instanceof ChuanlanServiceImpl){
//                    suMap.put(phoneNumber,ChannelEnum.INFOBIP.getCode());
//                }else if(sendMsgService instanceof SubmailServiceImpl){
//                    suMap.put(phoneNumber,ChannelEnum.CHUANGLAN.getCode());
//                }else if(sendMsgService instanceof InfobipServiceImpl){
//                    suMap.put(phoneNumber,ChannelEnum.SUBMAIL.getCode());
//                }else {
//                    suMap.put(phoneNumber,ChannelEnum.INFOBIP.getCode());
//                }
//
//            }else if(ApplicationEnmu.WU_YOU_DAI.equals(applicationEnmu)
//                    ||ApplicationEnmu.MX_I_LOAN.equals(applicationEnmu)
//                    ||ApplicationEnmu.MX_MONEYR.equals(applicationEnmu)
//                    ||ApplicationEnmu.MX_M_LOAN.equals(applicationEnmu)
//                    ||ApplicationEnmu.MX_Y_LOAN.equals(applicationEnmu)
//                    ||ApplicationEnmu.MX_U_LOAN.equals(applicationEnmu)
//                    ||ApplicationEnmu.MX_HI_LOAN.equals(applicationEnmu)){
//
//                //墨西哥短信平台,,天一泓,paasoo
//                if(suMap.size()==0){
//                    sendMsgService = getMsgSendInstance(sendMsgChannel);
//                }else{
//                    Integer resType = suMap.get(phoneNumber);
//                    if(resType!=null){
//                        if(ChannelEnum.PAASOO.getCode() == resType){
//                            sendMsgService = paasooService;
//                        }else if(ChannelEnum.TIANYIHONG.getCode() == resType){
//                            sendMsgService = tianyihongService;
//                        } else {
//                            sendMsgService = tianyihongService;
//                        }
//                        //// TODO: 2018/9/29 加入 亿美软通
//                    }else{
//                        sendMsgService = tianyihongService;
//                    }
//                }
//
//                LOGGER.info("发送类型 类型：[{}]", sendMsgService);
//                boolean res = sendMsgService.sendInternationalMsg(applicationEnmu,
//                        phoneNumber,
//                        templateCode,
//                        paramList,
//                        context
//                );
//
//                //发送优先级:1.tianyihong 2.paasoo
//                if(sendMsgService instanceof PaasooServiceImpl){
//                    suMap.put(phoneNumber,ChannelEnum.TIANYIHONG.getCode());//4
//                }else if(sendMsgService instanceof TainYiHongServiceImpl){
//                    suMap.put(phoneNumber,ChannelEnum.PAASOO.getCode());//5
//                }else {
//                    suMap.put(phoneNumber,ChannelEnum.TIANYIHONG.getCode());
//                }
//                //// TODO: 2018/9/29 加入 亿美软通
//            }else {
//                throw new MessageException(MessageFactory.getMsg("G19880111",context.getLocale()));
//            }

        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return BasicResult.error(BasicResultCode.ERROR.getCode(),
                    MessageFactory.getMsg("G19880108",context.getLocale()));
        }
    }

    @Override
    public BasicResult<Void> sendInternationalMsg(SendMsgBO sendMsgBO) {
        ApplicationEnmu applicationEnmu = sendMsgBO.getApplicationEnmu();
        String phoneNumber = sendMsgBO.getPhoneNumber();
        String templateCode = sendMsgBO.getTemplateCode();
        List<String> paramList = sendMsgBO.getParamList();
        Context context = sendMsgBO.getContext();
        ChannelEnum channelEnum = sendMsgBO.getChannelEnum();
        try {
            //todo 应该在开头就限制发送次数(因为现在的计算次数是再方法执行完放入换成执行的,如果几个请求同时执行,就会发生多个都算第一次的情况)
            ExceptionMessageUtils.verifyObjectIsNull(context,applicationEnmu);
            ExceptionMessageUtils.verifyStringIsBlank(context,phoneNumber,templateCode);

            ISendMsgService sendMsgService = null;

            //String locale = String.valueOf(context.getLocale());
            String i18n = applicationEnmu.getI18n();
            LOGGER.info("==========> 发送短信，获取到的 i18n ：[{}]",i18n);
            if("vi".equals(i18n)){
                //越南
                phoneNumber = 84 +phoneNumber;
                sendMsgService = tianyihongService;
            }else {
                //印尼
                phoneNumber = 62 +phoneNumber;
                sendMsgService = KMIService;
                if(channelEnum != null){
                    if(ChannelEnum.NIU_XIN.equals(channelEnum)){
                        sendMsgService = nxService;
                    }
                }
            }
            boolean res = sendMsgService.sendInternationalMsg(applicationEnmu,
                    phoneNumber,
                    templateCode,
                    paramList,
                    context,
                    sendMsgBO.getMsgType());
            if(res){
                return BasicResult.ok();
            }
            return BasicResult.error(BasicResultCode.ERROR.getCode()," msg send error");
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return BasicResult.error(BasicResultCode.ERROR.getCode(),
                    MessageFactory.getMsg("G19880108",context.getLocale()));
        }
    }


    @Override
    public BasicResult<List<String>> batchSendSameTemplateDiffParam(ApplicationEnmu applicationEnmu,
                                                               Map<String, List> paramMap,
                                                               String templateCode,
                                                               Context context) {
        //国际短信 批量单推（相同模板，不同参数）
        try {
            ISendMsgService sendMsgService = null;
            String i18n = applicationEnmu.getI18n();
            if("id_ID".equals(i18n)){
                //sendMsgService = KMIService;
            }else if("vi".equals(i18n)){
                sendMsgService = tianyihongService;
            }
            if(sendMsgService != null){
                sendMsgService.batchSendSameTemplateDiffParam(applicationEnmu,
                        paramMap,
                        templateCode,
                        context);
                return BasicResult.ok();
            }
            return BasicResult.error(BasicResultCode.ERROR.getCode(),"暂不支持批量发送");
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return BasicResult.error(BasicResultCode.ERROR.getCode(),
                    MessageFactory.getMsg("G19880108",context.getLocale()));
        }
    }

    @Override
    public BasicResult<Void> batchSendSameTemplateSameParam(ApplicationEnmu applicationEnmu,
                                                                  List phoneNumberList,
                                                                  String templateCode,
                                                                  List paramList,
                                                                  Context context) {
        //国际短信 批量发送（相同模板，相同参数）
        try {
            ISendMsgService sendMsgService = null;
            String i18n = applicationEnmu.getI18n();
            if("id_ID".equals(i18n)){
                //sendMsgService = KMIService;
            }else if("vi".equals(i18n)){
                sendMsgService = tianyihongService;
            }
            if(sendMsgService != null){
                sendMsgService.batchSendSameTemplateSameParam(applicationEnmu,
                        phoneNumberList,
                        templateCode,
                        paramList,
                        context);
                return BasicResult.ok();
            }
            return BasicResult.error(BasicResultCode.ERROR.getCode(),"暂不支持批量发送");
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return ExceptionMessageUtils.throwDefinedException(e,context);
        }
    }

    @Override
    public BasicResult<Void> batchSendSameTemplateSameParam(ApplicationEnmu applicationEnmu,
                                                            Integer queueId,
                                                            Integer consumerId,
                                                            Double fee,
                                                            List phoneNumberList,
                                                            String sendText,
                                                            Context context) {
        //国际短信 批量发送（相同模板，相同参数） 直接输入发送内容
        try {
            inaSMSService.batchSendSameTemplateSameParam(applicationEnmu,
                    queueId,
                    consumerId,
                    fee,
                    phoneNumberList,
                    sendText,
                    context);
            return BasicResult.ok();
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return ExceptionMessageUtils.throwDefinedException(e,context);
        }
    }

    @Override
    public BasicResult<Void> batchSendDiffTemplate(ApplicationEnmu applicationEnmu,
                                                   List<BatchSendDiffTemplateParamBO> paramList,
                                                   Context context) {
        //国际短信 批量发送（批量推送， 不同模板）
        try {
            ISendMsgService sendMsgService = null;
            String i18n = applicationEnmu.getI18n();
            if("id_ID".equals(i18n)){
                //sendMsgService = KMIService;
            }else if("vi".equals(i18n)){
                sendMsgService = tianyihongService;
            }
            if(sendMsgService != null){
                sendMsgService.batchSendDiffTemplate(applicationEnmu,
                        paramList,
                        context);
                return BasicResult.ok();
            }
            return BasicResult.error(BasicResultCode.ERROR.getCode(),"暂不支持批量发送");
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return ExceptionMessageUtils.throwDefinedException(e,context);
        }
    }

    @Override
    public BasicResult<Void> batchMsg(UrgentRecallMsgLogInputBO inputBO,
                                      Context context) {
        try {
            if(inputBO==null){
                return BasicResult.error(BasicResultCode.PARAMETER_EMPTY.getCode(),MessageFactory.getMsg("G19880002"));
            }
            List<String> phoneNumbers = inputBO.getPhoneNumbers();
            Integer orderId = inputBO.getOrderId();
            Integer operatorId = inputBO.getOperatorId();
            String operatorName = inputBO.getOperatorName();
            String content = inputBO.getContent();
            if(phoneNumbers==null
                    || phoneNumbers.size()==0
                    || operatorId == null
                    || StringUtils.isBlank(operatorName)
                    || StringUtils.isBlank(content)){
                return BasicResult.error(BasicResultCode.PARAMETER_EMPTY.getCode(),MessageFactory.getMsg("G19880002"));
            }
            if(phoneNumbers.size() > 500){
                //天一泓群发 get请求500, post10000
                return BasicResult.error(BasicResultCode.PARAMETER_INVALID.getCode(),"手机号码不能超过500个");
            }

            List<UrgentRecallMsgLog> logList = new ArrayList<>();
//            StringBuilder sb = new StringBuilder();
            for (String phoneNumber:phoneNumbers){
                if(StringUtils.isNotBlank(phoneNumber)){
//                    sb.append(phoneNumber);
//                    sb.append(",");

                    //构建insert对象
                    UrgentRecallMsgLog insertDO = new UrgentRecallMsgLog();
                    insertDO.setOrderId(orderId);
                    insertDO.setOperatorId(operatorId);
                    insertDO.setOperatorName(operatorName);
                    insertDO.setTargetPhoneNumber(phoneNumber);
                    insertDO.setContent(content);
//                    insertDO.setSendResult();//这里的发送结果暂时只是请求第三方短信发送是否成功的结果
                    logList.add(insertDO);

                    tianyihongService.sendInternationalMsg(inputBO.getApplicationEnmu(),phoneNumber,inputBO.getContent(),context,null);
                }
            }
//            String phoneNumberParams = sb.toString();
//            phoneNumberParams = phoneNumberParams.substring(0, phoneNumberParams.length()-1);
            urgentRecallMsgLogMapper.insertBatch(logList);
            return BasicResult.ok();
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return ExceptionMessageUtils.throwDefinedException(e,context);
        }
    }

    @Override
    public BasicResult<List<UrgentRecallMsgLogOutputBO>> queryUrgentRecallMsgLogByOrderId(Integer orderId, Context context) {
        try {
            UrgentRecallMsgLogExample urgentRecallMsgLogExample = new UrgentRecallMsgLogExample();
            UrgentRecallMsgLogExample.Criteria criteria = urgentRecallMsgLogExample.createCriteria();
            criteria.andOrderIdEqualTo(orderId);
            List<UrgentRecallMsgLog> logList = urgentRecallMsgLogMapper.selectByExample(urgentRecallMsgLogExample);
            if(logList!=null && logList.size()>0){
                List<UrgentRecallMsgLogOutputBO> outputBOS = DozerUtils.convertList(logList, UrgentRecallMsgLogOutputBO.class);
                return BasicResult.ok(outputBOS);
            }
            return BasicResult.ok(Collections.emptyList());
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return ExceptionMessageUtils.throwDefinedException(e,context);
        }
    }

    @Override
    public BasicResult<Void> insertCallRecord(UrgentRecallCallLogInputBO inputBO, Context context) {
        try {
            if(inputBO == null){
                return BasicResult.error(BasicResultCode.PARAMETER_EMPTY.getCode(),
                        MessageFactory.getMsg("G19880002"));
            }
            UrgentRecallCallLog callLog = DozerUtils.convert(inputBO,UrgentRecallCallLog.class);
            callLogMapper.insertSelective(callLog);
            return BasicResult.ok();
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return ExceptionMessageUtils.throwDefinedException(e,context);
        }
    }

    @Override
    public BasicResult<List<UrgentRecallCallLogOutputBO>> getCallRecordByOrderId(Integer orderId, Context context) {
        try {
            if(orderId == null){
                return BasicResult.error(BasicResultCode.PARAMETER_EMPTY.getCode(),
                        MessageFactory.getMsg("G19880002"));
            }
            UrgentRecallCallLogExample callLogExample = new UrgentRecallCallLogExample();
            UrgentRecallCallLogExample.Criteria criteria = callLogExample.createCriteria();
            criteria.andExtendIdEqualTo(String.valueOf(orderId));
            List<UrgentRecallCallLog>  callLogList = callLogMapper.selectByExample(callLogExample);
            if(callLogList != null && callLogList.size()>0){
                List<UrgentRecallCallLogOutputBO> outputBOS = DozerUtils.convertList(callLogList, UrgentRecallCallLogOutputBO.class);
                return BasicResult.ok(outputBOS);
            }
            return BasicResult.ok(Collections.emptyList());
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return ExceptionMessageUtils.throwDefinedException(e,context);
        }
    }

    @Override
    public BasicResult<List<UrgentRecallMsgLogOutputBO>> queryUrgentRecallMsgLogByParam(UrgentRecallMsgLogQO qo , Context context) {
        int count = urgentRecallMsgLogMapper.countByParam(qo);
        if(count>0){
            Page page = qo.getPage();
            page.setTotalNumber(count);
            List<UrgentRecallMsgLog> res = urgentRecallMsgLogMapper.queryByParam(qo);
            return BasicResult.ok(DozerUtils.convertList(res,UrgentRecallMsgLogOutputBO.class), qo.getPage());
        }
        return BasicResult.ok(Collections.EMPTY_LIST);
    }

    @Override
    public BasicResult<List<UrgentRecallCallLogOutputBO>> getCallRecordByParam(UrgentRecallCallLogQO qo, Context context) {

        int count = callLogMapper.countByParam(qo);
        if(count>0){
            Page page = qo.getPage();
            page.setTotalNumber(count);
            List<UrgentRecallCallLog> res = callLogMapper.queryByParam(qo);
            return BasicResult.ok(DozerUtils.convertList(res,UrgentRecallCallLogOutputBO.class), qo.getPage());
        }
        return BasicResult.ok(Collections.EMPTY_LIST);
    }

    @Override
    public BasicResult<List<MsgSendStatisticsBO>> querySendStatistics(MsgSendStatisticsQO qo, Context context) {
        try {
            List<MessageSendRecordDO> doList = messageSendRecordMapper.querySendStatistics(qo);
            if(CollectionUtils.isNotEmpty(doList)){
                Map<String,List<MessageSendRecordDO>> map = new HashMap<>();
                for (MessageSendRecordDO recordDO:doList){
                    String phoneNumber = recordDO.getPhoneNumber();
                    if(map.containsKey(phoneNumber)){
                        List<MessageSendRecordDO> recordDOS = map.get(phoneNumber);
                        recordDOS.add(recordDO);
                    }else {
                        List<MessageSendRecordDO> recordDOS = new ArrayList<>();
                        recordDOS.add(recordDO);
                        map.put(phoneNumber, recordDOS);
                    }
                }

                List<MsgSendStatisticsBO> boList = new ArrayList<>();
                for (Iterator<String> iterator = map.keySet().iterator();iterator.hasNext();){
                    String phoneNumer = iterator.next();
                    List<MessageSendRecordDO> list = map.get(phoneNumer);

                    MsgSendStatisticsBO sendStatisticsBO = new MsgSendStatisticsBO();
                    sendStatisticsBO.setPhoneNumber(phoneNumer);
                    sendStatisticsBO.setTotalSendCount(list.size());
                    Integer successCount = 0;
                    Integer failCount = 0;
                    for(MessageSendRecordDO recordDO:list){
                        Integer code = recordDO.getResCode();
                        if(code!=null && code==0){
                            successCount ++;
                        }else {
                            failCount++;
                        }
                    }
                    sendStatisticsBO.setSuccessSendCount(successCount);
                    sendStatisticsBO.setFailSendCount(failCount);
                    boList.add(sendStatisticsBO);
                }
                return BasicResult.ok(boList);
            }
            return BasicResult.ok(Collections.emptyList());
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return BasicResult.error(BasicResultCode.SYS_EXCEPTION.getCode(),"querySendStatistics error");
        }
    }

    @Override
    public BasicResult<MsgSendResultBO> querySendResult(MsgSendStatisticsQO qo, Context context) {
        try {
            MsgSendResultBO resultBO = new MsgSendResultBO();
//            long start_1 = System.currentTimeMillis();
            //List<MessageSendRecordDO> doList = messageSendRecordMapper.querySendStatistics(qo);
            Integer totalNum = messageSendRecordMapper.countByResCde(qo.getQueueId(),null);
            Integer successNum = messageSendRecordMapper.countByResCde(qo.getQueueId(),0);
            Integer failNum = messageSendRecordMapper.countByResCde(qo.getQueueId(), 1);
            resultBO.setTotalSendCount(totalNum);
            resultBO.setSuccessSendCount(successNum);
            resultBO.setFailSendCount(failNum);

//            long end_1 = System.currentTimeMillis();
//            LOGGER.info("========第次  messageFacade.querySendResult[{}]",end_1-start_1);

//            if(CollectionUtils.isNotEmpty(doList)){
//                //Integer totalSendCount =0;
//                Integer successSendCount=0;
//                Integer failSendCount=0;
//                //Double totalFee = 0D;
//                BigDecimal feeBigDecimal = BigDecimal.ZERO;
//                for(MessageSendRecordDO recordDO:doList){
//                    Integer resCode = recordDO.getResCode();
//                    if(resCode == 0){
//                        //// TODO: 2019/11/6 思考： 这里要改，成功失败数 是否用stat 回执显示
//                        successSendCount ++;
//                    }else {
//                        failSendCount ++;
//                    }
//                    Double fee = recordDO.getFee();
//                    feeBigDecimal = feeBigDecimal.add(new BigDecimal(fee));
//                }
//                resultBO.setTotalSendCount(doList.size());
//                resultBO.setSuccessSendCount(successSendCount);
//                resultBO.setFailSendCount(failSendCount);
//                resultBO.setFee(feeBigDecimal.doubleValue());
//            }
            return BasicResult.ok(resultBO);
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return BasicResult.error(BasicResultCode.SYS_EXCEPTION.getCode(),"querySendResult error");
        }
    }

    @Override
    public BasicResult<List<MessageSendRecordBO>> querySendRecordByParam(MessageSendRecordQO qo) {
        if(qo == null){
            qo = new MessageSendRecordQO();
        }
        Integer count = messageSendRecordMapper.countByParam(qo);
        Page page = qo.getPage();
        page.setTotalNumber(count);
        List<MessageSendRecordDO> doList = messageSendRecordMapper.queryByParam(qo);
        if(CollectionUtils.isNotEmpty(doList)){
            List<MessageSendRecordBO> boList =  DozerUtils.convertList(doList, MessageSendRecordBO.class);
            return BasicResult.ok(boList);
        }
        return BasicResult.ok(Collections.emptyList());
    }

    @Override
    public BasicResult<Integer> countSendRecordByParam(MessageSendRecordQO qo) {
        if(qo == null){
            qo = new MessageSendRecordQO();
        }
        Integer count = messageSendRecordMapper.countByParam(qo);
        return BasicResult.ok(count);
    }

    @Override
    public BasicResult<Integer> countPhoneNumberByParam(MessageSendRecordQO qo) {
        if(qo == null){
            qo = new MessageSendRecordQO();
        }
        Integer count = messageSendRecordMapper.countByParam(qo);
        return null;
    }


    /**
     * @description:    获取短信发送的实例
     * @param param
     * @Author shenjiankang
     * @Date 2018/8/2 11:52
     */
    //todo 优化，用哪个短信渠道最好在服务启动时，只加载一次 static
    private ISendMsgService getMsgSendInstance(String param){
        ISendMsgService sendMsgService = null;
        if(ChannelEnum.CHUANGLAN.getText().equals(param)){
            //创蓝
            sendMsgService = chuanglanMessageService;
        }else if(ChannelEnum.SUBMAIL.getText().equals(param)){
            //SUBMAIL
            sendMsgService = submailMessageService;
        }else if(ChannelEnum.INFOBIP.getText().equals(param)){
            //INFOBIP
            sendMsgService = infobipService;
        }else if(ChannelEnum.TIANYIHONG.getText().equals(param)){
            //天一泓
            sendMsgService = tianyihongService;
        }
        if(sendMsgService == null){
            sendMsgService = tianyihongService;
        }
        return sendMsgService;
    }


}
