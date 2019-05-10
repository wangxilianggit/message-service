package com.panshi.hujin2.message.dao.mapper.notification;

import com.panshi.hujin2.message.dao.model.AppPushTemplateCountry;
import com.panshi.hujin2.message.dao.model.AppPushTemplateCountryExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppPushTemplateCountryMapper {
    long countByExample(AppPushTemplateCountryExample example);

    int deleteByExample(AppPushTemplateCountryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppPushTemplateCountry record);

    int insertSelective(AppPushTemplateCountry record);

    List<AppPushTemplateCountry> selectByExample(AppPushTemplateCountryExample example);

    AppPushTemplateCountry selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppPushTemplateCountry record, @Param("example") AppPushTemplateCountryExample example);

    int updateByExample(@Param("record") AppPushTemplateCountry record, @Param("example") AppPushTemplateCountryExample example);

    int updateByPrimaryKeySelective(AppPushTemplateCountry record);

    int updateByPrimaryKey(AppPushTemplateCountry record);



    /**
     *  自定义方法
     */
    //批量插入
    void insertBatch(@Param("list") List<AppPushTemplateCountry> list);
    //根据templateCode删除
    int deleteByTemplateCode(@Param("templateCode") String templateCode);
}