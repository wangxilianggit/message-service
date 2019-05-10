package com.panshi.hujin2.message.dao.mapper.notification;

import com.panshi.hujin2.message.dao.model.AppPushTemplate;
import com.panshi.hujin2.message.dao.model.AppPushTemplateExample;
import com.panshi.hujin2.message.domain.dto.AppPushTemplateDTO;
import com.panshi.hujin2.message.domain.qo.getui.AppPushTemplateQO;
import com.panshi.hujin2.message.domain.qo.getui.TemplateQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppPushTemplateMapper {
    long countByExample(AppPushTemplateExample example);

    int deleteByExample(AppPushTemplateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppPushTemplate record);

    int insertSelective(AppPushTemplate record);

    List<AppPushTemplate> selectByExample(AppPushTemplateExample example);

    AppPushTemplate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppPushTemplate record, @Param("example") AppPushTemplateExample example);

    int updateByExample(@Param("record") AppPushTemplate record, @Param("example") AppPushTemplateExample example);

    int updateByPrimaryKeySelective(AppPushTemplate record);

    int updateByPrimaryKey(AppPushTemplate record);

    /**
     * 以下为自定义方法
     **/

    /**
     * @description:        统计模板总数
     * @param qo
     * @Author shenjiankang
     * @Date 2018/6/27 17:58
     */
    Integer countTemplateByCondition(AppPushTemplateQO qo);

    /**
     * @description:        根据条件查询模板（支持分页）
     * @param qo
     * @Author shenjiankang
     * @Date 2018/6/27 17:59
     */
    List<AppPushTemplate> queryTemplateByCondition(AppPushTemplateQO qo);

    /**
     * @description:        模板表和模板国际化关联查询统计
     * @param qo
     * @Author shenjiankang
     * @Date 2018/7/5 15:29
     */
    Integer countTemplate(TemplateQO qo);

    /**
     * @description:     模板表和模板国际化关联查询统计
     * @param qo
     * @Author shenjiankang
     * @Date 2018/7/5 15:29
     */
    List<AppPushTemplateDTO> queryTemplate(TemplateQO qo);
}