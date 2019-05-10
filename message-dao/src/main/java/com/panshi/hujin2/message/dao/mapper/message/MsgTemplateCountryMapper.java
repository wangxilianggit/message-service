package com.panshi.hujin2.message.dao.mapper.message;

import com.panshi.hujin2.message.dao.model.MsgTemplateCountry;
import com.panshi.hujin2.message.dao.model.MsgTemplateCountryExample;
import java.util.List;

import com.panshi.hujin2.message.domain.qo.MsgTemplateCountryQO;
import org.apache.ibatis.annotations.Param;

public interface MsgTemplateCountryMapper {
    long countByExample(MsgTemplateCountryExample example);

    int deleteByExample(MsgTemplateCountryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MsgTemplateCountry record);

    int insertSelective(MsgTemplateCountry record);

    List<MsgTemplateCountry> selectByExample(MsgTemplateCountryExample example);

    MsgTemplateCountry selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MsgTemplateCountry record, @Param("example") MsgTemplateCountryExample example);

    int updateByExample(@Param("record") MsgTemplateCountry record, @Param("example") MsgTemplateCountryExample example);

    int updateByPrimaryKeySelective(MsgTemplateCountry record);

    int updateByPrimaryKey(MsgTemplateCountry record);


    /**
     * 以下是自定义方法
     */

    Integer countMsgTemplateCountry(MsgTemplateCountryQO qo);

    List<MsgTemplateCountry> queryMsgTemplateCountry(MsgTemplateCountryQO qo);

    //批量插入
    void insertBatch(@Param("list") List<MsgTemplateCountry> list);

    //删除多条
    void deleteByTemplateCodes(@Param("List") List<String> list);

    int deleteByTemplateCode(@Param("templateCode") String templateCode);

}