package com.panshi.hujin2.message.service.message.nx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.common.utils.HttpUtil;
import com.panshi.hujin2.message.domain.enums.ChannelEnum;
import com.panshi.hujin2.message.domain.enums.ChuangLanIntResponseEnum;
import com.panshi.hujin2.message.facade.bo.MessageSendRecordInputBO;
import com.panshi.hujin2.message.service.message.impl.SendMsg;
import com.panshi.hujin2.message.service.message.kmi.KMIServiceImpl;
import com.panshi.hujin2.message.service.message.utils.HttpUtils;
import com.panshi.hujin2.message.service.message.utils.MsgUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.MacSpi;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author shenJianKang
 * @date 2020/5/8 10:51
 *
 * 牛信
 */

@Service("nxService")
public class NXServiceImpl extends SendMsg {
    private static Logger LOGGER = LoggerFactory.getLogger(NXServiceImpl.class);


    //HTTPS接口地址：https://api.nxcloud.com; 如果用户服务器在国内则建议对接地址使用：http://apichina.nxcloud.com:9099
    //POST,参数不要传body,用form表单方式提交
//    static String reqUrl = "http://api.nxcloud.com/api/sms/mtsend";
//    static String reqUrl2 = "http://apichina.nxcloud.com:9099";
//    static String appkey = "1wB7jHly";
//    static String secretkey = "ec9EOu3i";

    @Value("${sms.niuxin.request_url}")
    private String reqUrl;
    @Value("${sms.niuxin.appkey}")
    private String appkey;
    @Value("${sms.niuxin.secretkey}")
    private String secretkey;


    @Override
    public boolean sendInternationalMsg(ApplicationEnmu applicationEnmu, String phoneNumber, String msgText, Context context,Integer msgType) {
//        String phoneNumber = "6281285290562";
//        String msgText = "Kode verifikasi anda untuk login ke cashdog adalah 1111";

        try {
            String msgTextEncode = URLEncoder.encode(msgText, "UTF-8");

            Map<String, String> map = new HashMap<>();
            map.put("appkey", appkey);//必选          是	string	短信应用appkey
            map.put("secretkey", secretkey);//必选       是	string	短信应用secretkey
            map.put("phone", phoneNumber);//必选  是	string	被叫号码(国码+手机号，比如：8615088888888),可以多个并且以”,”英文逗号隔开
            map.put("content", msgTextEncode);//必选    是	string	短信内容,必须做urlencode(UFT-8)
//        sendJsonObj.put("source_address", "");//必选  否	string	sourceaddress/sender
//        sendJsonObj.put("task_time", "");//必选       否	string	定时时间yyyy-MM-dd HH:mm:ss（时区为GMT+8）
//        sendJsonObj.put("short_link", "");//必选      否	string	短链，数据来自于短链列表；如果此处赋值，短信内容里面必须包含#1#才能起作用，请注意
//        sendJsonObj.put("sys_messageid", "");//必选   否	string	用户自定义messageid，长度为10-50位之间，类型【0-9a-zA-Z-】

            //{"result":"参数错误或为空","messageid":"","code":"2"}
            //{"result":"请求成功","messageid":"d235116f4b6143eca7225b17ceee515d","code":"0"}
            String sendRes = HttpUtils.postForm(reqUrl, map,"UTF-8");

            String messageid = null;
            String code = null;
            String result = null;
            if(StringUtils.isNotBlank(sendRes)){
                JSONObject jsonObj = JSON.parseObject(sendRes);
                messageid = jsonObj.getString("messageid");
                code = jsonObj.getString("code");
                result = jsonObj.getString("result");
            }

            MessageSendRecordInputBO inputBO = new MessageSendRecordInputBO();
            inputBO.setAppId(applicationEnmu.getCode());
            //国家编码
//            inputBO.setCountryId();
            inputBO.setChannelId(ChannelEnum.NIU_XIN.getCode());
            inputBO.setPhoneNumber(phoneNumber);
            inputBO.setMsgText(msgText);
            if(StringUtils.isNotBlank(messageid)){
                inputBO.setMsgId(messageid);
            }
            if(StringUtils.isNotBlank(code)){
                inputBO.setResCode(Integer.valueOf(code));
            }
            if(StringUtils.isNotBlank(result)){
                inputBO.setResExplain(result);
            }

            inputBO.setReturnValue(sendRes);
            inputBO.setMsgType(msgType);
            msgDBService.addMsgSendRecord(inputBO,context);

            if("0".equals(code)){
                //成功
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
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            //TODO: 2020/5/8 14:18 by ShenJianKang 测试62后面有0 返回也是成功，但是实际是否收到不确定
//            String phoneNumber = "62081285290562";
//            String msgText = "Kode verifikasi anda untuk login ke cashdog adalah 1111";
//
//            msgText = URLEncoder.encode(msgText, "UTF-8");
//
//            Map<String, String> map = new HashMap<>();
//            map.put("appkey", appkey);//必选          是	string	短信应用appkey
//            map.put("secretkey", secretkey);//必选       是	string	短信应用secretkey
//            map.put("phone", phoneNumber);//必选  是	string	被叫号码(国码+手机号，比如：8615088888888),可以多个并且以”,”英文逗号隔开
//            map.put("content", msgText);//必选    是	string	短信内容,必须做urlencode(UFT-8)
////        sendJsonObj.put("source_address", "");//必选  否	string	sourceaddress/sender
////        sendJsonObj.put("task_time", "");//必选       否	string	定时时间yyyy-MM-dd HH:mm:ss（时区为GMT+8）
////        sendJsonObj.put("short_link", "");//必选      否	string	短链，数据来自于短链列表；如果此处赋值，短信内容里面必须包含#1#才能起作用，请注意
////        sendJsonObj.put("sys_messageid", "");//必选   否	string	用户自定义messageid，长度为10-50位之间，类型【0-9a-zA-Z-】
//
//            //{"result":"参数错误或为空","messageid":"","code":"2"}
//            //{"result":"请求成功","messageid":"d235116f4b6143eca7225b17ceee515d","code":"0"}
//            String sendRes = HttpUtils.postForm(reqUrl, map,"UTF-8");


            //解析json
            String sendRes1 = "{\"result\":\"参数错误或为空\",\"messageid\":\"\",\"code\":\"2\"}";
            String sendRes2 = "{\"result\":\"请求成功\",\"messageid\":\"d235116f4b6143eca7225b17ceee515d\",\"code\":\"0\"}";
            JSONObject jsonObj = JSON.parseObject(sendRes2);
            String result = jsonObj.getString("result");
            String messageid = jsonObj.getString("messageid");
            String code = jsonObj.getString("code");

            System.out.println("code = " + code);
            System.out.println("messageid = " + messageid);
            System.out.println("result = " + result);
        }catch (Exception e){
            System.out.println("e = " + e);
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
