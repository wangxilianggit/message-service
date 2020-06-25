package com.panshi.hujin2.message.service.message.nx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.common.utils.HttpUtil;
import com.panshi.hujin2.message.dao.mapper.message.MarketingMessageSendRecordMapper;
import com.panshi.hujin2.message.dao.model.MarketingMessageSendRecordDO;
import com.panshi.hujin2.message.domain.enums.ChannelEnum;
import com.panshi.hujin2.message.facade.bo.BatchSendSelfDefinedMsgBO;
import com.panshi.hujin2.message.facade.bo.MessageSendRecordInputBO;
import com.panshi.hujin2.message.service.message.impl.SendMsg;
import com.panshi.hujin2.message.service.message.utils.HttpUtils;
import com.panshi.hujin2.message.service.message.utils.MsgUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shenJianKang
 * @date 2020/6/24 16:46
 *
 * 牛信 营销短信  （账户区分otp）
 */

@Service("nxMarketingService")
public class NXMarketingServiceImpl extends SendMsg {

    private static Logger LOGGER = LoggerFactory.getLogger(NXMarketingServiceImpl.class);


    //HTTPS接口地址：https://api.nxcloud.com; 如果用户服务器在国内则建议对接地址使用：http://apichina.nxcloud.com:9099
    //POST,参数不要传body,用form表单方式提交
//    static String reqUrl = "http://api.nxcloud.com/api/sms/mtsend";
//    static String reqUrl2 = "http://apichina.nxcloud.com:9099";
//    static String appkey = "1wB7jHly";
//    static String secretkey = "ec9EOu3i";

    @Value("${sms.niuxin.request_url}")
    private String reqUrl;
    @Value("${sms.niuxin.marketing.appkey}")
    private String appkey;
    @Value("${sms.niuxin.marketing.secretkey}")
    private String secretkey;


    @Autowired
    private MarketingMessageSendRecordMapper marketingMessageSendRecordMapper;


