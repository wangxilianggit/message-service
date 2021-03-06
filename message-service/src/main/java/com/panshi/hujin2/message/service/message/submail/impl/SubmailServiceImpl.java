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

            LOGGER.info("??????????????????SUBMAIL???????????????????????????api???[{}]",sendUrl);
            HashMap<String, String> paramer = new HashMap<String, String>();
            paramer.put("appid", appAccount);
            paramer.put("signature", appKey);
            paramer.put("to", "+"+phoneNumber);
            paramer.put("content", msgText);
            String result =  executePostByUsual(sendUrl, paramer,context);

            LOGGER.info("SUBMAIL?????????????????????[{}]",result);

            boolean res = false;
            //??????
            LOGGER.info("????????????????????????????????????????????????=============================================");
            MessageSendRecordInputBO inputBO = new MessageSendRecordInputBO();
            inputBO.setAppId(applicationEnmu.getCode());
//            inputBO.setCountryId();
            inputBO.setChannelId(ChannelEnum.SUBMAIL.getCode());
            inputBO.setPhoneNumber(phoneNumber);
            inputBO.setMsgText(msgText);
            //????????????????????????{"status":"success","send_id":"05be9ad8e53e8cadc1d0b9d1e5f9e380","fee":"0.134","account_balance":"0.052"}
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
                //???
                Long endTime = MsgUtils.getEndTime();
                //????????????
                endTime = endTime * 1000;

                //?????????????????????????????????????????????
                MsgUtils.printMsgSendLogs(phoneNumber,sendNum, endTime);

                MsgUtils.expiryMap.put(phoneNumber, sendNum, endTime);
            }
//            inputBO.setResCode(Integer.valueOf(code));
//            inputBO.setResExplain(error);
            inputBO.setReturnValue(result);
            int count = msgDBService.addMsgSendRecord(inputBO,context);

            LOGGER.info("?????? [{}] ?????????", count);

            return res;
        }catch (Exception e){
            LOGGER.debug(e.getMessage(),e);
            throw e;
        }
    }

    //????????????
    @Override
    public void batchSendSameTemplateSameParam(ApplicationEnmu applicationEnmu,
                                               List phoneNumberList,
                                               String templateCode,
                                               List paramList,
                                               Context context) {
        //todo submail?????????api???????????????????????????id???????????????id?????????????????????var(xxxx)

        Map<String, List> paramMap = new HashMap<>();
        for (Object phoneNumber : phoneNumberList){
            paramMap.put(String.valueOf(phoneNumber),paramList);
        }
        super.batchSendSameTemplateDiffParam(applicationEnmu,paramMap,templateCode,context);
    }

    //???????????????
    @Override
    public void batchSendSameTemplateDiffParam(ApplicationEnmu applicationEnmu,
                                               Map<String, List> paramMap,
                                               String templateCode,
                                               Context context) {
        //todo   submail???????????????????????????????????????api ??? ?????????submail?????????????????????id???
        // ?????????????????????????????????
        super.batchSendSameTemplateDiffParam(applicationEnmu,paramMap,templateCode,context);
    }

    @Override
    public void batchSendDiffTemplate(ApplicationEnmu applicationEnmu,
                                      List<BatchSendDiffTemplateParamBO> paramList,
                                      Context context) {
        //???????????????????????????
        //???????????????????????????????????????
        LOGGER.info("--------SUBMAIL????????????????????????");
        super.batchSendDiffTemplate(applicationEnmu,paramList,context);

    }




    //todo 3 http?????? ?????????
    /**
     *  ??????http??????
     */
    private String executePostByUsual(String actionURL,
                                      HashMap<String, String> parameters,
                                      Context context){
        String response = "";
        try {
            URL url = new URL(actionURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // ??????post????????????????????????
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // ????????????????????????
            String requestContent = "";
            Set<String> keys = parameters.keySet();
            for (String key : keys) {
                requestContent = requestContent + key + "=" + URLEncoder.encode(parameters.get(key), "UTF-8") + "&";
            }
            requestContent = requestContent.substring(0, requestContent.lastIndexOf("&"));
            DataOutputStream ds = new DataOutputStream(connection.getOutputStream());
            // ??????write(requestContent.getBytes())?????????????????????????????????
            ds.write(requestContent.getBytes());
            ds.flush();
            try {
                // ??????URL?????????
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
