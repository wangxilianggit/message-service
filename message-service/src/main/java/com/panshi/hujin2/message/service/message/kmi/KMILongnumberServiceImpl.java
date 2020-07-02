package com.panshi.hujin2.message.service.message.kmi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.common.utils.HttpUtil;
import com.panshi.hujin2.message.dao.mapper.message.MarketingKmiBatchSendResponseMapper;
import com.panshi.hujin2.message.dao.mapper.message.MarketingMessageSendRecordMapper;
import com.panshi.hujin2.message.dao.model.MarketingKmiBatchSendResponseDO;
import com.panshi.hujin2.message.dao.model.MarketingMessageSendRecordDO;
import com.panshi.hujin2.message.domain.enums.ChannelEnum;
import com.panshi.hujin2.message.domain.exception.MessageException;
import com.panshi.hujin2.message.facade.bo.BatchSendSelfDefinedMsgBO;
import com.panshi.hujin2.message.facade.bo.MessageSendRecordInputBO;
import com.panshi.hujin2.message.service.message.impl.SendMsg;
import com.panshi.hujin2.message.service.message.utils.MsgUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author shenJianKang
 * @date 2020/5/12 16:56
 *
 * KMI 营销短信
 */
@Service("KMILongnumberService")
public class KMILongnumberServiceImpl extends SendMsg {
    private static Logger LOGGER = LoggerFactory.getLogger(KMILongnumberServiceImpl.class);

    @Autowired
    private MarketingMessageSendRecordMapper marketingMessageSendRecordMapper;
    @Autowired
    private MarketingKmiBatchSendResponseMapper marketingKmiBatchSendResponseMapper;

    @Autowired
    private KMIUtil kmiUtil;

    @Value("${kmi.send.longnumber.url}")
    private String kmiSendLongnumberUrl;

    @Value("${kmi.batch.send.longnumber.url}")
    private String kmiBatchSendLongnumberUrl;

