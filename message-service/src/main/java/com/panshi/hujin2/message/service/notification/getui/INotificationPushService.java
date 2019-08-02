package com.panshi.hujin2.message.service.notification.getui;

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
public interface INotificationPushService {

    //todo 关于个推提供的 别名绑定映射



    /**
     * @description:            批量单推
     * @param appEnmu           app应用程序id
     * @param pushTemplateCode  推送模板code
     * @param map               key：uid    value:参数模板替换的值
     * @param send              是否发送手机通知
     * @param recordHistory     消息中心是否记录
     * @param context           国际化上下文
     * @Author shenjiankang
     * @Date 2018/8/9 20:49
     */
    void batchPushToSingle(ApplicationEnmu appEnmu,
                           String pushTemplateCode,
                           Map<Integer, List> map,
                           Boolean send,
                           Boolean recordHistory,
                           Context context)throws Exception;

    /**
     * @description:            单个推送信息（TransmissionTemplate 透传模板）
     * @param appEnmu
     * @param userId            用户id
     * @param clientId          app客户端id
     * @param title             通知栏标题
     * @param text              通知栏内容
     * @param businessTypeId    消息业务类型id
     * @param context           国际化上下文
     * @Author shenjiankang
     * @Date 2018/6/26 14:19
     */
    void pushMessageToSingleByNotification(ApplicationEnmu appEnmu,
                                           Integer userId,
                                           String clientId,
                                           String title,
                                           String text,
                                           String transmissionContent,
                                           Integer businessTypeId,
                                           Context context);

    /**
     * @description:            单个推送信息（TransmissionTemplate 透传模板）
     * @param appEnmu
     * @param userId            用户id
     * @param clientId          app客户端id
     * @param title             通知栏标题
     * @param text              通知栏内容
     * @param businessTypeId    消息业务类型id
     * @param context           国际化上下文
     * @Author shenjiankang
     * @Date 2018/6/26 14:35
     */
    void pushMessageToSingleByTransmission(ApplicationEnmu appEnmu,
                                           Integer userId,
                                           String clientId,
                                           String title,
                                           String text,
                                           String transmissionContent,
                                           Integer businessTypeId,
                                           Context context);


    /**
     * @description:            根据用户id、通知标题、通知内容推送消息(单个推送)  (分模板，安卓：点击通知打开应用模板。ios：透传模板 )
     * @param appEnmu
     * @param userId            用户id
     * @param businessTypeId    所属业务id
     * @param title             通知栏标题
     * @param text              通知栏内容
     * @param context           国际化上下文
     * @Author shenjiankang
     * @Date 2018/6/27 14:45
     */
    void pushMessageToSingle(ApplicationEnmu appEnmu,
                             Integer userId,
                             Integer businessTypeId,
                             String title,
                             String text,
                             Context context);

    void pushMessageToSingle(
            ApplicationEnmu appEnmu,
            Integer userId,
            Integer businessTypeId,
            String title,
            String text,
            Boolean send,
            Boolean recordHistory,
            Context context);
    /**
     * @description:            根据用户id、模板code推送消息(单个推送)  (分模板，安卓：点击通知打开应用模板。ios：透传模板 )
     * @param appEnmu
     * @param userId            用户id
     * @param pushTemplateCode  模板编号
     * @param paramList         替换参数列表
     * @param send              是否推送消息
     * @param recordHistory     消息中心是否入库
     * @param context           国际化上下文
     * @Author shenjiankang
     * @Date 2018/6/27 14:46
     */
    void pushMessageToSingle(
            ApplicationEnmu appEnmu,
            Integer userId,
            String pushTemplateCode,
            List paramList,
            Boolean send,
            Boolean recordHistory,
            Context context);

    /**
     * @description:            根据用户id列表、通知标题、通知内容推送消息(列表推送)  (分模板，安卓：点击通知打开应用模板。ios：透传模板 )
     * @param appEnum
     * @param userIdList        用户id列表
     * @param title             通知栏标题
     * @param text              通知栏内容
     * @param context           国际化上下文
     * @Author shenjiankang
     * @Date 2018/6/27 14:48
     */
    String pushMessageToList(ApplicationEnmu appEnum,
                           List<Integer> userIdList,
                           String title,
                           String text,
                           Context context);

