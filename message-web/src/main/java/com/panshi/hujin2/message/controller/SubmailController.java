package com.panshi.hujin2.message.controller;

import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.facade.bo.SubmailMsgSubhookInputBO;
import com.panshi.hujin2.message.service.message.IMsgDBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * create by shenjiankang on 2018/6/21 19:
 * <p>
 * SUBMAIL SUBHOOK是邮件和短信 API 事件推送通知接口
 */

@RestController
@RequestMapping("submail")
public class SubmailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubmailController.class);

    @Value("${submail.subhook.secret.key1}")
    private String subhookSecretKey1;

    @Autowired
    private IMsgDBService msgDBService;


    //todo sdk要不要换成代码
    //todo 测试回调
    //todo 请求地址换成美国api
    //todo 考虑chuanglan和submail的短信发送记录表 要不要拆分开


    @RequestMapping("intMsgCallBack")
    public void messageCallBack(HttpServletRequest request) {

        LOGGER.info("--------SUBMAIL SUBHOOK事件推送--------");

        //token 参数是一个 32 位的随机字符串
        String token = request.getParameter("token");
        //signature 参数是 SUBHOOK 创建的唯一数字签名。
        String signature = request.getParameter("signature");

        //将获得的 token 值和你的 SUBHOOK 密匙拼接成字符串（即 token+key），随后使用 MD5 创建此签名，并与 signature 参数进行比对。

        //request
        String event = request.getParameter("events");
        //address： 此联系人的手机号码
        String address = request.getParameter("address");
        //app：应用 ID
        String app = request.getParameter("app");
        //send_id：该条短信的唯一发送标识，可在 API 请求时获取
        String sendId = request.getParameter("send_id");
        //timestamp：事件触发时间（此时间戳为此事件本身的触发时间，不参与计算签名）
        String timestamp = request.getParameter("timestamp");

        //网关失败回执
        String report = request.getParameter("report");
        //短信内容
        String content = request.getParameter("content");
        //模板ID
        String templateId = request.getParameter("template_id");
        //模板审核未通过原因
        String reason = request.getParameter("reason");

        LOGGER.info("======== event : [{}]", event);
        LOGGER.info("======== address: [{}]", address);
        LOGGER.info("======== app: [{}]", app);
        LOGGER.info("======== sendId: [{}]", sendId);
        LOGGER.info("======== timestamp: [{}]", timestamp);
        LOGGER.info("======== report: [{}]", report);
        LOGGER.info("======== content: [{}]", content);
        LOGGER.info("======== templateId: [{}]", templateId);
        LOGGER.info("======== reason: [{}]", reason);

        try {
            String verifyStr = getMd5(token, subhookSecretKey1);

            LOGGER.info("======== verifyStr: [{}]", verifyStr);
            LOGGER.info("======== signature: [{}]", signature);

            if (verifyStr != null && verifyStr.equals(signature)) {
                //验证通过
                LOGGER.info("======== SUBHOOK 回调验证通过");

                SubmailMsgSubhookInputBO inputBO = new SubmailMsgSubhookInputBO();
                inputBO.setEvents(event);
                inputBO.setPhoneNumber(address);
                inputBO.setApp(app);
                inputBO.setSendId(sendId);
                inputBO.setEventTimestamp(Integer.valueOf(timestamp));
                inputBO.setReport(report);
                inputBO.setContent(content);
                inputBO.setTemplateId(templateId);
                inputBO.setReason(reason);

                Context context = new Context();
                context.setLocale(Locale.CHINA);

                int count = msgDBService.addSubmailMsgSubhook(inputBO, context);

                LOGGER.info("SUBHOOK状态推送，[{}]条记录入库", count);
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            LOGGER.info("SUBMAIL国际短信SUBHOOK回调异常！");
        }

    }


//    @ResponseBody
//    @RequestMapping(value = "/message",method = RequestMethod.POST)
//    public  String messageSubhook( MultipartHttpServletRequest MultiRequest){
//        DefaultMultipartHttpServletRequest defaultRequest = (DefaultMultipartHttpServletRequest) MultiRequest;
//        MultiValueMap fileMap = defaultRequest.getMultiFileMap();
//
//        Map<String, String[]> params = defaultRequest.getParameterMap();
//        JSONObject json = new JSONObject();
//        JSONObject result = new JSONObject();
//        //将jarams转换成json
//        for (Map.Entry<String, String[]> entry : params.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue()[0];
//            json.put(key, value);
//        }
//        System.out.println("json:"+json.toString());
//
//        //保存文件的路径
//        String url=MultiRequest.getSession().getServletContext().getRealPath("/");
//        try {
//            //加密后的字符串
//            String newstr=Md5.getMd5(json.getString("token"),Config.messageConfig());
//            System.out.println("md5"+newstr);
//            //签名判断
//            if(!json.get("signature").equals(newstr)){
//                FileLoad.fileLoad(url, "err:wrong signture");
//                return "err:wrong signture";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            FileLoad.fileLoad(url, "abnormal data");
//            return "abnormal data";
//        }
//
//        String event=(String) json.get("events");
//        Message message = (Message) JSONObject.toBean(json, Message.class);
//        switch(event){
//            case "request":
//                //返回请求结果
//                result.put("status", "send success");
//                result.put("message", message);
//                FileLoad.fileLoad(url, result.toString());
//                return result.toString();
//
//            case "delivered":
//                //返回请求结果
//                result.put("status", "send success");
//                result.put("message", message);
//                FileLoad.fileLoad(url, result.toString());
//                return result.toString();
//
//            case "dropped":
//                //返回请求结果
//                result.put("status", "send fail");
//                result.put("message", message);
//                FileLoad.fileLoad(url, result.toString());
//                return result.toString();
//
//            case "sending":
//                //返回请求结果
//                result.put("status", "is sending");
//                result.put("message", message);
//                FileLoad.fileLoad(url, result.toString());
//                return result.toString();
//
//            case "mo":
//                //返回请求结果
//                result.put("status", "mo");
//                result.put("message", message);
//                FileLoad.fileLoad(url, result.toString());
//                return result.toString();
//
//            case "unkown":
//                //返回请求结果
//                result.put("status", "unkown");
//                result.put("message", message);
//                FileLoad.fileLoad(url, result.toString());
//                return result.toString();
//        }
//        FileLoad.fileLoad(url, "accept failure");
//        return "accept failure";
//    }

    private String getMd5(String token, String key) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String str = token + key;
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update((str).getBytes("UTF-8"));
        byte b[] = md5.digest();

        int i;
        StringBuffer buf = new StringBuffer("");

        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0) {
                i += 256;
            }
            if (i < 16) {
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));
        }

        String result = buf.toString();

        return result;
    }
}
