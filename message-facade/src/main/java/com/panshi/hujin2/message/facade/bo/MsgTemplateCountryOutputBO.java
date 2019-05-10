package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * create by shenjiankang on 2018/6/30 15:24
 */
@Data
public class MsgTemplateCountryOutputBO implements Serializable {
    private static final long serialVersionUID = -268181480424926258L;

    /**
     *  主键id
     */
    private Integer id;
    /**
     *  短信模板编号
     */
    private String templateCode;
    /**
     *  国家key
     */
    private String countryKey;
    /**
     *  国家名
     */
    private String countryName;
    /**
     *  短信内容
     */
    private String msgText;
    /**
     *  状态，0--失效，1--有效
     */
    private Boolean status;
    /**
     *  创建人
     */
    private String creator;
    /**
     *  创建时间
     */
    private Date createTime;
    /**
     *  修改人
     */
    private String modifier;
    /**
     *  修改时间
     */
    private Date modifyTime;
}
