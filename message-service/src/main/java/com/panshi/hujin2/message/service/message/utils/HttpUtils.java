package com.panshi.hujin2.message.service.message.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * create by shenjiankang on 2018/8/6 20:44
 */
public class HttpUtils {


    /**
     * @description:            httpPost
     * @param generalUrl        请求url
     * @param contentType       请求头中的Content-Type对应的值
     * @param postParams        POST请求参数
     * @param encoding          编码格式
     * @param requestHeaderMap  拼接到请求头中的参数
     * @Author shenjiankang
     * @Date 2018/7/18 21:58
     */
    public static String postGeneralUrl(String generalUrl,
                                        String contentType,
                                        String postParams,
                                        String encoding,
                                        Map<String, String> requestHeaderMap) throws Exception {
        if (contentType == null) {
//            contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE;
//            contentType = "application/x-www-form-urlencoded";
            contentType = "application/json";
        }
        if (encoding == null) {
            encoding = "UTF-8";
        }
        URL url = new URL(generalUrl);
        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        // 设置通用的请求属性
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestProperty("Connection", "Keep-Alive");

        if(requestHeaderMap != null){
            for (Iterator<String> iterator = requestHeaderMap.keySet().iterator(); iterator.hasNext();){
                String key = iterator.next();
                connection.setRequestProperty(key,requestHeaderMap.get(key));
            }
        }

        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        // 得到请求的输出流对象
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.write(postParams.getBytes(encoding));
        out.flush();
        out.close();

        // 建立实际的连接
        connection.connect();
        // 获取所有响应头字段
        Map<String, List<String>> headers = connection.getHeaderFields();
        // 遍历所有的响应头字段
        for (String key : headers.keySet()) {
            System.err.println(key + "--->" + headers.get(key));
        }
        // 定义 BufferedReader输入流来读取URL的响应
        BufferedReader in = null;
        in = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), encoding));
        StringBuilder result = new StringBuilder();
        String getLine;
        while ((getLine = in.readLine()) != null) {
            result.append(getLine);
        }
        in.close();
        return String.valueOf(result);
    }
}
