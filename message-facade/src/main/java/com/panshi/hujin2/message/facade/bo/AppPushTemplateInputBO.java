package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * create by shenjiankang on 2018/6/27 17:00
 */
@Data
public class AppPushTemplateInputBO implements Serializable {
    private static final long serialVersionUID = 4764209017980332902L;

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
     */
    private Date pushTime;
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
     * 国际化模板表参数
     */
    List<AppPushTemplateCountryInputBO> templateCountryInputBOS;


}
