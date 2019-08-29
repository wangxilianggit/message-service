package com.panshi.hujin2.message.facade;/**
 * @author shenJianKang
 * @date 2019/8/5 18:21
 */

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.common.enmu.ClientType;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.service.Context;

import java.util.List;

/**
 *@author: shenJianKang
 *@create: 2019-08-05 18:21
 *
 * FCM消息推送
 */
public interface IFCMFacade {

    //向单个设备推送
    BasicResult<Void> pushMessageToSingle(ApplicationEnmu appEnmu,
                                          Integer userId,
                                          String pushTemplateCode,
                                          List paramList,
                                          Boolean send,
                                          Boolean recordHistory,
                                          Context context);


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

    //todo  绑定


    //todo  解绑

}
