package com.panshi.hujin2.message.service.notification.getui.impl;

import com.gexin.rp.sdk.base.IBatch;
import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.NotyPopLoadTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.common.enmu.ClientType;
import com.panshi.hujin2.base.common.factory.MessageFactory;
import com.panshi.hujin2.base.common.util.DozerUtils;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.dao.mapper.notification.AppPushRecordMapper;
import com.panshi.hujin2.message.dao.mapper.notification.UserClientRelation;
import com.panshi.hujin2.message.dao.mapper.notification.UserClientRelationExample;
import com.panshi.hujin2.message.dao.mapper.notification.UserClientRelationMapper;
import com.panshi.hujin2.message.dao.model.AppPushRecord;
import com.panshi.hujin2.message.domain.enums.getui.GeTuiPushTemplateEnum;
import com.panshi.hujin2.message.domain.enums.getui.GeTuiResponseEnum;
import com.panshi.hujin2.message.domain.enums.getui.PushTypeEnum;
import com.panshi.hujin2.message.domain.exception.MessageException;
import com.panshi.hujin2.message.domain.exception.NotificationException;
import com.panshi.hujin2.message.facade.bo.AppPushHistoryInputBo;
import com.panshi.hujin2.message.facade.bo.AppPushRecordInputBO;
import com.panshi.hujin2.message.facade.bo.AppPushTemplateOutputBO;
import com.panshi.hujin2.message.facade.bo.UserClientRelationOutputBO;
import com.panshi.hujin2.message.service.message.utils.ExceptionMessageUtils;
import com.panshi.hujin2.message.service.message.utils.MsgUtils;
import com.panshi.hujin2.message.service.notification.getui.INotificationHistoryService;
import com.panshi.hujin2.message.service.notification.getui.INotificationPushService;
import com.panshi.hujin2.message.service.notification.getui.INotificationTemplateService;
import com.panshi.hujin2.message.service.notification.getui.bo.GeTuiPushConfigInfoBO;
import com.panshi.hujin2.message.service.notification.getui.utils.GeTuiTemplate;
import com.panshi.hujin2.message.service.notification.getui.utils.GeTuiUtils;
import com.panshi.hujin2.message.service.notification.utils.NotificationExceptionUtils;
import org.apache.commons.beanutils.locale.converters.BigIntegerLocaleConverter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * create by shenjiankang on 2018/7/7 11:06
 *
 * app????????????
 */
