package com.panshi.hujin2.message.facade;

import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.domain.qo.CountryConfigQO;
import com.panshi.hujin2.message.domain.qo.MsgTemplateCountryQO;
import com.panshi.hujin2.message.facade.bo.*;
import com.panshi.hujin2.message.domain.qo.MsgBusinessTypeQO;
import com.panshi.hujin2.message.domain.qo.MsgTemplateQO;

import java.util.List;

/**
 * create by shenjiankang on 2018/6/29 11:03
 *
 * 短信模板facade
 */
public interface IMsgTemplateFacade {

    /**
     * @description:    添加短信业务类型
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/29 9:49
     */
    BasicResult<Void> addMsgBusinessType(MsgBusinessTypeInputBO inputBO, Context context);

    /**
     * @description:    修改短信业务类型
     * @param updateBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/29 9:50
     */
    BasicResult<Void> updateMsgBusinessType(MsgBusinessTypeUpdateBO updateBO, Context context);

    /**
     * @description:    根据条件查询短信业务类型
     * @param qo
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/29 9:50
     */
    BasicResult<List<MsgBusinessTypeOutputBO>> queryMsgBusinessType(MsgBusinessTypeQO qo, Context context);

    /**
     * @description:    添加短信模板
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/29 11:00
     */
    BasicResult<Void> addMsgTemplate(MsgTemplateInputBO inputBO, Context context);

    /**
     * @description:    修改短信模板
     * @param updateBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/29 11:01
     */
    BasicResult<Void> updateMsgTemplate(MsgTemplateUpdateBO updateBO, Context context);

    /**
     * @description:    根据条件查询短信模板
     * @param qo
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/29 11:01
     */
    BasicResult<List<MsgTemplateOutputBO>>  queryMsgTemplate(MsgTemplateQO qo, Context context);


    /**
     * @description:    添加国际化短信模板
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/30 16:11
     */
    BasicResult<Void> addMsgTemplateCountry(MsgTemplateCountryInputBO inputBO,Context context);

    /**
     * @description:    修改国际化短信模板
     * @param updateBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/30 16:12
     */
    BasicResult<Void> updateMsgTemplateCountry(MsgTemplateCountryUpdateBO updateBO,Context context);

    /**
     * @description:    查询国际化短信模板
     * @param qo
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/30 16:12
     */
    BasicResult<List<MsgTemplateCountryOutputBO>> queryMsgTemplateCountry(MsgTemplateCountryQO qo, Context context);

    /**
     * @description:    新增国际化配置
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/30 17:39
     */
    BasicResult<Void> addCountryConfig(CountryConfigInputBO inputBO,Context context);

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

    /**
     * @description:        獲取app應用程序信息
     * @param
     * @Author shenjiankang
     * @Date 24/07/2018 11:51
     */
    BasicResult<List<ApplicationsOutputBO>> queryApplications(Context context);

    /**
     * @description:        添加短信发送报告
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/8/4 17:20
     */
    BasicResult<Integer> addInfobipMsgReports(InfobipMsgReportsInputBO inputBO,Context context);

    /**
     * @description:    添加短信发送结果（创蓝回调方法）
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/8/4 17:24
     */
    BasicResult<Integer> addMsgResponseResult(MsgResponseResultInputBO inputBO, Context context);

    /**
     * @description:    添加Subhook事件推送记录
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/8/4 17:30
     */
    BasicResult<Integer> addSubmailMsgSubhook(SubmailMsgSubhookInputBO inputBO, Context context);
}
