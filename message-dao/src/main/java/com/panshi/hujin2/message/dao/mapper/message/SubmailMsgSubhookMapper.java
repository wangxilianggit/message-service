package com.panshi.hujin2.message.dao.mapper.message;

import com.panshi.hujin2.message.dao.model.SubmailMsgSubhook;
import com.panshi.hujin2.message.dao.model.SubmailMsgSubhookExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SubmailMsgSubhookMapper {
    long countByExample(SubmailMsgSubhookExample example);

    int deleteByExample(SubmailMsgSubhookExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SubmailMsgSubhook record);

    int insertSelective(SubmailMsgSubhook record);

    List<SubmailMsgSubhook> selectByExample(SubmailMsgSubhookExample example);

    SubmailMsgSubhook selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SubmailMsgSubhook record, @Param("example") SubmailMsgSubhookExample example);

    int updateByExample(@Param("record") SubmailMsgSubhook record, @Param("example") SubmailMsgSubhookExample example);

    int updateByPrimaryKeySelective(SubmailMsgSubhook record);

    int updateByPrimaryKey(SubmailMsgSubhook record);
}