package com.panshi.hujin2.message.dao.mapper.message;

import com.panshi.hujin2.message.dao.model.MarketingKmiBatchSendResponseDO;

public interface MarketingKmiBatchSendResponseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MarketingKmiBatchSendResponseDO record);

    int insertSelective(MarketingKmiBatchSendResponseDO record);

    MarketingKmiBatchSendResponseDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MarketingKmiBatchSendResponseDO record);

    int updateByPrimaryKeyWithBLOBs(MarketingKmiBatchSendResponseDO record);

    int updateByPrimaryKey(MarketingKmiBatchSendResponseDO record);
}