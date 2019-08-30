package com.panshi.hujin2.message.service.fcm.facade;

import com.alibaba.fastjson.JSONObject;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.common.enmu.ClientType;
import com.panshi.hujin2.base.common.factory.MessageFactory;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.domain.result.BasicResultCode;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.dao.mapper.fcm.UserFcmRelationMapper;
import com.panshi.hujin2.message.dao.model.fcm.UserFcmRelationDO;
import com.panshi.hujin2.message.domain.enums.getui.GeTuiPushTemplateEnum;
import com.panshi.hujin2.message.domain.enums.getui.GeTuiResponseEnum;
import com.panshi.hujin2.message.domain.enums.getui.PushTypeEnum;
import com.panshi.hujin2.message.domain.exception.FCMException;
import com.panshi.hujin2.message.facade.IFCMFacade;
import com.panshi.hujin2.message.facade.bo.AppPushHistoryInputBo;
import com.panshi.hujin2.message.facade.bo.AppPushRecordInputBO;
import com.panshi.hujin2.message.facade.bo.AppPushTemplateOutputBO;
import com.panshi.hujin2.message.service.message.utils.HttpUtils;
import com.panshi.hujin2.message.service.message.utils.MsgUtils;
import com.panshi.hujin2.message.service.notification.fcm.FCMPushService;
import com.panshi.hujin2.message.service.notification.getui.INotificationHistoryService;
import com.panshi.hujin2.message.service.notification.getui.INotificationPushService;
import com.panshi.hujin2.message.service.notification.getui.INotificationTemplateService;
import com.panshi.hujin2.message.service.notification.utils.NotificationExceptionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author shenJianKang
 * @date 2019/8/5 18:24
 */
