package com.panshi.hujin2.message.dao.mapper.message;

import com.panshi.hujin2.message.dao.model.MsgBusinessType;
import com.panshi.hujin2.message.dao.model.MsgBusinessTypeExample;
import java.util.List;

import com.panshi.hujin2.message.domain.qo.MsgBusinessTypeQO;
import org.apache.ibatis.annotations.Param;

public interface MsgBusinessTypeMapper {
    long countByExample(MsgBusinessTypeExample example);

    int deleteByExample(MsgBusinessTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MsgBusinessType record);

    int insertSelective(MsgBusinessType record);

    List<MsgBusinessType> selectByExample(MsgBusinessTypeExample example);

    MsgBusinessType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MsgBusinessType record, @Param("example") MsgBusinessTypeExample example);

    int updateByExample(@Param("record") MsgBusinessType record, @Param("example") MsgBusinessTypeExample example);

    int updateByPrimaryKeySelective(MsgBusinessType record);

    int updateByPrimaryKey(MsgBusinessType record);


    /**
     *  以下是自定义方法
     */

    Integer countMsgBusinessTypeByCondition(MsgBusinessTypeQO qo);

    List<MsgBusinessType> queryMsgBusinessTypeByCondition(MsgBusinessTypeQO qo);
}