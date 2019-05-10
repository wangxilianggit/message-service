package com.panshi.hujin2.message.domain.qo;

import lombok.Data;

/**
 * create by shenjiankang on 2018/6/27 15:23
 */
@Data
public class EmailBusinessTypeQO extends BaseQO{

    /**
     *  大类id
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
