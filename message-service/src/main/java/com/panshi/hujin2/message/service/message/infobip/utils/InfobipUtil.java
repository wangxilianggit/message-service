package com.panshi.hujin2.message.service.message.infobip.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.facade.bo.InfobipMsgReportsInputBO;
import com.panshi.hujin2.message.service.message.utils.ExceptionMessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * create by shenjiankang on 24/07/2018 09:16
 */
public class InfobipUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(InfobipUtil.class);

    /**
     * @description:        解析infobip短信發送結果返回的json字符串
     * @param jsonParam
     * @param context
     * @Author shenjiankang
     * @Date 23/07/2018 21:01
     */
    public static InfobipMsgReportsInputBO resolverJsonStr(String jsonParam,Context context){
        LOGGER.info("--------開始解析infobip返回的發送信息json字符串: [{}]",jsonParam);

        ExceptionMessageUtils.verifyStringIsBlank(context,jsonParam);

        JSONObject jsonObject = JSON.parseObject(jsonParam);
        JSONArray jsonArray = jsonObject.getJSONArray("results");

        LOGGER.info("--------获取短信发送信息jsonArray: [{}]",jsonArray);

        if(jsonArray!=null && jsonArray.size()>0){
            JSONObject resultJSON = jsonArray.getJSONObject(0);
            String resBulkId = resultJSON.getString("bulkId");
            String messageId = resultJSON.getString("messageId");
            String phoneNumber = resultJSON.getString("to");
            String sentAt = resultJSON.getString("sentAt");
            String doneAt = resultJSON.getString("doneAt");
            Integer smsCount = resultJSON.getInteger("smsCount");


            JSONObject priceJson = resultJSON.getJSONObject("price");
            String pricePerMessage = priceJson.getString("pricePerMessage");
            String currency = priceJson.getString("currency");

            String callbackData = resultJSON.getString("callbackData");

            JSONObject statusJson = resultJSON.getJSONObject("status");
            Integer groupId = statusJson.getInteger("groupId");
            String groupName = statusJson.getString("groupName");
            Integer id = statusJson.getInteger("id");
            String name = statusJson.getString("name");
            String description = statusJson.getString("description");

            JSONObject errorJson = resultJSON.getJSONObject("error");
            Integer errorGroupId = errorJson.getInteger("groupId");
            String errorGroupName = errorJson.getString("groupName");
            Integer errorId = errorJson.getInteger("id");
            String errorName = errorJson.getString("name");
            String errorDescription = errorJson.getString("description");
            Boolean errorPermanent = errorJson.getBoolean("permanent");


            InfobipMsgReportsInputBO inputBO = new InfobipMsgReportsInputBO();
            inputBO.setBulkId(resBulkId);
            inputBO.setMessageId(messageId);
            inputBO.setPhoneNumber(phoneNumber);
            inputBO.setSentAt(sentAt);
            inputBO.setDoneAt(doneAt);
            inputBO.setSmsCount(smsCount);
            inputBO.setPricePerMessage(pricePerMessage);
            inputBO.setCurrency(currency);
            inputBO.setCallbackData(callbackData);
            inputBO.setStatusGroupId(groupId);
            inputBO.setStatusGroupName(groupName);
            inputBO.setStatusId(id);
            inputBO.setStatusName(name);
            inputBO.setStatusDescription(description);
            inputBO.setErrorGroupId(errorGroupId);
            inputBO.setErrorGroupName(errorGroupName);
            inputBO.setErrorId(errorId);
            inputBO.setErrorName(errorName);
            inputBO.setErrorDescription(errorDescription);
            inputBO.setErrorPermanent(errorPermanent);
            return inputBO;
        }
        LOGGER.info("--------infobip短信发送信息[{}]条, 发送结果[{}]",jsonArray.size(),jsonParam);
        return null;
    }

}
