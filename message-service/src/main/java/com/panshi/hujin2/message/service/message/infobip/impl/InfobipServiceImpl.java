package com.panshi.hujin2.message.service.message.infobip.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.domain.enums.ChannelEnum;
import com.panshi.hujin2.message.facade.bo.BatchSendDiffTemplateParamBO;
import com.panshi.hujin2.message.facade.bo.InfobipMsgReportsInputBO;
import com.panshi.hujin2.message.facade.bo.MessageSendRecordInputBO;
import com.panshi.hujin2.message.service.message.utils.ExceptionMessageUtils;
import com.panshi.hujin2.message.service.message.chuanglan.impl.ChuanlanServiceImpl;
import com.panshi.hujin2.message.service.message.impl.SendMsg;
import com.panshi.hujin2.message.service.message.infobip.entity.*;
import com.panshi.hujin2.message.service.message.infobip.utils.InfobipHttpUtil;
import com.panshi.hujin2.message.service.message.infobip.utils.InfobipUtil;
import com.panshi.hujin2.message.service.message.submail.impl.SubmailServiceImpl;
import com.panshi.hujin2.message.service.message.utils.HttpUtils;
import com.panshi.hujin2.message.service.message.utils.MsgUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * create by shenjiankang on 2018/7/17 14:22
 */
@Service("infobipService")
public class InfobipServiceImpl extends SendMsg {
    private static Logger LOGGER = LoggerFactory.getLogger(InfobipServiceImpl.class);

    private final String CONTENT_TYPE_JSON = "application/json";

    @Value("${infobip.single.sendurl.post}")
    private String singleSendUrl;

    @Value("${infobip.advanced.sendurl.post}")
    private String advancedSendUrl;

    @Value("${infobip.multi.sendurl.post}")
    private String multiSendUrl;

    @Value("${infobip.callback.url}")
    private String callbackUrl;

    @Value("${infobip.reports.url.get}")
    private String reportUrl;

    @Value("${infobip.account}")
    private String account;

    @Value("${infobip.pwd}")
    private String pwd;

    @Value("${infobip.from}")
    private String infobipFrom;

    @Autowired
    private ChuanlanServiceImpl chuanlanService;

    @Autowired
    private SubmailServiceImpl submailService;




    @Override
    public String key() {
        return ChannelEnum.INFOBIP.getText();
    }

    @Override
    public boolean isDefault() {
        return false;
    }


