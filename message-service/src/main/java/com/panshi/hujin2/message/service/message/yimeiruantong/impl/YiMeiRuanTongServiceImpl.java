package com.panshi.hujin2.message.service.message.yimeiruantong.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;
import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.domain.enums.ChannelEnum;
import com.panshi.hujin2.message.domain.enums.YiMeiRuanTongResponseEnum;
import com.panshi.hujin2.message.domain.exception.MessageException;
import com.panshi.hujin2.message.facade.bo.MessageSendRecordInputBO;
import com.panshi.hujin2.message.service.message.impl.SendMsg;
import com.panshi.hujin2.message.service.message.utils.ExceptionMessageUtils;
import com.panshi.hujin2.message.service.message.yimeiruantong.dto.CustomImsIdAndMobile;
import com.panshi.hujin2.message.service.message.yimeiruantong.dto.CustomImsIdAndMobileAndContent;
import com.panshi.hujin2.message.service.message.yimeiruantong.dto.request.ImsBatchRequest;
import com.panshi.hujin2.message.service.message.yimeiruantong.dto.request.ImsPersonalityRequest;
import com.panshi.hujin2.message.service.message.yimeiruantong.dto.request.ImsReportRequest;
import com.panshi.hujin2.message.service.message.yimeiruantong.dto.request.ImsSingleRequest;
import com.panshi.hujin2.message.service.message.yimeiruantong.dto.response.ImsResponse;
import com.panshi.hujin2.message.service.message.yimeiruantong.dto.response.ReportResponse;
import com.panshi.hujin2.message.service.message.yimeiruantong.dto.response.ResponseData;
import com.panshi.hujin2.message.service.message.yimeiruantong.util.DateUtil;
import com.panshi.hujin2.message.service.message.yimeiruantong.util.JsonHelper;
import com.panshi.hujin2.message.service.message.yimeiruantong.util.Md5;
import com.panshi.hujin2.message.service.message.yimeiruantong.util.http.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * create by shenjiankang on 2018/9/29 14:45
 *
 * 亿美软通
 */
@Service("yimeiruantongService")
public class YiMeiRuanTongServiceImpl extends SendMsg {
    private static Logger LOGGER = LoggerFactory.getLogger(YiMeiRuanTongServiceImpl.class);


    @Value("${em.single.url}")
    private String emSingleUrl;

    @Value("${em.appid}")
    private String emAppId;

    @Value("${em.secretkey}")
    private String emSecretKey;





