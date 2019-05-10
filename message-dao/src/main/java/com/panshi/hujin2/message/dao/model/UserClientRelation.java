package com.panshi.hujin2.message.dao.model;

import java.util.Date;

public class UserClientRelation {
    /**
     *  主鍵id
     */
    private Integer id;
    /**
     *  app应用id
     */
    private Integer appId;
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
    /**
     *  创建时间
     */
    private Date createTime;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    public Integer getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(Integer phoneType) {
        this.phoneType = phoneType;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}