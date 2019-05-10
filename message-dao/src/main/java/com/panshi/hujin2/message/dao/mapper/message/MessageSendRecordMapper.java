package com.panshi.hujin2.message.dao.mapper.message;

import com.panshi.hujin2.message.dao.model.MessageSendRecord;
import com.panshi.hujin2.message.dao.model.MessageSendRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessageSendRecordMapper {
    long countByExample(MessageSendRecordExample example);

    int deleteByExample(MessageSendRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MessageSendRecord record);

    int insertSelective(MessageSendRecord record);

    List<MessageSendRecord> selectByExample(MessageSendRecordExample example);

    MessageSendRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MessageSendRecord record, @Param("example") MessageSendRecordExample example);

    int updateByExample(@Param("record") MessageSendRecord record, @Param("example") MessageSendRecordExample example);

    int updateByPrimaryKeySelective(MessageSendRecord record);

    int updateByPrimaryKey(MessageSendRecord record);
}