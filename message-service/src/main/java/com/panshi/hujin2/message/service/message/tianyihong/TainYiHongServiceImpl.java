package com.panshi.hujin2.message.service.message.tianyihong;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gexin.fastjson.JSON;
import com.google.gson.JsonArray;
import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.common.util.DateUtils;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.common.utils.HttpUtil;
import com.panshi.hujin2.message.domain.enums.ChannelEnum;
import com.panshi.hujin2.message.facade.bo.MessageSendRecordInputBO;
import com.panshi.hujin2.message.service.message.impl.SendMsg;
import com.panshi.hujin2.message.service.message.utils.ExceptionMessageUtils;
import com.panshi.hujin2.message.service.message.utils.MsgUtils;
import com.panshi.hujin2.message.service.message.yimeiruantong.util.Md5;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * create by shenjiankang on 2018/8/20 9:02
 */
@Service("tianyihongService")
public class TainYiHongServiceImpl extends SendMsg {
    private static Logger LOGGER = LoggerFactory.getLogger(TainYiHongServiceImpl.class);

    @Value("${tianyihong.send.url}")
    private String sendUrl;

    @Value("${tianyihong.account}")
    private String account;

    @Value("${tianyihong.pwd}")
    private String pwd;

    @Value("${tianyihong.otp.account}")
    private String otpAccount;

    @Value("${tianyihong.otp.pwd}")
    private String otpPwd;

    private final String CONTENT_TYPE_JSON = "application/json";

