package com.panshi.hujin2.message.dao.mapper.message;

import com.panshi.hujin2.message.dao.model.SmsChannelConfigDO;

import java.util.List;

public interface SmsChannelConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SmsChannelConfigDO record);

    int insertSelective(SmsChannelConfigDO record);

    SmsChannelConfigDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SmsChannelConfigDO record);

    int updateByPrimaryKey(SmsChannelConfigDO record);


    /**
     *  自定义方法
     */
    List<SmsChannelConfigDO> queryAllValid();

}