package com.panshi.hujin2.message.service.message.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.common.utils.HttpUtil;
import com.panshi.hujin2.message.domain.enums.ChannelEnum;
import com.panshi.hujin2.message.domain.enums.PaasooResponseEnum;
import com.panshi.hujin2.message.facade.bo.MessageSendRecordInputBO;
import com.panshi.hujin2.message.service.message.utils.ExceptionMessageUtils;
import com.panshi.hujin2.message.service.message.utils.MsgUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by shenjiankang on 2018/8/31 23:16
 */
@Service("paasooService")
public class PaasooServiceImpl extends SendMsg {

    private static Logger LOGGER = LoggerFactory.getLogger(PaasooServiceImpl.class);

    //From参数是自定义的，3-11位字母;这个在某些国家和地区会显示成发送者，但是不支持的国家就会显示成运营商的短号
    private final String FROM = "Panshi";

    @Value("${paasoo.send.url}")
    private String reqUrl;

    @Value("${paasoo.api.key}")
    private String apiKey;

    @Value("${paasoo.api.secret}")
    private String apiSecret;

    @Override
    public String key() {
        return null;
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    @Override
    public boolean sendInternationalMsg(ApplicationEnmu applicationEnmu, String phoneNumber, String msgText, Context context,Integer msgType) {

        ExceptionMessageUtils.verifyObjectIsNull(context,applicationEnmu);
        ExceptionMessageUtils.verifyStringIsBlank(context,phoneNumber, msgText);

        LOGGER.info("paasoo发送。。。手机号：[{}]，发送内容[{}]",phoneNumber, msgText);

        String smsText =msgText;
        try {
            smsText = URLEncoder.encode(msgText,"UTF-8");
            System.out.println("smsText = " + smsText);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(),e);
            return false;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("key", apiKey);
        params.put("secret", apiSecret);
        params.put("from", FROM);
        params.put("to", phoneNumber);
        params.put("text", smsText);
        String res = HttpUtil.get(reqUrl, params);

        //成功示例: {"status":"0","messageid":"0120c3-bb0f74-8000"}
        //失败示例: {“status_code”：“Missing params。”，“status”：“2”}
        JSONObject jsonObject = JSON.parseObject(res);
        String status = jsonObject.getString("status");
        String messageid = jsonObject.getString("messageid");
        String failDsec = jsonObject.getString("status_code");

        MessageSendRecordInputBO inputBO = new MessageSendRecordInputBO();
        inputBO.setAppId(applicationEnmu.getCode());
        inputBO.setChannelId(ChannelEnum.PAASOO.getCode());
        inputBO.setPhoneNumber(phoneNumber);
        inputBO.setMsgText(msgText);

        inputBO.setMsgId(messageid);
        inputBO.setResCode(Integer.valueOf(status));
        inputBO.setResExplain(failDsec);
        inputBO.setReturnValue(res);
        inputBO.setMsgType(msgType);
        msgDBService.addMsgSendRecord(inputBO,context);

        if(PaasooResponseEnum.paasoo_sms_response_0.getCode().equals(status)){
            //请求成功,计入缓存,发送次数+1
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
    }

    @Override
    public void batchSendSameTemplateSameParam(ApplicationEnmu applicationEnmu, List phoneNumberList, String templateCode, List paramList, Context context) {
        //todo  paasoo 暂时没有群发api
    }

}
