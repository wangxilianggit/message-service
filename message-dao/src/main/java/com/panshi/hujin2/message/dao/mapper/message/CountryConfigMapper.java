package com.panshi.hujin2.message.dao.mapper.message;

import com.panshi.hujin2.message.dao.model.CountryConfig;
import com.panshi.hujin2.message.dao.model.CountryConfigExample;
import com.panshi.hujin2.message.domain.qo.CountryConfigQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CountryConfigMapper {
    long countByExample(CountryConfigExample example);

    int deleteByExample(CountryConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CountryConfig record);

    int insertSelective(CountryConfig record);

    List<CountryConfig> selectByExample(CountryConfigExample example);

    CountryConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CountryConfig record, @Param("example") CountryConfigExample example);

    int updateByExample(@Param("record") CountryConfig record, @Param("example") CountryConfigExample example);

    int updateByPrimaryKeySelective(CountryConfig record);

    int updateByPrimaryKey(CountryConfig record);

    /**
     *  以下是自定义方法
     */
    Integer countCountryConfig(CountryConfigQO qo);

    List<CountryConfig> queryCountryConfig(CountryConfigQO qo);
}