package com.panshi.hujin2.message.service.message.kmi;

import com.gexin.fastjson.JSON;
import com.gexin.fastjson.JSONObject;
import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.common.utils.HttpUtil;
import com.panshi.hujin2.message.domain.enums.ChannelEnum;
import com.panshi.hujin2.message.domain.exception.MessageException;
import com.panshi.hujin2.message.facade.bo.MessageSendRecordInputBO;
import com.panshi.hujin2.message.service.message.impl.SendMsg;
import com.panshi.hujin2.message.service.message.utils.MsgUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author shenJianKang
 * @date 2020/5/12 16:56
 *
 * KMI 营销短信
 */
@Service("KMILongnumberService")
public class KMILongnumberServiceImpl extends SendMsg {
    private static Logger LOGGER = LoggerFactory.getLogger(KMILongnumberServiceImpl.class);

    @Autowired
    private KMIUtil kmiUtil;

    @Value("${kmi.send.longnumber.url}")
    private String kmiSendLongnumberUrl;

    @Override
    public boolean sendInternationalMsg(ApplicationEnmu applicationEnmu, String phoneNumber, String msgText, Context context) {
        LOGGER.info("=========== INA KMI LONGNUMBER ====================");
        try {
            String token = kmiUtil.getToken();

            com.alibaba.fastjson.JSONObject sendJsonObj = new com.alibaba.fastjson.JSONObject();
            sendJsonObj.put("token", token);
            sendJsonObj.put("sendType", 1);//发送类型 1.LongNumber
            sendJsonObj.put("msisdn", phoneNumber);//短信接收方号码，格式为62xxxx如：6281210506807 （只支持印尼号码）
            sendJsonObj.put("message", msgText);//短信内容
            String params = sendJsonObj.toString();


            //{"result":{"code":-101,"desc":"Token Error"}}
            String sendRes = HttpUtil.post(kmiSendLongnumberUrl, params);
            if(StringUtils.isNotBlank(sendRes) && (sendRes.contains("-101") || sendRes.contains("Token Error"))){
                //token失效， 重新請求
                String token2 = kmiUtil.getTokenByUrl();
                com.alibaba.fastjson.JSONObject sendJsonObj2 = new com.alibaba.fastjson.JSONObject();
                sendJsonObj2.put("token", token2);
                sendJsonObj2.put("sendType", 1);
                sendJsonObj2.put("msisdn", phoneNumber);
                sendJsonObj2.put("message", msgText);
                String params2 = sendJsonObj2.toString();
                sendRes = HttpUtil.post(kmiSendLongnumberUrl, params2);
            }

            LOGGER.info("--------KMI longnumber 发送结果:[{}],[{}],[{}]",phoneNumber,msgText,sendRes);
            MessageSendRecordInputBO inputBO = new MessageSendRecordInputBO();
            //返回结果样例
            //{"result":{"code":-101,"desc":"Token Error"}}
            //{"data":{"trxid":"15892755803551052","trxdate":"20200512172620"},"result":{"code":0,"desc":"SUCCESS"}}
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
                            msgid = data.getString("trxid");
                            //String trxdate = data.getString("trxdate");
                        }
                    }
                }
            }
            inputBO.setAppId(applicationEnmu.getCode());
            //国家编码
            inputBO.setChannelId(ChannelEnum.KMI_LONGNUMBER.getCode());
            inputBO.setPhoneNumber(phoneNumber);
            inputBO.setMsgText(msgText);
            inputBO.setMsgId(msgid);
            inputBO.setResCode(Integer.valueOf(code));
            inputBO.setResExplain(desc);
            inputBO.setReturnValue(sendRes);
            msgDBService.addMsgSendRecord(inputBO,context);
            return true;
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return false;
        }
    }


    public static void main(String[] args) throws Exception{
        //todo 请求kmi 营销短信
//        com.alibaba.fastjson.JSONObject sendJsonObj = new com.alibaba.fastjson.JSONObject();
//        //[{"data":{"token":"1386F33F46A0765973792019DEC065199233CD9D7BCF1AE6AA44474EC889E65A","balance":"97736.20","smsBalance":"1202040.00"},"result":{"code":0,"desc":"SUCCESS"}}
//        String token = getToken();
//        sendJsonObj.put("token", token);
//        sendJsonObj.put("sendType", 1);//发送类型 1.LongNumber
//        sendJsonObj.put("msisdn", "6281210506807");//短信接收方号码，格式为62xxxx如：6281210506807 （只支持印尼号码）
//        sendJsonObj.put("message", "hello");//短信内容
//        String params = sendJsonObj.toString();
//
//        //{"result":{"code":-101,"desc":"Token Error"}}
//        //{"data":{"trxid":"15892755803551052","trxdate":"20200512172620"},"result":{"code":0,"desc":"SUCCESS"}}
//        String sendRes = HttpUtil.post("http://cs.kmindo.com:9980/sm/sender", params);
//        System.out.println("sendRes = " + sendRes);

        //todo 解析 返回的结果
//        String sendRes = "{\"data\":{\"trxid\":\"15892755803551052\",\"trxdate\":\"20200512172620\"},\"result\":{\"code\":0,\"desc\":\"SUCCESS\"}}";
//        JSONObject jsonObj = JSONObject.parseObject(sendRes);
//        if(jsonObj != null){
//            JSONObject data = jsonObj.getJSONObject("data");
//            String trxid = data.getString("trxid");
//            System.out.println("trxid = " + trxid);
//            String trxdate = data.getString("trxdate");
//            System.out.println("trxdate = " + trxdate);
//            JSONObject result = jsonObj.getJSONObject("result");
//            String code = result.getString("code");
//            System.out.println("code = " + code);
//            String desc = result.getString("desc");
//            System.out.println("desc = " + desc);
//        }
    }

    //TODO: 2020/5/12 17:24 by ShenJianKang  可删除
