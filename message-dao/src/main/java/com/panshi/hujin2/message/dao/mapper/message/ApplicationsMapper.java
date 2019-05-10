package com.panshi.hujin2.message.dao.mapper.message;

import com.panshi.hujin2.message.dao.model.Applications;
import com.panshi.hujin2.message.dao.model.ApplicationsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplicationsMapper {
    long countByExample(ApplicationsExample example);

    int deleteByExample(ApplicationsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Applications record);

    int insertSelective(Applications record);

    List<Applications> selectByExample(ApplicationsExample example);

    Applications selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Applications record, @Param("example") ApplicationsExample example);

    int updateByExample(@Param("record") Applications record, @Param("example") ApplicationsExample example);

    int updateByPrimaryKeySelective(Applications record);

    int updateByPrimaryKey(Applications record);
}