@Service
public class FCMFacadeImpl implements IFCMFacade {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());


    @Autowired
    private INotificationTemplateService notificationTemplateService;
    @Autowired
    private INotificationHistoryService notificationHistoryService;
    @Autowired
    private INotificationPushService notificationPushService;

    @Autowired
    private FCMPushService fCMPushService;
    @Autowired
    private UserFcmRelationMapper userFcmRelationMapper;


    @Value("${fcm.request.json.path}")
    private String fcmRequestJsonPath;

    @Value("${fcm.project.id}")
    private String projectId;
    @Value("${apppush.symbol}")
    private String symbol;




    String postUrl = "https://fcm.googleapis.com/v1/projects/myproject-b5ae1/messages:send";
    private final String SCOPES = "https://www.googleapis.com/auth/firebase.messaging";
    private final String GOOGLE_TOKEN = "firebase_fcm_req_token";





    @Override
    public BasicResult<Void> pushMessageToSingle(ApplicationEnmu appEnmu,
                                                 Integer userId,
                                                 String pushTemplateCode,
                                                 List paramList,
                                                 Boolean send,
                                                 Boolean recordHistory,
                                                 Context context) {

        try {
            NotificationExceptionUtils.verifyObjectIsNull(context,userId,appEnmu);
            NotificationExceptionUtils.verifyStringIsBlank(context,pushTemplateCode);

            //默认个推不发送通知
            if(send == null){
                send = Boolean.FALSE;
            }

            //默认消息中心记录发送历史
            if(recordHistory == null){
                recordHistory = Boolean.TRUE;
            }

            UserFcmRelationDO relationDO = userFcmRelationMapper.queryLastRecordByUserId(appEnmu.getCode(), userId);
            String userClientToken = relationDO.getClientToken();
            if(StringUtils.isBlank(userClientToken)){
                throw new FCMException("用户id["+userId+"]的clientToken为空");
            }

            AppPushTemplateOutputBO outputBO =
                    notificationTemplateService.getPushTemplate(appEnmu,pushTemplateCode, context);
            if(outputBO == null){
                throw new FCMException("无效的 模板code");
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
                //分期提醒 (和之前的不一样，这里title也需要替换参数)
                if(paramList==null || paramList.size()==0){
                    NotificationExceptionUtils.throwExceptionTemplateReplaceParamInvalid(context);
                }
                //title 替换参数
                List<String> titleParamList = new ArrayList<>();
                titleParamList.add(String.valueOf(paramList.get(0)));
                //text 替换参数
                paramList.remove(0);

                Integer titleNum = MsgUtils.getContainNum(title,symbol);
                title = gePushText(titleParamList, context, title, titleNum);


                Integer textNum = MsgUtils.getContainNum(text,symbol);
                text = gePushText(paramList, context, text, textNum);
            }else{
                //获取模板中的动态替换符号的个数
                Integer num = MsgUtils.getContainNum(text,symbol);
                text = gePushText(paramList, context, text, num);
            }
            LOGGER.info("用户[{}]单个推送的消息:[{}]",userId,text);

            String textStyle = outputBO.getTextStyle();
            Integer styleNum = MsgUtils.getContainNum(textStyle,symbol);
            textStyle = gePushText(paramList, context, textStyle ,styleNum);

            if(recordHistory){
                //通过参数校验后，不管发送是否成功失败，都要在消息中心记录
                AppPushHistoryInputBo historyInputBo = new AppPushHistoryInputBo();
                historyInputBo.setAppId(appEnmu.getCode());
                historyInputBo.setUserId(userId);
                historyInputBo.setBusinessTypeId(businessTypeId);
                historyInputBo.setTitle(title);
                historyInputBo.setText(textStyle);
                historyInputBo.setStatus(false);//未读
                int resCount = notificationHistoryService.addPushHistory(historyInputBo,context);
                if(resCount == 0){
                    NotificationExceptionUtils.throwExceptionAddFail(context);
                }
            }
            if(send){
                String res = push(userClientToken, title,text,"0",null,null);

                if (StringUtils.isNotBlank(res)) {
                    AppPushRecordInputBO inputBO = new AppPushRecordInputBO();
                    inputBO.setAppId(appEnmu.getCode());
                    inputBO.setPushType(PushTypeEnum.SINGLE_PUSH.getCode());
                    inputBO.setTemplateType(GeTuiPushTemplateEnum.FCM.getCode());
                    inputBO.setUserId(userId);
                    inputBO.setClientId(userClientToken);
                    inputBO.setBusinessTypeId(businessTypeId);
                    inputBO.setTitle(title);
                    inputBO.setText(text);
                    //透传内容
                    //inputBO.setTransmissionContent("");
                    inputBO.setPushResponse(res);
                    //推送消息记录表
                    notificationPushService.addPushRecord(inputBO,context);

                    LOGGER.debug("--------用户[{}]  firebase FCM 发送结果[{}]",res);
                }else {
                    return BasicResult.error(BasicResultCode.ERROR.getCode(), MessageFactory.getMsg("G19770108", context.getLocale()));
                }
            }

            return BasicResult.ok();
        } catch (Exception e){
            LOGGER.debug(e.getMessage(),e);
            return BasicResult.error(BasicResultCode.ERROR.getCode(), MessageFactory.getMsg("G19770108", context.getLocale()));
        }
    }


    /**
     *@Description:         发送消息
     *@param deviceToken	用户客户端token
     * @param title         标题
     * @param body          内容
     * @param route         0-无需跳转，1-需要跳转
     * @param dataType      暂时填 null
     * @param dataMsg       暂时填 null
     *@Author: shenJianKang
     *@date: 2019/8/29 17:53
     */
