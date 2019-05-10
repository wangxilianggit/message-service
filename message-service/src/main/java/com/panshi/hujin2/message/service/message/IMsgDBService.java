package com.panshi.hujin2.message.service.message;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.dao.model.MsgTemplateCountry;
import com.panshi.hujin2.message.domain.qo.CountryConfigQO;
import com.panshi.hujin2.message.domain.qo.MsgTemplateCountryQO;
import com.panshi.hujin2.message.facade.bo.*;
import com.panshi.hujin2.message.domain.qo.MsgBusinessTypeQO;
import com.panshi.hujin2.message.domain.qo.MsgTemplateQO;

import java.util.List;

/**
 * create by shenjiankang on 2018/6/22 16:33
 *
 * 消息类数据库操作服务
 */
public interface IMsgDBService {

    /**
     * @description:    添加短信发送记录（发送日志）
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/23 10:02
     */
    int addMsgSendRecord(MessageSendRecordInputBO inputBO, Context context);

    /**
     * @description:    添加短信发送结果（回调方法）
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/23 10:01
     */
    int addMsgResponseResult(MsgResponseResultInputBO inputBO, Context context);

    /**
     * @description:    添加Subhook事件推送记录
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/23 16:12
     */
    int addSubmailMsgSubhook(SubmailMsgSubhookInputBO inputBO, Context context);

    /**
     * @description:    添加短信业务类型
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/29 9:49
     */
    int addMsgBusinessType(MsgBusinessTypeInputBO inputBO, Context context);

    /**
     * @description:    修改短信业务类型
     * @param updateBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/29 9:50
     */
    int updateMsgBusinessType(MsgBusinessTypeUpdateBO updateBO, Context context);

    /**
     * @description:    根据条件查询短信业务类型
     * @param qo
     * @Author shenjiankang
     * @Date 2018/6/29 9:50
     */
    List<MsgBusinessTypeOutputBO> queryMsgBusinessType(MsgBusinessTypeQO qo);

    /**
     * @description:    添加短信模板
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/29 11:00
     */
    int addMsgTemplate(MsgTemplateInputBO inputBO,Context context);

    /**
     * @description:    修改短信模板
     * @param updateBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/29 11:01
     */
    void updateMsgTemplate(MsgTemplateUpdateBO updateBO, Context context);

//    /**
//     * @description:    根据条件查询短信模板 （多表查询，支持分页，弃用，不推荐，因为子表是循环去查询，或过多创建与数据库的链接）
//     * @param qo
//     * @Author shenjiankang
//     * @Date 2018/6/29 11:01
//     */
//    @Deprecated
//    List<MsgTemplateOutputBO>  queryMsgTemplate(MsgTemplateQO qo);

    /**
     * @description:    模板和国际化短信关联查询（多表关联查询，支持分页）
     * @param qo
     * @Author shenjiankang
     * @Date 2018/7/5 16:56
     */
    List<MsgTemplateOutputBO>  queryTemplate(MsgTemplateQO qo);

    //短信模板国际化
    /**
     * @description:    添加国际化短信模板
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/30 16:11
     */
    int addMsgTemplateCountry(MsgTemplateCountryInputBO inputBO,Context context);

    /**
     * @description:    修改国际化短信模板
     * @param updateBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/30 16:12
     */
    int updateMsgTemplateCountry(MsgTemplateCountryUpdateBO updateBO,Context context);

    /**
     * @description:    查询国际化短信模板
     * @param qo
     * @Author shenjiankang
     * @Date 2018/6/30 16:12
     */
    List<MsgTemplateCountryOutputBO> queryMsgTemplateCountry(MsgTemplateCountryQO qo);

    //国家名称配置
    /**
     * @description:    新增国际化配置
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/30 17:39
     */
    int addCountryConfig(CountryConfigInputBO inputBO,Context context);

    /**
     * @description:    修改国际化配置
     * @param updateBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/30 17:39
     */
    int updateCountryConfig(CountryConfigUpdateBO updateBO, Context context);

    /**
     * @description:    查询国际化配置
     * @param qo
     * @Author shenjiankang
     * @Date 2018/6/30 17:39
     */
    List<CountryConfigOutputBO> queryCountryConfig(CountryConfigQO qo);

    /**
     * @description:        获取模板
     * @param appEnum       app应用程序枚举
     * @param templateCode
     * @param context
     * @Author shenjiankang
     * @Date 2018/7/3 17:47
     */
    String getMsgTemplate(ApplicationEnmu appEnum, String templateCode, Context context);

    /**
     * @description:        添加短信发送报告
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2018/7/19 20:45
     */
    int addInfobipMsgReports(InfobipMsgReportsInputBO inputBO,Context context);

    /**
     * @description:        獲取app應用程序信息
     * @param
     * @Author shenjiankang
     * @Date 24/07/2018 11:51
     */
    List<ApplicationsOutputBO> queryApplications();


}
