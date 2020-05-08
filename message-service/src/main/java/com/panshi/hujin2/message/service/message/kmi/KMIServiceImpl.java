package com.panshi.hujin2.message.service.message.kmi;

import com.gexin.fastjson.JSON;
import com.gexin.fastjson.JSONObject;
import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.common.utils.HttpUtil;
import com.panshi.hujin2.message.common.utils.MD5Util;
import com.panshi.hujin2.message.dao.mapper.message.KmiTokenLogMapper;
import com.panshi.hujin2.message.dao.model.KmiTokenLogDO;
import com.panshi.hujin2.message.domain.enums.ChannelEnum;
import com.panshi.hujin2.message.domain.exception.MessageException;
import com.panshi.hujin2.message.facade.bo.MessageSendRecordInputBO;
import com.panshi.hujin2.message.service.message.impl.SendMsg;
import com.panshi.hujin2.message.service.message.submail.sdk.utils.StringUtil;
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
 * @date 2019/7/4 9:25
 */
@Service("KMIService")
public class KMIServiceImpl extends SendMsg {
    private static Logger LOGGER = LoggerFactory.getLogger(KMIServiceImpl.class);

    @Value("${kmi.account}")
    private String kmiAccount;

//    @Value("${kmi.pwd}")
//    private String kmiPwd;

//    @Value("${kmi.token.url}")
//    private String KmiTokenUrl;

    private final String KmiTokenUrl = "http://cs.kmindo.com:9980/cs/login?account=EasyKlick&password=b99846c549c57aa213fa8fe0033afdea";

    @Value("${kmi.send.msg.otp.url}")
    private String kmiSendOtpMsgUrl;

    @Autowired
    private KmiTokenLogMapper kmiTokenLogMapper;

    private final String TOKEN_KEY = "KMI_TOKEN";
    //private final long VALID_TIME = 59 * 60 * 1000 * 2;//kmi token有效期两小时，这里设置成不到两小时过期，-1分钟


    public static void main(String[] args) {
//        String pwd ="EasyKlick4818";
//        String pwd1 = MD5Util.MD5(pwd);
//        System.out.println("pwd1 = " + pwd1);

        String aa ="http://cs.kmindo.com:9980/cs/login?account=EasyKlick&password=b99846c549c57aa213fa8fe0033afdea_______{\"data\":{\"token\":\"3D8AF4F18A5CB27CF04F416CAD799E6297EED90E3D3B0FBCC5072128B697E27F\",\"balance\":\"0.00\"},\"result\":{\"code\":0,\"desc\":\"SUCCESS\"}}";
        System.out.println("aa.length() = " + aa.length());


        String bb = "{\"result\":{\"code\":-101,\"desc\":\"Token Error\"}}";
        System.out.println("bb.contains() = " + bb.contains("Token Error"));
    }
    @Override
    public String key() {
        return null;
    }

    @Override
    public boolean isDefault() {
        return false;
    }


    /**
     *@Description:     保存获取token的日志
     *@Param:  * @param result
     *@Author: shenJianKang
     *@date: 2019/7/23 11:19
     */
    private Integer saveReqTokenLog(String result){
        KmiTokenLogDO tokenLogDO = new KmiTokenLogDO();
        tokenLogDO.setResult(result);
        return kmiTokenLogMapper.insertSelective(tokenLogDO);
    }

