package com.panshi.hujin2.message.dao.mapper.notification;

import com.panshi.hujin2.message.dao.model.AppPushHistory;
import com.panshi.hujin2.message.dao.model.AppPushHistoryExample;
import com.panshi.hujin2.message.domain.qo.getui.AppPushHistoryQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppPushHistoryMapper {
    long countByExample(AppPushHistoryExample example);

    int deleteByExample(AppPushHistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppPushHistory record);

    int insertSelective(AppPushHistory record);

    List<AppPushHistory> selectByExample(AppPushHistoryExample example);

    AppPushHistory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppPushHistory record, @Param("example") AppPushHistoryExample example);

    int updateByExample(@Param("record") AppPushHistory record, @Param("example") AppPushHistoryExample example);

    int updateByPrimaryKeySelective(AppPushHistory record);

    int updateByPrimaryKey(AppPushHistory record);

    /**
     *  自定义方法
     */
    /**
     * @description:        统计记录数
     * @param qo
     * @Author shenjiankang
     * @Date 2018/7/7 10:16
     */
    Integer countPushHistory(AppPushHistoryQO qo);

    /**
     * @description:        查询历史消息
     * @param qo
     * @Author shenjiankang
     * @Date 2018/7/7 10:17
     */
    List<AppPushHistory> queryPushHistory(AppPushHistoryQO qo);

    /**
     * @description:        根据用户ud修改状态为已读
     * @param userId
     * @Author shenjiankang
     * @Date 2018/7/7 10:37
     */
    int updateStatusReadByUid(@Param("userId") Integer userId, @Param("appId") Integer appId);

    /**
     * @description:        批量插入
     * @param list
     * @Author shenjiankang
     * @Date 2018/7/30 17:46
     */
    void insertBatch(@Param("list") List<AppPushHistory> list);
}