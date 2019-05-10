package com.panshi.hujin2.message.dao.mapper.email;

import com.panshi.hujin2.message.dao.model.EmailPushRecord;
import com.panshi.hujin2.message.dao.model.EmailPushRecordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmailPushRecordMapper {
    long countByExample(EmailPushRecordExample example);

    int deleteByExample(EmailPushRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EmailPushRecord record);

    int insertSelective(EmailPushRecord record);

    List<EmailPushRecord> selectByExample(EmailPushRecordExample example);

    EmailPushRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EmailPushRecord record, @Param("example") EmailPushRecordExample example);

    int updateByExample(@Param("record") EmailPushRecord record, @Param("example") EmailPushRecordExample example);

    int updateByPrimaryKeySelective(EmailPushRecord record);

    int updateByPrimaryKey(EmailPushRecord record);
}