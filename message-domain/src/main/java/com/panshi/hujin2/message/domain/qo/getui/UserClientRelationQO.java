package com.panshi.hujin2.message.domain.qo.getui;
import com.panshi.hujin2.base.domain.qo.BaseQO;
import lombok.Data;

/**
 * create by shenjiankang on 2018/6/26 18:12
 */
@Data
public class UserClientRelationQO extends BaseQO {

    /**
     *  主鍵id
     */
    private Integer id;
    /**
     *  用户id
     */
    private Integer userId;
    /**
     *  个推业务层中的对外用户标识，用于标识客户端身份，由第三方客户端获取并保存到第三方服务端，是个推SDK的唯一识别号。
     */
    private String clientId;
    /**
     *  手机操作系统类型。 2--iOS； 3--ANDROID;
     */
    private Integer phoneType;
    /**
     *  状态，0--失效，1--有效
     */
    private Boolean status;



}
