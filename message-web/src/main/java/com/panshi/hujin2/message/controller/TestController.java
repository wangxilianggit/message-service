package com.panshi.hujin2.message.controller;

import com.gexin.fastjson.JSON;
import com.gexin.fastjson.JSONObject;
import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.base.service.utils.ContextUtils;
import com.panshi.hujin2.message.common.utils.HttpUtil;
import com.panshi.hujin2.message.common.utils.MD5Util;
import com.panshi.hujin2.message.common.utils.gethttps.HttpsGet;
import com.panshi.hujin2.message.domain.exception.MessageException;
import com.panshi.hujin2.message.facade.IMessageFacade;
import com.panshi.hujin2.message.facade.bo.BatchSendDiffTemplateParamBO;
import com.panshi.hujin2.message.service.message.IMsgDBService;
import com.panshi.hujin2.message.service.message.ISendMsgService;
import com.panshi.hujin2.message.service.message.infobip.entity.InfobipParam2;
import com.panshi.hujin2.message.service.message.infobip.impl.InfobipServiceImpl;
import com.panshi.hujin2.message.service.message.sms.bo.SMSSendBO;
import com.panshi.hujin2.message.service.message.submail.sdk.utils.StringUtil;
import com.panshi.hujin2.message.service.message.utils.HttpUtils;
import com.panshi.hujin2.message.service.message.utils.MsgUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * create by shenjiankang on 2018/6/26 11:22
 *
 *
 * 测试短信发送
 */
@RestController
//@Scope(value = WebApplicationContext.SCOPE_REQUEST)
//@Scope("prototype")
@RequestMapping("test")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private IMessageFacade messageFacade;

    @Autowired
    private IMsgDBService msgDBService;

    @Autowired
    @Qualifier("chuanglanService")
    private ISendMsgService chuanglanMessageService;

    @Autowired
    @Qualifier("submailSerivce")
    private ISendMsgService submailMessageService;

    @Autowired
    @Qualifier("infobipService")
    private ISendMsgService infobipService;

    @Autowired
    @Qualifier("tianyihongService")
    private ISendMsgService tianyihongService;

    @Autowired
    @Qualifier("paasooService")
    private ISendMsgService paasooService;


    @Autowired
    @Qualifier("yimeiruantongService")
    private ISendMsgService yimeiruantongService;


    @Autowired
    @Qualifier("KMIService")
    private ISendMsgService KMIService;




