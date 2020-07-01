package com.panshi.hujin2.message.service.message.kmi;

import com.gexin.fastjson.JSON;
import com.gexin.fastjson.JSONObject;
import com.panshi.hujin2.message.common.utils.HttpUtil;
import com.panshi.hujin2.message.common.utils.MD5Util;
import com.panshi.hujin2.message.dao.mapper.message.KmiTokenLogMapper;
import com.panshi.hujin2.message.dao.model.KmiTokenLogDO;
import com.panshi.hujin2.message.domain.exception.MessageException;
import com.panshi.hujin2.message.facade.bo.kmi.KMITokenBalanceInfoBO;
import com.panshi.hujin2.message.service.message.utils.MsgUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    public static final String KmiTokenUrl = "http://cs.kmindo.com:9980/cs/login?";

    @Value("${kmi.account}")
    private String account;

    @Value("${kmi.pwd}")
    private String pwd;

    @Autowired
    private KmiTokenLogMapper kmiTokenLogMapper;


    private String requestKmiToken(){
        // 拼接KmiTokenUrl
        String pwdMD5 = MD5Util.MD5(pwd);
        String KmiTokenUrlTemp = KmiTokenUrl+"account="+account+"&password="+pwdMD5;
        //{"data":{"token":"8859F58D08C57C42CFDA8B5F670FCD73EF286D39730F1D6DD9755034AE20D533","balance":"0.00","smsBalance":"1328550.00"},"result":{"code":0,"desc":"SUCCESS"}}
        LOGGER.info("map为空获取KMI 请求 [{}]",KmiTokenUrlTemp);
        String result = HttpUtil.get(KmiTokenUrlTemp);
        LOGGER.info("map为空获取KMI token [{}]",result);
        saveReqTokenLog(KmiTokenUrlTemp+"_____"+result);
        return result;
    }

    /**
     *@Description:     直接从API获取token、 账户余额；忽略缓存
     *@Param:  * @param
     *@Author: shenJianKang
     *@date: 2020/6/29 16:25
     */
    public KMITokenBalanceInfoBO getKMITokenAndBalanceIgnoreCache(){
        KMITokenBalanceInfoBO bo = new KMITokenBalanceInfoBO();
        String result = requestKmiToken();
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
                        String token = data.getString("token");
                        String balance = data.getString("balance");
                        bo.setToken(token);
                        if(StringUtils.isNotBlank(balance)){
                            bo.setBalance(Double.valueOf(balance));
                        }
                        String smsBalance = data.getString("smsBalance");
                        if(StringUtils.isNotBlank(smsBalance)){
                            bo.setSmsBalance(Double.valueOf(smsBalance));
                        }
                    }
                }
                String desc = res.getString("desc");
                bo.setCode(code);
                bo.setDesc(desc);
            }
        }
        return bo;
    }


    /**
     *@Description:     获取请求KMI需要的token (缓存有，优先从缓存获取)
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

            // 拼接KmiTokenUrl
            String pwdMD5 = MD5Util.MD5(pwd);
            String KmiTokenUrlTemp = KmiTokenUrl+"account="+account+"&password="+pwdMD5;

            LOGGER.info("map为空获取KMI 请求 [{}]",KmiTokenUrlTemp);
            String result = HttpUtil.get(KmiTokenUrlTemp);
            LOGGER.info("map为空获取KMI token [{}]",result);
            saveReqTokenLog(KmiTokenUrlTemp+"_____"+result);

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
                        LOGGER.info("重试获取KMI 请求 [{}]",KmiTokenUrlTemp);
                        result = HttpUtil.get(KmiTokenUrlTemp);
                        LOGGER.info("重试获取KMI token [{}]",result);
                        saveReqTokenLog(KmiTokenUrlTemp+"______"+result);

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

        String pwdMD5 = MD5Util.MD5(pwd);
        String KmiTokenUrlTemp = KmiTokenUrl+"account="+account+"&password="+pwdMD5;
        String result = HttpUtil.get(KmiTokenUrlTemp);
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
