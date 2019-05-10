package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * create by shenjiankang on 2018/7/6 11:46
 */
@Data
public class AppPushHistoryOutputBO implements Serializable {

    private static final long serialVersionUID = 4743964999929664382L;
    /**
     *  主键id
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
    /**
     *  创建时间
     */
    private Date createTime;
    /**
     *  更新时间
     */
    private Date modifyTime;
    /**
     *  发送时间（时间戳的形式）
     */
    private String sendTime;
}
