package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * create by shenjiankang on 2018/8/4 10:14
 */
@Data
public class BatchSendDiffTemplateParamBO implements Serializable {
    private static final long serialVersionUID = -5424697904375031358L;

    /**
     *  手機號
     */
    private String phoneNumber;
    /**
     *  模板編號
     */
    private String templateCode;
    /**
     *  模板需要替換的參數列表
     */
    private List paramList;
}
