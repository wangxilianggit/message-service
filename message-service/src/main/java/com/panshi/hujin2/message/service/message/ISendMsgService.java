package com.panshi.hujin2.message.service.message;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.facade.bo.BatchSendDiffTemplateParamBO;

import java.util.List;
import java.util.Map;

/**
 * create by shenjiankang on 2018/6/19 17:41
 *
 * 发送短信接口
 */
public interface ISendMsgService {

    /**
     * 通道KEY
     *
     * @return
     */
    String key();

    /**
     * 是否默认通道
     * 如果没有制定通道，那么启动默认通道
     *
     * @return
     */
    boolean isDefault();


    boolean sendInternationalMsg(ApplicationEnmu applicationEnmu,
                              String phoneNumber,
                              String templateCode,
                              List<String> paramList,
                              Context context);

    /**
     * @description:        发送国际短信（单个发送）
     * @param applicationEnmu         调用短信服务的应用程序(枚举在basic_project中)
     * @param phoneNumber   手机号码
     * @param msgText       短信内容
     * @param context       国际化上下文
     * @Author shenjiankang
     * @Date 2018/6/28 19:38
     */
    boolean sendInternationalMsg(ApplicationEnmu applicationEnmu,String phoneNumber, String msgText,Context context );






    /**
     * @description:     批量推送，同一个模板，相同参数
     * @param applicationEnmu
     * @param phoneNumberList
     * @param templateCode
     * @param paramList
     * @param context
     * @Author shenjiankang
     * @Date 2018/8/3 14:16
     */
    void batchSendSameTemplateSameParam(ApplicationEnmu applicationEnmu,
                                                List phoneNumberList,
                                                String templateCode,
                                                List paramList,
                                                Context context);

    /**
     *@Description:     根据手机号列表 ，和发送内容 批量发送
     *@Param:  * @param applicationEnmu
     * @param phoneNumberList
     * @param sendText
     * @param context
     *@Author: shenJianKang
     *@date: 2019/8/13 10:35
     */
    void batchSendSameTemplateSameParam(ApplicationEnmu applicationEnmu,
                                        Integer queueId,
                                        Integer consumerId,
                                        Double fee,
                                        List phoneNumberList,
                                        String sendText,
                                        Context context );

    /**
     * @description:        批量推送，同一个模板，不同参数;
     * @param applicationEnmu
     * @param paramMap
     * @param templateCode
     * @param context
     * @Author shenjiankang
     * @Date 2018/8/2 13:54
     */
    void batchSendSameTemplateDiffParam(ApplicationEnmu applicationEnmu,
                                                Map<String,List> paramMap,
                                                String templateCode,
                                                Context context);


    //批量推送， 不同模板
    void batchSendDiffTemplate(ApplicationEnmu applicationEnmu,
                               List<BatchSendDiffTemplateParamBO> paramList,
                               Context context);











}
