package com.panshi.hujin2.message.facade;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.domain.qo.MessageSendRecordQO;
import com.panshi.hujin2.message.domain.qo.MsgSendStatisticsQO;
import com.panshi.hujin2.message.domain.qo.UrgentRecallCallLogQO;
import com.panshi.hujin2.message.domain.qo.UrgentRecallMsgLogQO;
import com.panshi.hujin2.message.facade.bo.*;

import java.util.List;
import java.util.Map;

/**
 * create by shenjiankang on 2018/6/20 11:38
 *
 */
public interface IMessageFacade {


    /**
     * @description:            发送国际短信 (单个发送)
     * @param applicationEnmu   调用服务的应用，见com.panshi.hujin2.message.enums.ChannelEnum
     * @param phoneNumber       手机号
     * @param templateCode      模板编号
     * @param paramList         要替换的参数列表
     * @param context           国际化上下文
     * @Author shenjiankang
     * @Date 2018/7/3 17:54
     */
    BasicResult<Void> sendInternationalMsg(ApplicationEnmu applicationEnmu,
                                           String phoneNumber,
                                           String templateCode,
                                           List<String> paramList,
                                           Context context,Integer msgType);

    BasicResult<Void> sendInternationalMsg(SendMsgBO sendMsgBO);

    /**
     *@Description:             发送国际 语音 短信 (单个发送)
     *@param sendMsgBO
     *@Author: shenJianKang
     *@date: 2020/5/21 17:58
     */
    BasicResult<Void> sendInternationalVoiceMsg(SendMsgBO sendMsgBO);

    /**
     * @description:        批量推送，同一个模板，不同参数
     * @param applicationEnmu
     * @param paramMap
     * @param templateCode
     * @param context
     * @Author shenjiankang
     * @Date 2018/8/2 13:54
     */
    BasicResult<List<String>> batchSendSameTemplateDiffParam(ApplicationEnmu applicationEnmu,
                                           Map<String,List> paramMap,
                                           String templateCode,
                                           Context context);


    //同一个模板，相同参数 的群发
    /**
     * @description:        批量推送，同一个模板，相同参数
     * @param applicationEnmu
     * @param phoneNumberList
     * @param templateCode
     * @param paramList
     * @param context
     * @Author shenjiankang
     * @Date 2018/8/3 14:11
     */
    BasicResult<Void> batchSendSameTemplateSameParam(ApplicationEnmu applicationEnmu,
                                                List phoneNumberList,
                                                String templateCode,
                                                List paramList,
                                                Context context);



    /**
     *@Description:             根据手机号列表 ，和发送内容 批量发送
     *@param applicationEnmu	appid
     * @param queueId           发送队列id
     * @param consumerId        商户id
     * @param fee               单条发送费用
     * @param phoneNumberList   手机号list
     * @param sendText          发送内容
     * @param context           国际化对象
     *@Author: shenJianKang
     *@date: 2019/8/13 18:17
     */
    //// TODO: 2019/8/13 暂时只支持印尼运营商短信 群发
    BasicResult<Void> batchSendSameTemplateSameParam(ApplicationEnmu applicationEnmu,
                                                     Integer queueId,
                                                     Integer consumerId,
                                                     Double fee,
                                                     List phoneNumberList,
                                                     String sendText,
                                                     Context context );

    /**
     * @description:      批量推送， 不同模板
     * @param applicationEnmu
     * @param paramList
     * @param context
     * @Author shenjiankang
     * @Date 2018/8/6 15:09
     */
    BasicResult<Void> batchSendDiffTemplate(ApplicationEnmu applicationEnmu,
                               List<BatchSendDiffTemplateParamBO> paramList,
                               Context context);


    /**
     * @description:        自定义短信内容,批量发送  (天一泓)
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2019/1/30 10:39
     */
    BasicResult<Void> batchMsg(UrgentRecallMsgLogInputBO inputBO,
                               Context context);


    /**
     * @description:        根据订单id获取对应的短信催收记录
     * @param orderId
     * @Author shenjiankang
     * @Date 2019/1/30 15:49
     */
    BasicResult<List<UrgentRecallMsgLogOutputBO>> queryUrgentRecallMsgLogByOrderId(Integer orderId, Context context);


    /**
     * @description:        网页拨打电话记录 入库
     * @param inputBO
     * @param context
     * @Author shenjiankang
     * @Date 2019/1/31 9:36
     */
    BasicResult<Void> insertCallRecord(UrgentRecallCallLogInputBO inputBO, Context context);

    /**
     * @description:         根据订单id获取网页通话记录
     * @param orderId
     * @param context
     * @Author shenjiankang
     * @Date 2019/1/31 9:50
     */
    BasicResult<List<UrgentRecallCallLogOutputBO>> getCallRecordByOrderId(Integer orderId, Context context);

    /**
     * @description:        根据查询参数获取对应的短信催收记录
     * @param qo
     * @Author shenjiankang
     * @Date 2019/1/30 15:49
     */
    BasicResult<List<UrgentRecallMsgLogOutputBO>> queryUrgentRecallMsgLogByParam(UrgentRecallMsgLogQO qo, Context context);

    /**
     * @description:         根据查询参数获取网页通话记录
     * @param qo
     * @param context
     * @Author shenjiankang
     * @Date 2019/1/31 9:50
     */
    BasicResult<List<UrgentRecallCallLogOutputBO>> getCallRecordByParam(UrgentRecallCallLogQO qo, Context context);




    //按全部手机号码统计发送结果
    BasicResult<List<MsgSendStatisticsBO>> querySendStatistics(MsgSendStatisticsQO qo, Context context);

    //按队列id查询发送结果
    BasicResult<MsgSendResultBO> querySendResult(MsgSendStatisticsQO qo, Context context);

    /**
     *@Description:     查詢短信發送記錄
     *@Param:  * @param qo
     *@Author: shenJianKang
     *@date: 2019/8/21 16:28
     */
    BasicResult<List<MessageSendRecordBO>> querySendRecordByParam(MessageSendRecordQO qo);


    /**
     *@Description:     查詢短信發送記錄数量
     *@Param:  * @param qo
     *@Author: shenJianKang
     *@date: 2019/8/21 16:28
     */
    BasicResult<Integer> countSendRecordByParam(MessageSendRecordQO qo);


    /**
     *@Description:     查詢發送短信用户手机号数量
     *@Param:  * @param qo
     *@Author: shenJianKang
     *@date: 2019/8/21 16:28
     */
    BasicResult<Integer> countPhoneNumberByParam(MessageSendRecordQO qo);


    /**
     * @description:            发送固定模板短信
     * @param applicationEnmu   调用服务的应用，见com.panshi.hujin2.message.enums.ChannelEnum
     * @param phoneNumber       手机号
     * @param templateCode      模板编号
     * @param paramList         发送的键值信息列表
     * @param context           国际化上下文
     * @Author shenjiankang
     * @Date 2018/7/3 17:54
     */
    BasicResult<Void> sendInternationalMsg(ApplicationEnmu applicationEnmu,
                                           String phoneNumber,
                                           String templateCode,
                                           Map<String,String> paramList,
                                           Context context,
                                           Integer msgType);




}
