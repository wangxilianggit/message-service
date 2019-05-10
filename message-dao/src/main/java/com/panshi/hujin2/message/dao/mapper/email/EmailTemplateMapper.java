package com.panshi.hujin2.message.dao.mapper.email;

import com.panshi.hujin2.message.dao.model.EmailTemplate;
import com.panshi.hujin2.message.dao.model.EmailTemplateExample;
import com.panshi.hujin2.message.domain.dto.EmailTemplateDTO;
import com.panshi.hujin2.message.domain.qo.EmailTemplateQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmailTemplateMapper {
    long countByExample(EmailTemplateExample example);

    int deleteByExample(EmailTemplateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EmailTemplate record);

    int insertSelective(EmailTemplate record);

    List<EmailTemplate> selectByExample(EmailTemplateExample example);

    EmailTemplate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EmailTemplate record, @Param("example") EmailTemplateExample example);

    int updateByExample(@Param("record") EmailTemplate record, @Param("example") EmailTemplateExample example);

    int updateByPrimaryKeySelective(EmailTemplate record);

    int updateByPrimaryKey(EmailTemplate record);


    /**
     * 以下是自定义方法
     */
    Integer countEmailTemplate(EmailTemplateQO qo);

    List<EmailTemplateDTO> queryEmailTemplate(EmailTemplateQO qo);
}