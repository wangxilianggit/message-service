package com.panshi.hujin2.message.dao.mapper.email;

import com.panshi.hujin2.message.dao.model.EmailTemplateCountry;
import com.panshi.hujin2.message.dao.model.EmailTemplateCountryExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmailTemplateCountryMapper {
    long countByExample(EmailTemplateCountryExample example);

    int deleteByExample(EmailTemplateCountryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EmailTemplateCountry record);

    int insertSelective(EmailTemplateCountry record);

    List<EmailTemplateCountry> selectByExample(EmailTemplateCountryExample example);

    EmailTemplateCountry selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EmailTemplateCountry record, @Param("example") EmailTemplateCountryExample example);

    int updateByExample(@Param("record") EmailTemplateCountry record, @Param("example") EmailTemplateCountryExample example);

    int updateByPrimaryKeySelective(EmailTemplateCountry record);

    int updateByPrimaryKey(EmailTemplateCountry record);

    /**
     * 以下是自定义方法
     */
    //批量插入
    void insertBatch(@Param("list") List<EmailTemplateCountry> list);
    //根据templateCode删除
    int deleteByTemplateCode(@Param("templateCode") String templateCode);

    /**
     * 绕过多语言
     * @param templateCode
     * @return
     */
    EmailTemplateCountry selectTemplateCode(@Param("templateCode")String templateCode);
}