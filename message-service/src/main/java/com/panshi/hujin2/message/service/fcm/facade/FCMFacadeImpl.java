package com.panshi.hujin2.message.service.fcm.facade;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.common.factory.MessageFactory;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.domain.result.BasicResultCode;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.facade.IFCMFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenJianKang
 * @date 2019/8/5 18:24
 */
@Service
public class FCMFacadeImpl implements IFCMFacade {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    String postUrl = "https://fcm.googleapis.com/v1/projects/myproject-b5ae1/messages:send";


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
}
