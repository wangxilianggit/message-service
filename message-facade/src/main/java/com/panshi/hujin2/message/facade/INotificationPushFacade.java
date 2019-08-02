package com.panshi.hujin2.message.facade;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.common.enmu.ClientType;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.domain.enums.getui.GeTuiPushTemplateEnum;
import com.panshi.hujin2.message.facade.bo.BatchPushToSingleBO;

import java.util.List;
import java.util.Map;

/**
 * create by shenjiankang on 2018/6/25 14:54
 *
 * 消息推送
 */
public interface INotificationPushFacade {

    /**
     * @description:            默认消息中心记录入库,根据用户id、模板code推送消息(单个推送)  (分模板，安卓：点击通知打开应用模板。ios：透传模板 )
     * @param appEnmu
     * @param userId            用户id
     * @param pushTemplateCode  模板编号
     * @param paramList         替换参数列表
     * @param send              消息推送通知 ture发送，false不发送
     * @param context           国际化上下文
     * @Author shenjiankang
     * @Date 2018/6/27 14:46
     */
    @Deprecated
    BasicResult<Void> pushMessageToSingle(ApplicationEnmu appEnmu,
                                          Integer userId,
                                          String pushTemplateCode,
                                          List paramList,
                                          Boolean send,
                                          Context context);

    /**
     * @description:            是否发送，消息中心记录根据参数选择。(单个推送)
     * @param appEnmu
     * @param userId
     * @param pushTemplateCode
     * @param paramList
     * @param send              是否通知推送
     * @param recordHistory     是否在消息中心记录
     * @param context
     * @Author shenjiankang
     * @Date 2018/8/9 21:41
     */
    BasicResult<Void> pushMessageToSingle(ApplicationEnmu appEnmu,
                                          Integer userId,
                                          String pushTemplateCode,
                                          List paramList,
                                          Boolean send,
                                          Boolean recordHistory,
                                          Context context);


    /**
     * @description:            单个推送，直接入参 传入 标题和发送内容
     * @param appEnmu
     * @param userId
     * @param businessTypeId
     * @param title
     * @param text
     * @param send
     * @param recordHistory
     * @param context
     * @Author shenjiankang
     * @Date 2019/3/8 22:35
     */
    BasicResult<Void> pushMessageToSingle(ApplicationEnmu appEnmu,
                                          Integer userId,
                                          Integer businessTypeId,
                                          String title,
                                          String text,
                                          Boolean send,
                                          Boolean recordHistory,
                                          Context context);
    /**
     * @description:            默认个推发送，消息入库，根据用户id列表、模板code推送消息(列表推送)  (分模板，安卓：点击通知打开应用模板。ios：透传模板 )
     * @param appEnmu
     * @param userIdList        用户id列表
     * @param pushTemplateCode  模板编号
     * @param paramList         替换参数列表
     * @param context           国际化上下文
     * @Author shenjiankang
     * @Date 2018/6/27 14:48
     */
    @Deprecated
    BasicResult<Void> pushMessageToList(ApplicationEnmu appEnmu,
                                        List<Integer> userIdList,
                                        String pushTemplateCode,
                                        List paramList,
                                        Context context);

    /**
     * @description:            根据参数选择是否发送通知，消息中心记录(列表推送)
     * @param appEnmu
     * @param userIdList
     * @param pushTemplateCode
     * @param paramList
     * @param send              是否发送通知
     * @param recordHistory     是否在消息中心记录
     * @param context
     * @Author shenjiankang
     * @Date 2018/8/9 21:45
     */
    BasicResult<Void> pushMessageToList(ApplicationEnmu appEnmu,
                                        List<Integer> userIdList,
                                        String pushTemplateCode,
                                        List paramList,
                                        Boolean send,
                                        Boolean recordHistory,
                                        Context context);


