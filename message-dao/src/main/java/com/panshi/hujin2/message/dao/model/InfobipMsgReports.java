package com.panshi.hujin2.message.dao.model;

import java.util.Date;

public class InfobipMsgReports {
    /**
     *  id
     */
    private Integer id;
    /**
     *  唯一标识已发送请
     */
    private String bulkId;
    /**
     *  手机号码
     */
    private String phoneNumber;
    /**
     *  消息ID
     */
    private String messageId;
    /**
     *
     */
    private String sentAt;
    /**
     *
     */
    private String doneAt;
    /**
     *  消息数量
     */
    private Integer smsCount;
    /**
     *  短信每条单价
     */
    private String pricePerMessage;
    /**
     *  货币
     */
    private String currency;
    /**
     *  自定义的数据
     */
    private String callbackData;
    /**
     *  发送状态id，详细见infobip文档
     */
    private Integer statusGroupId;
    /**
     *
     */
    private String statusGroupName;
    /**
     *
     */
    private Integer statusId;
    /**
     *
     */
    private String statusName;
    /**
     *
     */
    private String statusDescription;
    /**
     *  错误id，详细见infobip文档
     */
    private Integer errorGroupId;
    /**
     *
     */
    private String errorGroupName;
    /**
     *
     */
    private Integer errorId;
    /**
     *
     */
    private String errorName;
    /**
     *
     */
    private String errorDescription;
    /**
     *
     */
    private Boolean errorPermanent;
    /**
     *  创建时间
     */
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBulkId() {
        return bulkId;
    }

    public void setBulkId(String bulkId) {
        this.bulkId = bulkId == null ? null : bulkId.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId == null ? null : messageId.trim();
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt == null ? null : sentAt.trim();
    }

    public String getDoneAt() {
        return doneAt;
    }

    public void setDoneAt(String doneAt) {
        this.doneAt = doneAt == null ? null : doneAt.trim();
    }

    public Integer getSmsCount() {
        return smsCount;
    }

    public void setSmsCount(Integer smsCount) {
        this.smsCount = smsCount;
    }

    public String getPricePerMessage() {
        return pricePerMessage;
    }

    public void setPricePerMessage(String pricePerMessage) {
        this.pricePerMessage = pricePerMessage == null ? null : pricePerMessage.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getCallbackData() {
        return callbackData;
    }

    public void setCallbackData(String callbackData) {
        this.callbackData = callbackData == null ? null : callbackData.trim();
    }

    public Integer getStatusGroupId() {
        return statusGroupId;
    }

    public void setStatusGroupId(Integer statusGroupId) {
        this.statusGroupId = statusGroupId;
    }

    public String getStatusGroupName() {
        return statusGroupName;
    }

    public void setStatusGroupName(String statusGroupName) {
        this.statusGroupName = statusGroupName == null ? null : statusGroupName.trim();
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName == null ? null : statusName.trim();
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription == null ? null : statusDescription.trim();
    }

    public Integer getErrorGroupId() {
        return errorGroupId;
    }

    public void setErrorGroupId(Integer errorGroupId) {
        this.errorGroupId = errorGroupId;
    }

    public String getErrorGroupName() {
        return errorGroupName;
    }

    public void setErrorGroupName(String errorGroupName) {
        this.errorGroupName = errorGroupName == null ? null : errorGroupName.trim();
    }

    public Integer getErrorId() {
        return errorId;
    }

    public void setErrorId(Integer errorId) {
        this.errorId = errorId;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName == null ? null : errorName.trim();
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription == null ? null : errorDescription.trim();
    }

    public Boolean getErrorPermanent() {
        return errorPermanent;
    }

    public void setErrorPermanent(Boolean errorPermanent) {
        this.errorPermanent = errorPermanent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}