package com.panshi.hujin2.message.dao.mapper.email;

import com.panshi.hujin2.message.dao.model.EmailBusinessType;
import com.panshi.hujin2.message.dao.model.EmailBusinessTypeExample;
import com.panshi.hujin2.message.domain.qo.EmailBusinessTypeQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmailBusinessTypeMapper {
    long countByExample(EmailBusinessTypeExample example);

    int deleteByExample(EmailBusinessTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EmailBusinessType record);

    int insertSelective(EmailBusinessType record);

    List<EmailBusinessType> selectByExample(EmailBusinessTypeExample example);

    EmailBusinessType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EmailBusinessType record, @Param("example") EmailBusinessTypeExample example);

    int updateByExample(@Param("record") EmailBusinessType record, @Param("example") EmailBusinessTypeExample example);

    int updateByPrimaryKeySelective(EmailBusinessType record);

    int updateByPrimaryKey(EmailBusinessType record);

    /**
     * 以下是自定义方法
     */

    /**
     * @description:    根据条件统计记录总数
     * @param qo
     * @Author shenjiankang
     * @Date 2018/6/27 16:54
     */
    Integer countBusinessTypeByCondition(EmailBusinessTypeQO qo);

    /**
     * @description:     根据条件查询产品信息
     * @param qo
     * @Author shenjiankang
     * @Date 2018/6/27 16:56
     */
    List<EmailBusinessType> queryBusinessTypeByCondition(EmailBusinessTypeQO qo);
}