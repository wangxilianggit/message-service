package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * create by shenjiankang on 2018/6/27 15:13
 */
@Data
public class AppPushBusinessTypeInputBO implements Serializable {
    private static final long serialVersionUID = 1462446597914894099L;

    /**
     *  app应用id
     */
    private Integer appId;
    /**
     *  对应大类id
     */
    private Integer parentId;
    /**
     *  描述
     */
    private String description;
    /**
     *  状态，0--失效，1--有效
     */
    private Boolean status;

}
