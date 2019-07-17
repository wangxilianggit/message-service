package com.panshi.hujin2.message.service.message.sms.bo;

import lombok.Data;

/**
 * @author shenJianKang
 * @date 2019/7/8 17:46
 */
@Data
public class SMSSendBO {


    private String from;
    private String to;
    private String body;
}
