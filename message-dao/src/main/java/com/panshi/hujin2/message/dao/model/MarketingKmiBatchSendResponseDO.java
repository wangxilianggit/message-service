package com.panshi.hujin2.message.dao.model;

import java.util.Date;

public class MarketingKmiBatchSendResponseDO {
    private Integer id;

    private Integer marketingSmsTaskRecordPrimaryKey;

    private Date createTime;

    private String responseInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMarketingSmsTaskRecordPrimaryKey() {
        return marketingSmsTaskRecordPrimaryKey;
    }

    public void setMarketingSmsTaskRecordPrimaryKey(Integer marketingSmsTaskRecordPrimaryKey) {
        this.marketingSmsTaskRecordPrimaryKey = marketingSmsTaskRecordPrimaryKey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getResponseInfo() {
        return responseInfo;
    }

    public void setResponseInfo(String responseInfo) {
        this.responseInfo = responseInfo == null ? null : responseInfo.trim();
    }
}