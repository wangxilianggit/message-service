package com.panshi.hujin2.message.dao.model;

import java.util.Date;

public class SubmailMsgSubhook {
    /**
     *  主键id
     */
    private Long id;
    /**
     *  事件类型
     */
    private String events;
    /**
     *  此联系人的手机号码
     */
    private String phoneNumber;
    /**
     *  应用ID
     */
    private String app;
    /**
     *  该条短信的唯一发送标识，可在 API 请求时获取
     */
    private String sendId;
    /**
     *  事件触发时间（此时间戳为此事件本身的触发时间，不参与计算签名）
     */
    private Integer eventTimestamp;
    /**
     *  网关失败回执
     */
    private String report;
    /**
     *  短信内容(只有在事件是''短信上行接口推送mo''时才会有该字段)
     */
    private String content;
    /**
     *  模板ID（只有在事件是''审核模板''时才有该字段）
     */
    private String templateId;
    /**
     *  审核未通过原因(只有在事件是''审核模板不通过''时才有该字段)
     */
    private String reason;
    /**
     *  创建时间
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events == null ? null : events.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app == null ? null : app.trim();
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId == null ? null : sendId.trim();
    }

    public Integer getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(Integer eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report == null ? null : report.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}