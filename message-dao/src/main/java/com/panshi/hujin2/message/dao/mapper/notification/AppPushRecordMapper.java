package com.panshi.hujin2.message.dao.mapper.notification;

import com.panshi.hujin2.message.dao.model.AppPushRecord;
import com.panshi.hujin2.message.dao.model.AppPushRecordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppPushRecordMapper {
    long countByExample(AppPushRecordExample example);

    int deleteByExample(AppPushRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppPushRecord record);

    int insertSelective(AppPushRecord record);

    List<AppPushRecord> selectByExample(AppPushRecordExample example);

    AppPushRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppPushRecord record, @Param("example") AppPushRecordExample example);

    int updateByExample(@Param("record") AppPushRecord record, @Param("example") AppPushRecordExample example);

    int updateByPrimaryKeySelective(AppPushRecord record);

    int updateByPrimaryKey(AppPushRecord record);
}