//    public static final String TOKEN_KEY = "KMI_TOKEN";
//    public static final String KmiTokenUrl = "http://cs.kmindo.com:9980/cs/login?account=EasyKlick&password=b99846c549c57aa213fa8fe0033afdea";
//    public static String getToken() throws Exception{
//        Object tokenObj = MsgUtils.expiryMap.get(TOKEN_KEY);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//        System.out.println("======當前時間 = " + sdf.format(new Date()));
//        System.out.println("======當前tokenObj = " + tokenObj);
//
//        String token = null;
//        if(tokenObj == null){
//            LOGGER.info("map为空获取KMI 请求 [{}]",KmiTokenUrl);
//            String result = HttpUtil.get(KmiTokenUrl);
//            LOGGER.info("map为空获取KMI token [{}]",result);
//
//
//            if(StringUtils.isNotBlank(result)){
//                //解析result
//                //{"data":{"token":"440E8E87F36D52E60631F892689D62E14A19623FE9147EC2FC1947E2D4AF5285","balance":"0.00"},"result":{"code":0,"desc":"SUCCESS"}}
//                JSONObject jsonObject = JSON.parseObject(result);
//                JSONObject res = jsonObject.getJSONObject("result");
//                if(res != null){
//                    String code = res.getString("code");
//                    if("0".equals(code)){
//                        JSONObject data = jsonObject.getJSONObject("data");
//                        if(data != null){
//                            token = data.getString("token");
//                            MsgUtils.expiryMap.put(TOKEN_KEY,token, 50 * 60 * 1000 * 2);
////                            if(StringUtils.isNotBlank(token)){
////                                return token;
////                            }
//                        }
//                    }
//                }
//            }else {
//                //重试机制
//                boolean flag = true;
//                int num = 0;
//                //重试
//                while (flag && num<10){
//                    try {
//                        Thread.sleep(1000);
//                        LOGGER.info("重试获取KMI 请求 [{}]",KmiTokenUrl);
//                        result = HttpUtil.get(KmiTokenUrl);
//                        LOGGER.info("重试获取KMI token [{}]",result);
//
//                        num ++;
//                        if(StringUtils.isNotBlank(result)){
//                            JSONObject jsonObject = JSON.parseObject(result);
//                            JSONObject res = jsonObject.getJSONObject("result");
//                            if(res != null){
//                                String code = res.getString("code");
//                                if("0".equals(code)){
//                                    JSONObject data = jsonObject.getJSONObject("data");
//                                    if(data != null){
//                                        token = data.getString("token");
//                                        MsgUtils.expiryMap.put(TOKEN_KEY,token, 50 * 60 * 1000 * 2);
//                                        //结束重试
//                                        flag = false;
//                                    }
//                                }
//                            }
//                        }
//                    }catch (Exception e){
//                        LOGGER.error(e.getMessage(),e);
//                        throw e;
//                    }
//                }
//            }
//        }else {
//            token = String.valueOf(tokenObj);
//        }
//        if(StringUtils.isBlank(token)){
//            LOGGER.error(" ======== KMI TOKEN 获取失败！");
//            throw new MessageException("KMI TOKEN 获取失败！");
//        }
//        return token;
//    }

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