    //支持相同模板N个手机号码发送
    private String sendMsgToSengle(List<String> phoneNumberList,String msgText){
        String sendRes = "";
        try {
//            String requestParam =
//                    "{  \n" +
//                            "   \"from\":\""+ infobipFrom +"\",\n" +
//                            "   \"to\":\""+ phoneNumber +"\",\n" +
//                            "   \"text\":\"" +msgText+ "\"\n" +
//                            "}";

            InfobipParam2 paramBean = new InfobipParam2();
            paramBean.setFrom(infobipFrom);
            paramBean.setTo(phoneNumberList);
            paramBean.setText(msgText);

            String requestParam = JSON.toJSONString(paramBean);


            String encodedText = createBase64EncodeParam();
            //infobip API 约定的请求头参数
            Map<String, String> map = new HashMap<>();
            map.put("POST", "/sms/1/text/single HTTP/1.1");
            map.put("Host", "api.infobip.com");
            map.put("Accept", CONTENT_TYPE_JSON);
            map.put("Authorization", " Basic " + encodedText);

            sendRes = HttpUtils.postGeneralUrl(singleSendUrl,
                    CONTENT_TYPE_JSON,
                    requestParam,
                    "UTF-8",
                    map);
            LOGGER.info("--------【infobip】 sengle发送结果。。。[{}]，", sendRes);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
        return sendRes;
    }

    // 全功能文本信息發送
    private String sendMsgAdvanced(String phoneNumber,String msgText) {
        String sendRes = "";
        try {
            LOGGER.info("--------开始通过【infobip】发送短信,全功能文本信息發送。。。手机号：[{}]，发送内容[{}]",phoneNumber, msgText);
            // 全功能文本信息 發送參數對象：具體每個屬性的作用見API文檔
            List<Message> messages = new ArrayList<>();

            //設置發送號碼，和messageId
            Destination destination = new Destination();
            destination.setTo(phoneNumber);

            List<Destination> destinations = new ArrayList<>();
            destinations.add(destination);

            Message msg = new Message();
            msg.setFrom(infobipFrom);
            msg.setDestinations(destinations);
            msg.setText(msgText);
            //回調地址
            msg.setNotifyUrl(callbackUrl);
            msg.setNotifyContentType(CONTENT_TYPE_JSON);

            messages.add(msg);

            Root root = new Root();
            root.setMessages(messages);

            String requestParam = JSON.toJSONString(root);

            String encodedText = createBase64EncodeParam();

            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("POST","/sms/1/text/advanced HTTP/1.1");
            paramMap.put("Host","api.infobip.com");
            paramMap.put("Authorization"," Basic " + encodedText);
            paramMap.put("Content-Type",CONTENT_TYPE_JSON);
            sendRes = HttpUtils.postGeneralUrl(advancedSendUrl,
                    CONTENT_TYPE_JSON,
                    requestParam,
                    "UTF-8",
                    paramMap);
            LOGGER.info("--------【infobip】全功能文本信息发送结果。。。[{}]，", sendRes);

            return sendRes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendRes;
    }

    @Override
    public boolean sendInternationalMsg(ApplicationEnmu applicationEnmu,
                                        String phoneNumber,
                                        String msgText,
                                        Context context,Integer msgType){

        ExceptionMessageUtils.verifyStringIsBlank(context,phoneNumber,msgText);

        boolean res = false;
        LOGGER.info("--------开始通过【infobip】发送短信,sengle發送。。。手机号：[{}]，发送内容[{}]",phoneNumber, msgText);
        try {
            //全功能文本信息返回res：
            //{"messages":[{"to":"8613777400292","status":{"groupId":1,"groupName":"PENDING","id":7,"name":"PENDING_ENROUTE","description":"Message sent to next instance"},"smsCount":1,"messageId":"2323993036121631701"}]}
            //單個短信發送res：
            //{"messages":[{"to":"8613777400292","status":{"groupId":1,"groupName":"PENDING","id":7,"name":"PENDING_ENROUTE","description":"Message sent to next instance"},"smsCount":1,"messageId":"2323995024113535452"}]}

            String sendRes =  sendMsgAdvanced(phoneNumber,msgText);

            //{"messages":[{"to":"8618239960775","status":{"groupId":1,"groupName":"PENDING","id":7,"name":"PENDING_ENROUTE","description":"Message sent to next instance"},"smsCount":1,"messageId":"2338174646673535492"}]}
            if(StringUtils.isNotBlank(sendRes)){
                JSONObject jsonObjRes = JSON.parseObject(sendRes);
                JSONArray jsonArray = jsonObjRes.getJSONArray("messages");
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String msgId = jsonObject.getString("messageId");

                LOGGER.info("开始执行数据库操作，添加发送记录=============================================");
                MessageSendRecordInputBO inputBO = new MessageSendRecordInputBO();
                inputBO.setAppId(applicationEnmu.getCode());

                inputBO.setChannelId(ChannelEnum.INFOBIP.getCode());
                inputBO.setPhoneNumber(phoneNumber);
                inputBO.setMsgText(msgText);
                boolean msgIdIsNotNull = false;
                if(StringUtils.isNotBlank(msgId)){
                    msgIdIsNotNull = true;
                    inputBO.setMsgId(msgId);
                }
                inputBO.setReturnValue(sendRes);
                int count = msgDBService.addMsgSendRecord(inputBO,context);
                LOGGER.info("添加 [{}] 条数据", count);


                //todo 补发逻辑是否应该抽离出来
                //todo 增加判断发送失败的条件（想要补发，最重要得判断是否真的失败的正确性）
                //// TODO: 2018/9/1   补发逻辑不对,墨西哥没有创蓝(暂时去掉补发逻辑)
                JSONObject sendStatus = jsonObject.getJSONObject("status");
                Integer groupId = sendStatus.getInteger("groupId");

                //Group id 2,3,4,5 - message have final status and a delivery report is available.
//                if(InfobipI18nMsgResponseEnum.REJECTED.getCode().equals(groupId)){
//                    LOGGER.info("--------补发第一次，创蓝发送，手机号[{}]", phoneNumber);
//                    //暂时只判断 groupId为5的情况（被拒绝发送），调用其它短信渠道
//                    boolean chuanglanSendRes = chuanlanService.sendInternationalMsg(applicationEnmu,
//                                                                                    phoneNumber,
//                                                                                    msgText,
//                                                                                    context);
//                    //现在默认如果infobip发送失败，创蓝也失败的话还是给记录一次发送限制+1
//                    if(chuanglanSendRes){
//                        return chuanglanSendRes;
//                    }
//                    //根据不同的app，制定不同策略  todo  补发流程中，判断每日发送限制+1的逻辑要梳理清楚
////                    if(ApplicationEnmu.PAN_GUAN_JIA.getCode().equals(applicationEnmu.getCode())){
////                        LOGGER.info("--------补发第二次，SUBMAIL发送，手机号[{}]", phoneNumber);
////                        //如果创蓝失败，继续走SUBmail
////                        boolean submailSendRes = submailService.sendInternationalMsg(applicationEnmu,
////                                                            phoneNumber,
////                                                            msgText,
////                                                            context);
////                        return submailSendRes;
////                    }else if(ApplicationEnmu.WU_YOU_DAI.getCode().equals(applicationEnmu.getCode())){
////                        //无忧贷（墨西哥无忧贷，短信只接了infobip，创蓝）
////                        return chuanglanSendRes;
////                    }
//                }

                //当日发送限制数
                Integer sendNum = 1;
                Object sendN = MsgUtils.expiryMap.get(phoneNumber);
                if(sendN != null){
                    sendNum = (Integer)sendN + 1;
                }
                Long endTime = MsgUtils.getEndTime();
                //转成毫秒
                endTime = endTime * 1000;

                //日志打印，当天手机号码发送次数
                MsgUtils.printMsgSendLogs(phoneNumber,sendNum, endTime);
                //放到缓存中
                MsgUtils.expiryMap.put(phoneNumber, sendNum, endTime);
                res = true;

//                if(msgIdIsNotNull){
//                    //todo  请求infobip API ，获取短信发送信息
//                    LOGGER.info("--------开始获取短信发送信息，手机号[{}]，msgid：[{}]",phoneNumber,msgId);
//
//                    //获取该条短信的发送信息 (這裏是主動獲取，但是這個接口只能獲取一次，之後再請求為空數據，這裏邏輯寫成用url回調的方式獲取發送信息)
//                    //getMsgSendInfo(msgId,null,1, context);
//                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
        return res;
    }

    //同模不同参
    @Override
    public void batchSendSameTemplateDiffParam(ApplicationEnmu applicationEnmu,
                                                       Map<String, List> paramMap,
                                                       String templateCode,
                                                       Context context) {
        // 群发短信 实现,同一模板，不同参数
        ExceptionMessageUtils.verifyObjectIsNull(context,applicationEnmu,paramMap);
        ExceptionMessageUtils.verifyStringIsBlank(context,templateCode);
        // 默认最多2000个手机号码
        if(paramMap.size() > paramSize){
            ExceptionMessageUtils.throwExceptionParamTooLong(context);
        }
        List<BatchSendDiffTemplateParamBO> batchSendDiffTemplateParamBOS = new ArrayList<>();
        for (Iterator<String> iterator = paramMap.keySet().iterator();iterator.hasNext();){
            String phoneNumber = iterator.next();
            List paramList = paramMap.get(phoneNumber);
            BatchSendDiffTemplateParamBO batchSendDiffTemplateParamBO = new BatchSendDiffTemplateParamBO();
            batchSendDiffTemplateParamBO.setPhoneNumber(phoneNumber);
            batchSendDiffTemplateParamBO.setParamList(paramList);
            batchSendDiffTemplateParamBO.setTemplateCode(templateCode);
            batchSendDiffTemplateParamBOS.add(batchSendDiffTemplateParamBO);
        }
        batchSendDiffTemplate(applicationEnmu,batchSendDiffTemplateParamBOS,context);
    }


    //同模同参
    @Override
    public void batchSendSameTemplateSameParam(ApplicationEnmu applicationEnmu,
                                          List phoneNumberList,
                                          String templateCode,
                                          List paramList,
                                          Context context) {

        ExceptionMessageUtils.verifyObjectIsNull(context,applicationEnmu,phoneNumberList);
        ExceptionMessageUtils.verifyStringIsBlank(context,templateCode);

        // 默认最多2000个手机号码
        if(phoneNumberList.size() > paramSize){
            ExceptionMessageUtils.throwExceptionParamTooLong(context);
        }
        String msgTemplate = msgDBService.getMsgTemplate(applicationEnmu,templateCode,context);
        if(StringUtils.isBlank(msgTemplate)){
            ExceptionMessageUtils.throwExceptionTemplateNotExist(context);
        }
        String sendContent = MsgUtils.getSendContent(msgTemplate,symbol,paramList,context);

        int addNum = 3;
        int k = addNum;
        for (int i=0;i<phoneNumberList.size();i+=addNum){
            if(k <= phoneNumberList.size()){
                List list = phoneNumberList.subList(i,k);
                //分段批量发送
                sendMsgToSengle(list,sendContent);

                k+=addNum;
            }else {
                List list = phoneNumberList.subList(i,phoneNumberList.size());
                sendMsgToSengle(list, sendContent);
            }
        }
    }

    //不同模板
    @Override
    public void batchSendDiffTemplate(ApplicationEnmu applicationEnmu, List<BatchSendDiffTemplateParamBO> paramList, Context context) {
        ExceptionMessageUtils.verifyObjectIsNull(context,applicationEnmu,paramList);
        if(paramList.size() > paramSize){
            ExceptionMessageUtils.throwExceptionParamTooLong(context);
        }

        List<InfobipParam1> infobipParam1List = new ArrayList<>();
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
                                InfobipParam1 infobipParam1 = new InfobipParam1();
                                infobipParam1.setText(sendContent);
                                infobipParam1.setTo(phoneNumber);
                                infobipParam1.setFrom(infobipFrom);
                                infobipParam1List.add(infobipParam1);

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

        try {
            Messages messages = new Messages();
            messages.setMessages(infobipParam1List);
            String paramStr = JSON.toJSONString(messages);

            String encodedText = createBase64EncodeParam();
            //infobip API 约定的请求头参数
            Map<String, String> map = new HashMap<>();
            map.put("POST", "/sms/1/text/multi HTTP/1.1");
            map.put("Host", "api.infobip.com");
            map.put("Accept", CONTENT_TYPE_JSON);
            map.put("Authorization", " Basic " + encodedText);

            //paramStr="{\"messages\":[{\"from\":\"Test\",\"to\":\"8613777400292\",\"text\":\"尊敬的用户，您好！您有一笔借款逾期，请尽快还款。【MoneyRush】\"},{\"from\":\"Test\",\"to\":\"8613989801412\",\"text\":\"尊敬的用户，您好！moneyrush已成功放款到您的借款账户 ycg的账户，请注意查收。【MoneyRush】\"}]}";
            String sendRes = HttpUtils.postGeneralUrl(multiSendUrl,
                    CONTENT_TYPE_JSON,
                    paramStr,
                    "UTF-8",
                    map);
            LOGGER.info("--------【infobip】 不同模板群發结果。。。[{}]，", sendRes);
            //todo  短信群发，是否需要数据库入库？
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }
    }





    /**
     * 生成infobipAPI 约定好的请求头中加密的参数
     * @return
     *
     * //todo 优化  最好只生成一次就行
     */
    private String createBase64EncodeParam(){
        //请求的授权表头参数
        String authParam = account+":"+pwd;
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] textByte = new byte[0];

        try {
            textByte = authParam.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //请求头中infobip约定的参数
        String encodedText = encoder.encodeToString(textByte);

        return encodedText;
    }


    /**
     * @description:        请求infobip接口，根据条件查询短信发送信息，在返回信息中，您将获得未读发送报告的集合
     *                      重要事项：发送报告只能检索一次。一旦检索了发送报告，您将不能使用此端点再次
     *                      获得相同的报告。
     *
     * @param msgId         唯一标识已发送消息的 ID。发送消息后，您会在回复中收到一个
     * @param bulkId        唯一标识已发送 SMS 请求的 ID。此筛选器可让您仅使用一个请求收取
                            所有消息的发送报告。发送 SMS请求后，您会在回复中收到一个 bulkId，或者
                            您可以设置一个自定义 bulkId。
     * @param limit         您想要获取的发送报告的最大数量。默认值为 50
     * @Author shenjiankang
     * @Date 2018/7/19 10:21
     */
    public String getMsgSendInfo(String msgId,String bulkId,Integer limit,Context context) throws Exception{

        try {
            //拼接url参数
            Map<String,String> urlParamMap = new HashMap<>();
            if(StringUtils.isNotBlank(msgId)){
                urlParamMap.put("messageId",msgId);
            }
            if(StringUtils.isNotBlank(bulkId)){
                urlParamMap.put("bulkId",bulkId);
            }
            if(limit == null){
                limit = 1;
            }
            urlParamMap.put("limit",String.valueOf(limit));

            String encodeText = createBase64EncodeParam();
            //infobip API 约定的请求头参数
            Map<String,String> headerMap = new HashMap<>();
            headerMap.put("GET", "/sms/1/reports HTTP/1.1");
            headerMap.put("Host", "api.infobip.com");
            headerMap.put("Authorization", "Basic "+ encodeText);
            headerMap.put("Accept", CONTENT_TYPE_JSON);

            String reqRes = InfobipHttpUtil.httpURLConectionGet(reportUrl, urlParamMap,headerMap);

            InfobipMsgReportsInputBO inputBO = InfobipUtil.resolverJsonStr(reqRes,context);
            if(inputBO != null){
                msgDBService.addInfobipMsgReports(inputBO, context);
            }
            return reqRes;
        }catch (Exception e){
            LOGGER.error(e.getMessage() , e);
            throw  e;
        }
    }












}