    @Override
    public String key() {
        return null;
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    @Override
    public boolean sendInternationalMsg(ApplicationEnmu applicationEnmu, String phoneNumber, String msgText, Context context,Integer msgType) {
        LOGGER.info("--------开始通过【tainyihong】发送短信,。。。手机号：[{}]，发送内容[{}]",phoneNumber, msgText);
        ExceptionMessageUtils.verifyObjectIsNull(context, applicationEnmu);
        ExceptionMessageUtils.verifyStringIsBlank(context,phoneNumber,msgText);

        // 时间戳
        String timestamp = DateUtil.toString(new Date(), "yyyyMMddHHmmss");
        // 签名
        String sign = Md5.md5((emAppId + emSecretKey + timestamp).getBytes());
        //自定义消息ID(选填) 最长32位;这里用时间戳的格式
        String customImsId = DateUtil.toString(new Date(), "yyyyMMddHHmmssSSS");
        // 发送单条短信
        String res = sendSingleIMS(phoneNumber,msgText, customImsId, timestamp, sign);
        if (StringUtils.isNotBlank(res)) {
//            ResponseData<ImsResponse> data = JsonHelper.fromJson(new TypeToken<ResponseData<ImsResponse>>() {}, res);
//            String code = data.getCode();
            JSONObject jsonObject = JSON.parseObject(res);
            String code = jsonObject.getString("code");
            JSONObject data = jsonObject.getJSONObject("data");
            String msgId="";
            if(data != null){
                msgId= data.getString("imsId");
            }

            // 发送记录数据入库
            MessageSendRecordInputBO inputBO = new MessageSendRecordInputBO();
            inputBO.setAppId(applicationEnmu.getCode());
            inputBO.setChannelId(ChannelEnum.YIMEIRUANTONG.getCode());
            inputBO.setPhoneNumber(phoneNumber);
            inputBO.setMsgText(msgText);
            inputBO.setReturnValue(res);
            inputBO.setMsgId(msgId);
            inputBO.setMsgType(msgType);
            if(StringUtils.isNotBlank(code)){
                String resExplain = YiMeiRuanTongResponseEnum.getStatusDesc(code).getDesc();
                inputBO.setResExplain(resExplain);
            }
            msgDBService.addMsgSendRecord(inputBO,context);
            if (YiMeiRuanTongResponseEnum.SUCCESS.getType().equals(code)) {
                return true;
            }else {
                throw new MessageException(inputBO.getResExplain());
            }
        }
        return false;
    }


    @Override
    public void batchSendSameTemplateSameParam(ApplicationEnmu applicationEnmu, List phoneNumberList, String templateCode, List paramList, Context context) {
        //// TODO: 2018/9/29 相同内容群发
    }


    @Override
    public void batchSendSameTemplateDiffParam(ApplicationEnmu applicationEnmu,
                                               Map<String, List> paramMap,
                                               String templateCode,
                                               Context context) {
        //// TODO: 2018/9/29 亿美软通，个性化群发
        //同模不同参
    }


    /**
     * 发送单条短信
     *
     * @param mobile
     * @param content
     * @param customImsId
     * @param timestamp
     * @param sign
     */
    private String sendSingleIMS(String mobile, String content, String customImsId , String timestamp, String sign) {
        ImsSingleRequest pamars = new ImsSingleRequest();
        pamars.setAppId(emAppId);
        pamars.setContent(content);
        pamars.setCustomImsId(customImsId);
        //亿美软通需要在国家区号前拼上00，如0086137XXXXXXX
        pamars.setMobile("00"+mobile);
        pamars.setTimestamp(timestamp);
        pamars.setSign(sign);
        String json = request(pamars, emSingleUrl);
        return json;
    }

    /**
     * 发送批次短信
     *
     * @param appId
     * @param host
     * @param content
     * @param customImsIdAndMobiles
     * @param timestamp
     * @param sign
     */
    private void sendBatchIMS(String appId, String host, String content, CustomImsIdAndMobile[] customImsIdAndMobiles, String timestamp, String sign) {
        LOGGER.info("=============begin sendBatchIMS==================");
        ImsBatchRequest pamars = new ImsBatchRequest();
        pamars.setImses(customImsIdAndMobiles);
        pamars.setContent(content);
        pamars.setAppId(appId);
        pamars.setTimestamp(timestamp);
        pamars.setSign(sign);
        String json = request(pamars, host + "/inter/sendBatchIMS");
        if (json != null) {
            ResponseData<ImsResponse[]> data = JsonHelper.fromJson(new TypeToken<ResponseData<ImsResponse[]>>() {
            }, json);
            String code = data.getCode();
            if ("SUCCESS".equals(code)) {
                for (ImsResponse imsResponse : data.getData()) {
                    LOGGER.info("result data:" + imsResponse.getMobile() + "," + imsResponse.getImsId() + "," + imsResponse.getCustomImsId());
                }
            }
        }
        LOGGER.info("=============end sendBatchIMS==================");
    }

    /**
     * 发送个性短信
     *
     * @param appId
     * @param host
     * @param customImsIdAndMobileAndContents
     * @param timestamp
     * @param sign
     */
    private void sendPersonalityIMS(String appId, String host, CustomImsIdAndMobileAndContent[] customImsIdAndMobileAndContents, String timestamp, String sign) {
        LOGGER.info("=============begin sendPersonalityIMS==================");
        ImsPersonalityRequest pamars = new ImsPersonalityRequest();
        pamars.setImses(customImsIdAndMobileAndContents);
        pamars.setAppId(appId);
        pamars.setTimestamp(timestamp);
        pamars.setSign(sign);
        String json = request(pamars, host + "/inter/sendPersonalityIMS");
        if (json != null) {
            ResponseData<ImsResponse[]> data = JsonHelper.fromJson(new TypeToken<ResponseData<ImsResponse[]>>() {
            }, json);
            String code = data.getCode();
            if ("SUCCESS".equals(code)) {
                for (ImsResponse imsResponse : data.getData()) {
                    LOGGER.info("result data:" + imsResponse.getMobile() + "," + imsResponse.getImsId() + "," + imsResponse.getCustomImsId());
                }
            }
        }
        LOGGER.info("=============end sendPersonalityIMS==================");
    }

    /**
     * 获取状态报告
     *
     * @param appId
     * @param host
     * @param timestamp
     * @param sign
     * @param number
     */
    private void getIMSReport(String appId, String host, String timestamp, String sign, int number) {
        LOGGER.info("=============begin getIMSReport==================");
        ImsReportRequest pamars = new ImsReportRequest();
        pamars.setAppId(appId);
        pamars.setTimestamp(timestamp);
        pamars.setSign(sign);
        pamars.setNumber(number);
        String json = request(pamars, host + "/inter/getIMSReport");
        if (json != null) {
            ResponseData<ReportResponse[]> data = JsonHelper.fromJson(new TypeToken<ResponseData<ReportResponse[]>>() {
            }, json);
            String code = data.getCode();
            if ("SUCCESS".equals(code)) {
                for (ReportResponse reportResponse : data.getData()) {
                    LOGGER.info("result data:" + reportResponse.getMobile() + "," + reportResponse.getImsId() + "," + reportResponse.getCustomImsId());
                }
            }
        }
        LOGGER.info("=============end getIMSReport==================");
    }

    /**
     * 公共请求方法
     */
    public String request(Object requestParams, String url) {
        HttpRequest<byte[]> request = null;
        try {
            String requestJson = JsonHelper.toJsonString(requestParams);
            LOGGER.info("request json: " + requestJson);
            byte[] bytes = requestJson.getBytes("UTF-8");
            LOGGER.info("request data size : " + bytes.length);
            HttpRequestParams<byte[]> params = new HttpRequestParams<byte[]>();
            params.setCharSet("UTF-8");
            params.setMethod("POST");
            params.setParams(bytes);
            params.setUrl(url);
            if (url.startsWith("https://")) {
                request = new HttpsRequestBytes(params, null);
            } else {
                request = new HttpRequestBytes(params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpClient client = new HttpClient();
        String code = null;
        String json = null;
        try {
            HttpResponseBytes res = client.service(request, new HttpResponseBytesPraser());
            if (res == null) {
                LOGGER.info("请求接口异常");
                return null;
            }
            if (res.getResultCode().equals(HttpResultCode.SUCCESS)) {
                if (res.getHttpCode() == 200) {
                    code = res.getResultCode().getCode();
                    if (code.equals("SUCCESS")) {
                        byte[] data = res.getResult();
                        LOGGER.info("response data size : " + data.length);
                        json = new String(data, "UTF-8");
                        LOGGER.info("response json: " + json);
                    }
                } else {
                    LOGGER.info("请求接口异常,请求码:" + res.getHttpCode());
                }
            } else {
                LOGGER.info("请求接口网络异常:" + res.getResultCode().getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
