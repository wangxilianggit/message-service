package com.panshi.hujin2.message.service.notification.facade;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.facade.INotificationHistoryFacade;
import com.panshi.hujin2.message.facade.bo.AppPushHistoryPagingOutputBO;
import com.panshi.hujin2.message.service.notification.getui.INotificationHistoryService;
import com.panshi.hujin2.message.service.notification.utils.NotificationExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create by shenjiankang on 2018/7/6 11:38
 */
@Service
public class NotificationHistoryFacadeImpl implements INotificationHistoryFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationHistoryFacadeImpl.class);

    @Autowired
    private INotificationHistoryService notificationHistoryService;

    @Override
    public BasicResult<AppPushHistoryPagingOutputBO> queryPushHistoryByUid(ApplicationEnmu appEnmu,
                                                                           Integer uid,
                                                                           Integer nextId,
                                                                           Integer limit,
                                                                           Context context) {
        try {
            AppPushHistoryPagingOutputBO res =
                    notificationHistoryService.queryPushHistoryByUid(appEnmu,uid, nextId, limit, context);
            return BasicResult.ok(res);
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return NotificationExceptionUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<Integer> queryUnreadMsgNumByUid(ApplicationEnmu appEnmu,Integer uid, Context context) {
        try {
            Integer count = notificationHistoryService.queryUnreadMsgNumByUid(appEnmu,uid, context);
            return BasicResult.ok(count);
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return NotificationExceptionUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<Integer> updateStatusReadByUid(ApplicationEnmu appEnmu,Integer uid, Context context) {
        try {
            Integer res = notificationHistoryService.updateStatusReadByUid(appEnmu,uid, context);
            return BasicResult.ok(res);
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return NotificationExceptionUtils.throwDefinedException(e, context);
        }
    }
}
