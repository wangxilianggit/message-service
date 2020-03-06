package com.panshi.hujin2.message.service.onesignal;/**
 * @author shenJianKang
 * @date 2020/1/9 16:32
 */

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.common.enmu.ClientType;
import com.panshi.hujin2.base.service.Context;

/**
 *@author: shenJianKang
 *@create: 2020-01-09 16:32
 */
public interface IOneSignalService {

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