    //b99846c549c57aa213fa8fe0033afdea
    //http://cs.kmindo.com:9980/cs/login?account=EasyKlick&password=b99846c549c57aa213fa8fe0033afdeab99846c549c57aa213fa8fe0033afdeab99846c549c57aa213fa8fe0033afdeab99846c549c57aa213fa8fe0033afdea______{"result":{"code":-100,"desc":"Channel Error"}}
    private String getTokenByUrl(){
//        String pwd = MD5Util.MD5(kmiPwd);
//        KmiTokenUrl = KmiTokenUrl + pwd;//全局變量不能重複 拼接
        LOGGER.info("map为空获取KMI 请求 [{}]",KmiTokenUrl);
        String result = HttpUtil.get(KmiTokenUrl);
        LOGGER.info("map为空获取KMI token [{}]",result);
        saveReqTokenLog(KmiTokenUrl+"___"+result);

        String token = "";
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
                        MsgUtils.expiryMap.put(TOKEN_KEY,token, 50 * 60 * 1000 * 2);
                    }
                }
            }
        }
        return token;
    }
    /**
     *@Description:     获取请求KMI需要的token
     *@Param:  * @param
     *@Author: shenJianKang
     *@date: 2019/7/4 10:10
     */
    private String getToken() throws Exception{
        Object tokenObj = MsgUtils.expiryMap.get(TOKEN_KEY);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        System.out.println("======當前時間 = " + sdf.format(new Date()));
        System.out.println("======當前tokenObj = " + tokenObj);

        String token = null;
        if(tokenObj == null){
            LOGGER.info("map为空获取KMI 请求 [{}]",KmiTokenUrl);
            String result = HttpUtil.get(KmiTokenUrl);
            LOGGER.info("map为空获取KMI token [{}]",result);
            saveReqTokenLog(KmiTokenUrl+"_____"+result);

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
                            MsgUtils.expiryMap.put(TOKEN_KEY,token, 50 * 60 * 1000 * 2);
//                            if(StringUtils.isNotBlank(token)){
//                                return token;
//                            }
                        }
                    }
                }
            }else {
                //重试机制
                boolean flag = true;
                int num = 0;
                //重试
                while (flag && num<10){
                    try {
                        Thread.sleep(1000);
                        LOGGER.info("重试获取KMI 请求 [{}]",KmiTokenUrl);
                        result = HttpUtil.get(KmiTokenUrl);
                        LOGGER.info("重试获取KMI token [{}]",result);
                        saveReqTokenLog(KmiTokenUrl+"______"+result);

                        num ++;
                        if(StringUtils.isNotBlank(result)){
                            JSONObject jsonObject = JSON.parseObject(result);
                            JSONObject res = jsonObject.getJSONObject("result");
                            if(res != null){
                                String code = res.getString("code");
                                if("0".equals(code)){
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    if(data != null){
                                        token = data.getString("token");
                                        MsgUtils.expiryMap.put(TOKEN_KEY,token, 50 * 60 * 1000 * 2);
                                        //结束重试
                                        flag = false;
                                    }
                                }
                            }
                        }
                    }catch (Exception e){
                        LOGGER.error(e.getMessage(),e);
                        throw e;
                    }
                }
            }
        }else {
            token = String.valueOf(tokenObj);
        }
        if(StringUtils.isBlank(token)){
            LOGGER.error(" ======== KMI TOKEN 获取失败！");
            throw new MessageException("KMI TOKEN 获取失败！");
        }
        return token;
    }


    @Override
    public boolean sendInternationalMsg(ApplicationEnmu applicationEnmu,
                                        String phoneNumber,
                                        String msgText,
                                        Context context) {
        LOGGER.info("=========== INA KMI ====================");
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


            //{"result":{"code":-101,"desc":"Token Error"}}
            String sendRes = HttpUtil.post(kmiSendOtpMsgUrl, params);
            if(StringUtils.isNotBlank(sendRes) && (sendRes.contains("-101") || sendRes.contains("Token Error"))){
                //token失效， 重新請求
                String token2 = getTokenByUrl();
                com.alibaba.fastjson.JSONObject sendJsonObj2 = new com.alibaba.fastjson.JSONObject();
                sendJsonObj2.put("token", token2);
                sendJsonObj2.put("from", "AFT");
                sendJsonObj2.put("to", phoneNumber);
                sendJsonObj2.put("message", msgText);
                String params2 = sendJsonObj2.toString();
                sendRes = HttpUtil.post(kmiSendOtpMsgUrl, params2);
            }

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
