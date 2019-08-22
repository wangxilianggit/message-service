package com.panshi.hujin2.message.dao.mapper.message;

import com.panshi.hujin2.message.dao.model.MessageSendRecordDO;
import com.panshi.hujin2.message.dao.model.MessageSendRecordExample;
import java.util.List;

import com.panshi.hujin2.message.domain.qo.MessageSendRecordQO;
import com.panshi.hujin2.message.domain.qo.MsgSendStatisticsQO;
import org.apache.ibatis.annotations.Param;

public interface MessageSendRecordMapper {
    long countByExample(MessageSendRecordExample example);

    int deleteByExample(MessageSendRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MessageSendRecordDO record);

    int insertSelective(MessageSendRecordDO record);

    List<MessageSendRecordDO> selectByExample(MessageSendRecordExample example);

    MessageSendRecordDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MessageSendRecordDO record, @Param("example") MessageSendRecordExample example);

    int updateByExample(@Param("record") MessageSendRecordDO record, @Param("example") MessageSendRecordExample example);

    int updateByPrimaryKeySelective(MessageSendRecordDO record);

    int updateByPrimaryKey(MessageSendRecordDO record);


    /**
     *  自定义方法
     */
    //todo 优化 分页
    List<MessageSendRecordDO> querySendStatistics(MsgSendStatisticsQO qo);


    Integer countByParam(MessageSendRecordQO qo);
    List<MessageSendRecordDO> queryByParam(MessageSendRecordQO qo);
}