package com.panshi.hujin2.message.service.onesignal.facade;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.common.enmu.ClientType;
import com.panshi.hujin2.base.common.factory.MessageFactory;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.domain.result.BasicResultCode;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.base.service.utils.ContextUtils;
import com.panshi.hujin2.message.dao.mapper.onesignal.UserOneSignalRelationMapper;
import com.panshi.hujin2.message.dao.model.onesignal.UserOneSignalRelationDO;
import com.panshi.hujin2.message.domain.enums.getui.GeTuiPushTemplateEnum;
import com.panshi.hujin2.message.domain.enums.getui.PushTypeEnum;
import com.panshi.hujin2.message.domain.exception.OneSignalException;
import com.panshi.hujin2.message.facade.IOneSignalFacade;
import com.panshi.hujin2.message.facade.bo.AppPushHistoryInputBo;
import com.panshi.hujin2.message.facade.bo.AppPushRecordInputBO;
import com.panshi.hujin2.message.facade.bo.AppPushTemplateOutputBO;
import com.panshi.hujin2.message.facade.bo.UserClientRelationOutputBO;
import com.panshi.hujin2.message.service.message.utils.ExceptionMessageUtils;
import com.panshi.hujin2.message.service.message.utils.MsgUtils;
import com.panshi.hujin2.message.service.notification.getui.INotificationHistoryService;
import com.panshi.hujin2.message.service.notification.getui.INotificationPushService;
import com.panshi.hujin2.message.service.notification.getui.INotificationTemplateService;
import com.panshi.hujin2.message.service.notification.utils.NotificationExceptionUtils;
import com.panshi.hujin2.message.service.onesignal.IOneSignalService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * @author shenJianKang
 * @date 2020/1/3 10:48
 */
@Service("oneSignalFacade")
public class OneSignalFacadeImpl implements IOneSignalFacade {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Value("${one.signal.push.url}")
    private String oneSignalPushUrl;
    @Value("${one.signal.push.appid}")
    private String oneSignalAppId;
    @Value("${apppush.symbol}")
    private String symbol;

    @Autowired
    private INotificationTemplateService notificationTemplateService;
    @Autowired
    private INotificationHistoryService notificationHistoryService;
    @Autowired
    private INotificationPushService notificationPushService;
    @Autowired
    private IOneSignalService oneSignalService;

    @Autowired
    private UserOneSignalRelationMapper userOneSignalRelationMapper;


