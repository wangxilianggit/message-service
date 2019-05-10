package com.panshi.hujin2.message.domain.qo;

import lombok.Data;

/**
 * create by shenjiankang on 2018/6/30 17:15
 */
@Data
public class CountryConfigQO extends BaseQO{
    /**
     *  主键id
     */
    private Integer id;
    /**
     *  国家key
     */
    private String countryKey;
    /**
     *  国家名
     */
    private String countryName;
    /**
     *  状态，0--失效，1--有效
     */
    private Boolean status;
    /**
     *  创建人
     */
    private String creator;
    /**
     *  修改人
     */
    private String modifier;
}
