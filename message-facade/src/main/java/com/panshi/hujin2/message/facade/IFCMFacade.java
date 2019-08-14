package com.panshi.hujin2.message.facade;/**
 * @author shenJianKang
 * @date 2019/8/5 18:21
 */

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
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

}
