package com.panshi.hujin2.message.dao.mapper.message;

import com.panshi.hujin2.message.dao.model.MsgTemplate;
import com.panshi.hujin2.message.dao.model.MsgTemplateExample;
import java.util.List;

import com.panshi.hujin2.message.domain.dto.MsgTemplateDTO;
import com.panshi.hujin2.message.domain.qo.MsgTemplateQO;
import org.apache.ibatis.annotations.Param;

public interface MsgTemplateMapper {
    long countByExample(MsgTemplateExample example);

    int deleteByExample(MsgTemplateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MsgTemplate record);

    int insertSelective(MsgTemplate record);

    List<MsgTemplate> selectByExample(MsgTemplateExample example);

    MsgTemplate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MsgTemplate record, @Param("example") MsgTemplateExample example);

    int updateByExample(@Param("record") MsgTemplate record, @Param("example") MsgTemplateExample example);

    int updateByPrimaryKeySelective(MsgTemplate record);

    int updateByPrimaryKey(MsgTemplate record);



    /**
     * 以下是自定义方法
     */
    Integer countMsgTemplate(MsgTemplateQO qo);

    List<MsgTemplate> queryMsgTemplate(MsgTemplateQO qo);

    /**
     * @description:        模板表和国际化字表关联统计
     * @param qo
     * @Author shenjiankang
     * @Date 2018/7/5 16:52
     */
    Integer countTemplate(MsgTemplateQO qo);
    /**
     * @description:        模板表和国际化字表关联查询
     * @param qo
     * @Author shenjiankang
     * @Date 2018/7/5 16:52
     */
    List<MsgTemplateDTO> queryTemplate(MsgTemplateQO qo);
}