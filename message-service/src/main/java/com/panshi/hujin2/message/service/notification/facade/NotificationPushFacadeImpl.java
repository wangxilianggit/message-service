package com.panshi.hujin2.message.service.notification.facade;


import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.common.enmu.ClientType;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.facade.INotificationPushFacade;
import com.panshi.hujin2.message.facade.bo.BatchPushToSingleBO;
import com.panshi.hujin2.message.service.notification.getui.INotificationPushService;
import com.panshi.hujin2.message.service.notification.utils.NotificationExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * create by shenjiankang on 2018/6/25 21:00
 */
@Service
public class NotificationPushFacadeImpl implements INotificationPushFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationPushFacadeImpl.class);


    @Autowired
    private INotificationPushService notificationPushService;

    @Override
    public BasicResult<Void> pushMessageToSingle(
                                                 ApplicationEnmu appEnmu,
                                                 Integer userId,
                                                 String pushTemplateCode,
                                                 List paramList,
                                                 Boolean send,
                                                 Context context) {
        try {
            notificationPushService.pushMessageToSingle(appEnmu,
                                                        userId,
                                                        pushTemplateCode,
                                                        paramList,
                                                        send,
                                                        Boolean.TRUE,
                                                        context);
            return BasicResult.ok();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return NotificationExceptionUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<Void> pushMessageToSingle(ApplicationEnmu appEnmu,
                                                 Integer userId,
                                                 String pushTemplateCode,
                                                 List paramList,
                                                 Boolean send,
                                                 Boolean recordHistory,
                                                 Context context) {
        try {
            notificationPushService.pushMessageToSingle(appEnmu,
                    userId,
                    pushTemplateCode,
                    paramList,
                    send,
                    recordHistory,
                    context);
            return BasicResult.ok();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return NotificationExceptionUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<Void> pushMessageToList(ApplicationEnmu appEnmu,
                                               List<Integer> userIdList,
                                               String pushTemplateCode,
                                               List paramList,
                                               Context context) {
        try {
            notificationPushService.pushMessageToList(appEnmu,
                    userIdList,
                    pushTemplateCode,
                    paramList,
                    Boolean.TRUE,
                    Boolean.TRUE,
                    context);
            return BasicResult.ok();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return NotificationExceptionUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<Void> pushMessageToList(ApplicationEnmu appEnmu,
                                               List<Integer> userIdList,
                                               String pushTemplateCode,
                                               List paramList,
                                               Boolean send,
                                               Boolean recordHistory,
                                               Context context) {
        try {
            notificationPushService.pushMessageToList(appEnmu,
                    userIdList,
                    pushTemplateCode,
                    paramList,
                    send,
                    recordHistory,
                    context);
            return BasicResult.ok();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return NotificationExceptionUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<Void> batchPushToSingle(ApplicationEnmu appEnmu,
                                               String pushTemplateCode,
                                               Map<Integer, List> map,
                                               Boolean send,
                                               Context context) {
        try {
            notificationPushService.batchPushToSingle(appEnmu,
                                                        pushTemplateCode,
                                                        map,
                                                        send,
                                                        Boolean.TRUE,
                                                        context);
            return BasicResult.ok();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return NotificationExceptionUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<Void> batchPushToSingle(ApplicationEnmu appEnmu,
                                               String pushTemplateCode,
                                               Map<Integer, List> map,
                                               Boolean send,
                                               Boolean recordHistory,
                                               Context context) {
        try {
            notificationPushService.batchPushToSingle(appEnmu,
                    pushTemplateCode,
                    map,
                    send,
                    recordHistory,
                    context);
            return BasicResult.ok();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return NotificationExceptionUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<Void> batchPushToSingle(String pushTemplateCode,
                                               Map<Integer, BatchPushToSingleBO> map,
                                               Boolean send,
                                               Boolean recordHistory,
                                               Context context) {
        try {
            NotificationExceptionUtils.verifyObjectIsNull(context,map);
            NotificationExceptionUtils.verifyStringIsBlank(context, pushTemplateCode);
            for (Iterator<Integer> iterator = map.keySet().iterator();iterator.hasNext();){
                Integer userId = iterator.next();
                BatchPushToSingleBO  batchPushToSingleBO = map.get(userId);
                Map<Integer,List> paramMap = new HashMap<>();
                paramMap.put(userId, batchPushToSingleBO.getList());
                batchPushToSingle(batchPushToSingleBO.getAppEnum(),pushTemplateCode,paramMap,send,recordHistory,context);
            }
            return BasicResult.ok();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return NotificationExceptionUtils.throwDefinedException(e, context);
        }
    }

    @Override
    @Transactional
//    @TxTransaction
    public BasicResult<Void> bindUidAndCid(ApplicationEnmu appEnmu,
                                            Integer userId,
                                          String clientId,
                                          ClientType clientType,
                                          Context context) {
        try {
            notificationPushService.insertUserClientRelation(appEnmu,userId,clientId,clientType,context);
            return BasicResult.ok();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return NotificationExceptionUtils.throwDefinedException(e, context);
//            throw e;
        }
    }

    @Override
    @Transactional
//    @TxTransaction
    public BasicResult<Void> unbindUidAndCid(ApplicationEnmu appEnmu,Integer userId, String clientId, ClientType clientType, Context context) {
        try {
            notificationPushService.unbindClientRelation(appEnmu,userId,clientId,clientType,context);
            return BasicResult.ok();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return NotificationExceptionUtils.throwDefinedException(e, context);
//            throw e;
        }
    }

    @Override
    public BasicResult<Void> pushAll(ApplicationEnmu appEnmu,
                                     String title,
                                     String text,
                                     String sendTime,
                                     String taskGroupName,
                                     Boolean send,
                                     Boolean recordHistory,
                                     Context context) {
        try {
            notificationPushService.pushAll(appEnmu,title,text,sendTime,taskGroupName,send,recordHistory,context);
            return BasicResult.ok();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return NotificationExceptionUtils.throwDefinedException(e, context);
        }
    }
}
