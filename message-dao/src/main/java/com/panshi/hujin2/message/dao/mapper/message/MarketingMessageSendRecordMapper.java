package com.panshi.hujin2.message.dao.mapper.message;

import com.panshi.hujin2.message.dao.model.MarketingMessageSendRecordDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketingMessageSendRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MarketingMessageSendRecordDO record);

    int insertSelective(MarketingMessageSendRecordDO record);

    MarketingMessageSendRecordDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MarketingMessageSendRecordDO record);

    int updateByPrimaryKey(MarketingMessageSendRecordDO record);



    /**
     *  以下是自定义方法
     * */

    //批量插入
    void batchInsert(@Param("list") List<MarketingMessageSendRecordDO> list);


}