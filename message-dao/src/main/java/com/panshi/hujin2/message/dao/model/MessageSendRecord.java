package com.panshi.hujin2.message.dao.model;

import java.util.Date;

public class MessageSendRecord {
    /**
     *  主键id
     */
    private Long id;
    /**
     *  应用id 如：（1.信用卡管家，）
     */
    private Integer appId;
    /**
     *  国家编号
     */
    private Integer countryId;
    /**
     *  渠道id（具体发送短信的渠道）
     */
    private Integer channelId;
    /**
     *  手机号码
     */
    private String phoneNumber;
    /**
     *  短信内容
     */
    private String msgText;
    /**
     *  消息id
     */
    private String msgId;
    /**
     *  响应状态码
     */
    private Integer resCode;
    /**
     *  状态码说明
     */
    private String resExplain;
    /**
     *  请求短信发送接口的返回信息
     */
    private String returnValue;
    /**
     *  创建时间
     */
    private Date createTime;
    /**
     *  修改时间
     */
    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText == null ? null : msgText.trim();
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId == null ? null : msgId.trim();
    }

    public Integer getResCode() {
        return resCode;
    }

    public void setResCode(Integer resCode) {
        this.resCode = resCode;
    }

    public String getResExplain() {
        return resExplain;
    }

    public void setResExplain(String resExplain) {
        this.resExplain = resExplain == null ? null : resExplain.trim();
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue == null ? null : returnValue.trim();
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