package com.panshi.hujin2.message.service.message.submail.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.common.factory.MessageFactory;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.domain.enums.ChannelEnum;
import com.panshi.hujin2.message.domain.enums.SubmailIntResponseEnum;
import com.panshi.hujin2.message.domain.exception.MessageException;
import com.panshi.hujin2.message.facade.bo.BatchSendDiffTemplateParamBO;
import com.panshi.hujin2.message.facade.bo.MessageSendRecordInputBO;
import com.panshi.hujin2.message.service.message.impl.SendMsg;
import com.panshi.hujin2.message.service.message.utils.ExceptionMessageUtils;
import com.panshi.hujin2.message.service.message.utils.MsgUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * create by shenjiankang on 2018/6/21 15:10
 */
@Service("submailSerivce")
public class SubmailServiceImpl extends SendMsg {

    private static Logger LOGGER = LoggerFactory.getLogger(SubmailServiceImpl.class);


    @Value("${submail.internationalsms.appid}")
    private String appAccount;

    @Value("${submail.internationalsms.appkey}")
    private String appKey;

    @Value("${submail.encryption}")
    private String encryptionType;

    @Value("${submail.i18n.timestamp}")
    private String timestampUrl;

    @Value("${submail.i18n.send}")
    private String sendUrl;


    @Override
    public String key() {
        return "submail";
    }

    @Override
    public boolean isDefault() {
        return false;
    }


    @Override
    public boolean sendInternationalMsg(ApplicationEnmu applicationEnmu,
                                        String phoneNumber,
                                        String msgText,
                                        Context context,Integer msgType) {
        try {
            ExceptionMessageUtils.verifyStringIsBlank(context,phoneNumber,msgText);

            LOGGER.info("开始准备通过SUBMAIL发送短信。。。请求api：[{}]",sendUrl);
            HashMap<String, String> paramer = new HashMap<String, String>();
            paramer.put("appid", appAccount);
            paramer.put("signature", appKey);
            paramer.put("to", "+"+phoneNumber);
            paramer.put("content", msgText);
            String result =  executePostByUsual(sendUrl, paramer,context);

            LOGGER.info("SUBMAIL发送结果。。。[{}]",result);

            boolean res = false;
            //写库
            LOGGER.info("开始执行数据库操作，添加发送记录=============================================");
            MessageSendRecordInputBO inputBO = new MessageSendRecordInputBO();
            inputBO.setAppId(applicationEnmu.getCode());
//            inputBO.setCountryId();
            inputBO.setChannelId(ChannelEnum.SUBMAIL.getCode());
            inputBO.setPhoneNumber(phoneNumber);
            inputBO.setMsgText(msgText);
            //发送成功的格式：{"status":"success","send_id":"05be9ad8e53e8cadc1d0b9d1e5f9e380","fee":"0.134","account_balance":"0.052"}
            JSONObject jsonObjRes = JSON.parseObject(result);
            String resStatus = jsonObjRes.getString("status");

            if(SubmailIntResponseEnum.SUCCESS.getType().equals(resStatus)){
                inputBO.setMsgId(jsonObjRes.getString("send_id"));
                res = true;

                Integer sendNum = 1;
                Object sendN = MsgUtils.expiryMap.get(phoneNumber);
                if(sendN != null){
                    sendNum = (Integer)sendN + 1;
                }
                //秒
                Long endTime = MsgUtils.getEndTime();
                //转成毫秒
                endTime = endTime * 1000;

                //日志打印，当天手机号码发送次数
                MsgUtils.printMsgSendLogs(phoneNumber,sendNum, endTime);

                MsgUtils.expiryMap.put(phoneNumber, sendNum, endTime);
            }
//            inputBO.setResCode(Integer.valueOf(code));
//            inputBO.setResExplain(error);
            inputBO.setReturnValue(result);
            int count = msgDBService.addMsgSendRecord(inputBO,context);

            LOGGER.info("添加 [{}] 条数据", count);

            return res;
        }catch (Exception e){
            LOGGER.debug(e.getMessage(),e);
            throw e;
        }
    }

    //同模同参
    @Override
    public void batchSendSameTemplateSameParam(ApplicationEnmu applicationEnmu,
                                               List phoneNumberList,
                                               String templateCode,
                                               List paramList,
                                               Context context) {
        //todo submail的群发api接口，必须创建模板id，根据模板id发送，替换变量var(xxxx)

        Map<String, List> paramMap = new HashMap<>();
        for (Object phoneNumber : phoneNumberList){
            paramMap.put(String.valueOf(phoneNumber),paramList);
        }
        super.batchSendSameTemplateDiffParam(applicationEnmu,paramMap,templateCode,context);
    }

    //同模不同参
    @Override
    public void batchSendSameTemplateDiffParam(ApplicationEnmu applicationEnmu,
                                               Map<String, List> paramMap,
                                               String templateCode,
                                               Context context) {
        //todo   submail有自己独立的同模不同参群发api ， 但是要submail平台报备模板的id号
        // 用循环调用单个发送实现
        super.batchSendSameTemplateDiffParam(applicationEnmu,paramMap,templateCode,context);
    }

    @Override
    public void batchSendDiffTemplate(ApplicationEnmu applicationEnmu,
                                      List<BatchSendDiffTemplateParamBO> paramList,
                                      Context context) {
        //批量發送，不同模板
        //只能循环调用单个发送的接口
        LOGGER.info("--------SUBMAIL不同模板群发开始");
        super.batchSendDiffTemplate(applicationEnmu,paramList,context);

    }




    //todo 3 http发送 分割线
    /**
     *  发送http请求
     */
    private String executePostByUsual(String actionURL,
                                      HashMap<String, String> parameters,
                                      Context context){
        String response = "";
        try {
            URL url = new URL(actionURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 发送post请求需要下面两行
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 设置请求数据内容
            String requestContent = "";
            Set<String> keys = parameters.keySet();
            for (String key : keys) {
                requestContent = requestContent + key + "=" + URLEncoder.encode(parameters.get(key), "UTF-8") + "&";
            }
            requestContent = requestContent.substring(0, requestContent.lastIndexOf("&"));
            DataOutputStream ds = new DataOutputStream(connection.getOutputStream());
            // 使用write(requestContent.getBytes())是为了防止中文出现乱码
            ds.write(requestContent.getBytes());
            ds.flush();
            try {
                // 获取URL的响应
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                String s = "";
                String temp = "";
                while ((temp = reader.readLine()) != null) {
                    s += temp;
                }
                response = s;
                reader.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
                throw new MessageException(MessageFactory.getMsg("G19880108",context.getLocale()));
            }
            ds.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new MessageException(MessageFactory.getMsg("G19880108",context.getLocale()));
        }
        return response;
    }





}
