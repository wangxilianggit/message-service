package com.panshi.hujin2.message.service.notification.getui;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.domain.qo.CountryConfigQO;
import com.panshi.hujin2.message.domain.qo.getui.AppPushBusinessTypeQO;
import com.panshi.hujin2.message.domain.qo.getui.AppPushTemplateQO;
import com.panshi.hujin2.message.domain.qo.getui.TemplateQO;
import com.panshi.hujin2.message.facade.bo.*;

import java.util.List;

/**
 * create by shenjiankang on 2018/7/7 11:29
 */
public interface INotificationTemplateService {


    /**
     * @description:        获取所有app应用程序的名称
     * @param
     * @Author shenjiankang
     * @Date 2018/8/13 14:33
     */
    List<ApplicationsOutputBO> queryApplications();

    /* app_push_business_type */

    /**
     * @description:        新增推送消息业务类型
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/27 16:34
     */
    int addBusinessType(AppPushBusinessTypeInputBO inputBO,
                        Context context);

    /**
     * @description:        修改推送消息业务类型
     * @param updateBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/27 16:34
     */
    int updateBusinessType(AppPushBusinessTypeUpdateBO updateBO,
                           Context context);

    /**
     * @description:        根据条件查询推送消息业务类型信息（支持分页）
     * @param qo
     * @Author shenjiankang
     * @Date 2018/6/27 16:35
     */
    List<AppPushBusinessTypeOutputBO> queryBusinessType(AppPushBusinessTypeQO qo);

    /* country_config */
    /**
     * @description:    新增国际化配置
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/7/3 17:39
     */
    int addCountryConfig(CountryConfigInputBO inputBO, Context context);

    /**
     * @description:    修改国际化配置
     * @param updateBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/7/3 17:39
     */
    int updateCountryConfig(CountryConfigUpdateBO updateBO, Context context);

    /**
     * @description:    查询国际化配置
     * @param qo
     * @Author shenjiankang
     * @Date 2018/7/3 17:39
     */
    List<CountryConfigOutputBO> queryCountryConfig(CountryConfigQO qo);

    /* app_push_template_country */

    /**
     * @description:    修改国际化消息模板
     * @param updateBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/7/4 17:27
     */
    int updateTemplateCountry(AppPushTemplateCountryUpdateBO updateBO, Context context);

    /**
     * @description:        新增模板
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/27 18:01
     */
    int addPushTemplate(AppPushTemplateInputBO inputBO,
                        Context context);

    /**
     * @description:        修改模板
     * @param updateBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/27 18:03
     */
    void updatePushTemplate(AppPushTemplateUpdateBO updateBO,
                            Context context);

    /**
     * @description:        根据条件查询模板（单表查询，支持分页）
     * @param qo
     * @Author shenjiankang
     * @Date 2018/6/27 18:03
     */
    List<AppPushTemplateOutputBO> queryPushTemplate(AppPushTemplateQO qo);

    /**
     * @description:        模板和国际化推送消息内容关联查询(关联查询，支持分页)
     * @param qo
     * @Author shenjiankang
     * @Date 2018/7/5 15:32
     */
    List<AppPushTemplateOutputBO> queryTemplate(TemplateQO qo);

    /**
     * @description:        根据模板code获取发送的模板
     * @param appEnmu
     * @param templateCode
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/27 9:44
     */
    AppPushTemplateOutputBO getPushTemplate(ApplicationEnmu appEnmu,
                                            String templateCode,
                                            Context context);



}
