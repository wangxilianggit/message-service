package com.panshi.hujin2.message.service.message.infobip.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * create by shenjiankang on 2018/7/17 20:16
 *
 * infobip http请求
 */
public class InfobipHttpUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(InfobipHttpUtil.class);



    private static final String CHARSET = "UTF-8";




    /**
     * 接口调用 GET
     */
    public static String httpURLConectionGet(String reqUrl,
                                             Map<String,String> urlParamMap,
                                             Map<String, String> requestHeaderMap) throws Exception{
        //拼接url后面的参数
        if(urlParamMap != null){
            reqUrl = reqUrl + "?";
            for (Iterator<String> iterator = urlParamMap.keySet().iterator(); iterator.hasNext();) {
                String key = iterator.next();
                String temp = key + "=" + urlParamMap.get(key) + "&";
                reqUrl += temp;
            }
            reqUrl = reqUrl.substring(0, reqUrl.length() - 1);
        }

        LOGGER.info("--------请求URL：[{}]",reqUrl);

        URL url = new URL(reqUrl);    //把字符串转换为URL请求地址
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接

        //setRequestProperty添加相同的key会覆盖value信息
        //setRequestProperty方法，如果key存在，则覆盖；不存在，直接添加。
        //addRequestProperty方法，不管key存在不存在，直接添加。
        //addRequestProperty添加相同的key不会覆盖，如果相同，内容会以{name1,name2}
        if(requestHeaderMap != null){
            for (Iterator<String> iterator = requestHeaderMap.keySet().iterator();iterator.hasNext();){
                String key = iterator.next();
                connection.setRequestProperty(key,requestHeaderMap.get(key));

                LOGGER.info("--------拼接的请求头信息[{}]:[{}]",key,requestHeaderMap.get(key));
            }
        }

//            InetAddress address = InetAddress.getLocalHost();
//            String ip=address.getHostAddress();//获得本机IP
//            connection.setRequestProperty("ip",ip);  //请求来源IP

        connection.connect();// 连接会话
        // 获取输入流
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {// 循环读取流
            sb.append(line);
        }
        br.close();// 关闭流
        connection.disconnect();// 断开连接

        return String.valueOf(sb);

    }


}
