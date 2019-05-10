package com.panshi.hujin2.message.domain.qo.getui;

import com.panshi.hujin2.message.domain.qo.BaseQO;
import lombok.Data;

import java.util.Date;

/**
 * create by shenjiankang on 2018/6/26 19:54
 */
@Data
public class AppPushTemplateQO extends BaseQO {

    /**
     *  主键id
     */
    private Integer id;
    /**
     *  app应用id
     */
    private Integer appId;
    /**
     *  消息模板的編號
     */
    private String templateCode;
    /**
     *  消息对应的业务类型，关联app_push_business_type主键
     */
    private Integer businessTypeId;
    /**
     *  通知栏标题
     */
    private String title;
    /**
     *  通知栏内容
     */
    private String text;
    /**
     *  备注
     */
    private String remark;
    /**
     *  推送设备 0-全部  2-ios; 3-andriod;
     */
    private Integer pushEquipment;
    /**
     *  推送时间，为空表示即时推送，有时间则按时间
     *  （起始）
     */
    private Date pushTimeMin;
    /**
     *  推送时间，为空表示即时推送，有时间则按时间
     *  （结束）
     */
    private Date pushTimeMax;
    /**
     *  推送状态：0-禁用，1-启用
     */
    private Boolean pushStatus;
    /**
     *  图形路径
     */
    private String imgPath;
    /**
     *  外链URL
     */
    private String linkUrl;
    /**
     *  推送区域
     */
    private String pushRegion;
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
