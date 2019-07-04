package com.panshi.hujin2.message.service.message.kmi;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.common.utils.HttpUtil;
import com.panshi.hujin2.message.domain.exception.MessageException;
import com.panshi.hujin2.message.service.message.impl.SendMsg;
import com.panshi.hujin2.message.service.message.utils.MsgUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shenJianKang
 * @date 2019/7/4 9:25
 */
@Service("KMIService")
public class KMIServiceImpl extends SendMsg {
    private static Logger LOGGER = LoggerFactory.getLogger(KMIServiceImpl.class);

    @Value("${kmi.account}")
    private String kmiAccount;

    @Value("${kmi.pwd}")
    private String kmiPwd;

    @Value("${kmi.token.url}")
    private String KmiTokenUrl;

    @Value("${kmi.send.msg.url}")
    private String kmiSendMsgUrl;

    private final String TOKEN_KEY = "KMI_TOKEN";
    private final long VALID_TIME = 60 * 60 * 1000 * 2;



    @Override
    public String key() {
        return null;
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    /**
     *@Description:     获取请求KMI需要的token
     *@Param:  * @param
     *@Author: shenJianKang
     *@date: 2019/7/4 10:10
     */
    private String getToken() throws Exception{
        Object tokenObj = MsgUtils.expiryMap.get(TOKEN_KEY);
        String token = null;
        if(tokenObj == null){
            token = HttpUtil.get(KmiTokenUrl);
            if(StringUtils.isBlank(token)){
                boolean flag = true;
                int num = 0;
                //重试
                while (flag && num<5){
                    try {
                        Thread.sleep(2000);
                        token = HttpUtil.get(KmiTokenUrl);
                        num ++;
                        if(StringUtils.isNotBlank(token)){
                            flag = false;
                        }
                    }catch (Exception e){
                        LOGGER.error(e.getMessage(),e);
                        throw e;
                    }
                }
            }
        }else {
            token = String.valueOf(tokenObj);
        }
        if(StringUtils.isBlank(token)){
            LOGGER.error(" ======== KMI TOKEN 获取失败！");
            throw new MessageException("KMI TOKEN 获取失败！");
        }
        MsgUtils.expiryMap.put(TOKEN_KEY,token, VALID_TIME);
        return token;
    }

    @Override
    public boolean sendInternationalMsg(ApplicationEnmu applicationEnmu,
                                        String phoneNumber,
                                        String msgText,
                                        Context context) {
        try {
            String token = getToken();
            Map<String, Object> map = new HashMap<>();
            map.put("token",token);
            map.put("sendType",1);//发送类型 1.LongNumber
            map.put("msisdn","62"+phoneNumber);//短信接收方号码，格式为62xxxx如：6281210506807 （只支持印尼号码）
            map.put("message",msgText);//短信内容
            String res = HttpUtil.post(kmiSendMsgUrl, map);
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return false;
        }



        return false;
    }

    @Override
    public void batchSendSameTemplateSameParam(ApplicationEnmu applicationEnmu, List phoneNumberList, String templateCode, List paramList, Context context) {

    }
}
