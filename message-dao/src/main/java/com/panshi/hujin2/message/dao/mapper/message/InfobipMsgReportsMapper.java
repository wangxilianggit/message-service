package com.panshi.hujin2.message.dao.mapper.message;

import com.panshi.hujin2.message.dao.model.InfobipMsgReports;
import com.panshi.hujin2.message.dao.model.InfobipMsgReportsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InfobipMsgReportsMapper {
    long countByExample(InfobipMsgReportsExample example);

    int deleteByExample(InfobipMsgReportsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(InfobipMsgReports record);

    int insertSelective(InfobipMsgReports record);

    List<InfobipMsgReports> selectByExample(InfobipMsgReportsExample example);

    InfobipMsgReports selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") InfobipMsgReports record, @Param("example") InfobipMsgReportsExample example);

    int updateByExample(@Param("record") InfobipMsgReports record, @Param("example") InfobipMsgReportsExample example);

    int updateByPrimaryKeySelective(InfobipMsgReports record);

    int updateByPrimaryKey(InfobipMsgReports record);
}