package com.panshi.hujin2.message.dao.mapper.message;

import com.panshi.hujin2.message.dao.model.UrgentRecallCallLog;
import com.panshi.hujin2.message.dao.model.UrgentRecallCallLogExample;
import java.util.List;

import com.panshi.hujin2.message.domain.qo.UrgentRecallCallLogQO;
import org.apache.ibatis.annotations.Param;

public interface UrgentRecallCallLogMapper {
    long countByExample(UrgentRecallCallLogExample example);

    int deleteByExample(UrgentRecallCallLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UrgentRecallCallLog record);

    int insertSelective(UrgentRecallCallLog record);

    List<UrgentRecallCallLog> selectByExample(UrgentRecallCallLogExample example);

    UrgentRecallCallLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UrgentRecallCallLog record, @Param("example") UrgentRecallCallLogExample example);

    int updateByExample(@Param("record") UrgentRecallCallLog record, @Param("example") UrgentRecallCallLogExample example);

    int updateByPrimaryKeySelective(UrgentRecallCallLog record);

    int updateByPrimaryKey(UrgentRecallCallLog record);

    /**
     * 自定义方法
     */
    List<UrgentRecallCallLog> queryByParam(UrgentRecallCallLogQO qo);

    int countByParam(UrgentRecallCallLogQO qo);

}