    /**
     * @description:        根据用户list批量发送
     * @param appEnum       app
     * @param userIdList    userid list
     * @param title         推送标题
     * @param text          推送内容
     * @param send          是否推送
     * @param recordHistory 是否在消息中心历史记录表记录
     * @param context       国际化
     * @Author shenjiankang
     * @Date 2019/3/8 21:45
     */
    String pushMessageToList(ApplicationEnmu appEnum,
                             List<Integer> userIdList,
                             String title,
                             String text,
                             Boolean send,
                             Boolean recordHistory,
                             Context context);

    /**
     * @description:            根据用户id列表、模板code推送消息(列表推送)  (分模板，安卓：点击通知打开应用模板。ios：透传模板 )
     * @param appEnmu
     * @param userIdList        用户id列表
     * @param pushTemplateCode  模板编号
     * @param paramList         替换参数列表
     * @param send              是否发送个推通知
     * @param recordHistory     消息中心不记录
     * @param context           国际化上下文
     * @Author shenjiankang
     * @Date 2018/6/27 14:48
     */
    void pushMessageToList(ApplicationEnmu appEnmu,
                           List<Integer> userIdList,
                           String pushTemplateCode,
                           List paramList,
                           Boolean send,
                           Boolean recordHistory,
                           Context context);




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

    //todo  解绑功能重复？？？
    /**
     * @description:        解绑clientiId和userid
     * @param userId
     * @param clientId
     * @Author shenjiankang
     * @Date 2018/6/26 13:59
     */
    int setUserClientIdStatusInvalid(Integer userId, String clientId);

    /**
     * @description:        根据用户id获取用户clientId映射信息
     * @param userId
     * @param status
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/26 16:00
     */
    UserClientRelationOutputBO queryUserClientRelationByUserId(Integer appId,
                                                               Integer userId,
                                                               Boolean status,
                                                               Context context);

    /**
     * @description:        根据用户id列表获取映射信息
     * @param uids
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/27 16:37
     */
    List<UserClientRelationOutputBO> queryUserClientRelationByUserIds(Integer appId, List<Integer> uids,
                                                                      Context context);

    /**
     * @description:        新增推送记录（推送日志）
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/26 13:58
     */
    int addPushRecord(AppPushRecordInputBO inputBO,
                      Context context);

    /**
     * @description:            app群推  定时推送
     * @param appEnmu           对应的app
     * @param title             标题
     * @param text              内容
     * @param sendTime          定时发送的时间 格式：201710261050 (sendTime 参数需要开通个推VIP， 暂时不支持)
     * @param taskGroupName 	任务别名
     * @param send              是否push
     * @param recordHistory     是否在用户消息中心记录
     * @param context           国际化对象
     * @Author shenjiankang
     * @Date 2018/11/30 10:31
     */
    void pushAll(ApplicationEnmu appEnmu,
                 String title,
                 String text,
                 String sendTime,
                 String taskGroupName,
                 Boolean send,
                 Boolean recordHistory,
                 Context context);


    /**
     *@Description:                 根据app群推
     * @param appEnmu	            对应的app
     * @param templateEnum          推送模板类型
     * @param transmissionContent   透传参数
     * @param title                 标题
     * @param text                  内容
     * @param sendTime              定时发送的时间 格式：201710261050 (sendTime 参数需要开通个推VIP， 暂时不支持)
     * @param taskGroupName         任务别名  (任务组 命名规范：taskGroupName的规则是只能传数字，字母，下划线和中文taskGroupName的utf8最大字节为40)
     * @param send                  是否push
     * @param recordHistory         是否在用户消息中心记录
     * @param context               国际化对象
     *@Author: shenJianKang
     *@date: 2019/8/1 11:50
     */
    void pushAllByTemplate(ApplicationEnmu appEnmu,
                           GeTuiPushTemplateEnum templateEnum,
                           String transmissionContent,
                           String title,
                           String text,
                           String sendTime,
                           String taskGroupName,
                           Boolean send,
                           Boolean recordHistory,
                           Context context);




}
