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
 * app推送通知
 */
@Service
public class NotificationPushServiceImpl implements INotificationPushService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationPushServiceImpl.class);

    //自定义匹配模板的正则
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
     * @description:    构建批量单推的实例
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
            //用什么模板推送看具体情况，这里用notificationTemplate
            NotificationTemplate template = GeTuiTemplate.getNotificationTemplate(appId,appKey,transmissionContent,title,text);

            SingleMessage message = new SingleMessage();
            message.setData(template);
            message.setOffline(true);
            message.setOfflineExpireTime(1 * 1000);

            // 设置推送目标，填入appid和clientId
            Target target = new Target();
            target.setAppId(appId);
            target.setClientId(cid);
            batch.add(message, target);
    }
    /**
     * @description:    批量单推
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
        //todo  群发推送 适配马甲包
        NotificationExceptionUtils.verifyObjectIsNull(context,appEnmu,map);
        NotificationExceptionUtils.verifyStringIsBlank(context,pushTemplateCode);

        GeTuiPushConfigInfoBO configInfoBO = GeTuiUtils.getGeTuiPushConfigInfo(appEnmu,context);
        String getuiAppId = configInfoBO.getAppId();
        String getuiAppKey = configInfoBO.getAppKey();
        String getuiMasterSecret = configInfoBO.getMasterSecret();
        NotificationExceptionUtils.verifyStringIsBlank(context,getuiAppId,getuiAppKey,getuiMasterSecret);

        //  map长度默认2000
        if(map.size() > paramSize){
            NotificationExceptionUtils.throwExceptionParamTooLong(context);
        }

        //默认个推不发送通知
        if(send == null){
            send = Boolean.FALSE;
        }

        //默认消息中心记录发送历史
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

        //获取模板中的动态替换符号的个数
        Integer num = MsgUtils.getContainNum(text,symbol);
        List<Integer> userIdList = new ArrayList<>();
        //templateMap 存放uid 和 替换参数后的发送模板
        Map<Integer,String> templateMap = new HashMap<>();

        //发送历史入库
        List<AppPushHistoryInputBo> inputBoList = new ArrayList<>();

        String textStyle = outputBO.getTextStyle();
        if(StringUtils.isBlank(textStyle)){
            NotificationExceptionUtils.throwExceptionDataNotExist(context);
        }
        Integer styleNum = MsgUtils.getContainNum(textStyle,symbol);

        //todo  优化，减少for循环

        for (Iterator<Integer> iterator = map.keySet().iterator(); iterator.hasNext();) {
            Integer userId = iterator.next();
            List paramList = map.get(userId);
            //校验map中的参数是否和发送模板匹配
            //todo 替换逻辑修改标记（以后可能要改）
            String repTextStyle = gePushText(paramList, context, textStyle ,styleNum);
            LOGGER.info("--------批量推送文本[{}]",repTextStyle);
            //todo 修改消息模板之后的替换逻辑
            //String  repTextStyle = MsgUtils.replaceTemplate(textStyle,REGULAR,paramList,context);

            if(recordHistory){
                //通过参数校验后，不管发送是否成功失败，都要在消息中心记录
                AppPushHistoryInputBo historyInputBo = new AppPushHistoryInputBo();
                historyInputBo.setAppId(appEnmu.getCode());
                historyInputBo.setUserId(userId);
                historyInputBo.setBusinessTypeId(businessTypeId);
                historyInputBo.setTitle(title);
                historyInputBo.setText(repTextStyle);
                //未读
                historyInputBo.setStatus(false);
                inputBoList.add(historyInputBo);
            }

            //发送消息需要的逻辑
            //todo 替换逻辑修改标记（以后可能要改）
            String repText = gePushText(paramList,context,text,num);

            //todo 修改消息模板之后的替换逻辑
//          String repText = MsgUtils.replaceTemplate(text,REGULAR,paramList,context);

            userIdList.add(userId);
            templateMap.put(userId, repText);
        }
        //批量插入
        if(recordHistory){
            LOGGER.info("---------------开始批量插入---------");
            notificationHistoryService.insertBatch(inputBoList, context);
        }

        if(send){
            //开始批量单推消息
            for (Iterator<Integer> iterator = map.keySet().iterator(); iterator.hasNext();) {
                Integer userId = iterator.next();
                List paramList = map.get(userId);
                //校验map中的参数是否和发送模板匹配
                //todo 替换逻辑修改标记（以后可能要改）
                String repText = gePushText(paramList,context,text,num);

                //todo 修改消息模板之后的替换逻辑
//            String repText  = MsgUtils.replaceTemplate(text,REGULAR,paramList,context);

                userIdList.add(userId);
                templateMap.put(userId, repText);
            }
            List<UserClientRelationOutputBO> relationOutputBOS = queryUserClientRelationByUserIds(appEnmu.getCode(),userIdList,context);
            //relationMap  存放uid和对应的cid
            Map<Integer ,String> relationMap = new HashMap<>();
            if(relationOutputBOS!=null && relationOutputBOS.size() > 0){
                for (UserClientRelationOutputBO relationOutputBO:relationOutputBOS){
                    Integer uid = relationOutputBO.getUserId();
                    String cid = relationOutputBO.getClientId();
                    if(uid!=null && StringUtils.isNotBlank(cid)){
                        relationMap.put(uid,cid);
                    }else{
                        //todo 批量单个推送，如果碰到参数错误的，是跳过，还是全都不发
                    }
                }
            }

            //cidMap,存放cid和对应的发送模板
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

            LOGGER.info("--------批量单推的发送结果:[{}]",sendRes);
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

            // 此处true为https域名，false为http，默认为false。java推荐使用此方法，没有特别需求不需要指定host。
            //IGtPush push1 = new IGtPush(appKey,masterSecret,true);

            IGtPush push = new IGtPush(requestUrl, getuiAppKey, getuiMasterSecret);
            SingleMessage message = new SingleMessage();
            message.setOffline(true);
            // 离线有效时间，单位为毫秒，可选
            message.setOfflineExpireTime(24 * 3600 * 1000);
            NotificationTemplate notificationTemplate =
                    GeTuiTemplate.getNotificationTemplate(getuiAppId, getuiAppKey,transmissionContent, title, text);//todo 透传参数
            message.setData(notificationTemplate);

            // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
            message.setPushNetWorkType(0);
            Target target = new Target();
            target.setAppId(getuiAppId);
            target.setClientId(clientId);
            IPushResult ret = null;
            int i = 1;
            try {

                ret = push.pushMessageToSingle(message, target);
                LOGGER.debug("--------用户[{}]个推推送结果[{}]",userId,ret.getResponse());
            } catch (RequestException e) {
                LOGGER.error(e.getMessage(), e);
                LOGGER.debug("--------clientId:" + clientId + " 发送失败，第" + (i + 1) + "次发送。RequestId: " + e.getRequestId() + "=======");
                //遇到异常，继续发送
                ret = push.pushMessageToSingle(message, target, e.getRequestId());
            }
            if (ret != null) {
                //todo  看情况需不需要做该表的插入操作
                AppPushRecordInputBO inputBO = new AppPushRecordInputBO();
                inputBO.setAppId(appEnmu.getCode());
                inputBO.setPushType(PushTypeEnum.SINGLE_PUSH.getCode());
                inputBO.setTemplateType(GeTuiPushTemplateEnum.NOTIFICATION_TEMPLATE.getCode());
                inputBO.setUserId(userId);
                inputBO.setClientId(clientId);
                inputBO.setBusinessTypeId(businessTypeId);
                inputBO.setTitle(title);
                inputBO.setText(text);
                //透传内容
                //inputBO.setTransmissionContent("");
                inputBO.setPushResponse(ret.getResponse().toString());
                //推送消息记录表
                addPushRecord(inputBO,context);

                LOGGER.debug("--------用户[{}]  Android个推发送结果[{}]",userId,ret.getResponse().toString());

                //发送成功返回信息{result=ok, taskId=OSS-1208_5fef81a71e5f79d6b61e51bddeaaa440, status=successed_offline}
                if (GeTuiResponseEnum.OK.getState().equals(String.valueOf(ret.getResponse().get(GeTuiResponseEnum.RESULT.getState())))) {
                    LOGGER.debug("--------用户[{}]推送通知成功",userId);
                }else if(GeTuiResponseEnum.CLIENT_ID_INVALID.getState().equals(String.valueOf(ret.getResponse().get(GeTuiResponseEnum.RESULT.getState())))){
                    //clientId失效
                    LOGGER.debug("--------用户[{}]clientId已失效",userId);
                    setUserClientIdStatusInvalid(userId,clientId);
                }
            } else {
                LOGGER.error("--------服务器响应异常");
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
            // 离线有效时间，单位为毫秒，可选
            message.setOfflineExpireTime(24 * 3600 * 1000);

            TransmissionTemplate transmissionTemplate = GeTuiTemplate.getTransmissionTemplate(getuiAppId, getuiAppKey, title, text,transmissionContent
            );
            message.setData(transmissionTemplate);

            //作为参数，主要用于富媒体、视频、应用下载等推送，仅在wifi环境下展现推送消息，用较精美的富文本内容展示通知，充分帮用户节省流量。
            // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
            message.setPushNetWorkType(0);
            Target target = new Target();
            target.setAppId(getuiAppId);
            //别名为空，按clientId发送
            target.setClientId(clientId);
            IPushResult ret = null;
            int i = 1;
            try {
                ret = push.pushMessageToSingle(message, target);
                LOGGER.debug("用户[{}]个推推送结果[{}]",userId,ret.getResponse());
            } catch (RequestException e) {
                LOGGER.error(e.getMessage(), e);
                LOGGER.debug("=======clientId:" + clientId + " 发送失败，第" + (i + 1) + "次发送。RequestId: " + e.getRequestId() + "=======");
                //遇到异常，继续发送
                ret = push.pushMessageToSingle(message, target, e.getRequestId());
            }
            if (ret != null) {
                //todo  看情况需不需要做该表的插入操作（）
                AppPushRecordInputBO inputBO = new AppPushRecordInputBO();
                inputBO.setAppId(appEnmu.getCode());
                inputBO.setPushType(PushTypeEnum.SINGLE_PUSH.getCode());
                inputBO.setTemplateType(GeTuiPushTemplateEnum.TRANSMISSION_TEMPLATE.getCode());
                inputBO.setUserId(userId);
                inputBO.setClientId(clientId);
                inputBO.setBusinessTypeId(businessTypeId);
                inputBO.setTitle(title);
                inputBO.setText(text);
                //透传内容
                //inputBO.setTransmissionContent("");
                inputBO.setPushResponse(ret.getResponse().toString());
                //推送消息记录表
                addPushRecord(inputBO,context);

                LOGGER.debug("--------用户[{}]  iOS个推发送结果[{}]",userId,ret.getResponse().toString());

                //发送成功返回信息{result=ok, taskId=OSS-1208_5fef81a71e5f79d6b61e51bddeaaa440, status=successed_offline}
                if (GeTuiResponseEnum.OK.getState().equals(String.valueOf(ret.getResponse().get(GeTuiResponseEnum.RESULT.getState())))) {
                    LOGGER.debug("--------用户[{}]推送通知成功",userId);
                }else if(GeTuiResponseEnum.CLIENT_ID_INVALID.getState().equals(String.valueOf(ret.getResponse().get(GeTuiResponseEnum.RESULT.getState())))){
                    //clientId失效
                    LOGGER.debug("--------用户[{}]clientId已失效",userId);
                    setUserClientIdStatusInvalid(userId,clientId);
                }
            } else {
                LOGGER.error("--------服务器响应异常");
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
                //NotificationTemplate 通知应用模板
                pushMessageToSingleByNotification(appEnmu,userId, clientId, title, text,"",businessTypeId,context);//todo 优化：业务id不需要
            } else if (ClientType.IOS.getType().equals(phoneType)) {
                //TransmissionTemplate 透传模板
                pushMessageToSingleByTransmission(appEnmu,userId, clientId, title, text,"",businessTypeId,context);//todo 优化：业务id不需要
            }else{
                //找不到对应的手机操作系统类型
                NotificationExceptionUtils.throwExceptionParamInvalid(context);
            }
        } catch (Exception e) {
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

            //默认个推不发送通知
            if(send == null){
                send = Boolean.FALSE;
            }

            //默认消息中心记录发送历史
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



            //todo 修改消息模板之后的替换逻辑
//            text = MsgUtils.replaceTemplate(text,REGULAR,paramList,context);

            LOGGER.info("用户[{}]单个推送的消息:[{}]",userId,text);

            String textStyle = outputBO.getTextStyle();
            Integer styleNum = MsgUtils.getContainNum(textStyle,symbol);
            textStyle = gePushText(paramList, context, textStyle ,styleNum);

            //todo 修改消息模板之后的替换逻辑
//            textStyle = MsgUtils.replaceTemplate(textStyle,REGULAR,paramList,context);

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
                //todo  思考：把消息中心  和 个推发送 拆分
                pushMessageToSingle(appEnmu,userId,businessTypeId,title,text,context);
            }
        }catch (Exception e){
//            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

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
    public void pushMessageToList(ApplicationEnmu appEnum,
                                  List<Integer> userIdList,
                                  String title,
                                  String text,
                                  Context context) {
        //列表推送需要分推送模板，安卓:NotificationTemplate  ios:TransmissionTemplate
        try {
            NotificationExceptionUtils.verifyObjectIsNull(context, userIdList);

            GeTuiPushConfigInfoBO configInfoBO = GeTuiUtils.getGeTuiPushConfigInfo(appEnum,context);
            String getuiAppId = configInfoBO.getAppId();
            String getuiAppKey = configInfoBO.getAppKey();
            String getuiMasterSecret = configInfoBO.getMasterSecret();
            NotificationExceptionUtils.verifyStringIsBlank(context,getuiAppId,getuiAppKey,getuiMasterSecret);

            // 配置返回每个用户返回用户状态，可选
            System.setProperty("gexin_pushList_needDetails", "true");
            // 配置返回每个别名及其对应cid的用户状态，可选
            // System.setProperty("gexin_pushList_needAliasDetails", "true");
            IGtPush push = new IGtPush(requestUrl, getuiAppKey, getuiMasterSecret);
            // 通知透传模板
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

            //android推送
            ListMessage androidMessage = new ListMessage();
            androidMessage.setData(notificationTemplate);
            // 设置消息离线，并设置离线时间
            androidMessage.setOffline(true);
            // 离线有效时间，单位为毫秒，可选
            androidMessage.setOfflineExpireTime(24 * 1000 * 3600);
            // 配置推送目标
            List androidTargetList = new ArrayList();

            for (String clientid : androidClientList){
                if(StringUtils.isNotBlank(clientid) && !"null".equals(clientid)){
                    Target androidTarget = new Target();
                    androidTarget.setAppId(getuiAppId);
                    androidTarget.setClientId(clientid);
//                target.setAlias();//按别名发送
                    androidTargetList.add(androidTarget);
                }
            }
            if(androidTargetList.size()>0){
                // taskId用于在推送时去查找对应的message
                String androidTaskId = push.getContentId(androidMessage);
                IPushResult ret = push.pushMessageToList(androidTaskId, androidTargetList);
                LOGGER.debug("消息群推 Android: [{}]",ret.getResponse().toString());
            }else{
                LOGGER.debug("消息群推:  无Android用户");
            }

            //ios推送
            ListMessage iosMessage = new ListMessage();
            iosMessage.setData(transmissionTemplate);
            // 设置消息离线，并设置离线时间
            iosMessage.setOffline(true);
            // 离线有效时间，单位为毫秒，可选
            iosMessage.setOfflineExpireTime(24 * 1000 * 3600);
            // 配置推送目标
            List iosTargetList = new ArrayList();
            for (String iosClientid : iosClientList){
                if(StringUtils.isNotBlank(iosClientid) && !"null".equals(iosClientid)){
                    Target iosTarget = new Target();
                    iosTarget.setAppId(getuiAppId);
                    iosTarget.setClientId(iosClientid);
//                target.setAlias();//按别名发送
                    iosTargetList.add(iosTarget);
                }
            }
            if(iosTargetList.size()>0){
                // taskId用于在推送时去查找对应的message
                String iosTaskId = push.getContentId(iosMessage);
                IPushResult ret = push.pushMessageToList(iosTaskId, iosTargetList);
                LOGGER.debug("消息群推 ios:  [{}]",ret.getResponse().toString());
            }else{
                LOGGER.debug("消息群推:  无ios用户");
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw e;
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

            //默认个推不发送通知
            if(send == null){
                send = Boolean.FALSE;
            }

            //默认消息中心记录发送历史
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

            //todo 修改消息模板之后的替换逻辑
//            text = MsgUtils.replaceTemplate(text,REGULAR,paramList,context);

            String textStyle = templateOutputBO.getTextStyle();
            Integer styleNum = MsgUtils.getContainNum(textStyle,symbol);
            textStyle = gePushText(paramList, context, textStyle ,styleNum);

            //todo 修改消息模板之后的替换逻辑
//            textStyle = MsgUtils.replaceTemplate(textStyle,REGULAR,paramList,context);

            for(Integer uid :userIdList){
                if(uid != null){
                    //todo 看情况不需要的话删除该插入操作  insertAppPushRecord(inputBO);//推送消息记录表
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
//                    //推送消息记录表
//                    dbService.addPushRecord(inputBO,context);

                    if(recordHistory){
                        //不管发送是否成功失败，都要在消息中心记录
                        AppPushHistoryInputBo historyInputBo = new AppPushHistoryInputBo();
                        historyInputBo.setAppId(appEnmu.getCode());
                        historyInputBo.setUserId(uid);
                        historyInputBo.setBusinessTypeId(businessTypeId);
                        historyInputBo.setTitle(title);
                        historyInputBo.setText(textStyle);
                        historyInputBo.setStatus(false);
                        //todo insert 批量插入  优化
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
            LOGGER.debug("绑定 用户id[{}]    clientid[{{}}]",userId,clientId);

            NotificationExceptionUtils.verifyObjectIsNull(context,userId,clientType,appEnmu);
            //clinetid如果用户拒绝了消息通知，clientid为空
            NotificationExceptionUtils.verifyStringIsBlank(context,clientId);

            if(!clientType.equals(ClientType.ANDROID) && !clientType.equals(ClientType.IOS)){
                NotificationExceptionUtils.throwExceptionParamInvalid(context);
            }

            // 判断clientid是否有和别userid绑定，存在的话解绑之前的记录
            UserClientRelationExample queryCidExample = new UserClientRelationExample();
            UserClientRelationExample.Criteria queryCidCriteria = queryCidExample.createCriteria();
            //todo 重新构建实体类
            queryCidCriteria.andAppIdEqualTo(appEnmu.getCode());
            queryCidCriteria.andUserIdNotEqualTo(userId);//非当前绑定userid
            queryCidCriteria.andClientIdEqualTo(clientId);
            queryCidCriteria.andStatusEqualTo(true);
            List<UserClientRelation> clientidExistList = userClientRelationMapper.selectByExample(queryCidExample);
            if(clientidExistList!=null && clientidExistList.size()>0){
                //批量更新解绑
                List<Integer> idList = new ArrayList<>();
                for(UserClientRelation  clientidExistDO: clientidExistList){
                    idList.add(clientidExistDO.getId());
                }
                LOGGER.debug("--------用户[{}] clientid历史相同存在 [{}] 个 pids:[{}]",userId,idList.size(),idList.toString());
                //更新之前存在，且是激活状态的clientid为失效
                if(idList.size()>0){
                    int resCount = userClientRelationMapper.updateClientIdLose(idList,appEnmu.getCode());
                    LOGGER.debug("--------更新 [{}] 条记录为失效",resCount);
                }
            }

            //查询用户id是否已经存在
            UserClientRelationExample userClientRelationExample = new UserClientRelationExample();
            UserClientRelationExample.Criteria criteria = userClientRelationExample.createCriteria();
            criteria.andAppIdEqualTo(appEnmu.getCode());
            criteria.andUserIdEqualTo(userId);
            List<UserClientRelation> userClientRelationDOList = userClientRelationMapper.selectByExample(userClientRelationExample);

            if(userClientRelationDOList!=null && userClientRelationDOList.size()>0){
                //有记录，更新状态为有效
//                    UserClientRelationDO resDo = userClientRelationDOList.get(0);
//                    String dbClientId = "";
//                    if(resDo!=null){
//                        dbClientId = resDo.getClientId();
//                    }
//                    if(dbClientId!=null && dbClientId.equals(clientId)){
//                        //clientid相同，不需要更新
//                    }

                UserClientRelation updateDo = new UserClientRelation();
                updateDo.setId(userClientRelationDOList.get(0).getId());
                updateDo.setClientId(clientId);
                updateDo.setPhoneType(clientType.getType());
                updateDo.setStatus(true);
                LOGGER.debug("用户[{}]clientId激活成功(update)",userId);
                return userClientRelationMapper.updateByPrimaryKeySelective(updateDo);
            }else{
                //没有绑定则新增一条记录
                UserClientRelation userClientRelationDO = new UserClientRelation();
                userClientRelationDO.setAppId(appEnmu.getCode());
                userClientRelationDO.setUserId(userId);
                userClientRelationDO.setClientId(clientId);
                userClientRelationDO.setPhoneType(clientType.getType());
                userClientRelationDO.setStatus(true);
                LOGGER.debug("用户[{}]clientId激活成功(insert)",userId);
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
            LOGGER.debug("解绑 用户id[{}]    clientid[{{}}]",userId,clientId);
            //校验userid 和手机系统类型
            NotificationExceptionUtils.verifyObjectIsNull(context,userId,clientType,appEnmu);
            //校验clientId
            NotificationExceptionUtils.verifyStringIsBlank(context,clientId);
            //校验手机操作系统类型
            if(!clientType.equals(ClientType.ANDROID) && !clientType.equals(ClientType.IOS)){
                //错误的手机系统类型
                NotificationExceptionUtils.throwExceptionParamInvalid(context);
            }
            //查询该信息是否存在
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
                LOGGER.debug("用户[{}]退出登录，解绑的id号[{}]",userId,relationDOList.get(0).getId());
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
        //todo  和方法unbindClientRelation（）冗余
        try {
            LOGGER.debug("用户[{}]clientId失效",userId);
            //clientId失效
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

    //todo appid 查询条件
    @Override
    public List<UserClientRelationOutputBO> queryUserClientRelationByUserIds(Integer appId,List<Integer> uids, Context context) {
        try {
            NotificationExceptionUtils.verifyObjectIsNull(context,uids);
            //todo  要不要 换成xml sql的查询
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

            //默认个推不发送通知
            if(send == null){
                send = Boolean.FALSE;
            }

            //默认消息中心记录发送历史
            if(recordHistory == null){
                recordHistory = Boolean.FALSE;
            }
            if(recordHistory){
                //todo  如果需要在消息中心记录，那就要对所有用户 的push histroy新增一条记录
            }
            if(send){
                //要打开的url页面
                String openUrl = "";
                IGtPush push = new IGtPush(requestUrl, getuiAppKey, getuiMasterSecret);
                LinkTemplate template = GeTuiTemplate.getLinkTemplate(getuiAppId,getuiAppKey,title,text,openUrl);
                AppMessage message = new AppMessage();
                message.setData(template);
                message.setOffline(true);
                //离线有效时间，单位为毫秒，可选
                message.setOfflineExpireTime(24 * 1000 * 3600);
                //设置推送时间
                //message.setPushTime("201710261050");//// TODO: 2018/11/24    设置定时推送的时间，需要开通个推VIP
                //推送给App的目标用户需要满足的条件
                AppConditions cdt = new AppConditions();
                List<String> appIdList = new ArrayList<String>();
                appIdList.add(getuiAppId);
                message.setAppIdList(appIdList);
                //手机类型
                List<String> phoneTypeList = new ArrayList<String>();
                //省份
                List<String> provinceList = new ArrayList<String>();
                //自定义tag
                List<String> tagList = new ArrayList<String>();
                cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
                cdt.addCondition(AppConditions.REGION, provinceList);
                cdt.addCondition(AppConditions.TAG, tagList);
                message.setConditions(cdt);

                IPushResult ret = push.pushMessageToApp(message, taskGroupName);
                try {
                    LOGGER.info(appEnmu.getDesc()+" APP群推结果:[{}]",ret.getResponse().toString());
                }catch (Exception e){
                    LOGGER.error(e.getMessage());
                }
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            throw e;
        }
    }


    //todo 参数列表中的参数格式化 统一在这里做
    //    private static List getParseAfterParamList(ApplicationEnmu applicationEnmu,
//                                        String pushTemplateCode,
//                                        List list,
//                                        Context context){
//        ExceptionUtils.verifyObjectIsNull(context,applicationEnmu,list);
//
//        if(ApplicationEnmu.BR_Eloan.getCode().equals(applicationEnmu.getCode())){
//            //转换金额为巴西格式
//            if(PushTemplateEnum.BR_ELOAN_1.getCode().equals(pushTemplateCode)){
//                //取list第二位
//                list.get(1)
//            }
//        }
//
//    }

}
