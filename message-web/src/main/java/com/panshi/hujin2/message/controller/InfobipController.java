package com.panshi.hujin2.message.controller;

import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.base.service.utils.ContextUtils;
import com.panshi.hujin2.message.facade.bo.InfobipMsgReportsInputBO;
import com.panshi.hujin2.message.service.message.IMsgDBService;
import com.panshi.hujin2.message.service.message.ISendMsgService;
import com.panshi.hujin2.message.service.message.infobip.utils.InfobipHttpUtil;
import com.panshi.hujin2.message.service.message.infobip.utils.InfobipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * create by shenjiankang on 23/07/2018 17:24
 */
@RestController
@RequestMapping("infobip")
public class InfobipController {
    private static Logger LOGGER = LoggerFactory.getLogger(InfobipController.class);

    @Autowired
    protected IMsgDBService msgDBService;


    @RequestMapping(value = "callback",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    public void callback(HttpServletRequest request){

        //  試了下,從短信發出去到infobip回調，大概間隔三四秒

        LOGGER.info("--------infobip 回調------");
        try {
            BufferedReader reader = null;
            reader = request.getReader();
            String input = null;
            StringBuffer requestBody = new StringBuffer();
            while ((input = reader.readLine())!=null){
                requestBody.append(input);
            }
            //{"results":[{"price":{"pricePerMessage":0.000000,"currency":"EUR"},"status":{"id":5,"groupId":3,"groupName":"DELIVERED","name":"DELIVERED_TO_HANDSET","description":"Message delivered to handset"},"error":{"id":0,"name":"NO_ERROR","description":"No Error","groupId":0,"groupName":"OK","permanent":false},"messageId":"2323494130731631527","doneAt":"2018-07-23T20:37:10.690+0800","smsCount":1,"sentAt":"2018-07-23T20:36:53.070+0800","to":"8613777400292"}]}
            LOGGER.info("--------infobip回調返回信息：[{}]", requestBody.toString());

            Context context = ContextUtils.getDefaultContext();
            InfobipMsgReportsInputBO inputBO = InfobipUtil.resolverJsonStr(requestBody.toString(),context);
            int count = 0;
            if(inputBO != null){
                count = msgDBService.addInfobipMsgReports(inputBO, context);
            }
            LOGGER.info("--------infobip短信發送信息回調，入庫[{}]條記錄",count);
        } catch (IOException e) {
            e.printStackTrace();
        }


//      第2種解析json的步驟++
//        try {
//            InputStreamReader reader = new InputStreamReader(request.getInputStream(),"UTF-8");
//             char[] buff = new char[1024];
//             int length = 0;
//             while ((length = reader.read(buff))!=-1){
//                 String x = new String(buff,0 ,length);
////{"results":[{"price":{"pricePerMessage":0.000000,"currency":"EUR"},"status":{"id":5,"groupId":3,"groupName":"DELIVERED","name":"DELIVERED_TO_HANDSET","description":"Message delivered to handset"},"error":{"id":0,"name":"NO_ERROR","description":"No Error","groupId":0,"groupName":"OK","permanent":false},"messageId":"2323495672331631940","doneAt":"2018-07-23T20:39:41.953+0800","smsCount":1,"sentAt":"2018-07-23T20:39:27.233+0800","to":"8613777400292"}]}
////{"results":[{"price":{"pricePerMessage":0.000000,"currency":"EUR"},"status":{"id":5,"groupId":3,"groupName":"DELIVERED","name":"DELIVERED_TO_HANDSET","description":"Message delivered to handset"},"error":{"id":0,"name":"NO_ERROR","description":"No Error","groupId":0,"groupName":"OK","permanent":false},"messageId":"2323494130731631527","doneAt":"2018-07-23T20:37:10.690+0800","smsCount":1,"sentAt":"2018-07-23T20:36:53.070+0800","to":"8613777400292"}]}
//
//                 System.out.println("x = " + x);
//                 System.out.println(" = ==================                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  " );
//             }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