    @Override
    public boolean sendInternationalMsg(ApplicationEnmu applicationEnmu, String phoneNumber, String msgText, Context context, Integer msgType) {
        LOGGER.info("=========== INA KMI LONGNUMBER ====================");
        try {
            String token = kmiUtil.getToken();

            JSONObject sendJsonObj = new JSONObject();
            sendJsonObj.put("token", token);
            sendJsonObj.put("sendType", 1);//发送类型 1.LongNumber
            sendJsonObj.put("msisdn", phoneNumber);//短信接收方号码，格式为62xxxx如：6281210506807 （只支持印尼号码）
            sendJsonObj.put("message", msgText);//短信内容
            String params = sendJsonObj.toString();


            //{"result":{"code":-101,"desc":"Token Error"}}
            String sendRes = HttpUtil.post(kmiSendLongnumberUrl, params);
//            if(StringUtils.isNotBlank(sendRes) && (sendRes.contains("-101") || sendRes.contains("Token Error"))){
//                //token失效， 重新請求
//                String token2 = kmiUtil.getTokenByUrl();
//                JSONObject sendJsonObj2 = new JSONObject();
//                sendJsonObj2.put("token", token2);
//                sendJsonObj2.put("sendType", 1);
//                sendJsonObj2.put("msisdn", phoneNumber);
//                sendJsonObj2.put("message", msgText);
//                String params2 = sendJsonObj2.toString();
//                sendRes = HttpUtil.post(kmiSendLongnumberUrl, params2);
//            }

            LOGGER.info("--------KMI longnumber 发送结果:[{}],[{}],[{}]",phoneNumber,msgText,sendRes);
            MessageSendRecordInputBO inputBO = new MessageSendRecordInputBO();
            //返回结果样例
            //{"result":{"code":-101,"desc":"Token Error"}}
            //{"data":{"trxid":"15892755803551052","trxdate":"20200512172620"},"result":{"code":0,"desc":"SUCCESS"}}
            JSONObject jsonObj = JSONObject.parseObject(sendRes);
            String msgid = null;
            String code = null;
            String desc = null;
            if(jsonObj != null){
                JSONObject result = jsonObj.getJSONObject("result");
                if(result != null){
                    code = result.getString("code");
                    desc = result.getString("desc");
                    if("0".equals(code)){
                        //成功
                        JSONObject data = jsonObj.getJSONObject("data");
                        if(data != null){
                            msgid = data.getString("trxid");
                            //String trxdate = data.getString("trxdate");
                        }
                    }
                }
            }
            inputBO.setAppId(applicationEnmu.getCode());
            //国家编码
            inputBO.setChannelId(ChannelEnum.KMI_LONGNUMBER.getCode());
            inputBO.setPhoneNumber(phoneNumber);
            inputBO.setMsgText(msgText);
            inputBO.setMsgId(msgid);
            inputBO.setResCode(Integer.valueOf(code));
            inputBO.setResExplain(desc);
            inputBO.setReturnValue(sendRes);
            inputBO.setMsgType(msgType);
            msgDBService.addMsgSendRecord(inputBO,context);

            if("0".equals(code)){
                //成功请求创蓝短信接口
                Integer sendNum = 1;
                Object sendN = MsgUtils.expiryMap.get(phoneNumber);
                if(sendN != null){
                    sendNum = (Integer)sendN + 1;
                }
                Long endTime = MsgUtils.getEndTime();
                //转成毫秒
                endTime = endTime * 1000;

                //日志打印，当天手机号码发送次数
                MsgUtils.printMsgSendLogs(phoneNumber,sendNum, endTime);

                MsgUtils.expiryMap.put(phoneNumber, sendNum, endTime);
            }
            return true;
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return false;
        }
    }


//    public static void main(String[] args) throws Exception{
        //todo 请求kmi 营销短信
//        JSONObject sendJsonObj = new JSONObject();
//        //[{"data":{"token":"1386F33F46A0765973792019DEC065199233CD9D7BCF1AE6AA44474EC889E65A","balance":"97736.20","smsBalance":"1202040.00"},"result":{"code":0,"desc":"SUCCESS"}}
//        String token = getToken();
//        sendJsonObj.put("token", token);
//        sendJsonObj.put("sendType", 1);//发送类型 1.LongNumber
//        sendJsonObj.put("msisdn", "6281210506807");//短信接收方号码，格式为62xxxx如：6281210506807 （只支持印尼号码）
//        sendJsonObj.put("message", "hello");//短信内容
//        String params = sendJsonObj.toString();
//
//        //{"result":{"code":-101,"desc":"Token Error"}}
//        //{"data":{"trxid":"15892755803551052","trxdate":"20200512172620"},"result":{"code":0,"desc":"SUCCESS"}}
//        String sendRes = HttpUtil.post("http://cs.kmindo.com:9980/sm/sender", params);
//        System.out.println("sendRes = " + sendRes);

        //todo 解析 返回的结果
//        String sendRes = "{\"data\":{\"trxid\":\"15892755803551052\",\"trxdate\":\"20200512172620\"},\"result\":{\"code\":0,\"desc\":\"SUCCESS\"}}";
//        JSONObject jsonObj = JSONObject.parseObject(sendRes);
//        if(jsonObj != null){
//            JSONObject data = jsonObj.getJSONObject("data");
//            String trxid = data.getString("trxid");
//            System.out.println("trxid = " + trxid);
//            String trxdate = data.getString("trxdate");
//            System.out.println("trxdate = " + trxdate);
//            JSONObject result = jsonObj.getJSONObject("result");
//            String code = result.getString("code");
//            System.out.println("code = " + code);
//            String desc = result.getString("desc");
//            System.out.println("desc = " + desc);
//        }
//    }

