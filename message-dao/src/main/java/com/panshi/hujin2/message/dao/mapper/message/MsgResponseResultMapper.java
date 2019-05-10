package com.panshi.hujin2.message.dao.mapper.message;

import com.panshi.hujin2.message.dao.model.MsgResponseResult;
import com.panshi.hujin2.message.dao.model.MsgResponseResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MsgResponseResultMapper {
    long countByExample(MsgResponseResultExample example);

    int deleteByExample(MsgResponseResultExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MsgResponseResult record);

    int insertSelective(MsgResponseResult record);

    List<MsgResponseResult> selectByExample(MsgResponseResultExample example);

    MsgResponseResult selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MsgResponseResult record, @Param("example") MsgResponseResultExample example);

    int updateByExample(@Param("record") MsgResponseResult record, @Param("example") MsgResponseResultExample example);

    int updateByPrimaryKeySelective(MsgResponseResult record);

    int updateByPrimaryKey(MsgResponseResult record);
}