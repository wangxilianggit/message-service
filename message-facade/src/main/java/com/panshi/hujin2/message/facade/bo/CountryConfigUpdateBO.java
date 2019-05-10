package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * create by shenjiankang on 2018/6/30 17:10
 */
@Data
public class CountryConfigUpdateBO implements Serializable {
    private static final long serialVersionUID = -7704016674420698360L;

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
     *  修改人
     */
    private String modifier;

}