//    @Autowired
//    private Map<String,ISendMsgService> stringISendMsgServiceMap;

    @Autowired
    private InfobipServiceImpl infobipServiceImpl;

    @Value("${kmi.account}")
    private String kmiAccount;

    @Value("${kmi.pwd}")
    private String kmiPwd;

    @Value("${kmi.token.url}")
    private String KmiTokenUrl;

    @Value("${kmi.send.msg.otp.url}")
    private String kmiSendOtpMsgUrl;

    private final String TOKEN_KEY = "KMI_TOKEN";
    //private final long VALID_TIME = 60 * 60 * 1000 * 2;

    private Context context = new Context();

    private final String myPhoneNumber = "8613777400292";
    private final String zzhPhoneNumber = "8613937368224";
    private final String ycgPhoneNumber = "8613989801412";
    private final String zccPhoneNumber = "8615268576785";

    //印尼号码
    private final String inaPhoneNumber_1 = "85211728995";
    private final String inaPhoneNumber_2 = "87877664066";
    private final String inaPhoneNumber_3 = "81905091102";
    private final String inaPhoneNumber_4 = "81818777085";//liuzongkui_1
    private final String inaPhoneNumber_5 = "87784736868";//liuzongkui_2

    private final String inaPhoneNumber_6 = "6281285290562";//自己这的 印尼测试手机号


    //測試KMI

    @RequestMapping("/kmitest")
   public void testKmi(){
        KMIService.sendInternationalMsg(ApplicationEnmu.VI_CASH_DOG,
                "6213777400262",
                "test sms",
                ContextUtils.getDefaultContext());
   }


    @RequestMapping("/fcm")
    public String fcm(){
        String postUrl = "https://fcm.googleapis.com/v1/projects/myproject-b5ae1/messages:send";



        return "success";
    }


    @RequestMapping("/sms222")
    public String sms222(){
        String url= "https://203.166.197.162/ApiLongNumber/receive.php";
        String uid = "userHorizon";
        String owd="Q21u1h3oq";

        String phoneNumber = inaPhoneNumber_6;
        String sendText = "Your verification code is 6789";
        try {
            sendText = URLEncoder.encode(sendText,"UTF-8");
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return e.getMessage();
        }

        String requestUrl = url +"?uid="+uid+"&pwd="+owd+"&serviceid=1000&msisdn="+phoneNumber+"&sms="+sendText+"&transid=1234567&smstype=0&sc=ABC";

        String res = HttpUtil.get(requestUrl);
        System.out.println("res = " + res);
        return res;
    }

    @RequestMapping("/sms333")
    public String sms333(){
        //todo https
        try {
            String url= "https://203.166.197.162/ApiLongNumber/receive.php";
            String uid = "userHorizon";
            String owd="Q21u1h3oq";

            String phoneNumber = inaPhoneNumber_6;
            String sendText = "Your verification code is 9999";

            String requestParam = "?uid="+uid+"&pwd="+owd+"&serviceid=1000&msisdn="+phoneNumber+"&sms="+sendText+"&transid=1234567&smstype=0&sc=ABC";


            requestParam = URLEncoder.encode(requestParam,"UTF-8");

//        String res = HttpUtil.get(url + requestParam);
//            String res = HttpUtil.post(url ,requestParam);

            String res = HttpsGet.httpsRequest(url+requestParam,"POST",null);
            System.out.println("res = " + res);
            return res;
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return e.getMessage();
        }
    }


    //todo  没有详细文档，没请求通