    @Override
    public boolean sendInternationalMsg(ApplicationEnmu applicationEnmu, String phoneNumber, String msgText, Context context,Integer msgType) {
//        String phoneNumber = "6281285290562";
//        String msgText = "Kode verifikasi anda untuk login ke cashdog adalah 1111";

        try {
            String msgTextEncode = URLEncoder.encode(msgText, "UTF-8");

            Map<String, String> map = new HashMap<>();
            map.put("appkey", appkey);//必选          是	string	短信应用appkey
            map.put("secretkey", secretkey);//必选       是	string	短信应用secretkey
            map.put("phone", phoneNumber);//必选  是	string	被叫号码(国码+手机号，比如：8615088888888),可以多个并且以”,”英文逗号隔开
            map.put("content", msgTextEncode);//必选    是	string	短信内容,必须做urlencode(UFT-8)
//        sendJsonObj.put("source_address", "");//必选  否	string	sourceaddress/sender
//        sendJsonObj.put("task_time", "");//必选       否	string	定时时间yyyy-MM-dd HH:mm:ss（时区为GMT+8）
//        sendJsonObj.put("short_link", "");//必选      否	string	短链，数据来自于短链列表；如果此处赋值，短信内容里面必须包含#1#才能起作用，请注意
//        sendJsonObj.put("sys_messageid", "");//必选   否	string	用户自定义messageid，长度为10-50位之间，类型【0-9a-zA-Z-】

            //{"result":"参数错误或为空","messageid":"","code":"2"}
            //{"result":"请求成功","messageid":"d235116f4b6143eca7225b17ceee515d","code":"0"}
            String sendRes = HttpUtils.postForm(reqUrl, map,"UTF-8");

            String messageid = null;
            String code = null;
            String result = null;
            if(StringUtils.isNotBlank(sendRes)){
                JSONObject jsonObj = JSON.parseObject(sendRes);
                messageid = jsonObj.getString("messageid");
                code = jsonObj.getString("code");
                result = jsonObj.getString("result");
            }

            MessageSendRecordInputBO inputBO = new MessageSendRecordInputBO();
            inputBO.setAppId(applicationEnmu.getCode());
            inputBO.setChannelId(ChannelEnum.NIU_XIN_MARKETING.getCode());
            inputBO.setPhoneNumber(phoneNumber);
            inputBO.setMsgText(msgText);
            if(StringUtils.isNotBlank(messageid)){
                inputBO.setMsgId(messageid);
            }
            if(StringUtils.isNotBlank(code)){
                inputBO.setResCode(Integer.valueOf(code));
            }
            if(StringUtils.isNotBlank(result)){
                inputBO.setResExplain(result);
            }

            inputBO.setReturnValue(sendRes);
            inputBO.setMsgType(msgType);
            //TODO: 2020/6/24 17:15 by ShenJianKang  发送记录入库的表，贷超应用发送 和营销短信发送 入库表 不同
            msgDBService.addMsgSendRecord(inputBO,context);

        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public void batchSendSelfDefinedMsg(BatchSendSelfDefinedMsgBO bo,
                                        Context context)throws Exception{
        try {
            ApplicationEnmu applicationEnmu = bo.getApplicationEnmu();
            List<String> phoneNumberList = bo.getPhoneNumberList();
            String msgContent = bo.getMsgContent();
            if(CollectionUtils.isEmpty(phoneNumberList)){
                return;
            }

            List<MarketingMessageSendRecordDO> insertDOS = new ArrayList<>();
            for(String phoneNumber:phoneNumberList){
                String msgTextEncode = URLEncoder.encode(msgContent, "UTF-8");

                Map<String, String> map = new HashMap<>();
                map.put("appkey", appkey);//必选          是	string	短信应用appkey
                map.put("secretkey", secretkey);//必选       是	string	短信应用secretkey
                map.put("phone", phoneNumber);//必选  是	string	被叫号码(国码+手机号，比如：8615088888888),可以多个并且以”,”英文逗号隔开
                map.put("content", msgTextEncode);//必选    是	string	短信内容,必须做urlencode(UFT-8)
//              map.put("source_address", "");//必选  否	string	sourceaddress/sender
//              map.put("task_time", "");//必选       否	string	定时时间yyyy-MM-dd HH:mm:ss（时区为GMT+8）
//              map.put("short_link", "");//必选      否	string	短链，数据来自于短链列表；如果此处赋值，短信内容里面必须包含#1#才能起作用，请注意
//              map.put("sys_messageid", "");//必选   否	string	用户自定义messageid，长度为10-50位之间，类型【0-9a-zA-Z-】

                //{"result":"参数错误或为空","messageid":"","code":"2"}
                //{"result":"请求成功","messageid":"d235116f4b6143eca7225b17ceee515d","code":"0"}
                String sendRes = HttpUtils.postForm(reqUrl, map,"UTF-8");

                String messageid = null;
                String code = null;
                String result = null;
                if(StringUtils.isNotBlank(sendRes)){
                    JSONObject jsonObj = JSON.parseObject(sendRes);
                    messageid = jsonObj.getString("messageid");
                    code = jsonObj.getString("code");
                    result = jsonObj.getString("result");
                }

                MarketingMessageSendRecordDO sendRecordDO = new MarketingMessageSendRecordDO();

                sendRecordDO.setAppId(applicationEnmu.getCode());
                sendRecordDO.setChannelId(ChannelEnum.NIU_XIN_MARKETING.getCode());
                sendRecordDO.setPhoneNumber(phoneNumber);
                sendRecordDO.setMsgText(msgContent);
                if(StringUtils.isNotBlank(messageid)){
                    sendRecordDO.setMsgId(messageid);
                }
                if(StringUtils.isNotBlank(code)){
                    sendRecordDO.setResCode(Integer.valueOf(code));
                }
                if(StringUtils.isNotBlank(result)){
                    sendRecordDO.setResExplain(result);
                }

                sendRecordDO.setReturnValue(sendRes);
                sendRecordDO.setMsgType(null);
                insertDOS.add(sendRecordDO);
                //TODO: 2020/6/24 17:15 by ShenJianKang  发送记录入库的表，贷超应用发送 和营销短信发送 入库表 不同
            }
            if(insertDOS.size() > 0){
                marketingMessageSendRecordMapper.batchInsert(insertDOS);
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            throw e;
        }
    }


    @Override
    public void batchSendSameTemplateSameParam(ApplicationEnmu applicationEnmu, List phoneNumberList, String templateCode, List paramList, Context context) {

    }


    @Override
    public String key() {
        return null;
    }
    @Override
    public boolean isDefault() {
        return false;
    }
}
