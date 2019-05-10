package com.panshi.hujin2.message.dao.mapper.notification;

import com.panshi.hujin2.message.dao.model.AppPushBusinessType;
import com.panshi.hujin2.message.dao.model.AppPushBusinessTypeExample;
import com.panshi.hujin2.message.domain.qo.getui.AppPushBusinessTypeQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppPushBusinessTypeMapper {
    long countByExample(AppPushBusinessTypeExample example);

    int deleteByExample(AppPushBusinessTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppPushBusinessType record);

    int insertSelective(AppPushBusinessType record);

    List<AppPushBusinessType> selectByExample(AppPushBusinessTypeExample example);

    AppPushBusinessType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppPushBusinessType record, @Param("example") AppPushBusinessTypeExample example);

    int updateByExample(@Param("record") AppPushBusinessType record, @Param("example") AppPushBusinessTypeExample example);

    int updateByPrimaryKeySelective(AppPushBusinessType record);

    int updateByPrimaryKey(AppPushBusinessType record);

    /**
     * 以下是自定义方法
     */

    /**
     * @description:    根据条件统计记录总数
     * @param qo
     * @Author shenjiankang
     * @Date 2018/6/27 16:54
     */
    Integer countBusinessTypeByCondition(AppPushBusinessTypeQO qo);

    /**
     * @description:     根据条件查询产品信息
     * @param qo
     * @Author shenjiankang
     * @Date 2018/6/27 16:56
     */
    List<AppPushBusinessType> queryBusinessTypeByCondition(AppPushBusinessTypeQO qo);
}