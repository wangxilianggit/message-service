package com.panshi.hujin2.message.dao.model.fcm;

import lombok.Data;

import java.util.Date;

@Data
public class UserFcmRelationDO {
    /**
     *  id
     */
    private Integer id;

    /**
     *  app应用id
     */
    private Integer appId;

    /**
     *  用户id
     */
    private Integer userId;

    /**
     *  FCM客户端token(测试长度152)
     */
    private String clientToken;

    /**
     *  手机操作系统类型。 2--iOS； 3--ANDROID;
     */
    private Integer phoneType;

    /**
     *  状态，0--失效，1--有效
     */
    private Integer status;

    /**
     *  创建时间
     */
    private Date createTime;

    /**
     *  更新时间
     */
    private Date modifyTime;

}