    @Override
    public String key() {
        return null;
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    /*
     * 中文转unicode编码
     */
    private String gbEncoding(final String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }

    /**
     * @description:        发送国际短信（单个发送）
     * @param applicationEnmu         调用短信服务的应用程序(枚举在basic_project中)
     * @param phoneNumber   手机号码   (短信接收号码，多个号码之间以英文逗号分隔（get 最多 100 个,post 最多 10000 个）)
     * @param msgText       短信内容    String长度不能超过1024
     * @param context       国际化上下文
     * @Author shenjiankang
     * @Date 2019/1/30
     */
    @Override
    public boolean sendInternationalMsg(ApplicationEnmu applicationEnmu, String phoneNumber, String msgText, Context context,Integer msgType) {
        LOGGER.info("--------开始通过【tainyihong】发送短信,。。。手机号：[{}]，发送内容[{}]",phoneNumber, msgText);
        ExceptionMessageUtils.verifyObjectIsNull(context, applicationEnmu);
        ExceptionMessageUtils.verifyStringIsBlank(context,phoneNumber,msgText);

//        if(StringUtils.isNotBlank(phoneNumber)){
//            if(phoneNumber.startsWith("620")){
//                phoneNumber = phoneNumber.replace("620","62");
//
//            }else if(phoneNumber.startsWith("0")){
//                if(0 == phoneNumber.indexOf("0")){
//                    phoneNumber = phoneNumber.substring(1);
//                    phoneNumber = "62" + phoneNumber;
//                }
//            }
//        }

        String smsText =msgText;
        try {
            smsText = URLEncoder.encode(msgText,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(),e);
            return false;
        }

        try {
            //天一泓新版本参数datetime
            Date currentDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            cal.add(Calendar.HOUR_OF_DAY,1);//时区问题，印尼晚一个小时，天一泓传参：调用信息里面的datetime需传GMT+8当前半小时内时间

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentDateStr = sdf.format(cal.getTime());

            //天一泓新版本参数sign
            //使用 账号+密码+时间 生成MD5字符串作为签名。MD5生成32位，且需要小写
            //例如：
            //账号是account
            //密码是pwd
            //时间是20181109231202
            //就需要用accountpwd20181109231202
            //来生成MD5的签名，生成的签名为
            //ec971c60092c2514826a3d64f53356e2
            StringBuilder sb = new StringBuilder();
            sb.append(otpAccount);
            sb.append(otpPwd);
            sb.append(currentDateStr);
            String signMd5 = Md5.md5(sb.toString().getBytes());


            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("account",otpAccount);
            paramsMap.put("password",otpPwd);
            paramsMap.put("numbers",phoneNumber);
            paramsMap.put("content",smsText);

            paramsMap.put("sign",signMd5);
            paramsMap.put("datetime",currentDateStr);

            //非必填， 越南短信发送需要 这个参数
            //paramsMap.put("senderid","VietID");
            //paramsMap.put("senderid","VIETID.NET");

            //{"status":0,"success":1,"fail":0,"array":[[6281285290562,2008031441171046099]],"reason":null}
            //{"status":-9,"success":0,"fail":1,"array":[],"reason":null}
            //{"status":-1,"success":0,"fail":0,"array":null,"reason":"MD5验证不通过"}
            //{"status":-13,"success":0,"fail":0,"array":null,"reason":"账户状态不可用,被锁定"}
            String sendRes = HttpUtil.get(sendUrl,paramsMap);
            //如果换成post请求,需要全都拼在url里
//            String sendRes = HttpUtil.post(sendUrl+"?account="+account+"&password="+pwd+"&numbers="+phoneNumber+"&content="+smsText,paramsMap);

            LOGGER.info("--------天一泓发送结果:[{}],[{}],[{}]",phoneNumber,msgText,sendRes);

            JSONObject jsonObj = JSONObject.parseObject(sendRes);
            //{"status":0, "array":[[525574991875,964206]], "success":1, "fail":0}
            String status = "";
            String msgid = null;
            if(jsonObj != null){
                status = jsonObj.getString("status");
            }

            if("0".equals(status)){
                JSONArray array = jsonObj.getJSONArray("array");
                if(array != null  && array.size()>0){
                    Object obj = array.get(0);
                    if(obj != null){
                        JSONArray array1 = (JSONArray)obj;
                        if(array1!=null && array1.size()>0){
                            msgid = String.valueOf(array1.get(1));
                        }
                    }
                }
            }

            // 数据入库
            MessageSendRecordInputBO inputBO = new MessageSendRecordInputBO();
            inputBO.setAppId(applicationEnmu.getCode());
            //国家编码
//            inputBO.setCountryId();
            inputBO.setChannelId(ChannelEnum.TIANYIHONG_OTP.getCode());
            inputBO.setPhoneNumber(phoneNumber);
            inputBO.setMsgText(msgText);
            inputBO.setMsgType(msgType);
            inputBO.setMsgId(msgid);
//            inputBO.setResCode(Integer.valueOf(code));
//            inputBO.setResExplain(error);
            inputBO.setReturnValue(sendRes);
            int count = msgDBService.addMsgSendRecord(inputBO,context);

            //短信限制  // TODO: 2018/9/29 魔法数字，改成枚举
            if("0".equals(status)){
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

        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
            return false;
        }
        return true;
    }
    


    @Override
    public void batchSendSameTemplateSameParam(ApplicationEnmu applicationEnmu, List phoneNumberList, String templateCode, List paramList, Context context) {
        //todo  群发还是用上面的api请求
    }


    public void batchMsg(List<String> phoneNumbers, String msgContent,Context context){

    }


    public static void main(String[] args) {
        String sendRes = "{\"status\":0,\"success\":1,\"fail\":0,\"array\":[[6281285290562,2008031441171046099]],\"reason\":null}";
        JSONObject jsonObj = JSONObject.parseObject(sendRes);
        //{"status":0, "array":[[525574991875,964206]], "success":1, "fail":0}
        String status = "";
        if(jsonObj != null){
            status = jsonObj.getString("status");
        }
        System.out.println("status = " + status);

        JSONArray array = jsonObj.getJSONArray("array");
        Object obj = array.get(0);
        //JsonArray array2 = JSON.parse(obj);

        JSONArray array1 = (JSONArray)obj;
        System.out.println("array1.get(1) = " + array1.get(1));

        if(obj != null){
            String res = String.valueOf(obj).split(",")[1];
            System.out.println("res = " + res);
        }




        System.out.println("array = " + array.get(0));
    }

}
