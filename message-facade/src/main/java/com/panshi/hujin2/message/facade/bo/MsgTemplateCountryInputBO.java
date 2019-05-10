package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * create by shenjiankang on 2018/6/30 15:22
 */
@Data
public class MsgTemplateCountryInputBO implements Serializable {
    private static final long serialVersionUID = -4185738620285292824L;


    /**
     *  短信模板编号（首次创建模板不用填，自动生成。在已有模板的情况下新增国际化，需要传）
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

}
