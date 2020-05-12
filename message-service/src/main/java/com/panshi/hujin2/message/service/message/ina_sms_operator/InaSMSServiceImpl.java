package com.panshi.hujin2.message.service.message.ina_sms_operator;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.base.service.utils.ContextUtils;
import com.panshi.hujin2.message.common.utils.XmlUtils;
import com.panshi.hujin2.message.common.utils.ignore_ssl.BasicHttpResponse;
import com.panshi.hujin2.message.common.utils.ignore_ssl.HttpUtilv2;
import com.panshi.hujin2.message.domain.enums.ChannelEnum;
import com.panshi.hujin2.message.domain.enums.YiMeiRuanTongResponseEnum;
import com.panshi.hujin2.message.domain.exception.MessageException;
import com.panshi.hujin2.message.facade.bo.MessageSendRecordInputBO;
import com.panshi.hujin2.message.service.message.impl.SendMsg;
import com.panshi.hujin2.message.service.message.ina_sms_operator.bo.XMLPushBO;
import com.panshi.hujin2.message.service.message.submail.sdk.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author shenJianKang
 * @date 2019/8/9 16:26
 */
@Service("inaSMSService")
public class InaSMSServiceImpl extends SendMsg {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());


    @Value("${ina.sms.single.get.url}")
    private String smsSingleUrl;
    @Value("${ina.sms.batch.get.url}")
    private String smsBatchUrl;
    @Value("${ina.sms.uid}")
    private String smsUid;
    @Value("${ina.sms.owd}")
    private String smsOwd;
    @Value("${ina.sms.serviceid}")
    private String smsServiceid;
    @Value("${ina.sms.sc}")
    private String smsSc;

    @Value("${ina.sms.balance.url}")
    private String balanceUrl;

    @Override
    public String key() {
        return null;
    }

    @Override
    public boolean isDefault() {
        return false;
    }



    /**
     *@Description:     查询余额
     ** @param
     *@Author: shenJianKang
     *@date: 2019/9/9 18:26
     */
    public void getBalance(){
        HttpUtilv2 http = new HttpUtilv2();
        try {
            BasicHttpResponse res = http.executeHttpsGet(balanceUrl);
            LOGGER.info(String.valueOf(res));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }

    }


    @Override
    public boolean sendInternationalMsg(ApplicationEnmu applicationEnmu, String phoneNumber, String msgText, Context context,Integer msgType) {
//        String url= "https://203.166.197.162/ApiLongNumber/receive.php";
//        String uid = "userHorizon";
//        String owd= "Q21u1h3oq";

        try {
            //只对短信文本 编码。
            msgText = URLEncoder.encode(msgText,"UTF-8");
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            //throw e;
        }

        //serviceid  已根据SMS网关定义的服务ID。 目前只需填写：1000
        //msisdn     国际格式（例如628xxxxxxx）
        //sms        填写要以utf-8格式发送的SMS
        //transid    合作伙伴的交易ID，必须是唯一的交易ID。
        //smstype    0 =短信文本，1 =二进制短信。 目前它只是短信支持。
        //sc         Sc根据将要发送的发件人ID填写。 最多11个字符

        String transid = UUID.randomUUID().toString();

        String requestUrl = smsSingleUrl +"?uid="+smsUid+
                "&pwd="+smsOwd+
                "&serviceid="+smsServiceid+
                "&msisdn="+phoneNumber+
                "&sms="+msgText+
                "&transid="+transid+
                "&smstype=0&sc="+smsSc;

        HttpUtilv2 http = new HttpUtilv2();
        try {
            BasicHttpResponse res = http.executeHttpsGet(requestUrl);
            LOGGER.info("印尼短信发送结果: [{}]",res);
            //解析结果
            //<?xml version='1.0' encoding='UTF-8'?><PUSH><STATUS>0</STATUS><TRANSID>4224c39c-7a1b-4993-8386-8db01d0adaf2</TRANSID><MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG></PUSH>
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }

        return false;
    }

    /**
     * we alr set for 1 API can send 10 phonenumbers in one hit.
     * for send more than 1 msisdn, just set bulk=1, if just 1 msisdn turn bulk into 0
     * for multiple msisdn separate with semicolon ;
     * new endpoint API :
     * https://203.166.197.162/ApiLongNumber/receiveLC.php?bulk=1
     * https://203.166.197.162/ApiLongNumber/receiveLC.php?bulk=1&uid=xxxx&pwd=xxx&sc=LONGNUMBER&msisdn=628xxx;628xxxx&transid=xxxxx&sms=TEST SMS&smstype=0&serviceid=1000
     */
    @Override
    public void batchSendSameTemplateSameParam(ApplicationEnmu applicationEnmu, List phoneNumberList, String templateCode, List paramList, Context context) {
        //同模板同参数， 根据templateCode查询发送
    }

    @Override
    public void batchSendSameTemplateSameParam(ApplicationEnmu applicationEnmu,
                                               Integer queueId,
                                               Integer consumerId,
                                               Double fee,
                                               List phoneNumberList,
                                               String sendText,
                                               Context context) {
//        try {
//
//        }catch (Exception e){
//            LOGGER.error(e.getMessage(), e);
//            throw e;
//        }
        //同模板同参数， 根据sendText 发送
        if(CollectionUtils.isEmpty(phoneNumberList)
            ||applicationEnmu == null
            || StringUtils.isBlank(sendText)){
            LOGGER.error("印尼sms 群发短信，参数为空");
            throw new MessageException("ina batch sms is null");
        }

        //对方提供的接口，一次最多只能发送10个号码
        if(phoneNumberList.size() > 10){

            int limit = 10;
            for(int i=0; i<phoneNumberList.size();i+=10){
                List<String> phoneNumbers = new ArrayList();
                String phoneNumbersStr = "";
                if(limit <= phoneNumberList.size()){
                    phoneNumbers  = phoneNumberList.subList(i,limit);
                }else {
                    phoneNumbers  = phoneNumberList.subList(i,phoneNumberList.size());
                }

                limit +=10;

                for (String phoneNumber:phoneNumbers){
                    phoneNumbersStr += phoneNumber+";";
                }
                //最后一个“;”去掉
                phoneNumbersStr = phoneNumbersStr.substring(0, phoneNumbersStr.length() - 1);

                batchSend(applicationEnmu,queueId,consumerId,fee,phoneNumbersStr, sendText, 1);
            }
        }else {
            //直接调用
            Integer bulk = 1;
            if(phoneNumberList.size() == 1){
                bulk = 0;
            }
            String phoneNumbersStr = "";
            for(Object phoneNumer:phoneNumberList){
                phoneNumbersStr += phoneNumer+";";
            }
            //最后一个“;”去掉
            phoneNumbersStr = phoneNumbersStr.substring(0, phoneNumbersStr.length() - 1);
            batchSend(applicationEnmu,
                    queueId,
                    consumerId,
                    fee,
                    phoneNumbersStr,
                    sendText,
                    bulk);
        }
    }

    /**
     *@Description:
     *  @param phoneNumbers
     * @param msgText
     * @param bulk              超过一个手机号设置1， 只有一个手机号设置0
     *@Author: shenJianKang
     *@date: 2019/8/12 14:45
     */
    public void batchSend(ApplicationEnmu applicationEnmu,
                           Integer queueId,
                           Integer consumerId,
                           Double fee,
                           String phoneNumbers,
                           String msgText,
                           Integer bulk)  {

        //todo sleep
//        try {
//            LOGGER.error("开始睡眠");
//            Thread.sleep(60*1000*1);//1分钟，为了测试暂停功能
//        }catch (Exception e){
//            LOGGER.error("批量发送短信睡眠异常");
//        }

        try {
            //只对短信文本 编码。
            msgText = URLEncoder.encode(msgText,"UTF-8");
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            //return e.getMessage();
        }

        //serviceid  已根据SMS网关定义的服务ID。 目前只需填写：1000
        //msisdn     国际格式（例如628xxxxxxx）
        //sms        填写要以utf-8格式发送的SMS
        //transid    合作伙伴的交易ID，必须是唯一的交易ID。
        //smstype    0 =短信文本，1 =二进制短信。 目前它只是短信支持。
        //sc         Sc根据将要发送的发件人ID填写。 最多11个字符

        String transid = UUID.randomUUID().toString();



        String requestUrl = smsBatchUrl +"?bulk=" + bulk +
                "&uid=" + smsUid +
                "&pwd=" + smsOwd +
                "&sc=LONGNUMBER" +
                "&msisdn=" + phoneNumbers +
                "&transid=" + transid +
                "&sms=" + msgText+
                "&smstype=0" +
                "&serviceid=" + smsServiceid;

        HttpUtilv2 http = new HttpUtilv2();
        try {
            BasicHttpResponse res = http.executeHttpsGet(requestUrl);
            LOGGER.error("发送结果: [{}]",res);
            int statusCode = res.getStatusCode();
            String htmlContent = res.getHtmlContent();
            if(statusCode!=200 || StringUtils.isBlank(htmlContent)){
                LOGGER.error("印尼短信发送失败");
                LOGGER.info("发送结果code[{}] htmlContent[{}]",statusCode,htmlContent);
            }

            //<?xml version='1.0' encoding='UTF-8'?>
            // <PUSH>
            // <STATUS>0</STATUS>
            // <TRANSID>15655994556281285290562</TRANSID>
            // <MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG>
            // </PUSH>
            //
            // <?xml version='1.0' encoding='UTF-8'?>
            // <PUSH>
            // <STATUS>0</STATUS>
            // <TRANSID>15655994556281285290562</TRANSID>
            // <MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG>
            // </PUSH>


            //// TODO: 2019/8/15 返回的transid 变了
//            <?xml version='1.0' encoding='UTF-8'?>
//            <PUSH>
//            <STATUS>0</STATUS>
//            <TRANSID>36f8a823-d18d-4673-90f5-72f24df90ea7</TRANSID>
//            <MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG>
//            </PUSH>

                    //todo  发送记录数据入库
            String resultStr = htmlContent.replace("<?xml version='1.0' encoding='UTF-8'?>",",");
            String[] arr = resultStr.split(",");
            for(int i=0;i<arr.length;i++){
                if(StringUtils.isNotBlank(arr[i])){
                    XMLPushBO xmlPushBO = (XMLPushBO) XmlUtils.toJavaBean(arr[i], XMLPushBO.class);

                    MessageSendRecordInputBO inputBO = new MessageSendRecordInputBO();
                    inputBO.setAppId(applicationEnmu.getCode());
                    inputBO.setConsumerId(consumerId);
                    inputBO.setQueueId(queueId);
                    inputBO.setChannelId(ChannelEnum.INA_HORIZON_SMS.getCode());

                    //bulk=0 for send 1 phone number
                    //bulk=1 for send more than 1 phone numbers
                    if(bulk == 0){
                        inputBO.setPhoneNumber(phoneNumbers);
                    }else {
                        String transId = xmlPushBO.getTransId();
                        if(StringUtils.isNotBlank(transId)){
                            inputBO.setMsgId(transId);

                            String phoneNumber = transId.substring(10);
                            inputBO.setPhoneNumber(phoneNumber);
                        }
                    }

                    Integer resCode = Integer.valueOf(xmlPushBO.getStatus());
                    if(resCode == 0){
                        // 发送成功
                        inputBO.setFee(fee);
                    }else {
                        inputBO.setFee(0d);
                    }
                    inputBO.setResCode(resCode);
                    inputBO.setMsgText(msgText);
                    inputBO.setReturnValue(htmlContent);

                    //todo 批量插入
                    msgDBService.addMsgSendRecord(inputBO, ContextUtils.getDefaultContext());
                }
            }

        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new MessageException("ina sms 發送失敗："+e.getMessage());
        }
    }

    public static void main(String[] args) {

        String result = "<?xml version='1.0' encoding='UTF-8'?><PUSH><STATUS>0</STATUS><TRANSID>15655994556281285290562</TRANSID><MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG></PUSH><?xml version='1.0' encoding='UTF-8'?><PUSH><STATUS>0</STATUS><TRANSID>15655994556281285290562_1</TRANSID><MSG>Success Quota : -1.0 Valid Period : 31/12/2020</MSG></PUSH>\n";
        System.out.println("result.contains(\"<?xml version='1.0' encoding='UTF-8'?>\") = " + result.contains("<?xml version='1.0' encoding='UTF-8'?>"));

        result = result.replace("<?xml version='1.0' encoding='UTF-8'?>",",");

//        String[] arr = result.split("\"<?xml version='1.0' encoding='UTF-8'?>\"");
        String[] arr = result.split(",");
        List<XMLPushBO> xmlPushBOS = new ArrayList<>();
        for(int i=0;i<arr.length;i++){
            System.out.println("arr[i] = " + arr[i]);
            if(StringUtils.isNotBlank(arr[i])){
                XMLPushBO responseInfo = (XMLPushBO) XmlUtils.toJavaBean(arr[i], XMLPushBO.class);
                xmlPushBOS.add(responseInfo);
            }
        }

        System.out.println("responseInfo = " + xmlPushBOS);

//        String result = "<?xml version='1.0' encoding='UTF-8'?><PUSH>";
//        String[] arr = result.split("<");//?xml version='1.0' encoding='UTF-8'?>
//        System.out.println("arr = " + arr);


        System.out.println("111 = " + 111);
        try {
            Thread.sleep(5000);
        }catch (Exception e){

        }
        System.out.println("222 = " + 222);
    }


    //TODO 测试读写入库 专用
