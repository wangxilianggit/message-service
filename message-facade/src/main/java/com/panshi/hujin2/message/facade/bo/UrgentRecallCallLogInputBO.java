package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * create by shenjiankang on 2019/1/30 17:58
 */
@Data
public class UrgentRecallCallLogInputBO implements Serializable {
    private static final long serialVersionUID = -2684551520610463376L;


    /**
     *  自定义拓展id（暂定orderId）
     */
    private String extendId;

    /**
     *  呼叫开始时间 格式为：yyyy-MM-dd HH:mm:ss
     */
    private String startTime;

    /**
     *  呼叫结束时间 格式为：yyyy-MM-dd HH:mm:ss
     */
    private String endTime;

    /**
     *  计费时长，单位秒
     */
    private Integer feeTime;

    /**
     *  挂机方向 0：主叫挂机 1：被叫挂机
     */
    private Integer endDirection;

    /**
     *  挂机原因:0-被叫号码有振铃;404-空号;-80-暂时无法接通
     */
    private Integer endReason;

    /**
     *  录音文件
     */
    private String recodingurl;

    /**
     *  主叫号码
     */
    private String caller;

    /**
     *  被叫号码
     */
    private String callee;

}