//    @RequestMapping("/sms")
    public String sms(){

        String  url = "https://sms.net1.co.id/sendsms/otp";
        String phoneNumber = "";


        SMSSendBO smsSendBO = new SMSSendBO();
        smsSendBO.setFrom("Shen JK");
        smsSendBO.setTo(phoneNumber);
        smsSendBO.setBody("Gunakan 570266 sebagai kode verifikasi nomor telepon Anda untuk login");

        String requestParam = com.alibaba.fastjson.JSON.toJSONString(smsSendBO);


        //infobip API 约定的请求头参数
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "WS19-Mobi-C001");
        map.put("Content-Type", "application/json");
        map.put("Content-Length", "168");

        String sendRes = "";
        try {
             sendRes = HttpUtils.postGeneralUrl(
                            url,
                            "application/json",
                            requestParam,
                            "UTF-8",
                            map);
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            System.err.println("e.getMessage(),e = " + e.getMessage());
        }

        return sendRes;
    }


    private final long  VALID_TIME = 60 * 1000;
    @RequestMapping("/put")
    public String put(){
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        System.out.println("sdf.format(now) = " + sdf.format(now));
        MsgUtils.expiryMap.put("123","666 啊",VALID_TIME);
        return "put";
    }

    @RequestMapping("/get")
    public String get(){
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        System.out.println("sdf.format(now) = " + sdf.format(now));
        Object obj = MsgUtils.expiryMap.get("123");

        System.out.println("j = " + (obj == null));

        return String.valueOf(obj);
    }


    private String getToken() throws Exception{
        Object tokenObj = MsgUtils.expiryMap.get(TOKEN_KEY);
        String token = null;
        if(tokenObj == null){
            String pwd = MD5Util.MD5(kmiPwd);
            KmiTokenUrl = KmiTokenUrl + pwd;
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
                            if(StringUtils.isNotBlank(token)){
                                return token;
                            }
                        }
                    }
                }
            }else {
                //重试
                boolean flag = true;
                int num = 0;
                //重试
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

//    @RequestMapping("/kmisend")
    public String sendKmiMsg(){
        try {
            String token = getToken();
//            Map<String, Object> map = new HashMap<>();
//            map.put("token",token);
//            map.put("sendType",1);//发送类型 1.LongNumber
//            map.put("msisdn","62"+inaPhoneNumber_1);//短信接收方号码，格式为62xxxx如：6281210506807 （只支持印尼号码）
//            map.put("message","hello cashdog");//短信内容
//            String res = HttpUtil.post(kmiSendMsgUrl, map);

            com.alibaba.fastjson.JSONObject jsonObj = new com.alibaba.fastjson.JSONObject();
            jsonObj.put("token", token);
            jsonObj.put("from", "AFT");
            jsonObj.put("to", "62"+inaPhoneNumber_5);
            String messagetest = "Anda mendaftar cashKangaroo, kode verifikasi adalah 7777777. [cashKangaroo]";
            jsonObj.put("message", messagetest);
            String params = jsonObj.toString();
            String res = HttpUtil.post(kmiSendOtpMsgUrl, params);
            //{"result":{"code":-113,"desc":"SMS Gateway Error"}}
            //{"data":{"trxid":"15622242966551293","trxdate":"20190704151136"},"result":{"code":0,"desc":"SUCCESS"}}
            return res;
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
        }
        return "失败";
    }




    //// TODO: 2019/6/14 shenjiankang  測試天一弘發送
//    @RequestMapping("/shentianyihong")
    public void testSend_tian_yi_hong(){
        //tianyihong
        //您这边测试的话带senderID:VietID   ; 没有ID可能发不出去
        //您的访问码是，，，，有效时间是，，  ;建议第一个和第三个,因为说OTP用户可能不知道
        String text1 = "Mã xác minh của bạn là 1111, mã xác minh này chỉ có hiệu lực trong 5 phút senderID:VietID";
//        String text2 = "Mã OTP của bạn là 2222, mã OTP này có hiệu lực trong ... phútMã OTP của bạn là ..., mã OTP này có hiệu lực trong ... phút senderID:VietID";
        String text2 = "Mã OTP của bạn là 2222, mã OTP này có hiệu lực trong 5 phút senderID:VietID";
        String text3 = "Mã xác thực của bạn là 3333, mã xác thực này có hiệu lực trong 5 phút senderID:VietID";
        String text4 = "Mã xác minh của bạn là 4444, mã xác minh này chỉ có hiệu lực trong 5 phút ID:VietID";
        String text5 = "Mã xác minh của bạn là 5555, mã xác minh này chỉ có hiệu lực trong 5 phút \n" +
                "ID:VietID";

        String text6 = "Mã xác thực của bạn là 6666, mã xác thực này có hiệu lực trong 5 phút";
        String text7 = "Mã xác thực của bạn là 7777, mã xác thực này có hiệu lực trong 5 phút";
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        String phoneNumber = "0789635440";
        String phoneNumber = "84789635440";
        String phoneNumber1 = "84586942918";
//        boolean subRes3 = tianyihongService.sendInternationalMsg(ApplicationEnmu.VI_CASH_DOG,
//                phoneNumber,
//                text6,
//                context);

        boolean subRes4 = tianyihongService.sendInternationalMsg(ApplicationEnmu.VI_CASH_DOG,
                phoneNumber1,
                text7,
                context);
//        System.out.println("tianyiHong发送结果： " + text1+"RES: "+subRes3 );
//        System.out.println("===============================================");
    }



    //测试如果没有对应的国际化模板 ，获取默认模板
//    @RequestMapping("/test6")
    public void test6(String templateCode,String language,String country){
        Context context = new Context();
//        context.setLocale(new Locale(language,country));
        context.setLocale(new Locale("zh","CN"));
        templateCode="201807240353333890001";
        List<String> list = new ArrayList<>();
        list.add("6666");
        infobipService.sendInternationalMsg(ApplicationEnmu.WU_YOU_DAI,
                "525574991875",
                templateCode,
                list,
                context);
    }


    //测试根据msgid获取infobip的发送信息
//    @RequestMapping("/getInfobipSendInfo")
    public String getInfobipSendInfo(String msgId){
        try {
            return infobipServiceImpl.getMsgSendInfo(msgId,"",1,ContextUtils.getDefaultContext());
        }catch (Exception e){
            return "异常了";
        }

    }

    @RequestMapping("/testtest1")
    public String terst1(){
        LOGGER.info("请求message成功了6");
      return "message 短信服务已经成功启动：success";
    }

//    @RequestMapping("/infobip")
    public void infobipSend(String phoneNumber,String msg){
        //巴西: 5511983525890
        //String requestParam = createRequestContent("8613777400292","欢迎使用悠中港！您的验证码是1241");

//        MsgTemplateCodeEnum msgTemplateCodeEnum = MsgTemplateCodeEnum.LOGIN_VERIFY;
        List<String> paramList = new ArrayList<>();
        //随机验证码
        paramList.add(generateRandomNumber(""));
        context.setLocale(new Locale("pt", "BR"));
        //infobipService
        infobipService.sendInternationalMsg(ApplicationEnmu.PAN_GUAN_JIA,
                                         phoneNumber,
                                            "201807130238138540004",
                                            paramList,
                                          context);
    }

//    @RequestMapping("/batchDiffTemplate")
    public void batchDiffTemplate(){
        Context context = new Context();
//        context.setLocale(new Locale(language,country));
        context.setLocale(new Locale("zh","CN"));

        String code = "";

        List<BatchSendDiffTemplateParamBO> list = new ArrayList<>();
        BatchSendDiffTemplateParamBO BO = new BatchSendDiffTemplateParamBO();
        List zlist = new ArrayList();
        BO.setPhoneNumber("8613777400292");
        //尊敬的用户，您好！您有一笔借款逾期，请尽快还款。【MoneyRush】
        BO.setTemplateCode("201807240414518250003");

        BatchSendDiffTemplateParamBO BO1 = new BatchSendDiffTemplateParamBO();
        List zlist1 = new ArrayList();
        BO1.setPhoneNumber("8613989801412");//ycg
        //尊敬的用户，您好！moneyrush已成功放款到您的借款账户 %s，请注意查收。【MoneyRush】
        BO1.setTemplateCode("201807240359082610002");
        zlist1.add("ycg的账户");
        BO1.setParamList(zlist1);

        list.add(BO);
        list.add(BO1);

        infobipServiceImpl.batchSendDiffTemplate(ApplicationEnmu.WU_YOU_DAI,list,context);

    }





    /**
     *  四大运营商目前所有的号码
     *
     * Claro:
     * + 5511993367723
     * +5511990150742
     *
     * oi:
     * +5511940410157
     *
     * tim:
     * +5511983525890
     *
     * vivo:
     * +5511942884159
     *
     */





    @RequestMapping("send1")
    public void sendIntMsgSubmailAndChuanglan(){
        List<String> phoneNumberList = new ArrayList<>();

        //墨西哥测试
        phoneNumberList.add("525574991875");
        phoneNumberList.add("525587243525");
//        phoneNumberList.add("525574199391");//暂时不发
        phoneNumberList.add("525546801122");
        phoneNumberList.add("525512694313");//暂时不发  0856新加
        phoneNumberList.add("525587236238");
        phoneNumberList.add("525587234988");
//        phoneNumberList.add("525528651230");

        phoneNumberList.add("525561032536");

//        phoneNumberList.add("8613777400292");

        //
        String msg = "Estimado señor,ra, el PIN es %s.[eLoan]";

        String msgText = "";
        for(int k=0;k<phoneNumberList.size();k++) {
            String phoneNumber = phoneNumberList.get(k);


            for (int i = 0; i < 5; i++) {
                //验证码
                String randomNumber = generateRandomNumber("C");
                try {
                    Thread.sleep(70000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



                //创蓝
                msgText = String.format(msg,randomNumber);
                boolean res = chuanglanMessageService.sendInternationalMsg(ApplicationEnmu.WU_YOU_DAI,
                        phoneNumber,
                        msgText,
                        context);
                System.out.println("创蓝发送结果： " + msgText+"RES: " +res);
                System.out.println("----------------------------------------------");


//                //submail
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                boolean subRes = submailMessageService.sendInternationalMsg(ApplicationEnmu.WU_YOU_DAI,
//                        phoneNumber,
//                        msg + randomNumber + "S",
//                        context);
//                System.out.println("submail发送结果" + subRes);
//                System.out.println("===============================================");
//
                //infobip
                randomNumber = generateRandomNumber("I");
                msgText = String.format(msg,randomNumber);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean subRes1 = infobipService.sendInternationalMsg(ApplicationEnmu.WU_YOU_DAI,
                        phoneNumber,
                        msgText,
                        context);
                System.out.println("INFOBIP发送结果： " + msgText+"RES: "+subRes1 );
                System.out.println("===============================================");


                //tianyihong
                randomNumber = generateRandomNumber("T");
                msgText = String.format(msg,randomNumber);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean subRes3 = tianyihongService.sendInternationalMsg(ApplicationEnmu.WU_YOU_DAI,
                        phoneNumber,
                        msgText,
                        context);
                System.out.println("tianyiHong发送结果： " + msgText+"RES: "+subRes3 );
                System.out.println("===============================================");


            }

//            for (int i = 0; i < 5; i++){
//                //验证码
//                String randomNumber = generateRandomNumber("S");
//                try {
//                    Thread.sleep(70000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                //submail
//                boolean subRes = submailMessageService.sendInternationalMsg(ApplicationEnmu.PAN_GUAN_JIA,
//                        phoneNumber,
//                        msg + randomNumber + "S",
//                        context);
//                System.out.println("submail发送结果" + subRes);
//                System.out.println("===============================================");
//            }
//
//            for (int i = 0; i < 5; i++){
//                //验证码
//                String randomNumber = generateRandomNumber("S");
//                try {
//                    Thread.sleep(70000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                //submail
//                boolean subRes = infobipService.sendInternationalMsg(ApplicationEnmu.PAN_GUAN_JIA,
//                        phoneNumber,
//                        msg + randomNumber + "I",
//                        context);
//                System.out.println("infobip发送结果" + subRes);
//                System.out.println("===============================================");
//            }


        }
        System.out.println("发送完成。。。。");
    }


//    @RequestMapping("send2")
    public void sendIntMsgChuanglan(@RequestParam String phoneNumber) {
        System.out.println("进来了1====,手机号码为[{}]：" + phoneNumber);
        //String phoneNumber = "5511993367723";
        String msg = "[盘管家]您的验证码是：11112";
        boolean res = chuanglanMessageService.sendInternationalMsg(ApplicationEnmu.PAN_GUAN_JIA,
                phoneNumber,
                msg+"C",
                context);
        System.out.println("创蓝res----------------:    "+res);

//        for (int i = 0; i < 1; i++) {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("第 " + i + " 次发送开始。。。。");
//            boolean subRes = submailMessageService.sendInternationalMsg(ApplicationEnmu.PAN_GUAN_JIA,
//                    phoneNumber,
//                    msg + "S",
//                    context);
//            System.out.println("submail发送结果" + subRes);
//            System.out.println("===============================================");
//        }
    }


//todo 发送手机号测试  tianyihongService
    @RequestMapping("test3")
    public BasicResult<Void> test3(String phoneNumber){
//        context.setLocale(Locale.CHINA);
        context.setLocale(new Locale("es","MX"));
        //String phoneNumber = "8613777400292";
        List<String> paramList = new ArrayList<>();
        paramList.add("3333");//验证码
        boolean res = tianyihongService.sendInternationalMsg(ApplicationEnmu.PAN_GUAN_JIA,
                phoneNumber,
                "201807240342551900001",
                paramList,
                context);
        return BasicResult.ok();
    }


    //双渠道 测试同一个手机号码每天0点前的发送次数是否超限
//    @RequestMapping("test4")
    public void test4(Integer type,String phoneNumber){
        context.setLocale(Locale.CHINA);
//        String phoneNumber = "13777400292";
        List<String> paramList = new ArrayList<>();
        paramList.add("1121");//验证码
        if(type==1){
            chuanglanMessageService.sendInternationalMsg(ApplicationEnmu.PAN_GUAN_JIA,
                    phoneNumber,
                    "201807130238138540004",
                    paramList,
                    context);
        }else {
            submailMessageService.sendInternationalMsg(ApplicationEnmu.PAN_GUAN_JIA,
                    phoneNumber,
                    "201807130238138540004",
                    paramList,
                    context);
        }
    }

//    @RequestMapping("test5")
    public void sendIntMsgInfobip1(String mobile){
        List<String> phoneNumberList = new ArrayList<>();

//        phoneNumberList.add("5511993367723");
//        phoneNumberList.add("5511990150742");
//        phoneNumberList.add("5511940410157");
//        phoneNumberList.add("5511983525890");
//        phoneNumberList.add("5511942884159");
//        phoneNumberList.add("5511983525890");
        phoneNumberList.add("8613777400292");
        phoneNumberList.add("8618557538128");

        String msg = "【盘管家】您的验证码是：";

        System.out.println("----------------------开始循环发送------------------------");
        for(int k=0;k<phoneNumberList.size();k++) {
            String phoneNumber = phoneNumberList.get(k);


            for (int i = 0; i < 2; i++) {
                //验证码
                String randomNumber = generateRandomNumber("I");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //infobip
                boolean res = infobipService.sendInternationalMsg(ApplicationEnmu.PAN_GUAN_JIA,
                        phoneNumber,
                        msg + randomNumber + "I",
                        context);
                System.out.println("INFOBIP发送结果： " + res);
                System.out.println("----------------------------------------------");
            }
        }
    }

//    @RequestMapping("sendIntMsgInfobip")
    public void sendIntMsgInfobip(String mobile){
        List<String> phoneNumberList = new ArrayList<>();

        phoneNumberList.add("5511993367723");
        phoneNumberList.add("5511990150742");
        phoneNumberList.add("5511940410157");
        phoneNumberList.add("5511983525890");
        phoneNumberList.add("5511942884159");
//        phoneNumberList.add("5511983525890");

//        String msg = "【盘管家】您的验证码是：";
        List<String> list = new ArrayList<>();
        list.add("I9999");

        System.out.println("----------------------开始循环发送------------------------");
        //todo 墨西哥

        context.setLocale(new Locale("es", "MX"));
        for(int k=0;k<phoneNumberList.size();k++) {
            String phoneNumber = phoneNumberList.get(k);


            for (int i = 0; i < 1; i++) {
                //验证码
                String randomNumber = generateRandomNumber("I");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //infobip
                boolean res = infobipService.sendInternationalMsg(ApplicationEnmu.PAN_GUAN_JIA,
                        phoneNumber,
                        "201807240342551900001",
                        list,
                        context);
                System.out.println("INFOBIP发送结果： " + res);
                System.out.println("----------------------------------------------");
            }
        }
    }

    //测试 expiryMap 时效性
//    public static void main(String[] args){
//        MsgUtils.expiryMap.put("sjk","sjk",2000);
//        System.out.println(MsgUtils.expiryMap.get("sjk"));
//        try {
//            Thread.sleep(1000);
//            System.out.println("1秒后： "+MsgUtils.expiryMap.get("sjk"));
//
//            Thread.sleep(998);
//            System.out.println("998s后： "+MsgUtils.expiryMap.get("sjk"));
//
//            Thread.sleep(1);
//            System.out.println("999s后： "+MsgUtils.expiryMap.get("sjk"));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }





    /**
     * 生成4位的随机数
     */
    private String generateRandomNumber(String s) {
        int radomInt = (int) ((Math.random() * 9 + 1) * 1000);
//            System.out.println("4位随机数："+radomInt);
        return s + String.valueOf(radomInt);
    }

//    @RequestMapping("test333")
    public void test333(){
        Context context = new Context();
        context.setLocale(new Locale("zh","CN"));

        List<String> list = new ArrayList<>();
        list.add("8613989801412");//ycg
        list.add("8613777400292");

        //尊敬的用户，您好！moneyrush已成功放款到您的借款账户 %s，请注意查收。【MoneyRush】
        String templateCode = "201807240359082610002";

        List paramList = new ArrayList();
        paramList.add("5018-8-6");
        chuanglanMessageService.batchSendSameTemplateSameParam(ApplicationEnmu.WU_YOU_DAI,list,templateCode,paramList,context);
    }


    //@RequestMapping("test555")
    public void test5(){

        Context context = new Context();
        context.setLocale(new Locale("zh","CN"));

        String mobile ="8615268576785";//zcc

        chuanglanMessageService.sendInternationalMsg(ApplicationEnmu.WU_YOU_DAI,
                mobile,
                "【盘管家】您的验证码是：4737C",
                context);
    }

    //測試infobip 不同模板群發
//    @RequestMapping("infobipBatchSend")
    public void infobipBatchSend(){
        Context context = new Context();
        context.setLocale(new Locale("zh","CN"));

        List<BatchSendDiffTemplateParamBO> list = new ArrayList<>();
        BatchSendDiffTemplateParamBO batchSendDiffTemplateParamBO1 = new BatchSendDiffTemplateParamBO();
        batchSendDiffTemplateParamBO1.setPhoneNumber("8613777400292");
        batchSendDiffTemplateParamBO1.setTemplateCode("201807240342551900001");//您好，您的验证码为%s。【MoneyRush】
        List slist = new ArrayList();
        slist.add("6699");
        batchSendDiffTemplateParamBO1.setParamList(slist);

        BatchSendDiffTemplateParamBO batchSendDiffTemplateParamBO2 = new BatchSendDiffTemplateParamBO();
        batchSendDiffTemplateParamBO2.setPhoneNumber(zzhPhoneNumber);
        batchSendDiffTemplateParamBO2.setTemplateCode("201807240357267080002");//尊敬的用户，您好！您已通过moneyrush借款审核，请尽快提现。【MoneyRush】
        List slist1 = new ArrayList();
        batchSendDiffTemplateParamBO2.setParamList(slist1);

        list.add(batchSendDiffTemplateParamBO1);
        list.add(batchSendDiffTemplateParamBO2);

        messageFacade.batchSendDiffTemplate(ApplicationEnmu.WU_YOU_DAI,list,context);
    }

    //同模同参
//    @RequestMapping("batchSendSTSP")
    public void batchSendSTSP(){
        Context context = new Context();
        context.setLocale(new Locale("zh","CN"));

        List phoneNumberList = new ArrayList();
        phoneNumberList.add("8613777400292");
        phoneNumberList.add("8613937368224");//zzh

        ////您好，您的验证码为%s。【MoneyRush】
        String templateCode = "201807240342551900001";

        List paramList = new ArrayList();
        paramList.add("666");

        messageFacade.batchSendSameTemplateSameParam(ApplicationEnmu.WU_YOU_DAI,
                                                phoneNumberList,
                templateCode,
                paramList,
                context);

    }


    //同模不同参
//    @RequestMapping("batchSendSTDP")
    public void batchSendSTDP(){
        context.setLocale(new Locale("zh","CN"));

        Map<String,List> paramMap = new HashMap<>();
        List list1 = new ArrayList();
        list1.add("111");
        paramMap.put("8613777400292",list1);

        List list2 = new ArrayList();
        list2.add("222");
        paramMap.put(zzhPhoneNumber,list2);

        ////您好，您的验证码为%s。【MoneyRush】
        String templateCode = "201807240342551900001";
        messageFacade.batchSendSameTemplateDiffParam(ApplicationEnmu.WU_YOU_DAI,
                                                paramMap,
                                                templateCode,
                                                context);
    }


    //todo 短信轮换发送机制
//    @RequestMapping("test111")
//    public void test111(){
//        infobipService.test111();
//    }
//    @RequestMapping("test222")
//    public void test222(){
//        infobipService.test222();
//    }
//    @RequestMapping("test3333")
//    public void test3333(){
//        infobipService.test333();
//    }


    //todo  轮换
    @RequestMapping("test666")
    public void trest666(String phoneNumber){
        context.setLocale(new Locale("zh","CN"));
        List list = new ArrayList();
        list.add("222");
        messageFacade.sendInternationalMsg(ApplicationEnmu.WU_YOU_DAI,
                phoneNumber,"201807240342551900001",list,context);
//        tianyihongService.sendInternationalMsg(ApplicationEnmu.WU_YOU_DAI,
//                phoneNumber,"201807240342551900001",list,context);
    }


    //todo  测试巴西 Eloan
    @RequestMapping("send2")
    public void sendBr() {
        List<String> phoneNumberList = new ArrayList<>();

        //baxi 巴西测试
//        phoneNumberList.add("5511950872638");
//        phoneNumberList.add("5511993367723");
//        phoneNumberList.add("5511999137255");
//        phoneNumberList.add("5511986932432");
//        phoneNumberList.add("5511984724647");

//        phoneNumberList.add("8613777400292");
//  08.27
//        phoneNumberList.add("5511950872638");
//        phoneNumberList.add("5511993367723");
//        phoneNumberList.add("5511940410157");
//        phoneNumberList.add("5511942884159");
//        0912
        phoneNumberList.add("5511950872638");
        phoneNumberList.add("5511993367723");
        phoneNumberList.add("5511940410157");
        phoneNumberList.add("5511942884159");
        phoneNumberList.add("5511990150742");
        phoneNumberList.add("5511983525890");

        //O seu código de verificação da operação é %s. [eLoan]
        String msg = "O seu código de verificação do cadastro é %s.[eLoan]";
        String msgText = "";
        for (int k = 0; k < phoneNumberList.size(); k++) {
            String phoneNumber = phoneNumberList.get(k);


            for (int i = 0; i < 3; i++) {
                //验证码
                String randomNumber = generateRandomNumber("C");
                try {
                    Thread.sleep(70000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                //创蓝
                msgText = String.format(msg, randomNumber);
                boolean res = chuanglanMessageService.sendInternationalMsg(ApplicationEnmu.BR_Eloan,
                        phoneNumber,
                        msgText,
                        context);
                System.out.println("创蓝发送结果： " + res);
                System.out.println("----------------------------------------------");


//                //submail
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                randomNumber = generateRandomNumber("S");
                msgText = String.format(msg, randomNumber);
                boolean subRes = submailMessageService.sendInternationalMsg(ApplicationEnmu.BR_Eloan,
                        phoneNumber,
                        msgText,
                        context);
                System.out.println("submail发送结果" + subRes);
                System.out.println("===============================================");

                //infobip
                randomNumber = generateRandomNumber("I");
                msgText = String.format(msg, randomNumber);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean subRes1 = infobipService.sendInternationalMsg(ApplicationEnmu.BR_Eloan,
                        phoneNumber,
                        msgText,
                        context);
                System.out.println("===============================================");


                //tianyihong
//                msgText = String.format(msg, "3333T");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                boolean subRes3 = tianyihongService.sendInternationalMsg(ApplicationEnmu.BR_Eloan,
//                        phoneNumber,
//                        msgText,
//                        context);
//
//                System.out.println("===============================================");


            }

        }
        System.out.println("发送完毕。。。。。。。。。。。。。。。。。。。。。。。。");
    }





    @RequestMapping("paasoo")
    public void sendPaasoo(String mobile){

        String phoneNumber = "861";
        //国内测试指定的 短信模板
        String sendText = "【无限云】您的验证码为：123456，请在一分钟内使用，谢谢！";
//        paasooService.sendInternationalMsg(ApplicationEnmu.WU_YOU_DAI,mobile,sendText, null);
        List list = new ArrayList();
        list.add("6661");

        //todo  测试轮换发送
        messageFacade.sendInternationalMsg(ApplicationEnmu.WU_YOU_DAI,mobile,"201807240353333890001",list, ContextUtils.getMexicoContext());
    }

    //yimeiruantongService
    @RequestMapping("yimeiruantong")
    public void sendYimeiruantong(String mobile){
        String phoneNumber = "861";
        String sendText = "【盘石】您的验证码为：123456";
        yimeiruantongService.sendInternationalMsg(ApplicationEnmu.WU_YOU_DAI,mobile,sendText, ContextUtils.getMexicoContext());
        List list = new ArrayList();
        list.add("6661");

        //todo  测试轮换发送
//        messageFacade.sendInternationalMsg(ApplicationEnmu.WU_YOU_DAI,mobile,"201807240353333890001",list, ContextUtils.getMexicoContext());
    }
}
