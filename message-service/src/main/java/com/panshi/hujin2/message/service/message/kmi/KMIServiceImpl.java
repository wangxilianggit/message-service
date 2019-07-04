package com.panshi.hujin2.message.service.message.kmi;

import com.gexin.fastjson.JSON;
import com.gexin.fastjson.JSONObject;
import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.common.utils.HttpUtil;
import com.panshi.hujin2.message.common.utils.MD5Util;
import com.panshi.hujin2.message.domain.enums.ChannelEnum;
import com.panshi.hujin2.message.domain.exception.MessageException;
import com.panshi.hujin2.message.facade.bo.MessageSendRecordInputBO;
import com.panshi.hujin2.message.service.message.impl.SendMsg;
import com.panshi.hujin2.message.service.message.utils.MsgUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenJianKang
 * @date 2019/7/4 9:25
 */
@Service("KMIService")
public class KMIServiceImpl extends SendMsg {
    private static Logger LOGGER = LoggerFactory.getLogger(KMIServiceImpl.class);

    @Value("${kmi.account}")
    private String kmiAccount;

    @Value("${kmi.pwd}")
    private String kmiPwd;

    @Value("${kmi.token.url}")
    private String KmiTokenUrl;

    @Value("${kmi.send.msg.otp.url}")
    private String kmiSendOtpMsgUrl;

    private final String TOKEN_KEY = "KMI_TOKEN";
    private final long VALID_TIME = 59 * 60 * 1000 * 2;//kmi token有效期两小时，这里设置成不到两小时过期，-1分钟



    @Override
    public String key() {
        return null;
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    /**
     *@Description:     获取请求KMI需要的token
     *@Param:  * @param
     *@Author: shenJianKang
     *@date: 2019/7/4 10:10
     */
    private String getToken() throws Exception{
        Object tokenObj = MsgUtils.expiryMap.get(TOKEN_KEY);
        String token = null;
        if(tokenObj == null){
            String pwd = MD5Util.MD5(kmiPwd);
            KmiTokenUrl = KmiTokenUrl + pwd;
            //// TODO: 2019/7/4 token获取记录 需要不需要入库 
            String result = HttpUtil.get(KmiTokenUrl);

            if(StringUtils.isNotBlank(result)){
                //解析result
                //{"data":{"token":"440E8E87F36D52E60631F892689D62E14A19623FE9147EC2FC1947E2D4AF5285","balance":"0.00"},"result":{"code":0,"desc":"SUCCESS"}}
                JSONObject jsonObject = JSON.parseObject(result);
                JSONObject res = jsonObject.getJSONObject("result");
                if(res != null){
                    String code = res.getString("code");
                    if("0".equals(code)){
                        JSONObject data = jsonObject.getJSONObject("data");
                        if(data != null){
                            token = data.getString("token");
//                            if(StringUtils.isNotBlank(token)){
//                                return token;
//                            }
                        }
                    }
                }
            }else {
                //重试机制
//                boolean flag = true;
//                int num = 0;
//                //重试
//                while (flag && num<5){
//                    try {
//                        Thread.sleep(2000);
//                        result = HttpUtil.get(KmiTokenUrl);
//                        num ++;
//                        if(StringUtils.isNotBlank(result)){
//
//                        }
//                    }catch (Exception e){
//                        LOGGER.error(e.getMessage(),e);
//                        throw e;
//                    }
//                }
            }
        }else {
            token = String.valueOf(tokenObj);
        }
        if(StringUtils.isBlank(token)){
            LOGGER.error(" ======== KMI TOKEN 获取失败！");
            throw new MessageException("KMI TOKEN 获取失败！");
        }
        MsgUtils.expiryMap.put(TOKEN_KEY,token, VALID_TIME);
        return token;
    }

    @Override
    public boolean sendInternationalMsg(ApplicationEnmu applicationEnmu,
                                        String phoneNumber,
                                        String msgText,
                                        Context context) {
        try {
            String token = getToken();
            //普通短信
//            Map<String, Object> map = new HashMap<>();
//            map.put("token",token);
//            map.put("sendType",1);//发送类型 1.LongNumber
//            map.put("msisdn","62"+phoneNumber);//短信接收方号码，格式为62xxxx如：6281210506807 （只支持印尼号码）
//            map.put("message",msgText);//短信内容
//            String res = HttpUtil.post(kmiSendMsgUrl, map);

            com.alibaba.fastjson.JSONObject sendJsonObj = new com.alibaba.fastjson.JSONObject();
            sendJsonObj.put("token", token);
            sendJsonObj.put("from", "AFT");
            sendJsonObj.put("to", phoneNumber);
            sendJsonObj.put("message", msgText);
            String params = sendJsonObj.toString();
            //{"result":{"code":-113,"desc":"SMS Gateway Error"}}
            //{"data":{"trxid":"15622242966551293","trxdate":"20190704151136"},"result":{"code":0,"desc":"SUCCESS"}}
            String sendRes = HttpUtil.post(kmiSendOtpMsgUrl, params);


            LOGGER.info("--------KMI发送结果:[{}],[{}],[{}]",phoneNumber,msgText,sendRes);

            JSONObject jsonObj = JSONObject.parseObject(sendRes);
            if(jsonObj != null){
                // 数据入库
                MessageSendRecordInputBO inputBO = new MessageSendRecordInputBO();
                inputBO.setAppId(applicationEnmu.getCode());
                //国家编码
//            inputBO.setCountryId();
                inputBO.setChannelId(ChannelEnum.KMI.getCode());
                inputBO.setPhoneNumber(phoneNumber);
                inputBO.setMsgText(msgText);
                //inputBO.setMsgId(msgid);
//            inputBO.setResCode(Integer.valueOf(code));
//            inputBO.setResExplain(error);
                inputBO.setReturnValue(sendRes);
                int count = msgDBService.addMsgSendRecord(inputBO,context);
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
}