@Service
public class NotificationPushServiceImpl implements INotificationPushService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationPushServiceImpl.class);

    //??????????????????????????????
    private final String REGULAR = "\\{[^\\}]+";

    @Value("${getui.request.url}")
    private String requestUrl;

    @Value("${apppush.symbol}")
    private String symbol;

    @Value("${param.size}")
    private Integer paramSize;


    @Autowired
    private AppPushRecordMapper appPushRecordMapper;

    @Autowired
    private UserClientRelationMapper userClientRelationMapper;

    @Autowired
    private INotificationTemplateService notificationTemplateService;

    @Autowired
    private INotificationHistoryService notificationHistoryService;


    /**
     * @description:    ???????????????????????????
     * @param cid
     * @param title
     * @param text
     * @param transmissionContent
     * @param batch
     * @Author shenjiankang
     * @Date 2018/7/30 15:13
     */
    private  void constructClientNotificationMsg(String appId,
                                                 String appKey,
                                                 String cid,
                                                 String title,
                                                 String text,
                                                 String transmissionContent,
                                                 IBatch batch)throws Exception{
            //????????????????????????????????????????????????notificationTemplate
            NotificationTemplate template = GeTuiTemplate.getNotificationTemplate(appId,appKey,transmissionContent,title,text);

            SingleMessage message = new SingleMessage();
            message.setData(template);
            message.setOffline(true);
            message.setOfflineExpireTime(1 * 1000);

            // ???????????????????????????appid???clientId
            Target target = new Target();
            target.setAppId(appId);
            target.setClientId(cid);
            batch.add(message, target);
    }
    /**
     * @description:    ????????????
     * @param cid
     * @param title
     * @param text
     * @param transmissionContent
     * @Author shenjiankang
     * @Date 2018/7/30 15:15
     */
    private void batchPushToSingle(ApplicationEnmu appEnum,
                                   String cid,
                                   String title,
                                   String text,
                                   String transmissionContent,
                                   Context context)throws Exception{
        GeTuiPushConfigInfoBO configInfoBO = GeTuiUtils.getGeTuiPushConfigInfo(appEnum,context);
        String getuiAppId = configInfoBO.getAppId();
        String getuiAppKey = configInfoBO.getAppKey();
        String getuiMasterSecret = configInfoBO.getMasterSecret();
        NotificationExceptionUtils.verifyStringIsBlank(context,getuiAppId,getuiAppKey,getuiMasterSecret);

        IIGtPush push = new IGtPush(requestUrl, getuiAppKey, getuiMasterSecret);
        IBatch batch = push.getBatch();
        constructClientNotificationMsg(getuiAppId,getuiAppKey,cid,title,text,transmissionContent,batch);
        batch.submit();
    }

    @Override
    public void batchPushToSingle(ApplicationEnmu appEnmu,
                                               String pushTemplateCode,
                                               Map<Integer, List> map,
                                               Boolean send,
                                               Boolean recordHistory,
                                               Context context) throws Exception{
        //todo  ???????????? ???????????????
        NotificationExceptionUtils.verifyObjectIsNull(context,appEnmu,map);
        NotificationExceptionUtils.verifyStringIsBlank(context,pushTemplateCode);

        GeTuiPushConfigInfoBO configInfoBO = GeTuiUtils.getGeTuiPushConfigInfo(appEnmu,context);
        String getuiAppId = configInfoBO.getAppId();
        String getuiAppKey = configInfoBO.getAppKey();
        String getuiMasterSecret = configInfoBO.getMasterSecret();
        NotificationExceptionUtils.verifyStringIsBlank(context,getuiAppId,getuiAppKey,getuiMasterSecret);

        //  map????????????2000
        if(map.size() > paramSize){
            NotificationExceptionUtils.throwExceptionParamTooLong(context);
        }

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
            NotificationExceptionUtils.throwExceptionTemplateNotExist(context);
        }
        String title = outputBO.getTitle();
        String text = outputBO.getText();
        Integer businessTypeId = outputBO.getBusinessTypeId();

        if (StringUtils.isBlank(text)){
            NotificationExceptionUtils.throwExceptionTemplateNotExist(context);
        }

        //?????????????????????????????????????????????
        Integer num = MsgUtils.getContainNum(text,symbol);
        List<Integer> userIdList = new ArrayList<>();
        //templateMap ??????uid ??? ??????????????????????????????
        Map<Integer,String> templateMap = new HashMap<>();

        //??????????????????
        List<AppPushHistoryInputBo> inputBoList = new ArrayList<>();

        String textStyle = outputBO.getTextStyle();
        if(StringUtils.isBlank(textStyle)){
            NotificationExceptionUtils.throwExceptionDataNotExist(context);
        }
        Integer styleNum = MsgUtils.getContainNum(textStyle,symbol);

        //todo  ???????????????for??????

        for (Iterator<Integer> iterator = map.keySet().iterator(); iterator.hasNext();) {
            Integer userId = iterator.next();
            List paramList = map.get(userId);
            //??????map???????????????????????????????????????
            //todo ????????????????????????????????????????????????
            String repTextStyle = gePushText(paramList, context, textStyle ,styleNum);
            LOGGER.info("--------??????????????????[{}]",repTextStyle);
            //todo ???????????????????????????????????????
            //String  repTextStyle = MsgUtils.replaceTemplate(textStyle,REGULAR,paramList,context);

            if(recordHistory){
                //????????????????????????????????????????????????????????????????????????????????????
                AppPushHistoryInputBo historyInputBo = new AppPushHistoryInputBo();
                historyInputBo.setAppId(appEnmu.getCode());
                historyInputBo.setUserId(userId);
                historyInputBo.setBusinessTypeId(businessTypeId);
                historyInputBo.setTitle(title);
                historyInputBo.setText(repTextStyle);
                //??????
                historyInputBo.setStatus(false);
                inputBoList.add(historyInputBo);
            }

            //???????????????????????????
            //todo ????????????????????????????????????????????????
            String repText = gePushText(paramList,context,text,num);

            //todo ???????????????????????????????????????
//          String repText = MsgUtils.replaceTemplate(text,REGULAR,paramList,context);

            userIdList.add(userId);
            templateMap.put(userId, repText);
        }
        //????????????
        if(recordHistory){
            LOGGER.info("---------------??????????????????---------");
            notificationHistoryService.insertBatch(inputBoList, context);
        }

        if(send){
            //????????????????????????
            for (Iterator<Integer> iterator = map.keySet().iterator(); iterator.hasNext();) {
                Integer userId = iterator.next();
                List paramList = map.get(userId);
                //??????map???????????????????????????????????????
                //todo ????????????????????????????????????????????????
                String repText = gePushText(paramList,context,text,num);

                //todo ???????????????????????????????????????
//            String repText  = MsgUtils.replaceTemplate(text,REGULAR,paramList,context);

                userIdList.add(userId);
                templateMap.put(userId, repText);
            }
            List<UserClientRelationOutputBO> relationOutputBOS = queryUserClientRelationByUserIds(appEnmu.getCode(),userIdList,context);
            //relationMap  ??????uid????????????cid
            Map<Integer ,String> relationMap = new HashMap<>();
            if(relationOutputBOS!=null && relationOutputBOS.size() > 0){
                for (UserClientRelationOutputBO relationOutputBO:relationOutputBOS){
                    Integer uid = relationOutputBO.getUserId();
                    String cid = relationOutputBO.getClientId();
                    if(uid!=null && StringUtils.isNotBlank(cid)){
                        relationMap.put(uid,cid);
                    }else{
                        //todo ?????????????????????????????????????????????????????????????????????????????????
                    }
                }
            }

            //cidMap,??????cid????????????????????????
            Map<String,String> cidMap = new HashMap<>();
            for (Iterator<Integer> iterator = relationMap.keySet().iterator();iterator.hasNext();){
                Integer userId = iterator.next();
                String cid = relationMap.get(userId);
                if(templateMap.containsKey(userId)){
                    cidMap.put(cid, templateMap.get(userId));
                }
            }


            IIGtPush push = new IGtPush(requestUrl, getuiAppKey, getuiMasterSecret);
            IBatch batch = push.getBatch();
            for (Iterator<String> iterator = cidMap.keySet().iterator();iterator.hasNext();){
                String cid = iterator.next();
                String sendText = cidMap.get(cid);
                if(StringUtils.isNotBlank(cid)
                        && StringUtils.isNotBlank(sendText)){
                    constructClientNotificationMsg(getuiAppId,getuiAppKey,cid,title,sendText,"",batch);
                }
            }
            IPushResult sendRes = batch.submit();

            LOGGER.info("--------???????????????????????????:[{}]",sendRes);
        }
    }

    @Override
    public void pushMessageToSingleByNotification(ApplicationEnmu appEnmu,
                                                  Integer userId,
                                                  String clientId,
                                                  String title,
                                                  String text,
                                                  String transmissionContent,
                                                  Integer businessTypeId,
                                                  Context context) {
        NotificationExceptionUtils.verifyObjectIsNull(context,userId);
        NotificationExceptionUtils.verifyStringIsBlank(context,clientId);

        try {
            GeTuiPushConfigInfoBO configInfoBO = GeTuiUtils.getGeTuiPushConfigInfo(appEnmu,context);
            String getuiAppId = configInfoBO.getAppId();
            String getuiAppKey = configInfoBO.getAppKey();
            String getuiMasterSecret = configInfoBO.getMasterSecret();
            NotificationExceptionUtils.verifyStringIsBlank(context,getuiAppId,getuiAppKey,getuiMasterSecret);

            // ??????true???https?????????false???http????????????false???java?????????????????????????????????????????????????????????host???
            //IGtPush push1 = new IGtPush(appKey,masterSecret,true);

            IGtPush push = new IGtPush(requestUrl, getuiAppKey, getuiMasterSecret);
            SingleMessage message = new SingleMessage();
            message.setOffline(true);
            // ?????????????????????????????????????????????
            message.setOfflineExpireTime(24 * 3600 * 1000);
            NotificationTemplate notificationTemplate =
                    GeTuiTemplate.getNotificationTemplate(getuiAppId, getuiAppKey,transmissionContent, title, text);//todo ????????????
            message.setData(notificationTemplate);

            // ?????????1???wifi???0?????????????????????????????????????????????????????????????????????????????????
            message.setPushNetWorkType(0);
            Target target = new Target();
            target.setAppId(getuiAppId);
            target.setClientId(clientId);
            IPushResult ret = null;
            int i = 1;
            try {

                ret = push.pushMessageToSingle(message, target);
                LOGGER.debug("--------??????[{}]??????????????????[{}]",userId,ret.getResponse());
            } catch (RequestException e) {
                LOGGER.error(e.getMessage(), e);
                LOGGER.debug("--------clientId:" + clientId + " ??????????????????" + (i + 1) + "????????????RequestId: " + e.getRequestId() + "=======");
                //???????????????????????????
                ret = push.pushMessageToSingle(message, target, e.getRequestId());
            }
            if (ret != null) {
                //todo  ?????????????????????????????????????????????
                AppPushRecordInputBO inputBO = new AppPushRecordInputBO();
                inputBO.setAppId(appEnmu.getCode());
                inputBO.setPushType(PushTypeEnum.SINGLE_PUSH.getCode());
                inputBO.setTemplateType(GeTuiPushTemplateEnum.NOTIFICATION_TEMPLATE.getCode());
                inputBO.setUserId(userId);
                inputBO.setClientId(clientId);
                inputBO.setBusinessTypeId(businessTypeId);
                inputBO.setTitle(title);
                inputBO.setText(text);
                //????????????
                //inputBO.setTransmissionContent("");
                inputBO.setPushResponse(ret.getResponse().toString());
                //?????????????????????
                addPushRecord(inputBO,context);

                LOGGER.debug("--------??????[{}]  Android??????????????????[{}]",userId,ret.getResponse().toString());

                //????????????????????????{result=ok, taskId=OSS-1208_5fef81a71e5f79d6b61e51bddeaaa440, status=successed_offline}
                if (GeTuiResponseEnum.OK.getState().equals(String.valueOf(ret.getResponse().get(GeTuiResponseEnum.RESULT.getState())))) {
                    LOGGER.debug("--------??????[{}]??????????????????",userId);
                }else if(GeTuiResponseEnum.CLIENT_ID_INVALID.getState().equals(String.valueOf(ret.getResponse().get(GeTuiResponseEnum.RESULT.getState())))){
                    //clientId??????
                    LOGGER.debug("--------??????[{}]clientId?????????",userId);
                    setUserClientIdStatusInvalid(userId,clientId);
                }
            } else {
                LOGGER.error("--------?????????????????????");
                throw new NotificationException(MessageFactory.getMsg("G19990101",context.getLocale()));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void pushMessageToSingleByTransmission(ApplicationEnmu appEnmu,
                                                  Integer userId,
                                                  String clientId,
                                                  String title,
                                                  String text,
                                                  String transmissionContent,
                                                  Integer businessTypeId,
                                                  Context context) {
        NotificationExceptionUtils.verifyObjectIsNull(context,userId);
        NotificationExceptionUtils.verifyStringIsBlank(context,clientId);

        GeTuiPushConfigInfoBO configInfoBO = GeTuiUtils.getGeTuiPushConfigInfo(appEnmu,context);
        String getuiAppId = configInfoBO.getAppId();
        String getuiAppKey = configInfoBO.getAppKey();
        String getuiMasterSecret = configInfoBO.getMasterSecret();
        NotificationExceptionUtils.verifyStringIsBlank(context,getuiAppId,getuiAppKey,getuiMasterSecret);
        try {
            IGtPush push = new IGtPush(requestUrl, getuiAppKey, getuiMasterSecret);
            SingleMessage message = new SingleMessage();
            message.setOffline(true);
            // ?????????????????????????????????????????????
            message.setOfflineExpireTime(24 * 3600 * 1000);

            TransmissionTemplate transmissionTemplate = GeTuiTemplate.getTransmissionTemplate(getuiAppId, getuiAppKey, title, text,transmissionContent
            );
            message.setData(transmissionTemplate);

            //??????????????????????????????????????????????????????????????????????????????wifi?????????????????????????????????????????????????????????????????????????????????????????????????????????
            // ?????????1???wifi???0?????????????????????????????????????????????????????????????????????????????????
            message.setPushNetWorkType(0);
            Target target = new Target();
            target.setAppId(getuiAppId);
            //??????????????????clientId??????
            target.setClientId(clientId);
            IPushResult ret = null;
            int i = 1;
            try {
                ret = push.pushMessageToSingle(message, target);
                LOGGER.debug("??????[{}]??????????????????[{}]",userId,ret.getResponse());
            } catch (RequestException e) {
                LOGGER.error(e.getMessage(), e);
                LOGGER.debug("=======clientId:" + clientId + " ??????????????????" + (i + 1) + "????????????RequestId: " + e.getRequestId() + "=======");
                //???????????????????????????
                ret = push.pushMessageToSingle(message, target, e.getRequestId());
            }
            if (ret != null) {
                //todo  ???????????????????????????????????????????????????
                AppPushRecordInputBO inputBO = new AppPushRecordInputBO();
                inputBO.setAppId(appEnmu.getCode());
                inputBO.setPushType(PushTypeEnum.SINGLE_PUSH.getCode());
                inputBO.setTemplateType(GeTuiPushTemplateEnum.TRANSMISSION_TEMPLATE.getCode());
                inputBO.setUserId(userId);
                inputBO.setClientId(clientId);
                inputBO.setBusinessTypeId(businessTypeId);
                inputBO.setTitle(title);
                inputBO.setText(text);
                //????????????
                //inputBO.setTransmissionContent("");
                inputBO.setPushResponse(ret.getResponse().toString());
                //?????????????????????
                addPushRecord(inputBO,context);

                LOGGER.debug("--------??????[{}]  iOS??????????????????[{}]",userId,ret.getResponse().toString());

                //????????????????????????{result=ok, taskId=OSS-1208_5fef81a71e5f79d6b61e51bddeaaa440, status=successed_offline}
                if (GeTuiResponseEnum.OK.getState().equals(String.valueOf(ret.getResponse().get(GeTuiResponseEnum.RESULT.getState())))) {
                    LOGGER.debug("--------??????[{}]??????????????????",userId);
                }else if(GeTuiResponseEnum.CLIENT_ID_INVALID.getState().equals(String.valueOf(ret.getResponse().get(GeTuiResponseEnum.RESULT.getState())))){
                    //clientId??????
                    LOGGER.debug("--------??????[{}]clientId?????????",userId);
                    setUserClientIdStatusInvalid(userId,clientId);
                }
            } else {
                LOGGER.error("--------?????????????????????");
                throw new NotificationException(MessageFactory.getMsg("G19990101",context.getLocale()));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void pushMessageToSingle(ApplicationEnmu appEnmu,
                                    Integer userId,
                                    Integer businessTypeId,
                                    String title,
                                    String text,
                                    Context context) {
        try {
            ExceptionMessageUtils.verifyObjectIsNull(context,userId);

            UserClientRelationOutputBO outputBO = queryUserClientRelationByUserId(appEnmu.getCode(),userId,Boolean.TRUE,context);
            if(outputBO == null){
                NotificationExceptionUtils.throwExceptionCidInvalid(context);
            }

            String clientId = outputBO.getClientId();
            Integer phoneType = outputBO.getPhoneType();
            if (ClientType.ANDROID.getType().equals(phoneType)) {
                //NotificationTemplate ??????????????????
                pushMessageToSingleByNotification(appEnmu,userId, clientId, title, text,"",businessTypeId,context);//todo ???????????????id?????????
            } else if (ClientType.IOS.getType().equals(phoneType)) {
                //TransmissionTemplate ????????????
                pushMessageToSingleByTransmission(appEnmu,userId, clientId, title, text,"",businessTypeId,context);//todo ???????????????id?????????
            }else{
                //??????????????????????????????????????????
                NotificationExceptionUtils.throwExceptionParamInvalid(context);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void pushMessageToSingle(ApplicationEnmu appEnmu, Integer userId, Integer businessTypeId, String title, String text, Boolean send, Boolean recordHistory, Context context) {
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
                pushMessageToSingle(appEnmu,userId,businessTypeId,title,text,context);
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void pushMessageToSingle(ApplicationEnmu appEnmu,
                                    Integer userId,
                                    String pushTemplateCode,
                                    List paramList,
                                    Boolean send,
                                    Boolean recordHistory,
                                    Context context) {
        try {//flag111
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
                NotificationExceptionUtils.throwExceptionTemplateNotExist(context);
            }
            String title = outputBO.getTitle();
            String text = outputBO.getText();
            Integer businessTypeId = outputBO.getBusinessTypeId();

            if (StringUtils.isBlank(text)){
                NotificationExceptionUtils.throwExceptionTemplateNotExist(context);
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



            //todo ???????????????????????????????????????
//            text = MsgUtils.replaceTemplate(text,REGULAR,paramList,context);

            LOGGER.info("??????[{}]?????????????????????:[{}]",userId,text);

            String textStyle = outputBO.getTextStyle();
            Integer styleNum = MsgUtils.getContainNum(textStyle,symbol);
            textStyle = gePushText(paramList, context, textStyle ,styleNum);

            //todo ???????????????????????????????????????
//            textStyle = MsgUtils.replaceTemplate(textStyle,REGULAR,paramList,context);

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
            if(send){
                //todo  ????????????????????????  ??? ???????????? ??????
                pushMessageToSingle(appEnmu,userId,businessTypeId,title,text,context);
            }
        }catch (Exception e){
//            LOGGER.error(e.getMessage(), e);
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
    public String pushMessageToList(ApplicationEnmu appEnum,
                                  List<Integer> userIdList,
                                  String title,
                                  String text,
                                  Context context) {
        //??????????????????????????????????????????:NotificationTemplate  ios:TransmissionTemplate
        String res = "";
        try {
            NotificationExceptionUtils.verifyObjectIsNull(context, userIdList);

            GeTuiPushConfigInfoBO configInfoBO = GeTuiUtils.getGeTuiPushConfigInfo(appEnum,context);
            String getuiAppId = configInfoBO.getAppId();
            String getuiAppKey = configInfoBO.getAppKey();
            String getuiMasterSecret = configInfoBO.getMasterSecret();
            NotificationExceptionUtils.verifyStringIsBlank(context,getuiAppId,getuiAppKey,getuiMasterSecret);

            // ???????????????????????????????????????????????????
            System.setProperty("gexin_pushList_needDetails", "true");
            // ????????????????????????????????????cid????????????????????????
            // System.setProperty("gexin_pushList_needAliasDetails", "true");
            IGtPush push = new IGtPush(requestUrl, getuiAppKey, getuiMasterSecret);
            // ??????????????????
            NotificationTemplate notificationTemplate =
                    GeTuiTemplate.getNotificationTemplate(getuiAppId, getuiAppKey, "", title, text);
            TransmissionTemplate transmissionTemplate =
                    GeTuiTemplate.getTransmissionTemplate(getuiAppId,getuiAppKey,title,text,"");

            List<String> androidClientList = new ArrayList<>();
            List<String> iosClientList = new ArrayList<>();

            List<UserClientRelationOutputBO> relationOutputBOS= queryUserClientRelationByUserIds(appEnum.getCode(),userIdList,context);

            if(relationOutputBOS!=null && relationOutputBOS.size()>0){
                for (UserClientRelationOutputBO outputBO : relationOutputBOS){
                    if (outputBO!=null){
                        Integer phoneType = outputBO.getPhoneType();
                        String clientId = outputBO.getClientId();
                        if(StringUtils.isNotBlank(clientId) && !"null".equals(clientId)){
                            if(ClientType.ANDROID.getType().equals(phoneType)){
                                androidClientList.add(clientId);
                            }else if(ClientType.IOS.getType().equals(phoneType)){
                                iosClientList.add(clientId);
                            }
                        }
                    }
                }
            }

            //android??????
            ListMessage androidMessage = new ListMessage();
            androidMessage.setData(notificationTemplate);
            // ??????????????????????????????????????????
            androidMessage.setOffline(true);
            // ?????????????????????????????????????????????
            androidMessage.setOfflineExpireTime(24 * 1000 * 3600);
            // ??????????????????
            List androidTargetList = new ArrayList();

            for (String clientid : androidClientList){
                if(StringUtils.isNotBlank(clientid) && !"null".equals(clientid)){
                    Target androidTarget = new Target();
                    androidTarget.setAppId(getuiAppId);
                    androidTarget.setClientId(clientid);
//                target.setAlias();//???????????????
                    androidTargetList.add(androidTarget);
                }
            }
            if(androidTargetList.size()>0){
                // taskId????????????????????????????????????message
                String androidTaskId = push.getContentId(androidMessage);
                IPushResult ret = push.pushMessageToList(androidTaskId, androidTargetList);
                res = ret.getResponse().toString();
                LOGGER.debug("???????????? Android: [{}]",ret.getResponse().toString());
            }else{
                LOGGER.debug("????????????:  ???Android??????");
            }

            //ios??????
            ListMessage iosMessage = new ListMessage();
            iosMessage.setData(transmissionTemplate);
            // ??????????????????????????????????????????
            iosMessage.setOffline(true);
            // ?????????????????????????????????????????????
            iosMessage.setOfflineExpireTime(24 * 1000 * 3600);
            // ??????????????????
            List iosTargetList = new ArrayList();
            for (String iosClientid : iosClientList){
                if(StringUtils.isNotBlank(iosClientid) && !"null".equals(iosClientid)){
                    Target iosTarget = new Target();
                    iosTarget.setAppId(getuiAppId);
                    iosTarget.setClientId(iosClientid);
//                target.setAlias();//???????????????
                    iosTargetList.add(iosTarget);
                }
            }
            if(iosTargetList.size()>0){
                // taskId????????????????????????????????????message
                String iosTaskId = push.getContentId(iosMessage);
                IPushResult ret = push.pushMessageToList(iosTaskId, iosTargetList);
                res = ret.toString();
                LOGGER.debug("???????????? ios:  [{}]",ret.getResponse().toString());
            }else{
                LOGGER.debug("????????????:  ???ios??????");
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
        return res;
    }

    @Override
    public String pushMessageToList(ApplicationEnmu appEnum, List<Integer> userIdList, String title, String text, Boolean send, Boolean recordHistory, Context context) {
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
                    for(Integer uid :uidList){
                        if(uid != null){
                            //TODO: 2020/3/6 12:54 by ShenJianKang  ??????????????????????????????????????????????????????
//                            AppPushRecordInputBO inputBO = new AppPushRecordInputBO();
//                            inputBO.setPushType(PushTypeEnum.LIST_PUSH.getCode());
//                            inputBO.setTemplateType(4);
//                            inputBO.setUserId(uid);
////                        inputBO.setClientId(clientId);
////                    inputBO.setBusinessTypeId(businessTypeId);
//                            inputBO.setTitle(title);
//                            inputBO.setText(text);
//                            inputBO.setTransmissionContent("");
////                        inputBO.setPushResponse(ret.getResponse().toString());
//                            //?????????????????????
//                            addPushRecord(inputBO,context);

                            if(recordHistory){
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
                    notificationHistoryService.insertBatch(insertList, context);
                    if(send){
                        res = pushMessageToList(appEnum, uidList ,title,text,context);
                    }
                }
            }else {
                List<AppPushHistoryInputBo> insertList = new ArrayList<>();
                for(Integer uid :userIdList){
                    if(uid != null){
                        //todo ?????????????????????????????????????????????  insertAppPushRecord(inputBO);//?????????????????????
                        //TODO: 2020/3/6 12:54 by ShenJianKang  ??????????????????????????????????????????????????????
//                        AppPushRecordInputBO inputBO = new AppPushRecordInputBO();
//                        inputBO.setPushType(PushTypeEnum.LIST_PUSH.getCode());
//                        inputBO.setTemplateType(4);
//                        inputBO.setUserId(uid);
////                        inputBO.setClientId(clientId);
////                    inputBO.setBusinessTypeId(businessTypeId);
//                        inputBO.setTitle(title);
//                        inputBO.setText(text);
//                        inputBO.setTransmissionContent("");
////                        inputBO.setPushResponse(ret.getResponse().toString());
//                        //?????????????????????
//                        addPushRecord(inputBO,context);

                        if(recordHistory){
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
                notificationHistoryService.insertBatch(insertList, context);
                if(send){
                    res = pushMessageToList(appEnum,userIdList,title,text,context);
                }
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
        return res;
    }

    public static void main(String[] args) {
        List<Integer> alist = new ArrayList<>();
        for(int i=1;i<=19;i++){
            alist.add(i);
        }

        //??????
        for(int i = 0;i<alist.size();i+=20){
            System.out.println("i = " + i);
            List blist = new ArrayList();

            Integer limit = i+20;
            System.out.println("limit = " + limit);
            blist = alist.subList(i,limit);
            System.out.println("blist = " + blist);
        }
    }

    @Override
    public void pushMessageToList(ApplicationEnmu appEnmu,
                                  List<Integer> userIdList,
                                  String pushTemplateCode,
                                  List paramList,
                                  Boolean send,
                                  Boolean recordHistory,
                                  Context context) {
        try {
            NotificationExceptionUtils.verifyObjectIsNull(context,userIdList,appEnmu);
            NotificationExceptionUtils.verifyStringIsBlank(context,pushTemplateCode);

            //???????????????????????????
            if(send == null){
                send = Boolean.FALSE;
            }

            //????????????????????????????????????
            if(recordHistory == null){
                recordHistory = Boolean.TRUE;
            }

            AppPushTemplateOutputBO templateOutputBO =
                    notificationTemplateService.getPushTemplate(appEnmu,pushTemplateCode,context);

            String title = templateOutputBO.getTitle();
            String text = templateOutputBO.getText();
            Integer businessTypeId = templateOutputBO.getBusinessTypeId();
            if (StringUtils.isBlank(text)){
                NotificationExceptionUtils.throwExceptionTemplateNotExist(context);
            }
            Integer num = MsgUtils.getContainNum(text,symbol);
            text = gePushText(paramList, context, text, num);

            //todo ???????????????????????????????????????
//            text = MsgUtils.replaceTemplate(text,REGULAR,paramList,context);

            String textStyle = templateOutputBO.getTextStyle();
            Integer styleNum = MsgUtils.getContainNum(textStyle,symbol);
            textStyle = gePushText(paramList, context, textStyle ,styleNum);

            //todo ???????????????????????????????????????
//            textStyle = MsgUtils.replaceTemplate(textStyle,REGULAR,paramList,context);

            for(Integer uid :userIdList){
                if(uid != null){
                    //todo ?????????????????????????????????????????????  insertAppPushRecord(inputBO);//?????????????????????
//                    AppPushRecordInputBO inputBO = new AppPushRecordInputBO();
//                    inputBO.setPushType(PushTypeEnum.LIST_PUSH.getCode());
//                    inputBO.setTemplateType();
//                    inputBO.setUserId(uid);
////                        inputBO.setClientId(clientId);
//                    inputBO.setBusinessTypeId(businessTypeId);
//                    inputBO.setTitle(title);
//                    inputBO.setText(text);
//                    inputBO.setTransmissionContent("");
////                        inputBO.setPushResponse(ret.getResponse().toString());
//                    //?????????????????????
//                    dbService.addPushRecord(inputBO,context);

                    if(recordHistory){
                        //????????????????????????????????????????????????????????????
                        AppPushHistoryInputBo historyInputBo = new AppPushHistoryInputBo();
                        historyInputBo.setAppId(appEnmu.getCode());
                        historyInputBo.setUserId(uid);
                        historyInputBo.setBusinessTypeId(businessTypeId);
                        historyInputBo.setTitle(title);
                        historyInputBo.setText(textStyle);
                        historyInputBo.setStatus(false);
                        //todo insert ????????????  ??????
                        notificationHistoryService.addPushHistory(historyInputBo,context);
                    }
                }
            }
            if(send){
                pushMessageToList(appEnmu,userIdList,title,text,context);
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public int insertUserClientRelation(ApplicationEnmu appEnmu,
                                        Integer userId,
                                        String clientId,
                                        ClientType clientType,
                                        Context context) {
        try {
            LOGGER.debug("?????? ??????id[{}]    clientid[{{}}]",userId,clientId);

            NotificationExceptionUtils.verifyObjectIsNull(context,userId,clientType,appEnmu);
            //clinetid????????????????????????????????????clientid??????
            NotificationExceptionUtils.verifyStringIsBlank(context,clientId);

            if(!clientType.equals(ClientType.ANDROID) && !clientType.equals(ClientType.IOS)){
                NotificationExceptionUtils.throwExceptionParamInvalid(context);
            }

            // ??????clientid???????????????userid??????????????????????????????????????????
            UserClientRelationExample queryCidExample = new UserClientRelationExample();
            UserClientRelationExample.Criteria queryCidCriteria = queryCidExample.createCriteria();
            //todo ?????????????????????
            queryCidCriteria.andAppIdEqualTo(appEnmu.getCode());
            queryCidCriteria.andUserIdNotEqualTo(userId);//???????????????userid
            queryCidCriteria.andClientIdEqualTo(clientId);
            queryCidCriteria.andStatusEqualTo(true);
            List<UserClientRelation> clientidExistList = userClientRelationMapper.selectByExample(queryCidExample);
            if(clientidExistList!=null && clientidExistList.size()>0){
                //??????????????????
                List<Integer> idList = new ArrayList<>();
                for(UserClientRelation  clientidExistDO: clientidExistList){
                    idList.add(clientidExistDO.getId());
                }
                LOGGER.debug("--------??????[{}] clientid?????????????????? [{}] ??? pids:[{}]",userId,idList.size(),idList.toString());
                //??????????????????????????????????????????clientid?????????
                if(idList.size()>0){
                    int resCount = userClientRelationMapper.updateClientIdLose(idList,appEnmu.getCode());
                    LOGGER.debug("--------?????? [{}] ??????????????????",resCount);
                }
            }

            //????????????id??????????????????
            UserClientRelationExample userClientRelationExample = new UserClientRelationExample();
            UserClientRelationExample.Criteria criteria = userClientRelationExample.createCriteria();
            criteria.andAppIdEqualTo(appEnmu.getCode());
            criteria.andUserIdEqualTo(userId);
            List<UserClientRelation> userClientRelationDOList = userClientRelationMapper.selectByExample(userClientRelationExample);

            if(userClientRelationDOList!=null && userClientRelationDOList.size()>0){
                //?????????????????????????????????
//                    UserClientRelationDO resDo = userClientRelationDOList.get(0);
//                    String dbClientId = "";
//                    if(resDo!=null){
//                        dbClientId = resDo.getClientId();
//                    }
//                    if(dbClientId!=null && dbClientId.equals(clientId)){
//                        //clientid????????????????????????
//                    }

                UserClientRelation updateDo = new UserClientRelation();
                updateDo.setId(userClientRelationDOList.get(0).getId());
                updateDo.setClientId(clientId);
                updateDo.setPhoneType(clientType.getType());
                updateDo.setStatus(true);
                LOGGER.debug("??????[{}]clientId????????????(update)",userId);
                return userClientRelationMapper.updateByPrimaryKeySelective(updateDo);
            }else{
                //?????????????????????????????????
                UserClientRelation userClientRelationDO = new UserClientRelation();
                userClientRelationDO.setAppId(appEnmu.getCode());
                userClientRelationDO.setUserId(userId);
                userClientRelationDO.setClientId(clientId);
                userClientRelationDO.setPhoneType(clientType.getType());
                userClientRelationDO.setStatus(true);
                LOGGER.debug("??????[{}]clientId????????????(insert)",userId);
                return userClientRelationMapper.insertSelective(userClientRelationDO);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public int unbindClientRelation(ApplicationEnmu appEnmu, Integer userId, String clientId, ClientType clientType, Context context) {
        try {
            LOGGER.debug("?????? ??????id[{}]    clientid[{{}}]",userId,clientId);
            //??????userid ?????????????????????
            NotificationExceptionUtils.verifyObjectIsNull(context,userId,clientType,appEnmu);
            //??????clientId
            NotificationExceptionUtils.verifyStringIsBlank(context,clientId);
            //??????????????????????????????
            if(!clientType.equals(ClientType.ANDROID) && !clientType.equals(ClientType.IOS)){
                //???????????????????????????
                NotificationExceptionUtils.throwExceptionParamInvalid(context);
            }
            //???????????????????????????
            UserClientRelationExample example = new UserClientRelationExample();
            UserClientRelationExample.Criteria criteria = example.createCriteria();
            criteria.andAppIdEqualTo(appEnmu.getCode());
            criteria.andUserIdEqualTo(userId);
            criteria.andClientIdEqualTo(clientId);
            criteria.andPhoneTypeEqualTo(clientType.getType());
            List<UserClientRelation> relationDOList = userClientRelationMapper.selectByExample(example);
            if(relationDOList!=null && relationDOList.size()>0){
                UserClientRelation updateDo = new UserClientRelation();
                updateDo.setStatus(false);
                updateDo.setId(relationDOList.get(0).getId());
                LOGGER.debug("??????[{}]????????????????????????id???[{}]",userId,relationDOList.get(0).getId());
                return userClientRelationMapper.updateByPrimaryKeySelective(updateDo);
            }
            return 0;
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int setUserClientIdStatusInvalid(Integer userId, String clientId) {
        //todo  ?????????unbindClientRelation????????????
        try {
            LOGGER.debug("??????[{}]clientId??????",userId);
            //clientId??????
            UserClientRelationExample userClientRelationExample = new UserClientRelationExample();
            UserClientRelationExample.Criteria criteria = userClientRelationExample.createCriteria();
            criteria.andUserIdEqualTo(userId);
            criteria.andClientIdEqualTo(clientId);
            criteria.andStatusEqualTo(true);

            UserClientRelation userClientRelationDO = new UserClientRelation();
            userClientRelationDO.setStatus(false);
            return userClientRelationMapper.updateByExampleSelective(userClientRelationDO,userClientRelationExample);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public UserClientRelationOutputBO queryUserClientRelationByUserId(Integer appId,Integer userId, Boolean status, Context context) {
        try {
            NotificationExceptionUtils.verifyObjectIsNull(context,appId,userId);
            if(status == null){
                status = Boolean.TRUE;
            }
            UserClientRelationOutputBO outputBO = null;

            UserClientRelationExample userClientRelationExample = new UserClientRelationExample();
            UserClientRelationExample.Criteria criteria = userClientRelationExample.createCriteria();
            criteria.andAppIdEqualTo(appId);
            criteria.andUserIdEqualTo(userId);
            criteria.andStatusEqualTo(status);

            List<UserClientRelation> userClientRelationDOList = userClientRelationMapper.selectByExample(userClientRelationExample);
            if (userClientRelationDOList != null && userClientRelationDOList.size() > 0) {
                UserClientRelation relationDO = userClientRelationDOList.get(0);
                if(relationDO != null){
                    outputBO = DozerUtils.convert(relationDO,UserClientRelationOutputBO.class);
                }
            }
            return outputBO;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    //todo appid ????????????
    @Override
    public List<UserClientRelationOutputBO> queryUserClientRelationByUserIds(Integer appId,List<Integer> uids, Context context) {
        try {
            NotificationExceptionUtils.verifyObjectIsNull(context,uids);
            //todo  ????????? ??????xml sql?????????
            UserClientRelationExample example = new UserClientRelationExample();
            UserClientRelationExample.Criteria criteria = example.createCriteria();
            criteria.andAppIdEqualTo(appId);
            criteria.andUserIdIn(uids);
            criteria.andStatusEqualTo(true);
            List<UserClientRelation> relationList = userClientRelationMapper.selectByExample(example);
            if(relationList != null && relationList.size()>0){
                List<UserClientRelationOutputBO> outputBOS =
                        DozerUtils.convertList(relationList,UserClientRelationOutputBO.class);
                return outputBOS;
            }
            return Collections.emptyList();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }




    @Override
    public int addPushRecord(AppPushRecordInputBO inputBO, Context context) {
        NotificationExceptionUtils.verifyObjectIsNull(context, inputBO);
        AppPushRecord appPushRecord = DozerUtils.convert(inputBO,AppPushRecord.class);
        return appPushRecordMapper.insertSelective(appPushRecord);
    }

    @Override
    public void pushAll(ApplicationEnmu appEnmu,
                        String title,
                        String text,
                        String sendTime,
                        String taskGroupName,
                        Boolean send,
                        Boolean recordHistory,
                        Context context) {
        try {
            GeTuiPushConfigInfoBO configInfoBO = GeTuiUtils.getGeTuiPushConfigInfo(appEnmu,context);
            String getuiAppId = configInfoBO.getAppId();
            String getuiAppKey = configInfoBO.getAppKey();
            String getuiMasterSecret = configInfoBO.getMasterSecret();
            NotificationExceptionUtils.verifyStringIsBlank(context,getuiAppId,getuiAppKey,getuiMasterSecret);

            //???????????????????????????
            if(send == null){
                send = Boolean.FALSE;
            }

            //????????????????????????????????????
            if(recordHistory == null){
                recordHistory = Boolean.FALSE;
            }
            if(recordHistory){
                //todo  ???????????????????????????????????????????????????????????? ???push histroy??????????????????
            }
            if(send){
                //????????????url??????
                String openUrl = "";
                IGtPush push = new IGtPush(requestUrl, getuiAppKey, getuiMasterSecret);
                LinkTemplate template = GeTuiTemplate.getLinkTemplate(getuiAppId,getuiAppKey,title,text,openUrl);
                AppMessage message = new AppMessage();
                message.setData(template);
                message.setOffline(true);
                //?????????????????????????????????????????????
                message.setOfflineExpireTime(24 * 1000 * 3600);
                //??????????????????
                //message.setPushTime("201710261050");//// TODO: 2018/11/24    ????????????????????????????????????????????????VIP
                //?????????App????????????????????????????????????
                AppConditions cdt = new AppConditions();
                List<String> appIdList = new ArrayList<String>();
                appIdList.add(getuiAppId);
                message.setAppIdList(appIdList);
                //????????????
                List<String> phoneTypeList = new ArrayList<String>();
                //??????
                List<String> provinceList = new ArrayList<String>();
                //?????????tag
                List<String> tagList = new ArrayList<String>();
                cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
                cdt.addCondition(AppConditions.REGION, provinceList);
                cdt.addCondition(AppConditions.TAG, tagList);
                message.setConditions(cdt);

                IPushResult ret = push.pushMessageToApp(message, taskGroupName);
                try {
                    LOGGER.info(appEnmu.getDesc()+" APP????????????:[{}]",ret.getResponse().toString());
                }catch (Exception e){
                    LOGGER.error(e.getMessage());
                }
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public void pushAllByTemplate(ApplicationEnmu appEnmu,
                                  GeTuiPushTemplateEnum templateEnum,
                                  String transmissionContent,
                                  String title,
                                  String text,
                                  String sendTime,
                                  String taskGroupName,
                                  Boolean send,
                                  Boolean recordHistory,
                                  Context context) {
        try {
            GeTuiPushConfigInfoBO configInfoBO = GeTuiUtils.getGeTuiPushConfigInfo(appEnmu,context);
            String getuiAppId = configInfoBO.getAppId();
            String getuiAppKey = configInfoBO.getAppKey();
            String getuiMasterSecret = configInfoBO.getMasterSecret();
            NotificationExceptionUtils.verifyStringIsBlank(context,getuiAppId,getuiAppKey,getuiMasterSecret);

            //???????????????????????????
            if(send == null){
                send = Boolean.FALSE;
            }

            //????????????????????????????????????
            if(recordHistory == null){
                recordHistory = Boolean.FALSE;
            }
            if(recordHistory){
                //todo  ???????????????????????????????????????????????????????????? ???push histroy??????????????????
                //// TODO: 2019/8/1  ????????????app??????????????????????????????????????????????????????????????? ??????????????????
            }
            if(send){
                //????????????url??????
                String openUrl = "";
                IGtPush push = new IGtPush(requestUrl, getuiAppKey, getuiMasterSecret);

                AppMessage message = new AppMessage();

                switch (templateEnum){
                    case NOTIFICATION_TEMPLATE:
                        NotificationTemplate notificationTemplate = GeTuiTemplate.getNotificationTemplate(getuiAppId,getuiAppKey,transmissionContent,title,text);
                        message.setData(notificationTemplate);
                        break;
                    case LINK_TEMPLATE:
                        LinkTemplate template = GeTuiTemplate.getLinkTemplate(getuiAppId,getuiAppKey,title,text,openUrl);
                        message.setData(template);
                        break;
                    case NOTYPOPLOAD_TEMPLATE:
                        NotyPopLoadTemplate notyPopLoadTemplate = GeTuiTemplate.getNotyPopLoadTemplate(getuiAppId,getuiAppKey,title,text);
                        message.setData(notyPopLoadTemplate);
                        break;
                    case TRANSMISSION_TEMPLATE:
                        TransmissionTemplate transmissionTemplate = GeTuiTemplate.getTransmissionTemplate(getuiAppId,getuiAppKey,title,text, transmissionContent);
                        message.setData(transmissionTemplate);
                        break;

                        default:
                            throw new MessageException("????????????????????????");
                }
//                if(GeTuiPushTemplateEnum.NOTIFICATION_TEMPLATE.equals(templateEnum)){
//                    NotificationTemplate notificationTemplate = GeTuiTemplate.getNotificationTemplate(getuiAppId,getuiAppKey,transmissionContent,title,text);
//                    message.setData(notificationTemplate);
//                }else if(GeTuiPushTemplateEnum.LINK_TEMPLATE.equals(templateEnum)){
//                    LinkTemplate template = GeTuiTemplate.getLinkTemplate(getuiAppId,getuiAppKey,title,text,openUrl);
//                    message.setData(template);
//                }else if(GeTuiPushTemplateEnum.NOTYPOPLOAD_TEMPLATE.equals(templateEnum)){
//                    NotyPopLoadTemplate notyPopLoadTemplate = GeTuiTemplate.getNotyPopLoadTemplate(getuiAppId,getuiAppKey,title,text);
//                    message.setData(notyPopLoadTemplate);
//                }else if(GeTuiPushTemplateEnum.TRANSMISSION_TEMPLATE.equals(templateEnum)){
//                    TransmissionTemplate transmissionTemplate = GeTuiTemplate.getTransmissionTemplate(getuiAppId,getuiAppKey,title,text, transmissionContent);
//                    message.setData(transmissionTemplate);
//                }


                message.setOffline(true);
                //?????????????????????????????????????????????
                message.setOfflineExpireTime(24 * 1000 * 3600);
                //??????????????????
                //message.setPushTime("201710261050");//// TODO: 2018/11/24    ????????????????????????????????????????????????VIP
                //?????????App????????????????????????????????????
                AppConditions cdt = new AppConditions();
                List<String> appIdList = new ArrayList<String>();
                appIdList.add(getuiAppId);
                message.setAppIdList(appIdList);
                //????????????
                List<String> phoneTypeList = new ArrayList<String>();
                //??????
                List<String> provinceList = new ArrayList<String>();
                //?????????tag
                List<String> tagList = new ArrayList<String>();
                cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
                cdt.addCondition(AppConditions.REGION, provinceList);
                cdt.addCondition(AppConditions.TAG, tagList);
                message.setConditions(cdt);

                IPushResult ret = push.pushMessageToApp(message, taskGroupName);
                try {
                    LOGGER.info(appEnmu.getDesc()+" APP????????????:[{}]",ret.getResponse().toString());
                }catch (Exception e){
                    LOGGER.error(e.getMessage());
                }
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            throw e;
        }
    }




    //todo ????????????????????????????????? ??????????????????
    //    private static List getParseAfterParamList(ApplicationEnmu applicationEnmu,
//                                        String pushTemplateCode,
//                                        List list,
//                                        Context context){
//        ExceptionUtils.verifyObjectIsNull(context,applicationEnmu,list);
//
//        if(ApplicationEnmu.BR_Eloan.getCode().equals(applicationEnmu.getCode())){
//            //???????????????????????????
//            if(PushTemplateEnum.BR_ELOAN_1.getCode().equals(pushTemplateCode)){
//                //???list?????????
//                list.get(1)
//            }
//        }
//
//    }

}
