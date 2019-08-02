package com.panshi.hujin2.message.facade;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.facade.bo.AppPushHistoryPagingOutputBO;

import java.util.List;

/**
 * create by shenjiankang on 2018/7/6 11:29
 *
 * 消息中心，消息历史
 */
public interface INotificationHistoryFacade {

    /**
     * @description:    根据用户id查询历史消息（分页）
     * @param appEnmu
     * @param uid       用户id
     * @param nextId    注：首次查询不传nextId，返回的查询结果对象中会返回下次查询要用到的nextId
     * @param limit     每页条数
     * @param context   国际化上下文
     * @Author shenjiankang
     * @Date 2018/7/7 10:13
     */
    BasicResult<AppPushHistoryPagingOutputBO> queryPushHistoryByUid(ApplicationEnmu appEnmu,
                                                                    Integer uid,
                                                                    Integer nextId,
                                                                    Integer limit,
                                                                    Context context);

    /**
     * @description:    根据用户id获取消息未读总数
     * @param appEnmu
     * @param uid
     * @param context
     * @Author shenjiankang
     * @Date 2018/7/7 10:31
     */
    BasicResult<Integer> queryUnreadMsgNumByUid(ApplicationEnmu appEnmu, Integer uid, Context context);

    /**
     * @description:     根据用户id修改该用户下的所有历史消息状体‘未读’为‘已读’
     * @param appEnmu
     * @param uid
     * @param context
     * @return           返回更新的执行行数
     * @Author shenjiankang
     * @Date 2018/7/7 10:50
     */
    BasicResult<Integer> updateStatusReadByUid(ApplicationEnmu appEnmu, Integer uid, Context context);


    /**
     *@Description:         消息中心批量插入
     * @param appEnum
     * @param userIdList
     * @param title
     * @param text
     * @param recordHistory
     * @param context
     *@Author: shenJianKang
     *@date: 2019/8/1 15:02
     */
    BasicResult<Void> batchInsertMsgHistory(ApplicationEnmu appEnum,
                                            List<Integer> userIdList,
                                            String title,
                                            String text,
                                            Boolean recordHistory,
                                            Context context);


}
