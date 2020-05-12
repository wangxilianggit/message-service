package com.panshi.hujin2.message.service.message.chuanglan.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.domain.enums.ChannelEnum;
import com.panshi.hujin2.message.domain.enums.ChuangLanIntResponseEnum;
import com.panshi.hujin2.message.facade.bo.BatchSendDiffTemplateParamBO;
import com.panshi.hujin2.message.facade.bo.MessageSendRecordInputBO;
import com.panshi.hujin2.message.service.message.utils.ExceptionMessageUtils;
import com.panshi.hujin2.message.service.message.chuanglan.entity.RequestParamBean1;
import com.panshi.hujin2.message.service.message.impl.SendMsg;
import com.panshi.hujin2.message.service.message.utils.HttpUtils;
import com.panshi.hujin2.message.service.message.utils.MsgUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * create by shenjiankang on 2018/6/19 18:48
 */
@Service("chuanglanService")
public class ChuanlanServiceImpl extends SendMsg {

    private static Logger LOGGER = LoggerFactory.getLogger(ChuanlanServiceImpl.class);

    private final String CONTENT_TYPE_JSON = "application/json";

    @Value("${chuanglan.international.sendurl}")
    private String intSendUrl;


    @Value("${chuanglan.international.multi.sendurl}")
    private String intMultiSendUrl;

    @Value("${chuanglan.international.account}")
    private String intAccount;

    @Value("${chuanglan.international.password}")
    private String intPassword;


    @Override
    public String key() {
        return "chuanglan";
    }

    @Override
    public boolean isDefault() {
        return true;
    }



    @Override
    public boolean sendInternationalMsg(ApplicationEnmu applicationEnmu, String phoneNumber, String msgText, Context context,Integer msgType) {
        try {
            ExceptionMessageUtils.verifyStringIsBlank(context,phoneNumber,msgText);

            JSONObject  jsonObj = new JSONObject();
            jsonObj.put("account", intAccount);
            jsonObj.put("password", intPassword);
            jsonObj.put("msg", msgText);
            jsonObj.put("mobile", phoneNumber);
            String params = jsonObj.toString();
            LOGGER.info("--------账号[{}]",intAccount);
            LOGGER.info("--------p[{}]",intPassword);

            LOGGER.info("开始通过【创蓝】发送短信。。。手机号：[{}]，发送内容[{}]",phoneNumber, msgText);
            //发送短信
//            String result = HttpUtil.post(intSendUrl,params);

            String result ="";
            try {
                 result = HttpUtils.postGeneralUrl(intSendUrl,CONTENT_TYPE_JSON,params,null,null);
            }catch (Exception e){
                LOGGER.error(e.getMessage(),e);
                return false;
            }


            //解析发送结果
            JSONObject jsonObjRes = JSON.parseObject(result);
            String code = jsonObjRes.get("code").toString();
            String error = jsonObjRes.get("error").toString();
            String msgid = jsonObjRes.get("msgid").toString();

            LOGGER.info("手机号：[{}] 发送结果：状态码[{}],状态码说明[{}],消息id[{}]",phoneNumber,code,error,msgid);

            LOGGER.info("开始执行数据库操作，添加发送记录=============================================");
            MessageSendRecordInputBO inputBO = new MessageSendRecordInputBO();
            inputBO.setAppId(applicationEnmu.getCode());
            //国家编码
//            inputBO.setCountryId();
            inputBO.setChannelId(ChannelEnum.CHUANGLAN.getCode());
            inputBO.setPhoneNumber(phoneNumber);
            inputBO.setMsgText(msgText);
            if(StringUtils.isNotBlank(msgid)){
                inputBO.setMsgId(msgid);
            }
            inputBO.setResCode(Integer.valueOf(code));
            if(StringUtils.isNotBlank(error)){
                inputBO.setResExplain(error);
            }
            inputBO.setReturnValue(result);
            int count = msgDBService.addMsgSendRecord(inputBO,context);

            LOGGER.info("添加 [{}] 条数据", count);

            if(ChuangLanIntResponseEnum.SUCCESS.getCode().toString().equals(code)){
                //成功请求创蓝短信接口
                Integer sendNum = 1;
                Object sendN = MsgUtils.expiryMap.get(phoneNumber);
                if(sendN != null){
                    sendNum = (Integer)sendN + 1;
                }
                Long endTime = MsgUtils.getEndTime();
                //转成毫秒
                endTime = endTime * 1000;

                //日志打印，当天手机号码发送次数
                MsgUtils.printMsgSendLogs(phoneNumber,sendNum, endTime);

                MsgUtils.expiryMap.put(phoneNumber, sendNum, endTime);
                return true;
            }
            return false;
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            throw e;
        }
    }

