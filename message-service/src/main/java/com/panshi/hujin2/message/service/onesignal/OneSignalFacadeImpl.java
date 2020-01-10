package com.panshi.hujin2.message.service.onesignal;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.common.enmu.ClientType;
import com.panshi.hujin2.base.common.factory.MessageFactory;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.domain.result.BasicResultCode;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.dao.model.fcm.UserFcmRelationDO;
import com.panshi.hujin2.message.domain.enums.getui.GeTuiPushTemplateEnum;
import com.panshi.hujin2.message.domain.enums.getui.PushTypeEnum;
import com.panshi.hujin2.message.domain.exception.FCMException;
import com.panshi.hujin2.message.facade.IOneSignalFacade;
import com.panshi.hujin2.message.facade.bo.AppPushHistoryInputBo;
import com.panshi.hujin2.message.facade.bo.AppPushRecordInputBO;
import com.panshi.hujin2.message.facade.bo.AppPushTemplateOutputBO;
import com.panshi.hujin2.message.service.message.utils.MsgUtils;
import com.panshi.hujin2.message.service.notification.getui.INotificationTemplateService;
import com.panshi.hujin2.message.service.notification.utils.NotificationExceptionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author shenJianKang
 * @date 2020/1/3 10:48
 */
@Service
public class OneSignalFacadeImpl implements IOneSignalFacade {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private INotificationTemplateService notificationTemplateService;


    @Override
    public BasicResult<Void> pushMessageToSingle(ApplicationEnmu appEnmu, Integer userId, String pushTemplateCode, List paramList, Boolean send, Boolean recordHistory, Context context) {
        //TODO: 2020/1/9 16:19 by ShenJianKang  已经在印尼贷款产品中 实现 oneSignal 推送
        return null;
    }

    @Override
    public BasicResult<Void> bindUidAndCid(ApplicationEnmu appEnmu, Integer userId, String clientToken, ClientType clientType, Context context) {
        return null;
    }

    @Override
    public BasicResult<Void> unbindUidAndCid(ApplicationEnmu appEnmu, Integer userId, String clientToken, ClientType clientType, Context context) {
        return null;
    }


    //TODO: 2020/1/3 14:38 by ShenJianKang  根据包的修改而修改
    //用于初始化和应用识别的公钥。
//    String  appId = "b6f5f6d2-5a27-49ee-b1ae-006e089251f8";
    static String  appId = "b6f5f6d2-5a27-49ee-b1ae-006e089251f8";
    //私钥用于大多数与发送推送通知和更新用户有关的API调用。如果您认为此密钥已被泄露或公开，则有关如何重置REST API密钥的详细信息。
    //String  apiKey = "MWQyMjA4ZjQtMWVmOC00OGI1LTg1ODAtMzU0ZGRlMmIzNzA3";
    String  apiKey = "MWQyMjA4ZjQtMWVmOC00OGI1LTg1ODAtMzU0ZGRlMmIzNzA3";
    //被推送的用户id
    String playerId = "";
    private void sendByPlayerIds(){
        //TODO: 2020/1/3 14:36 by ShenJianKang   发送逻辑  in main
    }


    public static void main(String[] args) {
        try {
//            System.out.println("=====>  1b563483-965d-4299-9225-025f6d77270c".length());
//            if(true){
//                return;
//            }


            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestMethod("POST");


            //TODO: 2020/1/3 14:40 by ShenJianKang 更多请求参数 见文档：https://documentation.onesignal.com/reference
            String strJsonBody = "{"
                    +   "\"app_id\": \""+ appId +"\","
//                    +   "\"include_player_ids\": [\"6392d91a-b206-4b7b-a620-cd68e32c3a76\",\"76ece62b-bcfe-468c-8a78-839aeaa8c5fa\",\"8e0f21fa-9a5a-4ae7-a9a6-ca1f24294b86\"],"
                    +   "\"include_player_ids\": [\"1b563483-965d-4299-9225-025f6d77270c\"],"
                    +   "\"data\": {\"foo\": \"bar\"},"
                    +   "\"headings\": {\"en\": \"标题 headings \"},"
                    +   "\"contents\": {\"en\": \"内容你好 2\"}"
//                    +   ","
//                    +   "\"subtitle\": {\"en\": \"通知字幕subtitle \"}"//iOS 10以上
                    + "}";

//            String strJsonBody = "{"
//                    +   "app_id: "+ appId +""
////                    +   "\"include_player_ids\": [\"6392d91a-b206-4b7b-a620-cd68e32c3a76\",\"76ece62b-bcfe-468c-8a78-839aeaa8c5fa\",\"8e0f21fa-9a5a-4ae7-a9a6-ca1f24294b86\"],"
//                    +   "include_player_ids: [1b563483-965d-4299-9225-025f6d77270c],"
//                    +   "data: {foo: bar},"
//                    +   "contents: {en: English Message}"
//                    + "}";


            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            if (  httpResponse >= HttpURLConnection.HTTP_OK
                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            //请求返回的结果：{"id":"0f27b392-5489-40dc-8236-86cdf8a08fd4","recipients":1,"external_id":null}
            System.out.println("jsonResponse:\n" + jsonResponse);

        } catch(Throwable t) {
            t.printStackTrace();
        }
    }
}
