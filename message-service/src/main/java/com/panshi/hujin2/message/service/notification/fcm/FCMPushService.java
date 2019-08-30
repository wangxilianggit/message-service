package com.panshi.hujin2.message.service.notification.fcm;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.common.enmu.ClientType;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.domain.enums.getui.GeTuiPushTemplateEnum;
import com.panshi.hujin2.message.facade.bo.AppPushRecordInputBO;
import com.panshi.hujin2.message.facade.bo.UserClientRelationOutputBO;

import java.util.List;
import java.util.Map;

/**
 * create by shenjiankang on 2018/7/7 11:24
 */
public interface FCMPushService {


    /**
     * @description:        绑定设备APP的clientId和userId
     * @param appEnmu       app应用枚举
     * @param userId        用户id
     * @param clientId      APP上的clientId
     * @param clientType    手机操作系统枚举
     * @param context       国际化上下文
     * @Author shenjiankang
     * @Date 2018/6/26 10:24
     */
    int insertUserClientRelation(ApplicationEnmu appEnmu,
                                 Integer userId,
                                 String clientId,
                                 ClientType clientType,
                                 Context context);

    /**
     * @description:        解绑设备APP的clientId和userId
     * @param appEnmu       app应用枚举
     * @param userId        用户id
     * @param clientId      APP上的clientId
     * @param clientType    手机操作系统枚举
     * @param context       国际化上下文
     * @Author shenjiankang
     * @Date 2018/6/26 10:28
     */
    int unbindClientRelation(ApplicationEnmu appEnmu,
                             Integer userId,
                             String clientId,
                             ClientType clientType,
                             Context context);






}