    //同模不同参     //todo 创蓝后台可以文本变量替换发送，用替换符号{s}，实现部分内容不一样的同一模板发送
    @Override
    public void batchSendSameTemplateDiffParam(ApplicationEnmu applicationEnmu,
                                                       Map<String, List> paramMap,
                                                       String templateCode,
                                                       Context context) {
        // 用循环调用单个发送实现
        super.batchSendSameTemplateDiffParam(applicationEnmu,paramMap,templateCode,context);
    }

    //同模同参
    @Override
    public void batchSendSameTemplateSameParam(ApplicationEnmu applicationEnmu,
                                               List phoneNumberList,
                                               String templateCode,
                                               List paramList,
                                               Context context) {
        //创蓝群发API
        ExceptionMessageUtils.verifyObjectIsNull(context,applicationEnmu,phoneNumberList);
        ExceptionMessageUtils.verifyStringIsBlank(context,templateCode);

        //默认一次发送不能超过限制的手机号数
        if(phoneNumberList.size() > paramSize){
            ExceptionMessageUtils.throwExceptionParamTooLong(context);
        }

        String msgTemplate = msgDBService.getMsgTemplate(applicationEnmu,templateCode,context);
        if(StringUtils.isBlank(msgTemplate)){
            ExceptionMessageUtils.throwExceptionTemplateNotExist(context);
        }
        //替换可变参数
        String sendContent = MsgUtils.getSendContent(msgTemplate,symbol,paramList,context);

        RequestParamBean1 requestParamBean1 = new RequestParamBean1();
        requestParamBean1.setAccount(intAccount);

        requestParamBean1.setPassword(intPassword);
        requestParamBean1.setMsg(sendContent);
        StringBuffer stringBuffer = new StringBuffer();

        //todo 拼接 []
        for(int i =0;i<phoneNumberList.size();i++){
            if(i==0){
                stringBuffer.append(phoneNumberList.get(i));
            }else{
                stringBuffer.append(",");
                stringBuffer.append(phoneNumberList.get(i));
            }


        }
        requestParamBean1.setMobile(stringBuffer.toString());
        //todo 短信限制数 和数据库入库
        String requestParamStr = JSON.toJSONString(requestParamBean1);

        try {
            //发送短信
            String  result = HttpUtils.postGeneralUrl(intMultiSendUrl,
                    CONTENT_TYPE_JSON,
                    requestParamStr,
                    null,
                    null);

            LOGGER.info("创蓝群发，同模同参[{}]",result);
            System.out.println("result = " + result);
        } catch (Exception e) {
            //todo
            LOGGER.error(e.getMessage(),e);
            e.printStackTrace();
        }


    }


    //不同模板
    @Override
    public void batchSendDiffTemplate(ApplicationEnmu applicationEnmu,
                                      List<BatchSendDiffTemplateParamBO> paramList,
                                      Context context) {
        //todo 只能循环调用单个发送的接口
        LOGGER.info("--------创蓝不同模板群发开始");
        super.batchSendDiffTemplate(applicationEnmu,paramList,context);

    }


}