    //TODO: 2020/5/12 17:24 by ShenJianKang  可删除
//    public static final String TOKEN_KEY = "KMI_TOKEN";
//    public static final String KmiTokenUrl = "http://cs.kmindo.com:9980/cs/login?account=EasyKlick&password=b99846c549c57aa213fa8fe0033afdea";
//    public static String getToken() throws Exception{
//        Object tokenObj = MsgUtils.expiryMap.get(TOKEN_KEY);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//        System.out.println("======當前時間 = " + sdf.format(new Date()));
//        System.out.println("======當前tokenObj = " + tokenObj);
//
//        String token = null;
//        if(tokenObj == null){
//            LOGGER.info("map为空获取KMI 请求 [{}]",KmiTokenUrl);
//            String result = HttpUtil.get(KmiTokenUrl);
//            LOGGER.info("map为空获取KMI token [{}]",result);
//
//
//            if(StringUtils.isNotBlank(result)){
//                //解析result
//                //{"data":{"token":"440E8E87F36D52E60631F892689D62E14A19623FE9147EC2FC1947E2D4AF5285","balance":"0.00"},"result":{"code":0,"desc":"SUCCESS"}}
//                JSONObject jsonObject = JSON.parseObject(result);
//                JSONObject res = jsonObject.getJSONObject("result");
//                if(res != null){
//                    String code = res.getString("code");
//                    if("0".equals(code)){
//                        JSONObject data = jsonObject.getJSONObject("data");
//                        if(data != null){
//                            token = data.getString("token");
//                            MsgUtils.expiryMap.put(TOKEN_KEY,token, 50 * 60 * 1000 * 2);
////                            if(StringUtils.isNotBlank(token)){
////                                return token;
////                            }
//                        }
//                    }
//                }
//            }else {
//                //重试机制
//                boolean flag = true;
//                int num = 0;
//                //重试
//                while (flag && num<10){
//                    try {
//                        Thread.sleep(1000);
//                        LOGGER.info("重试获取KMI 请求 [{}]",KmiTokenUrl);
//                        result = HttpUtil.get(KmiTokenUrl);
//                        LOGGER.info("重试获取KMI token [{}]",result);
//
//                        num ++;
//                        if(StringUtils.isNotBlank(result)){
//                            JSONObject jsonObject = JSON.parseObject(result);
//                            JSONObject res = jsonObject.getJSONObject("result");
//                            if(res != null){
//                                String code = res.getString("code");
//                                if("0".equals(code)){
//                                    JSONObject data = jsonObject.getJSONObject("data");
//                                    if(data != null){
//                                        token = data.getString("token");
//                                        MsgUtils.expiryMap.put(TOKEN_KEY,token, 50 * 60 * 1000 * 2);
//                                        //结束重试
//                                        flag = false;
//                                    }
//                                }
//                            }
//                        }
//                    }catch (Exception e){
//                        LOGGER.error(e.getMessage(),e);
//                        throw e;
//                    }
//                }
//            }
//        }else {
//            token = String.valueOf(tokenObj);
//        }
//        if(StringUtils.isBlank(token)){
//            LOGGER.error(" ======== KMI TOKEN 获取失败！");
//            throw new MessageException("KMI TOKEN 获取失败！");
//        }
//        return token;
//    }

    @Override
    public void batchSendSameTemplateSameParam(ApplicationEnmu applicationEnmu, List phoneNumberList, String templateCode, List paramList, Context context) {

    }

    public static void main(String[] args) {
        List<String> phoneNumberList = Stream.of("6201","6202").collect(Collectors.toList());
        System.out.println("phoneNumberList = " + phoneNumberList);

        for(String phoneNumber:phoneNumberList){
            if(StringUtils.isNotBlank(phoneNumber)){
                if(phoneNumber.startsWith("620")){
                    phoneNumber = phoneNumber.replace("620","62");
                    System.out.println("phoneNumber = " + phoneNumber);
                }
            }
        }
        System.out.println("phoneNumberList = " + phoneNumberList);
    }

