package com.panshi.hujin2.message.service.message.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.Adler32;

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


    /**
     *@Description:         post 表单提交
     *@Param:  * @param url
     * @param params
     * @param charset
     *@Author: shenJianKang
     *@date: 2020/5/8 12:12
     */
    public static String postForm(String url, Map<String, String> params, String charset) {
        if(StringUtils.isBlank(charset)){
            charset = "UTF-8";
        }

        URL u = null;
        HttpURLConnection con = null;
        // 构建请求参数
        StringBuffer sb = new StringBuffer();
        if (params != null) {
            for (Map.Entry<String, String> e : params.entrySet()) {
                sb.append(e.getKey());
                sb.append("=");
                sb.append(e.getValue());
                sb.append("&");
            }
            sb.substring(0, sb.length() - 1);
        }
//        System.out.println("send_url:" + url);
//        System.out.println("send_data:" + sb.toString());
        // 尝试发送请求
        try {
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            //// POST 只能为大写，严格限制，post会不识别
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), charset);
            osw.write(sb.toString());
            osw.flush();
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        // 读取返回内容
        StringBuffer buffer = new StringBuffer();
        try {
            //一定要有返回值，否则无法把请求发送给server端。
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
                buffer.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }


}
