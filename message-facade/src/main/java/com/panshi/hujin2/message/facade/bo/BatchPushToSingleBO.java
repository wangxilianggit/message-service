package com.panshi.hujin2.message.facade.bo;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * create by shenjiankang on 2018/10/10 22:10
 */
@Data
public class BatchPushToSingleBO implements Serializable {

    private static final long serialVersionUID = -6447309280552110554L;

    /**
     *  推送模板需要替换的参数列表
     */
    private List list;

    /**
     *  app应用
     */
    private ApplicationEnmu appEnum;
}
