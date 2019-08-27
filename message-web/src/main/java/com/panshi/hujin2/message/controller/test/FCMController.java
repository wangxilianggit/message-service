package com.panshi.hujin2.message.controller.test;


import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.util.Arrays;

/**
 * @author shenJianKang
 * @date 2019/8/22 19:31
 */

@RestController
@RequestMapping("fcm")
public class FCMController {


    @Value("${fcm.request.json.path}")
    private String fcmRequestJsonPath;
    String postUrl = "https://fcm.googleapis.com/v1/projects/myproject-b5ae1/messages:send";
    String SCOPES = "https://www.googleapis.com/auth/firebase.messaging";

    String clientToken = "fTynYFtXRUI:APA91bGMfdZkpnAbJnRa5iOv4zWh_VcDcd6pCc4Gx8w2KSK7DgzpBfD245hQHp_pa-F0jNGXxV3qievbp1ct6iEc4LX0D4_KGMo6jnA3exbbjp-5kogFS8o8TpA8p7LRPJi9BOQCjTtx";

    @RequestMapping("/aaa")
    public void  fcm1(){
        try {
//            FileInputStream serviceAccount =
//                    new FileInputStream(fcmRequestJsonPath);
//
//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                    .setDatabaseUrl("https://comauldlangsynecashcat-10afc.firebaseio.com")
//                    .build();
//
//            FirebaseApp.initializeApp(options);

            //comfcmtest-firebase-adminsdk-wona7-3881cb4882.json
            FileInputStream serviceAccount =
                    new FileInputStream("path/to/serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://comfcmtest.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);










        }catch (Exception e){
            System.out.println("e.getMessage() = " + e.getMessage());
        }
    }


    private String getToken(){
        String token = null;
        try {
            //獲取憑證
            GoogleCredential googleCredential = GoogleCredential
                    .fromStream(new FileInputStream(fcmRequestJsonPath))
                    .createScoped(Arrays.asList(SCOPES));
            googleCredential.refreshToken();
            token = googleCredential.getAccessToken();
            return token;
        }catch (Exception e){
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        return token;
    }






}
