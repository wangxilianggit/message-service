package com.panshi.hujin2.message.service.message.aliyun;

import lombok.Data;

/**
 * @Deacription 阿里云短信结果
 * @Author 20112128
 * @Date 2020/5/25 17:14
 **/
@Data
public class AliyunMessageResponse {

    private String Message;

    private String RequestId;

    private String BizId;

    private String Code;
}
