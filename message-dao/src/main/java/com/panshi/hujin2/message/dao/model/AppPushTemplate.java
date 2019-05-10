package com.panshi.hujin2.message.dao.model;

import java.util.Date;

public class AppPushTemplate {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode == null ? null : templateCode.trim();
    }

    public Integer getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(Integer businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getPushEquipment() {
        return pushEquipment;
    }

    public void setPushEquipment(Integer pushEquipment) {
        this.pushEquipment = pushEquipment;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Boolean getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Boolean pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath == null ? null : imgPath.trim();
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl == null ? null : linkUrl.trim();
    }

    public String getPushRegion() {
        return pushRegion;
    }

    public void setPushRegion(String pushRegion) {
        this.pushRegion = pushRegion == null ? null : pushRegion.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}