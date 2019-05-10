package com.panshi.hujin2.message.domain.qo.getui;

import com.panshi.hujin2.message.domain.qo.BaseQO;
import lombok.Data;

import java.util.Date;

/**
 * create by shenjiankang on 2018/7/3 21:22
 */
@Data
public class AppPushTemplateCountryQO extends BaseQO {

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
     *  通知栏标题
     */
    private String title;
    /**
     *  通知栏内容
     */
    private String text;
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
