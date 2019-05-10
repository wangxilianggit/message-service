package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * create by shenjiankang on 24/07/2018 11:40
 */
@Data
public class ApplicationsOutputBO implements Serializable {

    private static final long serialVersionUID = 6867217626101011988L;
    /**
     *  主键id
     */
    private Integer id;
    /**
     *  app应用id
     */
    private Integer appId;
    /**
     *  app应用名称
     */
    private String appName;
    /**
     *  false--失效，true--有效
     */
    private Boolean status;
    /**
     *  创建时间
     */
    private Date createTime;
}
