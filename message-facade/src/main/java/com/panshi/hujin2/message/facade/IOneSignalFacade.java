package com.panshi.hujin2.message.facade;/**
 * @author shenJianKang
 * @date 2020/1/3 10:16
 */

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.common.enmu.ClientType;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.service.Context;

import java.util.List;

/**
 *@author: shenJianKang
 *@create: 2020-01-03 10:16
 */
public interface IOneSignalFacade {

    //向单个设备推送， 配置模版推送
    BasicResult<Void> pushMessageToSingle(ApplicationEnmu appEnmu,
                                          Integer userId,
                                          String pushTemplateCode,
                                          List paramList,
                                          Boolean send,
                                          Boolean recordHistory,
                                          Context context);

    //向单个设备推送，自定义内容
    BasicResult<Void> pushMessageToSingle(ApplicationEnmu appEnmu,
                                          Integer userId,
                                          Integer businessTypeId,
                                          String title,
                                          String text,
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
                                          String title,
                                          String text,
                                          Boolean send,
                                          Boolean recordHistory,
                                          Context context);

    //TODO: 2020/1/3 10:17 by ShenJianKang  实现批量发送


    /**
     * @Description:        绑定用户id和app客户端的clientId
     * @param appEnmu       app应用枚举
     * @param userId        用户id
     * @param clientToken  app客户端Token
     * @param clientType     手机操作系统类型：2-ios 3-android
     * @Author: shenJianKang
     * @Date: 2018/7/6 10:27
     */
    BasicResult<Void> bindUidAndCid(ApplicationEnmu appEnmu,
                                    Integer userId,
                                    String clientToken,
                                    ClientType clientType,
                                    Context context);

    /**
     * @description         用户退出时解绑userid和clisnetid
     * @param appEnmu       app应用枚举
     * @param userId        用户id
     * @param clientToken   app客户端Token
     * @param clientType    （枚举）手机操作系统类型 2--ios  3--android
     * @Author shenjiankang
     * @Date 2018/7/6 20:50
     */
    BasicResult<Void> unbindUidAndCid(ApplicationEnmu appEnmu,
                                      Integer userId,
                                      String clientToken,
                                      ClientType clientType,
                                      Context context);
}
