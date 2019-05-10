package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

/**
 * create by shenjiankang on 2018/6/23 9:59
 */
@Data
public class MsgResponseResultInputBO {


    /**
     *  提交短信时的手机号码
     */
    private String mobile;
    /**
     *  发送结果状态
     */
    private String status;
    /**
     *  消息 id
     */
    private String msgId;
    /**
     *  接收状态报告验证的用户名（不是账户名），是按照用户要求配置的名称，默认为空
     */
    private String receiver;
    /**
     *  接收状态报告验证的密码，默认为空
     */
    private String pswd;
    /**
     *  格式YYMMDDhhmm，其中YY=年份的最后两位（00-99），MM=月份（01-12），DD=日（01-31），hh=小时（00-23），mm=分钟（00-59）网关平台返回的时间 有时差.网关不同，格式有偏差，以具体返回格式为准。
     */
    private String reportTime;
    /**
     *  平台收到运营商回复状态报告的时间，格/式 yyyyMMddHHmmss
     */
    private String notifyTime;
    /**
     *  场景名（英文或者拼音）-批次编号" //自助通系统内使用 UID 判断短信使用的场景类型，可重复使用，可自定义场景名称，示例如 VerificationCode（选填参数）
     */
    private String uid;

}
