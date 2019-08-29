package com.panshi.hujin2.message.service.fcm.facade;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.common.enmu.ClientType;
import com.panshi.hujin2.base.common.factory.MessageFactory;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.domain.result.BasicResultCode;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.dao.mapper.fcm.UserFcmRelationMapper;
import com.panshi.hujin2.message.facade.IFCMFacade;
import com.panshi.hujin2.message.service.notification.fcm.FCMPushService;
import com.panshi.hujin2.message.service.notification.utils.NotificationExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author shenJianKang
 * @date 2019/8/5 18:24
 */
@Service
public class FCMFacadeImpl implements IFCMFacade {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());


    @Autowired
    private UserFcmRelationMapper userFcmRelationMapper;
    @Autowired
    private FCMPushService fCMPushService;


    @Value("${fcm.request.json.path}")
    private String fcmRequestJsonPath;

    String postUrl = "https://fcm.googleapis.com/v1/projects/myproject-b5ae1/messages:send";
    String SCOPES = "https://www.googleapis.com/auth/firebase.messaging";


//    FileInputStream serviceAccount =
//            new FileInputStream("path/to/serviceAccountKey.json");
//
//    FirebaseOptions options = new FirebaseOptions.Builder()
//            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//            .setDatabaseUrl("https://comauldlangsynecashcat-10afc.firebaseio.com")
//            .build();
//
//FirebaseApp.initializeApp(options);

    @Override
    public BasicResult<Void> pushMessageToSingle(ApplicationEnmu appEnmu,
                                                 Integer userId,
                                                 String pushTemplateCode,
                                                 List paramList,
                                                 Boolean send,
                                                 Boolean recordHistory,
                                                 Context context) {
        // This registration token comes from the client FCM SDKs.
        String registrationToken = "YOUR_REGISTRATION_TOKEN";

        try {
            // See documentation on defining a message payload.
            Message message = Message.builder()
                    .putData("score", "850")
                    .putData("time", "2:45")
                    .setToken(registrationToken)
                    .build();

            // Send a message to the device corresponding to the provided
            // registration token.
            String response = FirebaseMessaging.getInstance().send(message);
            // Response is a message ID string.
            System.out.println("Successfully sent message: " + response);









            return BasicResult.ok();
        } catch (Exception e){
            LOGGER.debug(e.getMessage(),e);
            //throw e;
            return BasicResult.error(BasicResultCode.ERROR.getCode(), MessageFactory.getMsg("G19770108", context.getLocale()));
        }
    }

    /**
     *@Description:     获取访问凭据
     *@Param:  * @param
     *@Author: shenJianKang
     *@date: 2019/8/22 18:29
     */
    private String getAccessToken() throws IOException {
        GoogleCredential googleCredential = GoogleCredential
                .fromStream(new FileInputStream(fcmRequestJsonPath))
                .createScoped(Arrays.asList(SCOPES));
        googleCredential.refreshToken();
        return googleCredential.getAccessToken();
    }

    public static void main(String[] args) {
//        FileInputStream serviceAccount =  new FileInputStream("path/to/serviceAccountKey.json");
//        FileInputStream serviceAccount =  new FileInputStream(fcmRequestJsonPath);
//
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .setDatabaseUrl("https://comauldlangsynecashcat-10afc.firebaseio.com")
//                .build();
//
//        FirebaseApp.initializeApp(options);
    }



//    public static void main(String[] args) {
//        String aa = "[BasicHttpResponse(statusCode=200, htmlContent=<?xml version='1.0' encoding='UTF-8'?><PUSH><STATUS>0</STATUS><TRANSID>15664704366287851993929</TRANSID><MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG></PUSH><?xml version='1.0' encoding='UTF-8'?><PUSH><STATUS>0</STATUS><TRANSID>15664704366287741245680</TRANSID><MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG></PUSH><?xml version='1.0' encoding='UTF-8'?><PUSH><STATUS>0</STATUS><TRANSID>15664704366289676162843</TRANSID><MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG></PUSH><?xml version='1.0' encoding='UTF-8'?><PUSH><STATUS>0</STATUS><TRANSID>15664704366289627806458</TRANSID><MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG></PUSH><?xml version='1.0' encoding='UTF-8'?><PUSH><STATUS>0</STATUS><TRANSID>1566470436628569964027</TRANSID><MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG></PUSH><?xml version='1.0' encoding='UTF-8'?><PUSH><STATUS>0</STATUS><TRANSID>15664704366281310466357</TRANSID><MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG></PUSH><?xml version='1.0' encoding='UTF-8'?><PUSH><STATUS>0</STATUS><TRANSID>15664704366285888665684</TRANSID><MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG></PUSH><?xml version='1.0' encoding='UTF-8'?><PUSH><STATUS>0</STATUS><TRANSID>15664704366282122421864</TRANSID><MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG></PUSH><?xml version='1.0' encoding='UTF-8'?><PUSH><STATUS>0</STATUS><TRANSID>15664704366282114853568</TRANSID><MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG></PUSH>\n" +
//                ")]";
//
//        System.out.println("aa.length() = " + aa.length());
//    }





    @Override
    public BasicResult<Void> bindUidAndCid(ApplicationEnmu appEnmu, Integer userId, String clientToken, ClientType clientType, Context context) {
        try {
           fCMPushService.insertUserClientRelation(appEnmu,userId,clientToken,clientType,context);
            return BasicResult.ok();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return NotificationExceptionUtils.throwDefinedException(e, context);
//            throw e;
        }

    }

    @Override
    public BasicResult<Void> unbindUidAndCid(ApplicationEnmu appEnmu, Integer userId, String clientToken, ClientType clientType, Context context) {
        try {
            fCMPushService.unbindClientRelation(appEnmu,userId,clientToken,clientType,context);
            return BasicResult.ok();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return NotificationExceptionUtils.throwDefinedException(e, context);
//            throw e;

    }




    }
}