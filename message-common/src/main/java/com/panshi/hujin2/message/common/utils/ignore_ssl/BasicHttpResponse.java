package com.panshi.hujin2.message.common.utils.ignore_ssl;

import lombok.Data;

@Data
public class BasicHttpResponse {
    /**
     * 响应码
     */
    private int statusCode;
    /**
     * 页面内容
     */
    private String htmlContent;
}