//    /**
//     *@Description:
//     *  @param phoneNumbers
//     * @param msgText
//     * @param bulk              超过一个手机号设置1， 只有一个手机号设置0
//     *@Author: shenJianKang
//     *@date: 2019/8/12 14:45
//     */
//    public void batchSend_BAK(ApplicationEnmu applicationEnmu,
//                          Integer queueId,
//                          Integer consumerId,
//                          Double fee,
//                          String phoneNumbers,
//                          String msgText,
//                          Integer bulk)  {
//
//        //todo sleep
////        try {
////            LOGGER.error("开始睡眠");
////            Thread.sleep(60*1000*1);//1分钟，为了测试暂停功能
////        }catch (Exception e){
////            LOGGER.error("批量发送短信睡眠异常");
////        }
//
//        try {
//            //只对短信文本 编码。
//            msgText = URLEncoder.encode(msgText,"UTF-8");
//        }catch (Exception e){
//            LOGGER.error(e.getMessage(), e);
//            //return e.getMessage();
//        }
//
//        String transid = UUID.randomUUID().toString();
//        try {
//
//            //// TODO: 2019/8/19 测试写库效率
//            String[] phoneArr = phoneNumbers.split(";");
//            for(int i=0;i<phoneArr.length;i++){
//                MessageSendRecordInputBO inputBO = new MessageSendRecordInputBO();
//                inputBO.setAppId(applicationEnmu.getCode());
//                inputBO.setConsumerId(consumerId);
//                inputBO.setQueueId(queueId);
//                inputBO.setChannelId(ChannelEnum.INA_HORIZON_SMS.getCode());
//
//                //bulk=0 for send 1 phone number
//                //bulk=1 for send more than 1 phone numbers
//                if(bulk == 0){
//                    inputBO.setPhoneNumber(phoneArr[i]);
//                }else {
//                    String transId = "999";
//                    inputBO.setPhoneNumber(phoneArr[i]);
//                }
//
//                Integer resCode = Integer.valueOf(888);
//                if(resCode == 0){
//                    // 发送成功
//                    inputBO.setFee(fee);
//                }else {
//                    inputBO.setFee(0d);
//                }
//                inputBO.setResCode(resCode);
//                inputBO.setMsgText(msgText);
//                inputBO.setReturnValue("测试读写");
//
//                //todo 批量插入
//                msgDBService.addMsgSendRecord(inputBO, ContextUtils.getDefaultContext());
//            }
//        }catch (Exception e){
//            LOGGER.error(e.getMessage(), e);
//            throw new MessageException("ina sms 發送失敗："+e.getMessage());
//        }
//    }
    //TODO 测试读写入库 专用






}