    @Override
    public BasicResult<Void> pushMessageToSingle(ApplicationEnmu appEnmu, Integer userId, String pushTemplateCode, List paramList, Boolean send, Boolean recordHistory, Context context) {
        try {
            NotificationExceptionUtils.verifyObjectIsNull(context,userId,appEnmu);
            NotificationExceptionUtils.verifyStringIsBlank(context,pushTemplateCode);

            //???????????????????????????
            if(send == null){
                send = Boolean.FALSE;
            }

            //????????????????????????????????????
            if(recordHistory == null){
                recordHistory = Boolean.TRUE;
            }

            AppPushTemplateOutputBO outputBO =
                    notificationTemplateService.getPushTemplate(appEnmu,pushTemplateCode, context);
            if(outputBO == null){
                throw new OneSignalException("????????? ??????code");
                //NotificationExceptionUtils.throwExceptionTemplateNotExist(context);
            }
            String title = outputBO.getTitle();
            String text = outputBO.getText();
            Integer businessTypeId = outputBO.getBusinessTypeId();

            if(StringUtils.isBlank(text)){
                NotificationExceptionUtils.throwExceptionDataNotExist(context);
            }
            if("201812171438380000010".equals(pushTemplateCode)
                    ||"201812171438380000011".equals(pushTemplateCode)){
                //???????????? (??????????????????????????????title?????????????????????)
                if(paramList==null || paramList.size()==0){
                    NotificationExceptionUtils.throwExceptionTemplateReplaceParamInvalid(context);
                }
                //title ????????????
                List<String> titleParamList = new ArrayList<>();
                titleParamList.add(String.valueOf(paramList.get(0)));
                //text ????????????
                paramList.remove(0);

                Integer titleNum = MsgUtils.getContainNum(title,symbol);
                title = gePushText(titleParamList, context, title, titleNum);


                Integer textNum = MsgUtils.getContainNum(text,symbol);
                text = gePushText(paramList, context, text, textNum);
            }else{
                //?????????????????????????????????????????????
                Integer num = MsgUtils.getContainNum(text,symbol);
                text = gePushText(paramList, context, text, num);
            }
            LOGGER.info("??????[{}]?????????????????????:[{}]",userId,text);

            String textStyle = outputBO.getTextStyle();
            Integer styleNum = MsgUtils.getContainNum(textStyle,symbol);
            textStyle = gePushText(paramList, context, textStyle ,styleNum);

            if(recordHistory){
                //????????????????????????????????????????????????????????????????????????????????????
                AppPushHistoryInputBo historyInputBo = new AppPushHistoryInputBo();
                historyInputBo.setAppId(appEnmu.getCode());
                historyInputBo.setUserId(userId);
                historyInputBo.setBusinessTypeId(businessTypeId);
                historyInputBo.setTitle(title);
                historyInputBo.setText(textStyle);
                historyInputBo.setStatus(false);//??????
                int resCount = notificationHistoryService.addPushHistory(historyInputBo,context);
                if(resCount == 0){
                    NotificationExceptionUtils.throwExceptionAddFail(context);
                }
            }

            UserOneSignalRelationDO relationDO = userOneSignalRelationMapper.queryLastRecordByUserId(appEnmu.getCode(), userId);
            String playerId = relationDO.getClientId();
            if(StringUtils.isBlank(playerId)){
                throw new OneSignalException("??????id["+userId+"]???onesignal playerId??????");
            }

            if(send){
                String res = sendByPlayerId(
                        appEnmu,
                        userId,
                        businessTypeId,
                        playerId,
                        title,
                        text);
            }

            return BasicResult.ok();
        } catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return BasicResult.error(BasicResultCode.ERROR.getCode(), MessageFactory.getMsg("G19770108", context.getLocale()));
        }
    }

    @Override
    public BasicResult<Void> pushMessageToSingle(ApplicationEnmu appEnmu, Integer userId, Integer businessTypeId, String title, String text, Boolean send, Boolean recordHistory, Context context) {
        try {
            NotificationExceptionUtils.verifyObjectIsNull(context,userId,appEnmu);
            NotificationExceptionUtils.verifyStringIsBlank(context,text);

            //???????????????????????????
            if(send == null){
                send = Boolean.FALSE;
            }

            //????????????????????????????????????
            if(recordHistory == null){
                recordHistory = Boolean.TRUE;
            }

            if(recordHistory){
                //????????????????????????????????????????????????????????????????????????????????????
                AppPushHistoryInputBo historyInputBo = new AppPushHistoryInputBo();
                historyInputBo.setAppId(appEnmu.getCode());
                historyInputBo.setUserId(userId);
                historyInputBo.setBusinessTypeId(businessTypeId);
                historyInputBo.setTitle(title);
                historyInputBo.setText(text);
                historyInputBo.setStatus(false);//??????
                int resCount = notificationHistoryService.addPushHistory(historyInputBo,context);
                if(resCount == 0){
                    NotificationExceptionUtils.throwExceptionAddFail(context);
                }
            }
            if(send){
                //??????????????????????????????
                pushToSingle(appEnmu,userId,businessTypeId,title,text,context);
            }
            return BasicResult.ok();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public BasicResult<String> pushMessageToList(
            ApplicationEnmu appEnum,
            List<Integer> userIdList,
            String title,
            String text,
            Boolean send,
            Boolean recordHistory,
            Context context) {
        String res = "";
        try {
            NotificationExceptionUtils.verifyObjectIsNull(context,userIdList,appEnum);
            NotificationExceptionUtils.verifyStringIsBlank(context,text);

            //???????????????????????????
            if(send == null){
                send = Boolean.FALSE;
            }
            //????????????????????????????????????
            if(recordHistory == null){
                recordHistory = Boolean.TRUE;
            }

            Integer executeNum = 1000;//??????????????????????????????
            if(userIdList.size() > executeNum){
                //??????
                for(int i = 0;i<userIdList.size();i += executeNum){
                    List<Integer> uidList = new ArrayList();
                    Integer limit = i+executeNum;
                    uidList = userIdList.subList(i,limit);
                    List<AppPushHistoryInputBo> insertList = new ArrayList<>();
                    if(recordHistory){
                        for(Integer uid :uidList){
                            if(uid != null){
                                //????????????????????????????????????????????????????????????
                                AppPushHistoryInputBo historyInputBo = new AppPushHistoryInputBo();
                                historyInputBo.setAppId(appEnum.getCode());
                                historyInputBo.setUserId(uid);
                                //                        historyInputBo.setBusinessTypeId(businessTypeId);
                                historyInputBo.setTitle(title);
                                historyInputBo.setText(text);
                                historyInputBo.setStatus(false);
                                insertList.add(historyInputBo);
                            }
                        }
                    }
                    if(insertList.size() > 0){
                        notificationHistoryService.insertBatch(insertList, context);
                    }
                    if(send){
                        //TODO: 2020/3/6 13:06 by ShenJianKang  ????????????????????????????????????
                        for(Integer uid :uidList){
                            pushToSingle(appEnum,
                                    uid,
                                    null,
                                    title,
                                    text,
                                    context);
                        }
                    }
                }
            }else {
                if(recordHistory){
                    List<AppPushHistoryInputBo> insertList = new ArrayList<>();
                    for(Integer uid :userIdList){
                        if(uid != null){
                            //????????????????????????????????????????????????????????????
                            AppPushHistoryInputBo historyInputBo = new AppPushHistoryInputBo();
                            historyInputBo.setAppId(appEnum.getCode());
                            historyInputBo.setUserId(uid);
//                        historyInputBo.setBusinessTypeId(businessTypeId);
                            historyInputBo.setTitle(title);
                            historyInputBo.setText(text);
                            historyInputBo.setStatus(false);
                            insertList.add(historyInputBo);
                        }
                    }
                    if(insertList.size()>0){
                        notificationHistoryService.insertBatch(insertList, context);
                    }
                }
                if(send){
                    //TODO: 2020/3/6 13:06 by ShenJianKang  ????????????????????????????????????
                    for(Integer uid :userIdList){
                        pushToSingle(appEnum,
                                uid,
                                null,
                                title,
                                text,
                                context);
                    }
                }
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
        //TODO: 2020/3/6 13:15 by ShenJianKang  ???????????????res????????????????????????????????????
        return BasicResult.ok(res);
    }

    private String pushToSingle(ApplicationEnmu appEnmu,
                                    Integer userId,
                                    Integer businessTypeId,
                                    String title,
                                    String text,
                                    Context context) {
        try {
            ExceptionMessageUtils.verifyObjectIsNull(context,userId);

            String res = "";
            UserOneSignalRelationDO relationDO = userOneSignalRelationMapper.queryLastRecordByUserId(appEnmu.getCode(), userId);
            if(relationDO != null){
                String playerId = relationDO.getClientId();
                if(StringUtils.isBlank(playerId)){
                    throw new OneSignalException("??????id["+userId+"]???onesignal playerId??????");
                }

                res = sendByPlayerId(
                        appEnmu,
                        userId,
                        businessTypeId,
                        playerId,
                        title,
                        text);
            }
            return res;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }


    //????????????????????????????????????????????????????????????????????????
    private String gePushText(List paramList, Context context, String text, Integer num) {
        if(num != 0){
            if(paramList==null){
                NotificationExceptionUtils.throwExceptionParamIsNull(context);
            }else if(num.equals(paramList.size())){
                Object[] paramArr = paramList.toArray();
                text = String.format(text,paramArr);
            }else{
                NotificationExceptionUtils.throwExceptionTemplateReplaceParamInvalid(context);
            }
        }
        return text;
    }

    @Override
    public BasicResult<Void> bindUidAndCid(ApplicationEnmu appEnmu, Integer userId, String clientToken, ClientType clientType, Context context) {
        try {
            oneSignalService.insertUserClientRelation(appEnmu,userId,clientToken,clientType,context);
            return BasicResult.ok();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return NotificationExceptionUtils.throwDefinedException(e, context);
//            throw e;
        }
    }

    @Override
    public BasicResult<Void> unbindUidAndCid(ApplicationEnmu appEnmu, Integer userId, String clientToken, ClientType clientType, Context context) {
        try {
            oneSignalService.unbindClientRelation(appEnmu,userId,clientToken,clientType,context);
            return BasicResult.ok();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return NotificationExceptionUtils.throwDefinedException(e, context);
//            throw e;
        }
    }






    /**
     *@Description:         ??????playerId ??????????????????????????????
     * @param playerId	    ????????????oneSignal?????????SDK?????????????????????
     * @param title         ???????????????
     * @param content       ???????????????
     *@Author: shenJianKang
     *@date: 2020/1/9 16:17
     */
    @Async
    public String sendByPlayerId(ApplicationEnmu appEnmu,
                                 Integer userId,
                                 Integer businessTypeId,
                                 String playerId,
                                 String title,
                                 String content){
        String jsonResponse = null;
        try {

            URL url = new URL(oneSignalPushUrl);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            //con.setRequestProperty("Authorization", "ZjJkZTEzOTItNmY4Mi00NmUyLWI5MmItMjkyNGRiOWUyYTA1");
            con.setRequestMethod("POST");

            //TODO: 2020/1/3 14:40 by ShenJianKang ?????????????????? ????????????https://documentation.onesignal.com/reference
            String strJsonBody = "{"
                    +   "\"app_id\": \""+ oneSignalAppId +"\","
                    +   "\"include_player_ids\": [\""+playerId+"\"],"
                    +   "\"data\": {\"foo\": \"bar\"},"
                    +   "\"headings\": {\"en\": \""+title+"\"},"
                    +   "\"contents\": {\"en\": \""+content+"\"}"
//                    +   ","
//                    +   "\"subtitle\": {\"en\": \"????????????subtitle \"}"//iOS 10??????
                    + "}";

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            LOGGER.info("onesignal sendByPlayerId httpResponse: " + httpResponse);
            jsonResponse =  "responseCode:"+httpResponse+";";
            if (  httpResponse >= HttpURLConnection.HTTP_OK
                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse += scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse += scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            ////????????????????????????{"id":"0f27b392-5489-40dc-8236-86cdf8a08fd4","recipients":1,"external_id":null}
            LOGGER.info("onesignal sendByPlayerId jsonResponse: " + jsonResponse);

            if (StringUtils.isNotBlank(jsonResponse)) {
                AppPushRecordInputBO inputBO = new AppPushRecordInputBO();
                inputBO.setAppId(appEnmu.getCode());
                inputBO.setPushType(PushTypeEnum.SINGLE_PUSH.getCode());
                inputBO.setTemplateType(GeTuiPushTemplateEnum.ONE_SIGNAL.getCode());
                inputBO.setUserId(userId);
                inputBO.setClientId(playerId);
                inputBO.setBusinessTypeId(businessTypeId);
                inputBO.setTitle(title);
                inputBO.setText(content);
                //????????????
                //inputBO.setTransmissionContent("");
                inputBO.setPushResponse(jsonResponse);
                //?????????????????????
                Context context = ContextUtils.getDefaultContext();
                notificationPushService.addPushRecord(inputBO,context);

//                    LOGGER.debug("--------??????[{}]  onesignale ????????????[{}]",userId, res);
            }
        } catch(Throwable t) {
            t.printStackTrace();
        }
        return jsonResponse;
    }





    //    static String  appId = "b6f5f6d2-5a27-49ee-b1ae-006e089251f8";
//    //??????????????????????????????????????????????????????????????????API??????????????????????????????????????????????????????????????????????????????REST API????????????????????????
//    //String  apiKey = "MWQyMjA4ZjQtMWVmOC00OGI1LTg1ODAtMzU0ZGRlMmIzNzA3";
//    String  apiKey = "MWQyMjA4ZjQtMWVmOC00OGI1LTg1ODAtMzU0ZGRlMmIzNzA3";
//    //??????????????????id
//    String playerId = "";
    public static void main(String[] args) {
        try {
            System.out.println("=====>  1b563483-965d-4299-9225-025f6d77270c".length());
            if(true){
                return;
            }
            //??????????????????????????????????????????
            String  appId = "b6f5f6d2-5a27-49ee-b1ae-006e089251f8";
            String playerId = "";//????????????id
            String title = "";//??????
            String content = "";//??????

            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestMethod("POST");


            //TODO: 2020/1/3 14:40 by ShenJianKang ?????????????????? ????????????https://documentation.onesignal.com/reference
            String strJsonBody = "{"
                    +   "\"app_id\": \""+ appId +"\","
//                    +   "\"include_player_ids\": [\"6392d91a-b206-4b7b-a620-cd68e32c3a76\",\"76ece62b-bcfe-468c-8a78-839aeaa8c5fa\",\"8e0f21fa-9a5a-4ae7-a9a6-ca1f24294b86\"],"
                    +   "\"include_player_ids\": [\""+playerId+"\"],"
                    +   "\"data\": {\"foo\": \"bar\"},"
                    +   "\"headings\": {\"en\": \""+title+"\"},"
                    +   "\"contents\": {\"en\": \""+content+"\"}"
//                    +   ","
//                    +   "\"subtitle\": {\"en\": \"????????????subtitle \"}"//iOS 10??????
                    + "}";

//            String strJsonBody = "{"
//                    +   "app_id: "+ appId +""
////                    +   "\"include_player_ids\": [\"6392d91a-b206-4b7b-a620-cd68e32c3a76\",\"76ece62b-bcfe-468c-8a78-839aeaa8c5fa\",\"8e0f21fa-9a5a-4ae7-a9a6-ca1f24294b86\"],"
//                    +   "include_player_ids: [1b563483-965d-4299-9225-025f6d77270c],"
//                    +   "data: {foo: bar},"
//                    +   "contents: {en: English Message}"
//                    + "}";


            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            if (  httpResponse >= HttpURLConnection.HTTP_OK
                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            System.out.println("jsonResponse:\n" + jsonResponse);

        } catch(Throwable t) {
            t.printStackTrace();
        }
    }
}
