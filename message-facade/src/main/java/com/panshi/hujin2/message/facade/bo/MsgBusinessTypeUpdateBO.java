package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * create by shenjiankang on 2018/6/28 20:48
 */
@Data
public class MsgBusinessTypeUpdateBO implements Serializable {
    private static final long serialVersionUID = -666040857449325393L;

    /**
     *  主键id
     */
    private Integer id;
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
