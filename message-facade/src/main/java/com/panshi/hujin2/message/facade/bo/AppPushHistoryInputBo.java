package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * create by shenjiankang on 2018/6/27 10:42
 */
@Data
public class AppPushHistoryInputBo implements Serializable {
    private static final long serialVersionUID = -8231598333888717425L;

    /**
     *  app应用id
     */
    private Integer appId;
    /**
     *  用户id
     */
    private Integer userId;
    /**
     *  所属的业务类型id
     */
    private Integer businessTypeId;
    /**
     *  通知栏标题
     */
    private String title;
    /**
     *  通知栏内容
     */
    private String text;
    /**
     *  0--未读；1--已读
     */
    private Boolean status;

}
