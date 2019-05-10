package com.panshi.hujin2.message.facade;

import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.domain.qo.CountryConfigQO;
import com.panshi.hujin2.message.domain.qo.getui.AppPushBusinessTypeQO;
import com.panshi.hujin2.message.domain.qo.getui.AppPushTemplateQO;
import com.panshi.hujin2.message.domain.qo.getui.TemplateQO;
import com.panshi.hujin2.message.facade.bo.*;

import java.util.List;

/**
 * create by shenjiankang on 2018/6/29 15:16
 *
 * 消息模板配置
 */
public interface INotificationTemplateFacade {

    /**
     * @description:        获取所有app应用程序的名称
     * @param context
     * @Author shenjiankang
     * @Date 2018/8/13 14:36
     */
    BasicResult<List<ApplicationsOutputBO>> queryApplications(Context context);

    /**
     * @description:        新增模板
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/27 18:01
     */
    BasicResult<Void> addPushTemplate(AppPushTemplateInputBO inputBO,
                                      Context context);

    /**
     * @description:        修改模板
     * @param updateBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/27 18:03
     */
    BasicResult<Void> updatePushTemplate(AppPushTemplateUpdateBO updateBO,
                                         Context context);

    /**
     * @description:        根据条件查询模板（单表查询，支持分页）
     * @param qo
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/27 18:03
     */
    BasicResult<List<AppPushTemplateOutputBO>> queryPushTemplate(AppPushTemplateQO qo, Context context);

    /**
     * @description:        模板和国际化推送消息内容关联查询（关联查询，支持分页）
     * @param qo
     * @param context
     * @Author shenjiankang
     * @Date 2018/7/5 15:32
     */
    BasicResult<List<AppPushTemplateOutputBO>> queryTemplate(TemplateQO qo, Context context);

    /**
     * @description:        新增推送消息业务类型
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/27 16:34
     */
    BasicResult<Void> addBusinessType(AppPushBusinessTypeInputBO inputBO,
                                      Context context);

    /**
     * @description:        修改推送消息业务类型
     * @param updateBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/27 16:34
     */
    BasicResult<Void> updateBusinessType(AppPushBusinessTypeUpdateBO updateBO,
                                         Context context);

    /**
     * @description:        根据条件查询推送消息业务类型信息（支持分页）
     * @param qo
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/27 16:35
     */
    BasicResult<List<AppPushBusinessTypeOutputBO>> queryBusinessType(AppPushBusinessTypeQO qo, Context context);


    /**
     * @description:    新增国际化配置
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/30 17:39
     */
    BasicResult<Void> addCountryConfig(CountryConfigInputBO inputBO, Context context);

    /**
     * @description:    修改国际化配置
     * @param updateBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/30 17:39
     */
    BasicResult<Void> updateCountryConfig(CountryConfigUpdateBO updateBO, Context context);

    /**
     * @description:    查询国际化配置
     * @param qo
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/30 17:39
     */
    BasicResult<List<CountryConfigOutputBO>> queryCountryConfig(CountryConfigQO qo, Context context);
}