//    @Async
    private String push(String deviceToken,
                      String title,
                      String body,
                      String route,
                      Integer dataType,
                      String dataMsg)
            throws Exception {
        LOGGER.info("[START]开始推送FCM消息");
        // 请求标头
        Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("Content-Type", "application/json; UTF-8");
        requestHeader.put("Authorization", "Bearer " + getToken());
        // 请求体
        JSONObject json = new JSONObject();

        JSONObject message = new JSONObject();
        message.put("token", deviceToken);
        JSONObject jsonObject = new JSONObject();

        // 发送弹窗提示信息
        if(!StringUtils.isEmpty(title) && !StringUtils.isEmpty(body)) {
            JSONObject notification = new JSONObject();
            notification.put("title", title);
            notification.put("body", body);
            message.put("notification", notification);

            jsonObject.put("route", route);
            // flag: 0-无需跳转，1-需要跳转
            jsonObject.put("routeFlag", StringUtils.isEmpty(route) ? "0" : "1");
        }

        // 发送数据
        if(!StringUtils.isEmpty(dataMsg)) {
            jsonObject.put("dataType", String.valueOf(dataType));
            jsonObject.put("params", dataMsg);
        }

        message.put("data", jsonObject);
        json.put("message", message);

        LOGGER.info("请求json内容===> {}", json.toString());

        String reqPostUrl = postUrl.replace("myproject-b5ae1",projectId);
        LOGGER.info("======> 请求的url [{}]",reqPostUrl);
        String httpResponse = HttpUtils.postGeneralUrl(reqPostUrl,"application/json",json.toString(),"UTF-8",requestHeader);

        LOGGER.info("fcm响应内容===> {}", httpResponse);
        LOGGER.info("[END]推送FCM消息结束");

        return httpResponse;
    }



    private String getToken (){
        //todo 测试 token 时效性
//        Object obj = MsgUtils.expiryMap.get(GOOGLE_TOKEN);
//        if(obj != null){
//            LOGGER.info("====== 缓存的FCM 访问token[{}]",obj);
//            return String.valueOf(obj);
//        }

        //獲取憑證
        String token = null;
        try {
            GoogleCredential googleCredential = GoogleCredential
                    .fromStream(new FileInputStream(fcmRequestJsonPath))
                    .createScoped(Arrays.asList(SCOPES));
            googleCredential.refreshToken();
            token = googleCredential.getAccessToken();

//            if (StringUtils.isNotBlank(token)){
//                MsgUtils.expiryMap.put(GOOGLE_TOKEN,token);
//            }
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
        }
        LOGGER.info("======>请求返回的 FCM token : [{}]",token);
        return token;
    }



//    /**
//     *@Description:     获取访问凭据
//     *@Param:  * @param
//     *@Author: shenJianKang
//     *@date: 2019/8/22 18:29
//     */
//    private String getAccessToken() throws IOException {
//        GoogleCredential googleCredential = GoogleCredential
//                .fromStream(new FileInputStream(fcmRequestJsonPath))
//                .createScoped(Arrays.asList(SCOPES));
//        googleCredential.refreshToken();
//        return googleCredential.getAccessToken();
//    }




//    public static void main(String[] args) {
//        String aa = "[BasicHttpResponse(statusCode=200, htmlContent=<?xml version='1.0' encoding='UTF-8'?><PUSH><STATUS>0</STATUS><TRANSID>15664704366287851993929</TRANSID><MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG></PUSH><?xml version='1.0' encoding='UTF-8'?><PUSH><STATUS>0</STATUS><TRANSID>15664704366287741245680</TRANSID><MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG></PUSH><?xml version='1.0' encoding='UTF-8'?><PUSH><STATUS>0</STATUS><TRANSID>15664704366289676162843</TRANSID><MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG></PUSH><?xml version='1.0' encoding='UTF-8'?><PUSH><STATUS>0</STATUS><TRANSID>15664704366289627806458</TRANSID><MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG></PUSH><?xml version='1.0' encoding='UTF-8'?><PUSH><STATUS>0</STATUS><TRANSID>1566470436628569964027</TRANSID><MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG></PUSH><?xml version='1.0' encoding='UTF-8'?><PUSH><STATUS>0</STATUS><TRANSID>15664704366281310466357</TRANSID><MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG></PUSH><?xml version='1.0' encoding='UTF-8'?><PUSH><STATUS>0</STATUS><TRANSID>15664704366285888665684</TRANSID><MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG></PUSH><?xml version='1.0' encoding='UTF-8'?><PUSH><STATUS>0</STATUS><TRANSID>15664704366282122421864</TRANSID><MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG></PUSH><?xml version='1.0' encoding='UTF-8'?><PUSH><STATUS>0</STATUS><TRANSID>15664704366282114853568</TRANSID><MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG></PUSH>\n" +
//                ")]";
//        System.out.println("aa.length() = " + aa.length());
//    }

    //校验替换参数的合法性，并返回替换参数后的消息模板
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
           fCMPushService.insertUserClientRelation(appEnmu,userId,clientToken,clientType,context);
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
            fCMPushService.unbindClientRelation(appEnmu,userId,clientToken,clientType,context);
            return BasicResult.ok();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return NotificationExceptionUtils.throwDefinedException(e, context);
//            throw e;
        }
    }





}