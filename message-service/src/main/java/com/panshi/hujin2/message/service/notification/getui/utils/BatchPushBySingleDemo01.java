package com.panshi.hujin2.message.service.notification.getui.utils;

import com.gexin.rp.sdk.base.IBatch;
import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

import java.io.IOException;

/**
 * create by shenjiankang on 2018/7/30 11:44
 *
 *
 * 无用的类
 * 批量单推 demo
 */
public class BatchPushBySingleDemo01 {

    public class MyBatchPushDemo {
        //采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
        private  String appId = "";
        private  String appKey = "";
        private  String masterSecret = "";

         String CID_A = "";
         String CID_B = "";
        //别名推送方式
        // static String Alias = "";
         String host = "http://sdk.open.api.igexin.com/apiex.htm";

        public  void main(String[] args) throws IOException {

            IIGtPush push = new IGtPush(host, appKey, masterSecret);
            IBatch batch = push.getBatch();

            try {
                //构建客户a的透传消息a
                constructClientTransMsg(CID_A,"msgA",batch);
                //构建客户B的点击通知打开网页消息b
                constructClientLinkMsg(CID_B,"msgB",batch);
            } catch (Exception e) {
                e.printStackTrace();
            }
            batch.submit();
        }

        private  void constructClientTransMsg(String cid, String msg ,IBatch batch) throws Exception {

            SingleMessage message = new SingleMessage();
            TransmissionTemplate template = new TransmissionTemplate();
            template.setAppId(appId);
            template.setAppkey(appKey);
            template.setTransmissionContent(msg);
            template.setTransmissionType(1); // 这个Type为int型，填写1则自动启动app

            message.setData(template);
            message.setOffline(true);
            message.setOfflineExpireTime(1 * 1000);

            // 设置推送目标，填入appid和clientId
            Target target = new Target();
            target.setAppId(appId);
            target.setClientId(cid);
            batch.add(message, target);
        }

        private  void constructClientLinkMsg(String cid, String msg ,IBatch batch) throws Exception {

            SingleMessage message = new SingleMessage();
            LinkTemplate template = new LinkTemplate();
            template.setAppId(appId);
            template.setAppkey(appKey);
            template.setTitle("title");
            template.setText("msg");
            template.setLogo("push.png");
            template.setLogoUrl("logoUrl");
            template.setUrl("url");

            message.setData(template);
            message.setOffline(true);
            message.setOfflineExpireTime(1 * 1000);

            // 设置推送目标，填入appid和clientId
            Target target = new Target();
            target.setAppId(appId);
            target.setClientId(cid);
            batch.add(message, target);
        }
    }
}
