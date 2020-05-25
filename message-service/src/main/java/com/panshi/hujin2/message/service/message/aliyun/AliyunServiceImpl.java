package com.panshi.hujin2.message.service.message.aliyun;

import com.alibaba.fastjson.JSON;
import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.common.utils.HttpUtil;
import com.panshi.hujin2.message.domain.enums.ChannelEnum;
import com.panshi.hujin2.message.facade.bo.MessageSendRecordInputBO;
import com.panshi.hujin2.message.service.message.IMsgDBService;
import com.panshi.hujin2.message.service.message.utils.ExceptionMessageUtils;
import com.panshi.hujin2.message.service.message.utils.MsgUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangxl
 * @date 2020/5/21 17:05
 *
 * 阿里云短息
 */

@Service("aliyunServiceImpl")
public class AliyunServiceImpl{

    private static Logger LOGGER = LoggerFactory.getLogger(AliyunServiceImpl.class);

    private String aliyunUrl = "https://dysmsapi.aliyuncs.com/";

    @Value("${everyday.send.num}")
    protected Integer everydaySendNum;

    @Value("${aliyun.message.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.message.accessSecret}")
    private String accessSecret;

    /**
     * 有贝而来
     */
    @Value("${aliyun.message.SignName}")
    private String SignName;

    @Autowired
    protected IMsgDBService msgDBService;

    public boolean sendInternationalMsg(ApplicationEnmu applicationEnmu,
                                        String phoneNumber,
                                        String templateCode,
                                        Map<String,String> paramList,
                                        Context context,
                                        Integer msgType) {
        ExceptionMessageUtils.verifyObjectIsNull(context, applicationEnmu);
        ExceptionMessageUtils.verifyStringIsBlank(context, phoneNumber,templateCode);

        if(!MsgUtils.checkSendNum(phoneNumber,everydaySendNum)){
            ExceptionMessageUtils.throwExceptionMobileSendNumOverLimit(context);
        }

        LOGGER.info("==========send aliyun message,params content={}", JSON.toJSONString(paramList));

        Map<String,String> params = new HashMap<>();
        params.putAll(paramList);

        //请求签名，即最终生成的签名结果值。
        params.put("PhoneNumbers", phoneNumber);
        params.put("TemplateCode", templateCode);
        params.put("TemplateParam", JSON.toJSONString(paramList));
        String url = null;
        try{
            url = signature(params);
        }catch (Exception ex){
            LOGGER.error(ex.getMessage(),ex);
            return false;
        }
        String responseStr = HttpUtil.get(url);
        if(StringUtils.isEmpty(responseStr)){
            LOGGER.error("send aliyun message res = null");
            return false;
        }
        AliyunMessageResponse aliyunMessageResponse = JSON.parseObject(responseStr,AliyunMessageResponse.class);
        // 数据入库
        MessageSendRecordInputBO inputBO = new MessageSendRecordInputBO();
        inputBO.setAppId(applicationEnmu.getCode());
        inputBO.setChannelId(ChannelEnum.ALIYUN.getCode());
        inputBO.setPhoneNumber(phoneNumber);
        inputBO.setMsgText(JSON.toJSONString(paramList));
        inputBO.setMsgId(aliyunMessageResponse.getRequestId());
        if(aliyunMessageResponse.getCode().equalsIgnoreCase("ok")){
            inputBO.setResCode(1);
        }else{
            inputBO.setResCode(0);
        }
        inputBO.setResExplain(aliyunMessageResponse.getMessage());
        inputBO.setReturnValue(aliyunMessageResponse.getMessage());
        inputBO.setMsgType(msgType);
        msgDBService.addMsgSendRecord(inputBO,context);
        return true;
    }

    private String signature(Map<String,String> params) throws Exception {

        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(new java.util.SimpleTimeZone(0, "GMT"));// 这里一定要设置GMT时区

        // 1. 系统参数
        params.put("SignatureMethod", "HMAC-SHA1");
        params.put("SignatureNonce", java.util.UUID.randomUUID().toString());
        params.put("AccessKeyId", accessKeyId);
        params.put("SignatureVersion", "1.0");
        params.put("Timestamp", df.format(new Date()));
        params.put("Format", "JSON");

        // 2. 业务API参数
        params.put("Action", "SendSms");
        params.put("Version", "2017-05-25");
        params.put("RegionId", "cn-hangzhou");
        params.put("SignName", SignName);
        params.put("OutId", "123");
        // 3. 去除签名关键字Key
        if (params.containsKey("Signature"))
            params.remove("Signature");
        // 4. 参数KEY排序
        java.util.TreeMap<String, String> sortParas = new java.util.TreeMap<String, String>();
        sortParas.putAll(params);
        // 5. 构造待签名的字符串
        java.util.Iterator<String> it = sortParas.keySet().iterator();
        StringBuilder sortQueryStringTmp = new StringBuilder();
        while (it.hasNext()) {
            String key = it.next();
            sortQueryStringTmp.append("&").append(specialUrlEncode(key)).append("=").append(specialUrlEncode(params.get(key)));
        }
        String sortedQueryString = sortQueryStringTmp.substring(1);// 去除第一个多余的&符号
        StringBuilder stringToSign = new StringBuilder();
        stringToSign.append("GET").append("&");
        stringToSign.append(specialUrlEncode("/")).append("&");
        stringToSign.append(specialUrlEncode(sortedQueryString));
        String sign = sign(accessSecret + "&", stringToSign.toString());
        // 6. 签名最后也要做特殊URL编码
        String signature = specialUrlEncode(sign);
        // 最终打印出合法GET请求的URL
        String url = aliyunUrl+"?Signature=" + signature + sortQueryStringTmp;
        return url;
    }

    public static String specialUrlEncode(String value) throws Exception {
        return java.net.URLEncoder.encode(value, "UTF-8")
                .replace("+", "%20")
                .replace("*", "%2A")
                .replace("%7E", "~");
    }
    public static String sign(String accessSecret, String stringToSign) throws Exception {
        javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA1");
        mac.init(new javax.crypto.spec.SecretKeySpec(accessSecret.getBytes("UTF-8"), "HmacSHA1"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        return new sun.misc.BASE64Encoder().encode(signData);
    }
}