    @Override
    public void batchSendSelfDefinedMsg(BatchSendSelfDefinedMsgBO bo,
                                        Context context)throws Exception{
        try {
            Integer MarketingSmsTaskRecordPrimaryKey = bo.getMarketingSmsTaskRecordPrimaryKey();
            ApplicationEnmu applicationEnmu = bo.getApplicationEnmu();
            List<String> phoneNumberListParam = bo.getPhoneNumberList();
            String msgContent = bo.getMsgContent();
            if(CollectionUtils.isEmpty(phoneNumberListParam)){
                return;
            }

            List<String> phoneNumberList = new ArrayList<>();
            //去掉62后面的0； 测试后格式62081808222759不能发送成功；返回：Check your phone number
            // 格式6281808222759发送成功
            for(String phoneNumber:phoneNumberListParam){
                if(StringUtils.isNotBlank(phoneNumber)){
                    if(phoneNumber.startsWith("620")){
                        phoneNumber = phoneNumber.replace("620","62");
                        phoneNumberList.add(phoneNumber);
                    }else {
                        phoneNumberList.add(phoneNumber);
                    }
                }
            }

            JSONArray phoneArray = JSONArray.parseArray(JSON.toJSONString(phoneNumberList));
            if(phoneArray == null){
                throw new RuntimeException("解析手机号 list 失败");
            }

            String token = kmiUtil.getToken();

            JSONObject sendJsonObj = new JSONObject();
            sendJsonObj.put("token", token);
            sendJsonObj.put("msisdn", phoneArray);
            sendJsonObj.put("message", msgContent);
            String params = sendJsonObj.toString();


            //{"result":{"code":-101,"desc":"Token Error"}}
            //{"result":{"code":-99,"desc":"Params Error"}}
            //{"data":{"cid":"1593424117810"},"list":[{"error":"Check your phone number"},{"error":"Check your phone number"},{"error":"Check your phone number"},{"error":"Check your phone number"},{"error":"Check your phone number"},{"error":"Check your phone number"},{"error":"Check your phone number"},{"error":"Check your phone number"},{"error":"Check your phone number"}],"result":{"code":0,"desc":"SUCCESS"}}
            String sendRes = HttpUtil.post(kmiBatchSendLongnumberUrl, params);
//            if(StringUtils.isNotBlank(sendRes) && (sendRes.contains("-101") || sendRes.contains("Token Error"))){
//                //token失效， 重新請求
//                String token2 = kmiUtil.getTokenByUrl();
//                JSONObject sendJsonObj2 = new JSONObject();
//                sendJsonObj2.put("token", token2);
//                sendJsonObj2.put("msisdn", phoneArray.toJSONString());
//                sendJsonObj2.put("message", msgContent);
//                String params2 = sendJsonObj2.toString();
//                sendRes = HttpUtil.post(kmiBatchSendLongnumberUrl, params2);
//            }

            LOGGER.info("--------KMI 批量营销 发送结果,[{}],[{}]",msgContent,sendRes);

            MarketingKmiBatchSendResponseDO responseDO = new MarketingKmiBatchSendResponseDO();
            responseDO.setMarketingSmsTaskRecordPrimaryKey(MarketingSmsTaskRecordPrimaryKey);
            responseDO.setResponseInfo(sendRes);
            marketingKmiBatchSendResponseMapper.insertSelective(responseDO);
            //TODO: 2020/6/23 20:02 by ShenJianKang  批量短信的发送入库记录
            if(StringUtils.isNotBlank(sendRes)){
                String msgId = null;
                String code = null;
                String desc = null;
                JSONObject sendResJsonObj = JSON.parseObject(sendRes);
                if(sendResJsonObj != null){
                    JSONObject dataJsonObj = sendResJsonObj.getJSONObject("data");
                    if(dataJsonObj != null){
                        msgId = dataJsonObj.getString("cid");
                    }
                    JSONObject result = sendResJsonObj.getJSONObject("result");
                    if(result != null){
                        code = result.getString("code");
                        desc = result.getString("desc");
                    }
                    if(StringUtils.isNotBlank(code)){
                        if(!"0".equals(code)){
                            //失败
                            MarketingMessageSendRecordDO sendRecordDO = new MarketingMessageSendRecordDO();

                            sendRecordDO.setAppId(applicationEnmu.getCode());
                            sendRecordDO.setChannelId(ChannelEnum.KMI_LONGNUMBER.getCode());
                            //sendRecordDO.setPhoneNumber(msisdn);
                            sendRecordDO.setMsgText(msgContent);
                            //sendRecordDO.setMsgId(trxid);
                            if(StringUtils.isNotBlank(code)){
                                sendRecordDO.setResCode(Integer.valueOf(code));
                            }
                            if(StringUtils.isNotBlank(desc)){
                                sendRecordDO.setResExplain(desc);
                            }
                            sendRecordDO.setReturnValue(sendRes);
                            //sendRecordDO.setReturnValue(trxdate);//这里存储 trxdate
                            sendRecordDO.setMsgType(null);
                            sendRecordDO.setMarketingSmsTaskRecordPrimaryKey(MarketingSmsTaskRecordPrimaryKey);
                            marketingMessageSendRecordMapper.insertSelective(sendRecordDO);
                            throw new MessageException("KMI 营销短信批量发送 失败：["+sendRes+"]");
                        }else {
                            //成功
                            JSONArray jsonArray = sendResJsonObj.getJSONArray("list");

                            List<MarketingMessageSendRecordDO> insertDOS = new ArrayList<>();
                            if(jsonArray!= null  && jsonArray.size()>0){
                                for(int i=0; i<jsonArray.size(); i++){
                                    JSONObject item = jsonArray.getJSONObject(i);
                                    if(item != null){
                                        String msisdn = item.getString("msisdn");
                                        String trxid = item.getString("trxid");
                                        String trxdate = item.getString("trxdate");
                                        String error = item.getString("error");

                                        MarketingMessageSendRecordDO sendRecordDO = new MarketingMessageSendRecordDO();

                                        sendRecordDO.setAppId(applicationEnmu.getCode());
                                        sendRecordDO.setChannelId(ChannelEnum.KMI_LONGNUMBER.getCode());
                                        sendRecordDO.setPhoneNumber(msisdn);
                                        sendRecordDO.setMsgText(msgContent);
                                        sendRecordDO.setMsgId(trxid);
                                        if(StringUtils.isNotBlank(code)){
                                            sendRecordDO.setResCode(Integer.valueOf(code));
                                        }
                                        if(StringUtils.isNotBlank(desc)){
                                            sendRecordDO.setResExplain(desc);
                                        }
                                        //sendRecordDO.setReturnValue(sendRes);//批量返回的总json太大，不入库
                                        sendRecordDO.setReturnValue(trxdate);//这里存储 trxdate
                                        if(StringUtils.isNotBlank(error)){
                                            sendRecordDO.setReturnValue(error);
                                        }
                                        sendRecordDO.setMsgType(null);
                                        sendRecordDO.setMarketingSmsTaskRecordPrimaryKey(MarketingSmsTaskRecordPrimaryKey);
                                        insertDOS.add(sendRecordDO);

                                    }
                                }
                            }
                            if(insertDOS.size() > 0){
                                marketingMessageSendRecordMapper.batchInsert(insertDOS);
                            }
                        }
                    }
                }
            }else {
                throw new MessageException("KMI 营销短信批量发送 响应结果为空");
            }

        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            throw e;
        }
    }

//    public static void main(String[] args) {
////        List<String> phones = Stream.of("1","2").collect(Collectors.toList());
////        System.out.println("phones = " + phones.toString());
////
////        JSONArray array= JSONArray.parseArray(JSON.toJSONString(phones));
////        System.out.println("array = " + array);
////        System.out.println("array.toJSONString() = " + array.toJSONString());
////        System.out.println("array.toJSONString() = " + array.toString());
//
//        String sendRes = "{\n" +
//                "    \"data\": {\n" +
//                "        \"cid\": \"1554364985764\"\n" +
//                "    },\n" +
//                "    \"list\": [\n" +
//                "        {\n" +
//                "            \"msisdn\": \"6281210506807\",\n" +
//                "            \"trxid\": \"15543649857681087\",\n" +
//                "            \"trxdate\": \"20190404160305\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"msisdn\": \"6281210506808\",\n" +
//                "            \"trxid\": \"15543649857681313\",\n" +
//                "            \"trxdate\": \"20190404160305\"\n" +
//                "        }\n" +
//                "    ],\n" +
//                "    \"result\": {\n" +
//                "        \"code\": 0,\n" +
//                "        \"desc\": \"SUCCESS\"\n" +
//                "    }\n" +
//                "}";
//        JSONObject sendResJsonObj = JSON.parseObject(sendRes);
//        JSONObject dataJsonObj = sendResJsonObj.getJSONObject("data");
//        String msgId = dataJsonObj.getString("cid");
//        JSONObject result = sendResJsonObj.getJSONObject("result");
//        String code = result.getString("code");
//        String desc = result.getString("desc");
//        JSONArray jsonArray = sendResJsonObj.getJSONArray("list");
//        System.out.println("jsonArray.size() = " + jsonArray.size());
//        if(jsonArray!= null  && jsonArray.size()>0){
//            for(int i=0; i<jsonArray.size(); i++){
//                JSONObject item = jsonArray.getJSONObject(i);
//                if(item != null){
//                    String msisdn = item.getString("msisdn");
//                    String trxid = item.getString("trxid");
//                    String trxdate = item.getString("trxdate");
//                    System.out.println("msisdn = " + msisdn);
//                    System.out.println("trxid = " + trxid);
//                    System.out.println("trxdate = " + trxdate);
//                }
//            }
//        }
//
//
//
//    }

    @Override
    public String key() {
        return null;
    }

    @Override
    public boolean isDefault() {
        return false;
    }
}
