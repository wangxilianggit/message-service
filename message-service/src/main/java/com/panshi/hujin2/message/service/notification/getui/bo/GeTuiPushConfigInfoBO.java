package com.panshi.hujin2.message.service.notification.getui.bo;

import lombok.Data;

/**
 * create by shenjiankang on 2018/10/8 17:38
 *
 * 个推消息推送应用配置信息
 */
@Data
public class GeTuiPushConfigInfoBO {
    private String appId;
    private String appKey;
    private String appSecret;
    private String masterSecret;

}
