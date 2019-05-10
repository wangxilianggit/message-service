package com.panshi.hujin2.message.dao.mapper.message;

import com.panshi.hujin2.message.dao.model.UrgentRecallMsgLog;
import com.panshi.hujin2.message.dao.model.UrgentRecallMsgLogExample;
import java.util.List;

import com.panshi.hujin2.message.domain.qo.UrgentRecallMsgLogQO;
import org.apache.ibatis.annotations.Param;

public interface UrgentRecallMsgLogMapper {
    long countByExample(UrgentRecallMsgLogExample example);

    int deleteByExample(UrgentRecallMsgLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UrgentRecallMsgLog record);

    int insertSelective(UrgentRecallMsgLog record);

    List<UrgentRecallMsgLog> selectByExample(UrgentRecallMsgLogExample example);

    UrgentRecallMsgLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UrgentRecallMsgLog record, @Param("example") UrgentRecallMsgLogExample example);

    int updateByExample(@Param("record") UrgentRecallMsgLog record, @Param("example") UrgentRecallMsgLogExample example);

    int updateByPrimaryKeySelective(UrgentRecallMsgLog record);

    int updateByPrimaryKey(UrgentRecallMsgLog record);


    /**
     * 以下是自定义方法
     */

    /**
     * @description:            批量插入
     * @param urgentRecallMsgLogs
     * @Author shenjiankang
     * @Date 2019/1/30 15:15
     */
    void insertBatch(@Param("list") List<UrgentRecallMsgLog> urgentRecallMsgLogs);

    List<UrgentRecallMsgLog> queryByParam(UrgentRecallMsgLogQO qo);

    int countByParam(UrgentRecallMsgLogQO qo);
}