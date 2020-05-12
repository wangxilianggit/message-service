package com.panshi.hujin2.message.service.message.kmi;

import com.gexin.fastjson.JSON;
import com.gexin.fastjson.JSONObject;
import com.panshi.hujin2.message.common.utils.HttpUtil;
import com.panshi.hujin2.message.dao.mapper.message.KmiTokenLogMapper;
import com.panshi.hujin2.message.dao.model.KmiTokenLogDO;
import com.panshi.hujin2.message.domain.exception.MessageException;
import com.panshi.hujin2.message.service.message.utils.MsgUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author shenJianKang
 * @date 2020/5/12 16:43
 */
@Component
public class KMIUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(KMIUtil.class);

    public static final String TOKEN_KEY = "KMI_TOKEN";
    public static final String KmiTokenUrl = "http://cs.kmindo.com:9980/cs/login?account=EasyKlick&password=b99846c549c57aa213fa8fe0033afdea";

    @Autowired
    private KmiTokenLogMapper kmiTokenLogMapper;


    /**
     *@Description:     获取请求KMI需要的token
     *@Param:  * @param
     *@Author: shenJianKang
     *@date: 2019/7/4 10:10
     */
    public String getToken() throws Exception{
        Object tokenObj = MsgUtils.expiryMap.get(TOKEN_KEY);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        System.out.println("======當前時間 = " + sdf.format(new Date()));
        System.out.println("======當前tokenObj = " + tokenObj);

        String token = null;
        if(tokenObj == null){
            LOGGER.info("map为空获取KMI 请求 [{}]",KmiTokenUrl);
            String result = HttpUtil.get(KmiTokenUrl);
            LOGGER.info("map为空获取KMI token [{}]",result);
            saveReqTokenLog(KmiTokenUrl+"_____"+result);

            if(StringUtils.isNotBlank(result)){
                //解析result
                //{"data":{"token":"440E8E87F36D52E60631F892689D62E14A19623FE9147EC2FC1947E2D4AF5285","balance":"0.00"},"result":{"code":0,"desc":"SUCCESS"}}
                JSONObject jsonObject = JSON.parseObject(result);
                JSONObject res = jsonObject.getJSONObject("result");
                if(res != null){
                    String code = res.getString("code");
                    if("0".equals(code)){
                        JSONObject data = jsonObject.getJSONObject("data");
                        if(data != null){
                            token = data.getString("token");
                            MsgUtils.expiryMap.put(TOKEN_KEY,token, 50 * 60 * 1000 * 2);
//                            if(StringUtils.isNotBlank(token)){
//                                return token;
//                            }
                        }
                    }
                }
            }else {
                //重试机制
                boolean flag = true;
                int num = 0;
                //重试
                while (flag && num<10){
                    try {
                        Thread.sleep(1000);
                        LOGGER.info("重试获取KMI 请求 [{}]",KmiTokenUrl);
                        result = HttpUtil.get(KmiTokenUrl);
                        LOGGER.info("重试获取KMI token [{}]",result);
                        saveReqTokenLog(KmiTokenUrl+"______"+result);

                        num ++;
                        if(StringUtils.isNotBlank(result)){
                            JSONObject jsonObject = JSON.parseObject(result);
                            JSONObject res = jsonObject.getJSONObject("result");
                            if(res != null){
                                String code = res.getString("code");
                                if("0".equals(code)){
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    if(data != null){
                                        token = data.getString("token");
                                        MsgUtils.expiryMap.put(TOKEN_KEY,token, 50 * 60 * 1000 * 2);
                                        //结束重试
                                        flag = false;
                                    }
                                }
                            }
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
        return token;
    }

    public String getTokenByUrl(){
//        String pwd = MD5Util.MD5(kmiPwd);
//        KmiTokenUrl = KmiTokenUrl + pwd;//全局變量不能重複 拼接
        LOGGER.info("map为空获取KMI 请求 [{}]",KmiTokenUrl);
        String result = HttpUtil.get(KmiTokenUrl);
        LOGGER.info("map为空获取KMI token [{}]",result);
        saveReqTokenLog(KmiTokenUrl+"___"+result);

        String token = "";
        if(StringUtils.isNotBlank(result)){
            //解析result
            //{"data":{"token":"440E8E87F36D52E60631F892689D62E14A19623FE9147EC2FC1947E2D4AF5285","balance":"0.00"},"result":{"code":0,"desc":"SUCCESS"}}
            JSONObject jsonObject = JSON.parseObject(result);
            JSONObject res = jsonObject.getJSONObject("result");
            if(res != null){
                String code = res.getString("code");
                if("0".equals(code)){
                    JSONObject data = jsonObject.getJSONObject("data");
                    if(data != null){
                        token = data.getString("token");
//                            if(StringUtils.isNotBlank(token)){
//                                return token;
//                            }
                        MsgUtils.expiryMap.put(TOKEN_KEY,token, 50 * 60 * 1000 * 2);
                    }
                }
            }
        }
        return token;
    }

    /**
     *@Description:     保存获取token的日志
     *@Param:  * @param result
     *@Author: shenJianKang
     *@date: 2019/7/23 11:19
     */
    public Integer saveReqTokenLog(String result){
        KmiTokenLogDO tokenLogDO = new KmiTokenLogDO();
        tokenLogDO.setResult(result);
        return kmiTokenLogMapper.insertSelective(tokenLogDO);
    }

}