    /**
     * @description:            根据用户id list 批量推送（入参title，text不需要再查询出来）
     * @param appEnum
     * @param userIdList
     * @param pushTitle
     * @param pushContent
     * @param send
     * @param recordHistory
     * @param context
     * @Author shenjiankang
     * @Date 2019/3/8 21:52
     */
    BasicResult<String> pushMessageToList(ApplicationEnmu appEnum,
                                          List<Integer> userIdList,
                                          String pushTitle,
                                          String pushContent,
                                          Boolean send,
                                          Boolean recordHistory,
                                          Context context);

    /**
     * @description:            批量单推,消息中心会记录该数据(场景：多个用户推送，同一个模板，不同参数，时使用该接口)
     * @param appEnmu           app应用id
     * @param pushTemplateCode  推送模板编号
     * @param map               map key用户id，value 是该用户需要替换的参数列表list
     * @param send              是否推送通知，如果选false，只在用户的消息中心展示
     * @param context           国际化上下文
     * @Author shenjiankang
     * @Date 2018/7/30 20:18
     */
    @Deprecated
    BasicResult<Void> batchPushToSingle(ApplicationEnmu appEnmu,
                                        String pushTemplateCode,
                                        Map<Integer, List> map,
                                        Boolean send,
                                        Context context);

    /**
     * @description:            批量单推,消息中心记录该数据通过参数选择(场景：多个用户推送，同一个模板，不同参数，时使用该接口)
     * @param appEnmu
     * @param pushTemplateCode
     * @param map
     * @param send
     * @param recordHistory     是否在消息中心记录发送历史
     * @param context
     * @Author shenjiankang
     * @Date 2018/8/9 20:52
     */
    BasicResult<Void> batchPushToSingle(ApplicationEnmu appEnmu,
                                        String pushTemplateCode,
                                        Map<Integer, List> map,
                                        Boolean send,
                                        Boolean recordHistory,
                                        Context context);

    /**
     * @description:            批量单推,消息中心记录该数据通过参数选择(场景：多个用户推送，同一个模板，不同参数，时使用该接口)（支持不同马甲）
     * @param pushTemplateCode
     * @param map               key是用户id，BatchPushToSingleBO对象包含要替换推送模板的参数list和app应用
     * @param send
     * @param recordHistory
     * @param context
     * @Author shenjiankang
     * @Date 2018/10/10 22:14
     */
    BasicResult<Void> batchPushToSingle(String pushTemplateCode,
                                        Map<Integer, BatchPushToSingleBO> map,
                                        Boolean send,
                                        Boolean recordHistory,
                                        Context context);


    /**
     * @Description:        绑定用户id和app客户端的clientId
     * @param appEnmu       app应用枚举
     * @param userId        用户id
     * @param clientId      app客户端id
     * @param clientType     手机操作系统类型：2-ios 3-android
     * @Author: shenJianKang
     * @Date: 2018/7/6 10:27
     */
    BasicResult<Void> bindUidAndCid(ApplicationEnmu appEnmu,
                                    Integer userId,
                                    String clientId,
                                    ClientType clientType,
                                    Context context);

    /**
     * @description         用户退出时解绑userid和clisnetid
     * @param appEnmu       app应用枚举
     * @param userId        用户id
     * @param clientId      app客户端id
     * @param clientType    （枚举）手机操作系统类型 2--ios  3--android
     * @Author shenjiankang
     * @Date 2018/7/6 20:50
     */
    BasicResult<Void> unbindUidAndCid(ApplicationEnmu appEnmu, Integer userId, String clientId, ClientType clientType, Context context);

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
    BasicResult<Void> pushAll(ApplicationEnmu appEnmu,
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
     * @param taskGroupName         任务别名
     * @param send                  是否push
     * @param recordHistory         是否在用户消息中心记录
     * @param context               国际化对象
     *@Author: shenJianKang
     *@date: 2019/8/1 11:50
     */
    BasicResult<Void> pushAllByTemplate(
                              ApplicationEnmu appEnmu,
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
