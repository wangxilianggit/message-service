package com.panshi.hujin2.message.service.message.kmi;

import com.alibaba.fastjson.JSONObject;
import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.common.utils.HttpUtil;
import com.panshi.hujin2.message.domain.enums.ChannelEnum;
import com.panshi.hujin2.message.facade.bo.MessageSendRecordInputBO;
import com.panshi.hujin2.message.service.message.impl.SendMsg;
import com.panshi.hujin2.message.service.message.utils.MsgUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenJianKang
 * @date 2020/5/21 17:05
 *
 * KMI 语音短信
 */

@Service("KMIVoiceServiceImpl")
public class KMIVoiceServiceImpl extends SendMsg {
    private static Logger LOGGER = LoggerFactory.getLogger(KMIVoiceServiceImpl.class);

    @Autowired
    private KMIUtil kmiUtil;

    @Value("${kmi.send.voice.verify.url}")
    private String kmiSendVoiceVerifyUrl;



    @Override
    public boolean sendInternationalMsg(ApplicationEnmu applicationEnmu, String phoneNumber, String msgText, Context context, Integer msgType) {
        LOGGER.info("=========== INA KMI 语音短信 ====================");

        try {
            String token = kmiUtil.getToken();

            JSONObject sendJsonObj = new JSONObject();
            sendJsonObj.put("token", token);
            //传进来的手机号默认去掉第一位0
            sendJsonObj.put("msisdn", 62 + phoneNumber);//接收方号码，格式为62xxxx如：6281210506807
            sendJsonObj.put("verifyCode", msgText);//短信验证码,6位纯数字
            String params = sendJsonObj.toString();

            //以下是正常6位验证码:111
            //手机号0              返回:{"result":{"code":0,"desc":"SUCCESS"}}
            //以下是正常6位验证码:111111
            //手机号6281210506807  返回:{"data":{"id":78,"voiceCode":"111111"},"result":{"code":0,"desc":"SUCCESS"}}
            //手机号62081210506807 返回:{"result":{"code":0,"desc":"SUCCESS"}}
            //手机号62081210       返回:{"result":{"code":0,"desc":"SUCCESS"}}
            //手机号081210         返回:{"data":{"id":81,"voiceCode":"111111"},"result":{"code":0,"desc":"SUCCESS"}}
            String sendRes = HttpUtil.post(kmiSendVoiceVerifyUrl, params);
            if(StringUtils.isNotBlank(sendRes) && (sendRes.contains("-101") || sendRes.contains("Token Error"))){
                //token失效， 重新請求
                String token2 = kmiUtil.getTokenByUrl();
                JSONObject sendJsonObj2 = new JSONObject();
                sendJsonObj2.put("token", token2);
                sendJsonObj2.put("msisdn", phoneNumber);
                sendJsonObj2.put("verifyCode", msgText);
                String params2 = sendJsonObj2.toString();
                sendRes = HttpUtil.post(kmiSendVoiceVerifyUrl, params2);
            }

            LOGGER.info("--------KMI longnumber 发送结果:[{}],[{}],[{}]",phoneNumber,msgText,sendRes);
            MessageSendRecordInputBO inputBO = new MessageSendRecordInputBO();
            JSONObject jsonObj = JSONObject.parseObject(sendRes);
            String msgid = null;
            String code = null;
            String desc = null;
            if(jsonObj != null){
                JSONObject result = jsonObj.getJSONObject("result");
                if(result != null){
                    code = result.getString("code");
                    desc = result.getString("desc");
                    if("0".equals(code)){
                        //成功
                        JSONObject data = jsonObj.getJSONObject("data");
                        if(data != null){
                            msgid = data.getString("id");
                        }
                    }
                }
            }
            inputBO.setAppId(applicationEnmu.getCode());
            //国家编码
            inputBO.setChannelId(ChannelEnum.KMI_VOICE_VERIFY.getCode());
            inputBO.setPhoneNumber(phoneNumber);
            inputBO.setMsgText(msgText);
            inputBO.setMsgId(msgid);
            inputBO.setResCode(Integer.valueOf(code));
            inputBO.setResExplain(desc);
            inputBO.setReturnValue(sendRes);
            inputBO.setMsgType(msgType);
            msgDBService.addMsgSendRecord(inputBO,context);

            if("0".equals(code)){
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
            }
            return true;
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return false;
        }
    }

    @Override
    public void batchSendSameTemplateSameParam(ApplicationEnmu applicationEnmu, List phoneNumberList, String templateCode, List paramList, Context context) {

    }



    @Override
    public String key() {
        return null;
    }

    @Override
    public boolean isDefault() {
        return false;
    }
}
