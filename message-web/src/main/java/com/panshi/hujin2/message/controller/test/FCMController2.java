package com.panshi.hujin2.message.controller.test;

import com.alibaba.fastjson.JSONObject;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.panshi.hujin2.message.common.utils.HttpUtil;
import com.panshi.hujin2.message.service.message.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shenJianKang
 * @date 2019/8/23 9:36
 */
@Slf4j
@RestController
@RequestMapping("fcm/v2")
public class FCMController2 {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

//    @Autowired
//    private GetSetCacheService getSetCacheService;
//
//    @Autowired
//    private HttpAskInterface httpAskInterface;

    //todo 在linux环境中配置export GOOGLE_APPLICATION_CREDENTIALS="/home/user/Downloads/service-account-file.json"
    //todo 隐式的指向秘钥

    @Value("${fcm.request.json.path}")
    private String fcmRequestJsonPath;
    String postUrl = "https://fcm.googleapis.com/v1/projects/myproject-b5ae1/messages:send";

    String clientToken = "fTynYFtXRUI:APA91bGMfdZkpnAbJnRa5iOv4zWh_VcDcd6pCc4Gx8w2KSK7DgzpBfD245hQHp_pa-F0jNGXxV3qievbp1ct6iEc4LX0D4_KGMo6jnA3exbbjp-5kogFS8o8TpA8p7LRPJi9BOQCjTtx";
    String SCOPES = "https://www.googleapis.com/auth/firebase.messaging";


    @RequestMapping("/aaa")
    public void  fcm1(){
        try {
            push(clientToken, "sjk_title","sjk_body","0",null,null);    
        }catch (Exception e){
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        
    }

    public void send(){
        String BASE_URL = "https://fcm.googleapis.com/v1/projects/";
        String FCM_SEND_ENDPOINT = "";

//        URL url = new URL(BASE_URL + FCM_SEND_ENDPOINT);
//        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//        httpURLConnection.setRequestProperty("Authorization", "Bearer " + getAccessToken());
//        httpURLConnection.setRequestProperty("Content-Type", "application/json; UTF-8");
//        return httpURLConnection;
    }

    /**
     * 消息推送，推送的消息用于提示，并且点击提示消息将会跳转链接至指定页面
     * A: Captain&D
     * W: https://www.cnblogs.com/captainad/
     * @param deviceToken
     * @param title
     * @param body
     * @param route
     * @throws Exception
     */
//    @Async
    public void push(final String deviceToken, final String title, final String body,
                     final String route, final Integer dataType, final String dataMsg)
            throws Exception {
        log.info("[START]开始推送FCM消息");
        // 请求标头
        Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("Content-Type", "application/json; UTF-8");
//        requestHeader.put("Authorization", "Bearer " + getAccessToken());
        requestHeader.put("Authorization", "Bearer " + getToken());

        // 请求体
        JSONObject json = new JSONObject();

        JSONObject message = new JSONObject();
        message.put("token", deviceToken);
        JSONObject jsonObject = new JSONObject();

        // 发送弹窗提示信息
        if(!StringUtils.isEmpty(title) && !StringUtils.isEmpty(body)) {
            JSONObject notification = new JSONObject();
            notification.put("title", title);
            notification.put("body", body);
            message.put("notification", notification);

            jsonObject.put("route", route);
            // flag: 0-无需跳转，1-需要跳转
            jsonObject.put("routeFlag", StringUtils.isEmpty(route) ? "0" : "1");
        }

        // 发送数据
        if(!StringUtils.isEmpty(dataMsg)) {
            jsonObject.put("dataType", String.valueOf(dataType));
            jsonObject.put("params", dataMsg);
        }

        message.put("data", jsonObject);
        json.put("message", message);

        log.info("请求json内容===> {}", json.toString());
        // https://fcm.googleapis.com/v1/projects/bluepay-tesla/messages:send
//        String fcmApiUrl = getSetCacheService.getConfigValue("fcm_api_path");
//        HttpResponse httpResponse = httpAskInterface.synSendPost(fcmApiUrl, json.toString(), requestHeader);

        String httpResponse = HttpUtils.postGeneralUrl(postUrl,"application/json",json.toString(),"UTF-8",requestHeader);

        log.info("fcm响应内容===> {}", httpResponse);
        log.info("[END]推送FCM消息结束");
    }

    /**
     * 获取定时刷新的令牌
     * A: Captain&D
     * W: https://www.cnblogs.com/captainad/
     * @return
     * @throws Exception
     */
    private String getAccessToken() throws Exception {
//        String jsonPath = getSetCacheService.getConfigValue("fcm_access_token_json");
        URL url = new URL(fcmRequestJsonPath);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        InputStream inputStream = conn.getInputStream();

        GoogleCredential googleCredential = GoogleCredential
                .fromStream(inputStream)
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/firebase.messaging"));
        googleCredential.refreshToken();
        if(inputStream != null) {
            inputStream.close();
        }
        return googleCredential.getAccessToken();
    }


    private String getToken (){
        //獲取憑證
        String token = null;
        try {
            GoogleCredential googleCredential = GoogleCredential
                    .fromStream(new FileInputStream(fcmRequestJsonPath))
                    .createScoped(Arrays.asList(SCOPES));
            googleCredential.refreshToken();
            token = googleCredential.getAccessToken();
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
        }
        LOGGER.info("======> FCM token : [{}]",token);
        return token;
    }



}
